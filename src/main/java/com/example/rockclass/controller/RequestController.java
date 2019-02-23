package com.example.rockclass.controller;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.config.JwtPayload;
import com.example.rockclass.entity.*;
import com.example.rockclass.service.RequestService;
import com.example.rockclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;

    @GetMapping("request/message")
    public void  requestMassage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        Teacher teacher = userService.selectTeacherByTeacherAccount(account);
        Byte status = new Byte("0");
        List<ShareSeminarApplication>shareSeminarApplications=requestService.selectSSAByTeacherIdAndStatus(teacher.getId(),status);
        List<ShareTeamApplication>shareTeamApplications =  requestService.selectSTAByTeacherIdAndStatus(teacher.getId(),status);
        List<TeamValidApplication>teamValidApplications = requestService.selectTVAByTeacherIdAndStatus(teacher.getId(),status);
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (ShareTeamApplication shareTeamApplication:shareTeamApplications)
        {
            Map map = new HashMap(6);
            map.put("teamShareId",shareTeamApplication.getId());
            map.put("masterCourseId",shareTeamApplication.getMainCourse().getId());
            map.put("masterCourseName",shareTeamApplication.getMainCourse().getCourseName());
            map.put("teacherName",shareTeamApplication.getMainCourse().getTeacher().getTeacherName());
            map.put("recieveCourseId",shareTeamApplication.getSubCourse().getId());
            map.put("recieveCourseName",shareTeamApplication.getSubCourse().getCourseName());
            list1.add(map);
        }
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        for (ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            Map map = new HashMap(4);
            map.put("seminarShareId",shareSeminarApplication.getId());
            map.put("masterCourseId",shareSeminarApplication.getMainCourse().getId());
            map.put("masterCourseName",shareSeminarApplication.getMainCourse().getCourseName());
            map.put("teacherName",shareSeminarApplication.getMainCourse().getTeacher().getTeacherName());
            map.put("recieveCourseId",shareSeminarApplication.getSubCourse().getId());
            map.put("recieveCourseName",shareSeminarApplication.getSubCourse().getCourseName());
            list2.add(map);
        }
        List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
        for (TeamValidApplication teamValidApplication:teamValidApplications)
        {
            Map map = new HashMap(9);
            map.put("teamValidId",teamValidApplication.getId());
            map.put("courseId",teamValidApplication.getTeam().getCourse().getId());
            map.put("courseName",teamValidApplication.getTeam().getCourse().getCourseName());
            map.put("classId",teamValidApplication.getTeam().getKlass().getId());
            map.put("className",teamValidApplication.getTeam().getKlass().getGrade().toString()+"-"+teamValidApplication.getTeam().getKlassSerial());
            map.put("teamId",teamValidApplication.getTeam().getId());
            map.put("leaderId",teamValidApplication.getTeam().getLeader().getId());
            map.put("leaderName",teamValidApplication.getTeam().getLeader().getStudentName());
            map.put("reason",teamValidApplication.getReason());
            list3.add(map);
        }
        Map mapAll = new HashMap(3);
        if (list1.size()!=0)mapAll.put("teamShareRequests",list1);
        if (list2.size()!=0)mapAll.put("seminarShareRequests",list2);
        if (list3.size()!=0)mapAll.put("teamValidRequests",list3);
        String json = JSON.toJSONString(mapAll);
        httpServletResponse.getWriter().write(json);
        httpServletResponse.setStatus(200);
    }

    @GetMapping("/request/teamshare/{teamshareId}")
    public void getOneTeamShare(@PathVariable("teamshareId") Long teamshareId,HttpServletResponse httpServletResponse)throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ShareTeamApplication shareTeamApplication = requestService.selectSTAById(teamshareId);
        if (shareTeamApplication==null){httpServletResponse.setStatus(404);}
        else {
            Map map = new HashMap(6);
            map.put("teamShareId", teamshareId);
            map.put("masterCourseId", shareTeamApplication.getMainCourse().getId());
            map.put("masterCourseName", shareTeamApplication.getMainCourse().getCourseName());
            map.put("teacherName", shareTeamApplication.getMainCourse().getTeacher().getTeacherName());
            map.put("recieveCourseId", shareTeamApplication.getSubCourse().getId());
            map.put("recieveCourseName", shareTeamApplication.getSubCourse().getCourseName());
            String json = JSON.toJSONString(map);
            httpServletResponse.getWriter().write(json);
            httpServletResponse.setStatus(200);
        }
    }

    @GetMapping("/request/seminarshare/{seminarshareId}")
    public void getOneSeminarShare(@PathVariable("seminarshareId") Long seminarshareId,HttpServletResponse httpServletResponse)throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ShareSeminarApplication shareSeminarApplication = requestService.selectSSAById(seminarshareId);
        if (shareSeminarApplication==null){httpServletResponse.setStatus(404);}
        else {
            Map map = new HashMap(6);
            map.put("seminarShareId",shareSeminarApplication.getId());
            map.put("masterCourseId",shareSeminarApplication.getMainCourse().getId());
            map.put("masterCourseName",shareSeminarApplication.getMainCourse().getCourseName());
            map.put("teacherName",shareSeminarApplication.getMainCourse().getTeacher().getTeacherName());
            map.put("recieveCourseId",shareSeminarApplication.getSubCourse().getId());
            map.put("recieveCourseName",shareSeminarApplication.getSubCourse().getCourseName());
            String json = JSON.toJSONString(map);
            httpServletResponse.getWriter().write(json);
            httpServletResponse.setStatus(200);
        }
    }

    @GetMapping("/request/teamvalid/{teamvalidId}")
    public void getOneTeamValid(@PathVariable("teamvalidId") Long teamvalidId,HttpServletResponse httpServletResponse)throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        TeamValidApplication teamValidApplication = requestService.selectTVAById(teamvalidId);
        if (teamValidApplication==null){httpServletResponse.setStatus(404);}
        else {
            Map map = new HashMap(9);
            map.put("teamValidId",teamValidApplication.getId());
            map.put("courseId",teamValidApplication.getTeam().getCourse().getId());
            map.put("courseName",teamValidApplication.getTeam().getCourse().getCourseName());
            map.put("classId",teamValidApplication.getTeam().getKlass().getId());
            map.put("className",teamValidApplication.getTeam().getKlass().getGrade().toString()+"-"+teamValidApplication.getTeam().getKlassSerial());
            map.put("teamId",teamValidApplication.getTeam().getId());
            map.put("leaderId",teamValidApplication.getTeam().getLeader().getId());
            map.put("leaderName",teamValidApplication.getTeam().getLeader().getStudentName());
            map.put("reason",teamValidApplication.getReason());
            String json = JSON.toJSONString(map);
            httpServletResponse.getWriter().write(json);
            httpServletResponse.setStatus(200);
        }
    }
    @PutMapping("/request/teamshare/{teamshareId}")
    public void putTeamShare(@PathVariable("teamshareId") Long teamShareId,@RequestParam("isApprove") Byte isApprove, HttpServletResponse httpServletResponse)throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ShareTeamApplication shareTeamApplication = requestService.selectSTAById(teamShareId);
        if (isApprove.equals(Byte.parseByte("1")))
            requestService.approveTeamShare(teamShareId);
        else {
            shareTeamApplication.setStatus(Byte.parseByte("2"));
            requestService.updateSTA(shareTeamApplication);
        }

    }

    @PutMapping("/request/teamvalid/{teamvalidId}")
    public void putTeamValid(@PathVariable("teamvalidId") Long teamValidId,@RequestParam("isApprove") Byte isApprove, HttpServletResponse httpServletResponse)throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        TeamValidApplication teamValidApplication = requestService.selectTVAById(teamValidId);
        if (isApprove.equals(Byte.parseByte("1")))
        {
            requestService.approveTVA(teamValidApplication);
        }
        else {
            teamValidApplication.setStatus(Byte.parseByte("2"));
            requestService.updateTVA(teamValidApplication);
        }
    }

    @PutMapping("/request/seminarshare/{seminarshareId}")
    public void putSeminarShare(@PathVariable("seminarshareId") Long seminarshareId,@RequestParam("isApprove") Byte isApprove, HttpServletResponse httpServletResponse)throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ShareSeminarApplication shareSeminarApplication = requestService.selectSSAById(seminarshareId);
        if (isApprove.equals(Byte.parseByte("1")))
        {
            requestService.approveSSA(shareSeminarApplication);
        }
        else {
            shareSeminarApplication.setStatus(Byte.parseByte("2"));
            requestService.updateSSA(shareSeminarApplication);
        }
    }


}
