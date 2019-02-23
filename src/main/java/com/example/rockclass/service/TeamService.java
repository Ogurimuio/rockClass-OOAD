package com.example.rockclass.service;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.dao.*;
import com.example.rockclass.entity.*;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.mapper.KlassStudentMapper;
import com.example.rockclass.vo.CourseMemberLimitIsConflictVo;
import com.example.rockclass.vo.CreatTeamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamDao teamDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeamStudentDao teamStudentDao;

    @Autowired
    private KlassStudentDao klassStudentDao;

    @Autowired
    private  KlassDao klassDao;

    @Autowired
    private KlassTeamDao klassTeamDao;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private TeamValidApplicationDao teamValidApplicationDao;

    @Autowired
    private CourseDao courseDao;


    @Autowired
    private ConflictCourseStrategyDao conflictCourseStrategyDao;



    public List<Team> selectTeamByCourseId(Long courseId){
        List<Team> teams= teamDao.selectByCourseId(courseId);
        if (teams==null) return null;
        else {
            Collections.sort(teams, new Comparator<Team>() {
                @Override
                public int compare(Team x, Team y) {
                    {
                        if (x == null)
                        {
                            if (y == null)
                            {
                                return 0;
                            }
                            return 1;
                        }
                        if (y == null)
                        {
                            return -1;
                        }
                        if(x.getKlassSerial()<y.getKlassSerial())return -1;
                        else if(x.getKlassSerial()>y.getKlassSerial()) return  1;
                        else if (x.getTeamSerial()<y.getTeamSerial())return -1;
                        else if (x.getTeamSerial()>y.getTeamSerial()) return 1;
                        return 0;
                    }
                }
            });
           return teams;
        }
    }
    public List<Team> selectTeamBySubCourseId(Long subCourseId){
        List<Klass> klasses = klassDao.selectKlassByCourseId(subCourseId);
        List<KlassTeam> klassTeams = new ArrayList<KlassTeam>();
        List<Team> teams = new ArrayList<Team>();
        for (Klass klass:klasses)
        { klassTeams.addAll(klassTeamDao.selectByKlassId(klass.getId()));
        }
        for (KlassTeam klassTeam:klassTeams)
        {
            teams.add(klassTeam.getTeam());
        }
            return teams;
    }
    public  Team selectTeamByCourseIdAndStudentAccount(Long courseId,String studentAccount)
    {
        Student student=studentDao.selectByAccount(studentAccount);
        KlassStudent klassStudent= teamDao.selectKlassStudentByCourseIdAndStudentId(courseId,student.getId());
        List<TeamStudent> teamStudents = teamStudentDao.selectByStudentId(student.getId());
        int i=0;
        for ( i=0;i<teamStudents.size();i++)
        {
            if (teamStudents.get(i).getTeam().getCourse().getId().equals(courseId))
                break;
        }
        if(i>=teamStudents.size()) return null;
        return teamStudents.get(i).getTeam();
    }
    public Team selectTeamByTeamId(Long teamId){

        return teamDao.selectByPrimaryKey(teamId);
    }


    public List<Student> selectMemberByTeam(Team team){
        List<Student>students=new ArrayList<Student>();
        List<TeamStudent> teamStudents = teamStudentDao.selectByTeamId(team.getId());
        for(int i=0;i<teamStudents.size();i++)
        {
            students.add(teamStudents.get(i).getStudent());
        }
        return  students;
    }
    public  List<Student> selectNoTeamStudentByCourseId(Long courseId){
        List<KlassStudent> klassStudents =teamDao.selectKlassStudentByCourseId(courseId);
        List<Student> students = new ArrayList<Student>();

        for(int i=0;i<klassStudents.size();i++) {
            int flag=0;
            List<TeamStudent> teamStudents = teamStudentDao.selectByStudentId(klassStudents.get(i).getStudent().getId());
            if (teamStudents.size()==0) students.add(klassStudents.get(i).getStudent());
            else{
                for (int j = 0; j < teamStudents.size(); j++)
                {
                    if(teamStudents.get(j).getTeam().getCourse().getId().equals(courseId)) {flag=1;break;}
                }
                if (flag==0 ) students.add(teamStudents.get(0).getStudent());
            }
        }
        return  students;
    }

    public void deleteTeamByTeamId(Long teamId)
    { teamDao.deleteByPrimaryKey(teamId);
        teamStudentDao.deleteByTeamId(teamId);}

    public int addTeamMember(Long studentId,String myAccount,Long teamId){
        Student student=studentDao.selectByAccount(myAccount);
        List<TeamStudent> teamStudents=teamStudentDao.selectByTeamId(teamId);
        if(teamStudents==null) return 404;
        int flag=0;
        int i=0;
        for(i=0;i<teamStudents.size();i++)
        {
            if(student.getId().equals(teamStudents.get(i).getStudent().getId())) {flag=1;}
            if(studentId.equals(teamStudents.get(i).getStudent().getId())) return 409;
        }
        if(flag==0) return 403;
        else{
            Long courseId=teamStudents.get(0).getTeam().getCourse().getId();
            KlassStudent klassStudent = klassStudentDao.selectByCourseIdAndStudentId(courseId,studentId);
            if(klassStudent==null) return 400;
            TeamStudent teamStudent = new TeamStudent();
            teamStudent.setTeam(teamStudents.get(0).getTeam());
            teamStudent.setStudent(studentDao.selectByStudentId(studentId));
            teamStudentDao.insert(teamStudent);
            return 200;
        }
    }

    public int delectTeamMember(Long studentId,String myAccount,Long teamId){
        Student student=studentDao.selectByAccount(myAccount);
        List<TeamStudent> teamStudents=teamStudentDao.selectByTeamId(teamId);
        if(teamStudents==null) return 404;
        int flag=0;
        int flag1=0;
        int i=0;
        for(i=0;i<teamStudents.size();i++)
        {
            //System.out.println(teamStudents.get(i).getStudent().getId());
            //System.out.println(student.getId());
            if(student.getId().equals(teamStudents.get(i).getStudent().getId())) {flag=1;}
            if(studentId.equals(teamStudents.get(i).getStudent().getId())) {flag1=1;};
        }
        //System.out.println(flag);
        if(flag==0) return 403;
        if (flag1==0) return 409;
        else{
            Long courseId=teamStudents.get(0).getTeam().getCourse().getId();
            if (studentDao.selectByStudentId(studentId)==null)
                return 400;
            teamStudentDao.deleteByPrimaryKey(teamId,studentId);
            return 200;
        }
    }
    public  Student selectStudentByStudentId(Long studentId){
        return  studentDao.selectByStudentId(studentId);
    }

    public Long creatTeam(CreatTeamVo creatTeamVo)throws CourseNotFoundException
    {
        Team team = new Team();
        Course course = courseDao.selectCourseById(creatTeamVo.getCourseId());
        team.setCourse(course);
        Klass klass = klassDao.selectKlassByKlassId(creatTeamVo.getClassId());
        team.setKlass(klass);
        team.setKlassSerial(klass.getKlassSerial());
        Student leader = studentDao.selectByStudentId(creatTeamVo.getLeader().getId());
        team.setLeader(leader);
        team.setTeamName(creatTeamVo.getName());
        Byte teamNum =Byte.parseByte(String.valueOf(klassTeamDao.selectByKlassId(klass.getId()).size())) ;
        team.setTeamSerial(teamNum++);
        team.setStatus(Byte.parseByte("0"));
        team.setId(teamDao.insert(team));
        KlassTeam klassTeam = new KlassTeam();
        klassTeam.setKlass(klass);
        klassTeam.setTeam(team);
        klassTeamDao.insert(klassTeam);
        TeamStudent teamLeader = new TeamStudent();
        teamLeader.setTeam(team);
        teamLeader.setStudent(leader);
        int b = teamStudentDao.insert(teamLeader);
        if (creatTeamVo.getMembers()!=null)
            for (int i=0;i<creatTeamVo.getMembers().size();i++)
            {
                TeamStudent teamStudent = new TeamStudent();
                teamStudent.setTeam(team);
                Student member = studentDao.selectByStudentId(creatTeamVo.getMembers().get(i));
                teamStudent.setStudent(member);
                teamStudentDao.insert(teamStudent);
            }
        return  team.getId();
    }


    public Long creatTeamValidApplication(TeamValidApplication teamValidApplication){return teamValidApplicationDao.insert(teamValidApplication);}


    //判断是否有冲突课程的
    public boolean judgeTeamConflictCourseStrategy(List<Student> members,List<ConflictCourseStrategy> conflictCourseStrategies){
        int flag=0;
        for (ConflictCourseStrategy conflictCourseStrategy : conflictCourseStrategies)
        {
            for (Student student:members)
            {
                List<KlassStudent> klassStudents =klassStudentDao.selectByStudentId(student.getId());
                int i=0;
                for (;i<klassStudents.size();i++)
                    if (klassStudents.get(i).getCourse().getId()==conflictCourseStrategy.getCourse().getId()) {
                        flag++;
                        break;
                    }
                if (i!=klassStudents.size()) break;
            }
            if (flag>1) return false;
        }
        return true;
    }
    //判断是否满足课程人数限制
    public boolean judgeCourseMemberLimitStrategy(List<Student> students,List<CourseMemberLimitStrategy> courseMemberLimitStrategies,Byte isConflict)
    {
        int flag=0;
        for (CourseMemberLimitStrategy courseMemberLimitStrategy:courseMemberLimitStrategies)
        {
            int num=0;
            for (Student student:students)
            {
                List<KlassStudent> klassStudents =klassStudentDao.selectByStudentId(student.getId());
                for (int j=0;j<klassStudents.size();j++)
                {
                    if (klassStudents.get(j).getCourse().getId()==courseMemberLimitStrategy.getCourse().getId())
                    {num++;break;}
                }
            }
            //System.out.println(num+"人数"+courseMemberLimitStrategy.getMinMember()+" "+courseMemberLimitStrategy.getMaxMember());
            if (courseMemberLimitStrategy.getMinMember()!=null&&courseMemberLimitStrategy.getMaxMember()!=null)
            {   if (num>=courseMemberLimitStrategy.getMinMember()&&num<=courseMemberLimitStrategy.getMaxMember())  flag++;}
            else if (courseMemberLimitStrategy.getMaxMember()!=null&&courseMemberLimitStrategy.getMinMember()==null)
            {   if (num<=courseMemberLimitStrategy.getMaxMember()) flag++;}
            else if (courseMemberLimitStrategy.getMinMember()!=null&&courseMemberLimitStrategy.getMaxMember()==null)
            {  if (num>=courseMemberLimitStrategy.getMinMember()) flag++;}
        }
        if (isConflict==0){if (flag==0) return false;}
        else {if (flag!=1)return false;}
        return true;
    }





    public boolean  judgeTeamValidRequest(Long teamId ) {
        Team team = teamDao.selectByPrimaryKey(teamId);
        List<Student> members = selectMemberByTeam(team);
        //判断冲突课程
        List<TeamStrategy> teamStrategies=strategyService.selectTeamStrategyByCourseIdAndName(team.getCourse().getId(),"ConflictCourseStrategy");
        if(teamStrategies!=null||teamStrategies.size()!=0)
            for (TeamStrategy teamStrategy :teamStrategies)
            {
                if (!judgeTeamConflictCourseStrategy(members,conflictCourseStrategyDao.selectById(teamStrategy.getStrategyId())))
                {   team.setStatus(Byte.parseByte("0"));
                    teamDao.updateByPrimaryKey(team);return false;}
            }
        //判断总人数限制\
        MemberLimitStrategy memberLimitStrategy = strategyService.selectMemberLimitStrategyByCourseId(team.getCourse().getId());
        if (memberLimitStrategy!= null)
            if (members.size()>memberLimitStrategy.getMaxMember()||members.size()<memberLimitStrategy.getMinMember()) {   team.setStatus(Byte.parseByte("0"));
                teamDao.updateByPrimaryKey(team);return false;}
        //判断其他课程人数限制
        CourseMemberLimitIsConflictVo courseMemberLimitIsConflictVo = strategyService.selectCourseMemberLimitStrategyByCourseId(team.getCourse().getId());
        //System.out.println(JSON.toJSONString(courseMemberLimitIsConflictVo));
        if (courseMemberLimitIsConflictVo.equals(null))
        {if (!judgeCourseMemberLimitStrategy(members,courseMemberLimitIsConflictVo.getCourseMemberLimitStrategies(),courseMemberLimitIsConflictVo.getIsConflict())) {   team.setStatus(Byte.parseByte("0"));
            teamDao.updateByPrimaryKey(team);return false;}}
        team.setStatus(Byte.parseByte("1"));
        teamDao.updateByPrimaryKey(team);
        return true;
    }

    public List<TeamStudent> selectTeamStudentByStudentId(Long id) { return teamStudentDao.selectByStudentId(id);
    }
    public void changeKlassTeam(Team team,Byte isValid){
        KlassTeam klassTeam = klassTeamDao.selectByPrimaryKey(team.getKlass().getId(),team.getId());
        if (klassTeam ==null){
            if (isValid.equals(Byte.parseByte("1"))){
                klassTeam.setKlass(team.getKlass());
                klassTeam.setTeam(team);
                klassTeamDao.insert(klassTeam);
            }
        }
        else {
            if (isValid.equals(Byte.parseByte("0")))
                klassTeamDao.deleteByPrimaryKey(team.getKlass().getId(),team.getId());
        }
    }

}
