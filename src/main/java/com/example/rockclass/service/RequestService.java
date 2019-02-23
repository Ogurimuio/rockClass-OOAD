package com.example.rockclass.service;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.dao.*;
import com.example.rockclass.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    @Autowired
    private ShareTeamApplicationDao shareTeamApplicationDao;
    @Autowired
    private ShareSeminarApplicationDao shareSeminarApplicationDao;

    @Autowired
    private TeamValidApplicationDao teamValidApplicationDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private TeamService teamService;
    @Autowired
    private KlassStudentDao klassStudentDao;
    @Autowired
    private KlassDao klassDao;
    @Autowired
    private KlassTeamDao klassTeamDao;
    @Autowired
    private TeamDao teamDao;
    public List<ShareSeminarApplication> selectSSAByTeacherIdAndStatus(Long teacherId,Byte status){return shareSeminarApplicationDao.selectBySubTeacherIdAndStatus(teacherId,status); }

    public List<ShareTeamApplication> selectSTAByTeacherIdAndStatus(Long teacherId,Byte status){return shareTeamApplicationDao.selectBySubTeacherIdAndStatus(teacherId,status);}

    public List<TeamValidApplication> selectTVAByTeacherIdAndStatus(Long teacherId, Byte status){return teamValidApplicationDao.selectByTeacherIdAndStatus(teacherId,status);}

    public ShareTeamApplication selectSTAById(Long id){
        return shareTeamApplicationDao.selectByPrimaryKey(id);
    }
    public ShareSeminarApplication selectSSAById(Long id){
        return shareSeminarApplicationDao.selectByPrimaryKey(id);
    }
    public TeamValidApplication selectTVAById(Long id){
        return teamValidApplicationDao.selectByPrimaryKey(id);
    }

    public void approveTeamShare(Long teamShareId) {
        ShareTeamApplication shareTeamApplication = selectSTAById(teamShareId);
        shareTeamApplication.setStatus(Byte.parseByte("1"));
        List<Team> teams = teamService.selectTeamByCourseId(shareTeamApplication.getMainCourse().getId());
        for (Team team : teams) {
            List<Student> students = teamService.selectMemberByTeam(team);
            students.add(team.getLeader());
            Map<Long, Integer> klassStudentNums = new HashMap<Long, Integer>();
            for (Student student : students) {
                KlassStudent klassStudentSub = klassStudentDao.selectByCourseIdAndStudentId(shareTeamApplication.getSubCourse().getId(), student.getId());
                if (klassStudentSub != null) {
                    int flag = 1;
                    for (Map.Entry<Long, Integer> klassStudentNum : klassStudentNums.entrySet()) {
                        if (klassStudentSub.getKlass().getId().equals(klassStudentNum.getKey())) {
                            klassStudentNum.setValue(klassStudentNum.getValue() + 1);
                            flag = 0;
                            break;
                        }
                    }
                    if (flag == 1) klassStudentNums.put(klassStudentSub.getKlass().getId(), Integer.valueOf("1"));
                }
            }
            if (klassStudentNums == null || klassStudentNums.isEmpty()) {
                continue;
            } else {
                Long maxLong = null;
                Integer max = new Integer("0");
                for (Map.Entry<Long, Integer> klassStudentNum : klassStudentNums.entrySet()) {
                    if (klassStudentNum.getValue().compareTo(max) > 0) {
                        max = klassStudentNum.getValue();
                        maxLong = klassStudentNum.getKey();
                    }
                }
                KlassTeam klassTeam = new KlassTeam();
                klassTeam.setTeam(team);
                klassTeam.setKlass(klassDao.selectKlassByKlassId(maxLong));
                klassTeamDao.insert(klassTeam);
                shareTeamApplication.getSubCourse().setTeamMainCourseId(shareTeamApplication.getMainCourse().getId());
                shareTeamApplication.setStatus(Byte.parseByte("1"));
                shareTeamApplicationDao.insert(shareTeamApplication);
                courseDao.updateCourse(shareTeamApplication.getSubCourse());
            }
        }
    }

    public  void updateTVA(TeamValidApplication teamValidApplication){teamValidApplicationDao.updateByPrimaryKey(teamValidApplication);}
    public  void  updateSTA(ShareTeamApplication shareTeamApplication){shareTeamApplicationDao.updateByPrimaryKey(shareTeamApplication);}
    public void  updateSSA (ShareSeminarApplication shareSeminarApplication){shareSeminarApplicationDao.updateByPrimaryKey(shareSeminarApplication);}

    public void approveTVA(TeamValidApplication teamValidApplication){
        teamValidApplication.setStatus(Byte.parseByte("1"));
        updateTVA(teamValidApplication);
        Team team = teamValidApplication.getTeam();
        team.setStatus(Byte.parseByte("1"));
        teamDao.updateByPrimaryKey(team);
    }
    public void approveSSA(ShareSeminarApplication shareSeminarApplication){
        shareSeminarApplication.setStatus(Byte.parseByte("1"));
        updateSSA(shareSeminarApplication);
        Course course = shareSeminarApplication.getSubCourse();
        course.setSeminarMainCourseId(shareSeminarApplication.getMainCourse().getId());
        courseDao.updateCourse(course);
    }
}
