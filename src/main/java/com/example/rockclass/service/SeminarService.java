package com.example.rockclass.service;

import com.example.rockclass.dao.*;
import com.example.rockclass.entity.*;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.mapper.SeminarMapper;
import com.example.rockclass.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SeminarService {
    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private KlassStudentDao klassStudentDao;

    @Autowired
    private KlassSeminarDao klassSeminarDao;

    @Autowired
    private KlassDao klassDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeamStudentDao teamStudentDao;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    @Autowired
    private  KlassRoundDao klassRoundDao;
    public List<Seminar> selectSeminarByRoundId(Long roundId){
        return  seminarDao.selectSeminarByRoundId(roundId);
    }

    public Seminar selectSeminarById(Long seminarId){
        return  seminarDao.selectByPrimaryKey(seminarId);
    }

    public Long createSeminar(SeminarVO seminarVO) throws CourseNotFoundException {
        Seminar seminar = new Seminar();
        seminar.setCourse(courseDao.selectCourseById(seminarVO.getCourseId()));
        Round round = roundDao.selectRoundByCourseIdAndRoundSerial(seminarVO.getCourseId(),seminarVO.getRoundSerial());
        List<Klass> klasses=klassDao.selectKlassByCourseId(seminarVO.getCourseId());
        if(round==null){
            round = new Round();
            round.setRoundSerial(seminarVO.getRoundSerial());
            round.setCourse(courseDao.selectCourseById(seminarVO.getCourseId()));
            round.setPresentationScoreMethod((byte)0);
            round.setQuestionScoreMethod((byte)0);
            round.setReportScoreMethod((byte)0);
            Long roundId=roundDao.insert(round);
            seminar.setRound(roundDao.selectByPrimaryKey(roundId));
            //创建对应class round记录
            for(Klass klass:klasses){
                KlassRound klassRound = new KlassRound();
                klassRound.setKlass(klass);
                klassRound.setRound(roundDao.selectByPrimaryKey(roundId));
                klassRoundDao.insert(klassRound);
            }

        }
        else {
            seminar.setRound(roundDao.selectByPrimaryKey(round.getId()));
        }
        seminar.setSeminarName(seminarVO.getSeminarName());
        seminar.setIntroduction(seminarVO.getIntroduction());
        seminar.setSeminarSerial((byte)(seminarDao.selectByCourseId(seminarVO.getCourseId()).size()));
        seminar.setIsVisible(seminarVO.getIsVisible());
        seminar.setMaxTeam(seminarVO.getMaxTeam());
        seminar.setEnrollStartTime(seminarVO.getEnrollStartTime());
        seminar.setEnrollEndTime(seminarVO.getEnrollEndTime());
        Long seminarId=seminarDao.insert(seminar);
        //创建班级讨论课表


        for (Klass k : klasses) {
            KlassSeminar klassSeminar = new KlassSeminar();
            klassSeminar.setKlass(k);
            klassSeminar.setSeminar(seminarDao.selectByPrimaryKey(seminarId));
            klassSeminar.setStatus((byte)0);
            klassSeminarDao.insert(klassSeminar);
        }

        return seminarId;
    }


    public List<SeminarClassVO> getSeminarClassByCourse(Course course) {
        List<Klass> klasses=klassDao.selectKlassByCourseId(course.getId());
        List<SeminarClassVO> seminarClassVOS=new ArrayList<SeminarClassVO>();

        for (Klass klass : klasses) {
            SeminarClassVO seminarClassVO=new SeminarClassVO();
            seminarClassVO.setId(klass.getId());
            seminarClassVO.setName(klass.getGrade().toString()+"-("+klass.getKlassSerial().toString()+")");
            seminarClassVOS.add(seminarClassVO);
        }
        return seminarClassVOS;
    }

    public int updateSeminar(Long seminarId, SeminarVO seminarVO) {
        Seminar seminar = seminarDao.selectByPrimaryKey(seminarId);

        Long roundId=roundDao.selectRoundByCourseIdAndRoundSerial(seminarVO.getCourseId(),seminarVO.getRoundSerial()).getId();
        seminar.setRound(roundDao.selectByPrimaryKey(roundId));
        seminar.setSeminarSerial(seminarVO.getSeminarSerial());
        seminar.setSeminarName(seminarVO.getSeminarName());
        seminar.setIntroduction(seminarVO.getIntroduction());
        seminar.setIsVisible(seminarVO.getIsVisible());
        seminar.setMaxTeam(seminarVO.getMaxTeam());
        seminar.setEnrollStartTime(seminarVO.getEnrollStartTime());
        seminar.setEnrollEndTime(seminarVO.getEnrollEndTime());
        return seminarDao.updateByPrimaryKey(seminar);
    }

    public int deleteSeminar(Long seminarId) {
        return seminarDao.deleteByPrimaryKey(seminarId);
    }

    public int setReportDDL(Long seminarId, Long classId, Date reportDDL) {

        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        klassSeminar.setStatus((byte)2);
        klassSeminar.setReportDdl(reportDDL);

        return klassSeminarDao.updateByPrimaryKey(klassSeminar);
    }

    public KlassSeminarVO getClassSeminar(Long classId, Long seminarId) {
        Seminar seminar = seminarDao.selectByPrimaryKey(seminarId);
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        KlassSeminarVO klassSeminarVO = new KlassSeminarVO();
        klassSeminarVO.setId(seminarId);
        klassSeminarVO.setSeminarName(seminar.getSeminarName());
        klassSeminarVO.setIntroduction(seminar.getIntroduction());
        klassSeminarVO.setStatus(klassSeminar.getStatus());
        klassSeminarVO.setSeminarSerial(seminar.getSeminarSerial());
        klassSeminarVO.setMaxTeam(seminar.getMaxTeam());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(klassSeminar.getReportDdl()!=null) {
            klassSeminarVO.setReportDDL(format.format(klassSeminar.getReportDdl()));
        }


        klassSeminarVO.setEnrollStartTime(format.format(seminar.getEnrollStartTime()));
        klassSeminarVO.setEnrollEndTime(format.format(seminar.getEnrollEndTime()));
        return klassSeminarVO;
    }

    public void setSeminarStatus(Long seminarId, Long classId, Byte status) {
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        klassSeminar.setStatus(status);
        klassSeminarDao.updateByPrimaryKey(klassSeminar);
    }

    public Long enrollSeminar(Long seminarId, Long classId, TeamOrderVO teamOrderVO) {
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        Long roundId=seminarDao.selectByPrimaryKey(seminarId).getRound().getId();
        int enrollNum =(int) klassRoundDao.selectByPrimaryKey(classId,roundId).getEnrollNumber();

        List<Seminar> seminars = seminarDao.selectSeminarByRoundId(roundId);
        int enrollSum=0;
        //List<KlassSeminar> klassSeminars = new ArrayList<KlassSeminar>();
        for(Seminar seminar:seminars){
            if(klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminar.getId())!=null){

                List<Attendance> attendanceList = attendanceDao.selectByKlassSeminarId(klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminar.getId()).getId());
                for (Attendance attendance:attendanceList){
                    if(attendance.getTeam().getId().equals(teamOrderVO.getTeamId())){
                        enrollSum=enrollSum+1;
                    }
                }
            }
        }

        if(enrollSum<enrollNum) {
            Attendance attendance = new Attendance();
            attendance.setKlassSeminar(klassSeminar);
            attendance.setIsPresent((byte) 0);
            attendance.setTeamOrder(teamOrderVO.getTeamOrder());
            //System.out.println(teamOrderVO.getTeamId()+"  "+teamOrderVO.getTeamOrder());
            attendance.setTeam(teamDao.selectByPrimaryKey(teamOrderVO.getTeamId()));
            Long attendanceId = attendanceDao.insert(attendance);
            return attendanceId;
        }
        else return (long)0;
    }

    public List<TeamAttendanceVO> getSeminarEnroll(Long seminarId, Long classId) {

        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        List<Attendance> attendanceList=attendanceDao.selectByKlassSeminarId(klassSeminar.getId());
        List<TeamAttendanceVO> teamAttendanceVOS = new ArrayList<TeamAttendanceVO>();
        for(Attendance attendance:attendanceList){

            TeamAttendanceVO teamAttendanceVO = new TeamAttendanceVO();
            teamAttendanceVO.setAttendanceId(attendance.getId());
            teamAttendanceVO.setTeamId(attendance.getTeam().getId());
            teamAttendanceVO.setSerial(attendance.getKlassSeminar().getKlass().getKlassSerial().toString()+"-"+attendance.getTeam().getTeamSerial().toString());
            teamAttendanceVO.setLeader(attendance.getTeam().getLeader().getStudentName());
            teamAttendanceVO.setTeamOrder(attendance.getTeamOrder());
            teamAttendanceVO.setClassId(attendance.getKlassSeminar().getKlass().getId());
            teamAttendanceVO.setPresent(attendance.getIsPresent());
            teamAttendanceVO.setPptName(attendance.getPptName());
            teamAttendanceVO.setPptUrl(attendance.getPptUrl());
            teamAttendanceVO.setReportName(attendance.getReportName());
            teamAttendanceVO.setReportUrl(attendance.getReportUrl());
            teamAttendanceVOS.add(teamAttendanceVO);
        }
        return teamAttendanceVOS;
    }

    public List<TeamAttendanceVO> seminarStart(Long seminarId, Long classId, Byte order) {
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        List<Attendance> attendanceList=attendanceDao.selectByKlassSeminarId(klassSeminar.getId());
        for(int i=1;i<=attendanceList.size();i++)
        {
            Attendance attendance=attendanceList.get(i-1);
            if(order.equals((byte)1)){
                if(attendance.getTeamOrder().equals(order)){
                    attendance.setIsPresent((byte)1);
                    attendanceDao.updateByPrimaryKey(attendance);
                }
            }else {
                if(attendance.getTeamOrder().equals(order)){
                    attendance.setIsPresent((byte)1);
                    attendanceDao.updateByPrimaryKey(attendance);
                    attendanceList.get(i-2).setIsPresent((byte)0);
                    attendanceDao.updateByPrimaryKey(attendanceList.get(i-2));

                }
            }
        }
        List<TeamAttendanceVO> teamAttendanceVOS = new ArrayList<TeamAttendanceVO>();
        for(Attendance attendance:attendanceList){

            TeamAttendanceVO teamAttendanceVO = new TeamAttendanceVO();
            teamAttendanceVO.setAttendanceId(attendance.getId());
            teamAttendanceVO.setTeamId(attendance.getTeam().getId());
            teamAttendanceVO.setSerial(attendance.getKlassSeminar().getKlass().getKlassSerial().toString()+"-"+attendance.getTeam().getTeamSerial().toString());
            teamAttendanceVO.setTeamOrder(attendance.getTeamOrder());
            teamAttendanceVO.setClassId(attendance.getKlassSeminar().getKlass().getId());
            teamAttendanceVO.setPresent(attendance.getIsPresent());
            teamAttendanceVO.setPptName(attendance.getPptName());
            teamAttendanceVO.setPptUrl(attendance.getPptUrl());
            teamAttendanceVO.setReportName(attendance.getReportName());
            teamAttendanceVO.setReportUrl(attendance.getReportUrl());
            teamAttendanceVOS.add(teamAttendanceVO);
        }
        return teamAttendanceVOS;
    }

    //pengshuyuan
    public SeminarScore selectSeminarScoreBySeminarIdAndTeamId(Long seminarId,Long teamId)
    {
        Team team = teamDao.selectByPrimaryKey(teamId);
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(team.getKlass().getId(),seminarId);
        SeminarScore seminarScore = seminarScoreDao.selectByPrimaryKey(klassSeminar.getId(),teamId);
        return  seminarScore;
    }



    public List<SeminarScore> selectSeminarScoreBySeminarIdAndKlassId(Long seminarId,Long klassId)
    {
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(klassId,seminarId);
        List<Attendance> attendances = attendanceDao.selectByKlassSeminarId(klassSeminar.getId());

        List<SeminarScore> seminarScores  = new ArrayList<SeminarScore>();
        for (Attendance attendance:attendances){

            SeminarScore seminarScore = seminarScoreDao.selectByPrimaryKey(klassSeminar.getId(),attendance.getTeam().getId());
            seminarScores.add(seminarScore);
        }
        return  seminarScores;
    }

    public void insertSeminarScore(SeminarScore seminarScore)
    {
        seminarScoreDao.insert(seminarScore);
    }

    public void updateSeminarScore(SeminarScore seminarScore)
    {
        seminarScoreDao.updateByPrimaryKey(seminarScore);
    }


}
