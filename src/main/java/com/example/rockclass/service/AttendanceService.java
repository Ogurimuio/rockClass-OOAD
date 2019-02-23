package com.example.rockclass.service;

import com.example.rockclass.dao.*;
import com.example.rockclass.entity.Attendance;
import com.example.rockclass.entity.KlassSeminar;
import com.example.rockclass.entity.Question;
import com.example.rockclass.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    @Value("${file.uploadFolder}")
    private String UPLOAD_FOLDER;

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private KlassSeminarDao klassSeminarDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeamDao teamDao;

    public Attendance selectByAttendanceId(Long attendanceId) {
        return attendanceDao.selectByPrimaryKey(attendanceId);
    }

    public void deleteEnroll(Long attendanceId) {
        attendanceDao.deleteByPrimaryKey(attendanceId);
    }


    public int uploadPPT(MultipartFile file, String url, Long attendanceId) {
        Attendance attendance = attendanceDao.selectByPrimaryKey(attendanceId);
        int status=fileService.upload(file,url);
        if(status==200) {
            attendance.setPptUrl(url+ file.getOriginalFilename());
            attendance.setPptName(file.getOriginalFilename());
            attendanceDao.updateByPrimaryKey(attendance);
        }
        return status;
    }

    public int uploadReport(MultipartFile file, String url, Long attendanceId) {
        Attendance attendance = attendanceDao.selectByPrimaryKey(attendanceId);
        int status=fileService.upload(file,url);
        if(status==200) {
            attendance.setReportUrl(url + file.getOriginalFilename());
            attendance.setReportName(file.getOriginalFilename());
            attendanceDao.updateByPrimaryKey(attendance);
        }
        return status;
    }

    public int updatePPT(MultipartFile file, String url, Long attendanceId) {
        Attendance attendance = attendanceDao.selectByPrimaryKey(attendanceId);
        int status=fileService.update(file,url,attendance.getPptUrl());
        if(status==200) {
            attendance.setPptUrl(url + file.getOriginalFilename());
            attendance.setPptName(file.getOriginalFilename());
            attendanceDao.updateByPrimaryKey(attendance);
        }
        return status;
    }

    public int updateReport(MultipartFile file, String url,Long attendanceId) {
        Attendance attendance = attendanceDao.selectByPrimaryKey(attendanceId);
        int status=fileService.update(file,url,attendance.getReportUrl());
        if(status==200) {
            attendance.setReportUrl(url + file.getOriginalFilename());
            attendance.setReportName(file.getOriginalFilename());
            attendanceDao.updateByPrimaryKey(attendance);
        }
        return status;
    }

    public List<Attendance> getReportList(Long seminarId, Long classId) {
        KlassSeminar klassSeminar = klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
        if (klassSeminar==null){
            return null;
        }
        return attendanceDao.selectByKlassSeminarId(klassSeminar.getId());
    }

    public Long postQuestion(Long klassSeminarId, Long attendanceId, Long teamId, Long studentId) {
        Question question = new Question();
        question.setKlassSeminar(klassSeminarDao.selectByPrimaryKey(klassSeminarId));
        question.setAttendance(attendanceDao.selectByPrimaryKey(attendanceId));
        question.setStudent(studentDao.selectByStudentId(studentId));
        question.setTeam(teamDao.selectByPrimaryKey(teamId));
        question.setIsSelected((byte)0);
        Long questionId = questionDao.insert(question);
        return questionId;

    }

    public Question selectQuestion(Long attendanceId) {
        List<Question> questions=questionDao.selectByAttendanceId(attendanceId);
        if(questions==null){
            return null;
        }
        Question question=new Question();
        for(Question question0:questions){
            if(question0.getIsSelected().equals((byte)0)){
                question.setId(question0.getId());
                question.setKlassSeminar(question0.getKlassSeminar());
                question.setAttendance(question0.getAttendance());
                question.setStudent(question0.getStudent());
                question.setTeam(question0.getTeam());
                question.setIsSelected((byte)1);
                questionDao.updateByPrimaryKey(question);
                break;
            }
        }
        return question;
    }
    public List<QuestionVO> questionList(Long attendanceId) {
        List<Question> questions=questionDao.selectByAttendanceId(attendanceId);
        if(questions==null){
            return null;
        }
        List<QuestionVO> questionVOS=new ArrayList<QuestionVO>();
        for(Question question0:questions){
            if(question0.getIsSelected().equals((byte)0)){
                QuestionVO questionVO = new QuestionVO();
                questionVO.setQuestionId(question0.getId());
                questionVO.setTeamSerial(question0.getKlassSeminar().getKlass().getKlassSerial().toString()+"-"+question0.getTeam().getTeamSerial().toString());
                questionVO.setTeamName(question0.getTeam().getTeamName());

                questionVOS.add(questionVO);
            }
        }
        return questionVOS;
    }

}
