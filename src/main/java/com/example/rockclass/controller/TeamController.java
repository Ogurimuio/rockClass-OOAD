package com.example.rockclass.controller;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.config.JwtPayload;
import com.example.rockclass.entity.Student;
import com.example.rockclass.entity.Team;
import com.example.rockclass.entity.TeamValidApplication;
import com.example.rockclass.service.*;
import com.example.rockclass.vo.IdNameVo;
import com.example.rockclass.vo.StudentVo;
import com.example.rockclass.vo.TeamValidApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TeamController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private KlassService klassService;


    @GetMapping("/team/{teamId}")
    public void getTeam(@PathVariable("teamId") Long teamId, HttpServletResponse httpServletResponse)throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Team team=teamService.selectTeamByTeamId(teamId);
        if(team ==null){
            httpServletResponse.setStatus(404);
        }
        else {
            Map<String,Object> map = new HashMap<String, Object>();
            List<Student> members = teamService.selectMemberByTeam(team);
            StudentVo leader =new StudentVo();
            leader.setAccount(team.getLeader().getAccount());
            leader.setId(team.getLeader().getId());
            leader.setName(team.getLeader().getStudentName());
            List<StudentVo> memberVos = new ArrayList<StudentVo>();
            for (int j=0;j<members.size();j++)
            {
                StudentVo member = new StudentVo();
                member.setName(members.get(j).getStudentName());
                member.setAccount(members.get(j).getAccount());
                member.setId(members.get(j).getId());
                memberVos.add(member);
            }
            IdNameVo courseIdName = new IdNameVo();
            courseIdName.setId(team.getCourse().getId());
            courseIdName.setName(team.getCourse().getCourseName());
            IdNameVo classIdName = new IdNameVo();
            classIdName.setId(team.getKlass().getId());
            classIdName.setName(team.getKlass().getGrade().toString()+"-"+team.getKlassSerial());
            map.put("name",team.getKlassSerial().toString()+"-"+team.getTeamSerial().toString()+" "+team.getTeamName());
            map.put("valid",team.getStatus());
            map.put("course",courseIdName);
            map.put("class",classIdName);
            map.put("leader",leader);
            map.put("members",memberVos);
            String json = JSON.toJSONString(map);
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().write(json);}
    }

    @PutMapping("/team/{teamId}")
    public void updateTeam(@PathVariable("teamId") Long teamId, HttpServletResponse httpServletResponse )throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
    }

    @DeleteMapping("/team/{teamId}")
    public void delectTeam(@PathVariable("teamId") Long teamId, HttpServletResponse httpServletResponse )throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        teamService.deleteTeamByTeamId(teamId);
        httpServletResponse.setStatus(200);
    }

    @PutMapping("/team/{teamId}/add")
    public void addTeamMember(@PathVariable("teamId") Long teamId, @RequestParam("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse )throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        int status=teamService.addTeamMember(id,account,teamId);
        httpServletResponse.setStatus(status);
    }

    @PutMapping("/team/{teamId}/remove")
    public void deleteTeamMember(@PathVariable("teamId") Long teamId, @RequestParam("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse )throws IOException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        int status=teamService.delectTeamMember(id,account,teamId);
        httpServletResponse.setStatus(status);
    }

    @PostMapping("/team/{teamId}/teamvalidrequest")
    public void  creatTeamValiRequest(@PathVariable("teamId") Long teamId, @RequestBody TeamValidApplicationVo teamValidApplicationVo, HttpServletResponse httpServletResponse )throws IOException{
        TeamValidApplication teamValidApplication =new TeamValidApplication();
        Team team=teamService.selectTeamByTeamId(teamId);
        if (team == null) {httpServletResponse.setStatus(404);}
        else {
            teamValidApplication.setTeam(team);
            teamValidApplication.setReason(teamValidApplicationVo.getReason());
            teamValidApplication.setStatus(Byte.parseByte("0"));
            teamValidApplication.setTeacher(team.getCourse().getTeacher());
            Long id=teamService.creatTeamValidApplication(teamValidApplication);
            Map map = new HashMap(1);
            map.put("id",id);
            String json = JSON.toJSONString(map);
            httpServletResponse.setStatus(200);
        }
    }


    @GetMapping("/team/{teamId}/approve")
    public void  approveTeamValiRequest(@PathVariable("teamId") Long teamId, HttpServletResponse httpServletResponse )throws IOException {
        Team team =teamService.selectTeamByTeamId(teamId);
        if (team==null) httpServletResponse.setStatus(404);
        else{
                int isValid = 0;
                //System.out.println(teamService.judgeTeamValidRequest(teamId));
                if (teamService.judgeTeamValidRequest(teamId)) {isValid = 1;}
                else {isValid=0;}
                Map map = new HashMap(1);
                map.put("isValid",isValid);
                httpServletResponse.getWriter().write(JSON.toJSONString(map));
                httpServletResponse.setStatus(200);
            }
        }
    }

