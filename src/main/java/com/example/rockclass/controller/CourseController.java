package com.example.rockclass.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.rockclass.config.JwtPayload;

import com.example.rockclass.entity.*;


import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.service.*;
import com.example.rockclass.vo.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private KlassService klassService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private SeminarService seminarService;

    @PostMapping("/course")
    public void createCourse( HttpServletRequest request,HttpServletResponse response,@RequestBody CreateCourseVo createCourseVo) throws IOException, CourseNotFoundException, ParseException {
        response.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)request.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        if(role.equals("student")){
            response.setStatus(403);
        }
        else{
            Long courseId=courseService.createCourseByCourseVo(createCourseVo,account);
            response.setStatus(200);
            Map map = new HashMap(1);
            map.put("courseId",courseId);
            String json =JSON.toJSONString(map);
            response.getWriter().write(json);
        }
    }


    @GetMapping("/course")
    public void getCourses(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        List<CourseInfoVo> courseInfoVos=courseService.selectCourseVoByAccount(account,role);
        httpServletResponse.setStatus(200);
        String json = JSON.toJSONString(courseInfoVos);
        httpServletResponse.getWriter().write(json);
    }
    @GetMapping("/course/allcourse")
    public void getAllCourseInfo(HttpServletResponse httpServletResponse) throws IOException,CourseNotFoundException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        List<Course>courses = courseService.selectAllCourses();
        if(courses==null){httpServletResponse.setStatus(404); }
        else{
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (Course course:courses)
            {
                Map map = new HashMap(3);
                map.put("courseId",course.getId());
                map.put("courseName",course.getCourseName());
                map.put("teacherName",course.getTeacher().getTeacherName());
                list.add(map);
            }
            String json = JSON.toJSONString(list);
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().write(json);
        }
    }

    @GetMapping("/course/{courseId}/round")
    public void CourseRound(@PathVariable("courseId") Long courseId,HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        List<Round> rounds = courseService.selectRoundByCourseId(courseId);
        List<RoundVo> roundVos = new ArrayList<RoundVo>();
        if (rounds.size()==0) {
            httpServletResponse.setStatus(404);
            //System.out.println("404");
        }
        else{
            for (int i=0;i<rounds.size();i++)
            {
                RoundVo roundVo = new RoundVo();
                roundVo.setId(rounds.get(i).getId());
                roundVo.setOrder(rounds.get(i).getRoundSerial());
                roundVos.add(roundVo);
            }
            httpServletResponse.setStatus(200);
            String json = JSON.toJSONString(roundVos, SerializerFeature.DisableCircularReferenceDetect);
            httpServletResponse.getWriter().write(json);}
    }

    @GetMapping("/course/{courseId}")
    public void getCourseInfo(@PathVariable("courseId")Long courseId,HttpServletResponse httpServletResponse) throws IOException,CourseNotFoundException
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Course course = courseService.selectCourseByCourseId(courseId);
        if(course==null){httpServletResponse.setStatus(404); }
        else{
            CourseVo courseVo = new CourseVo();
            courseVo.setName(course.getCourseName());
            courseVo.setIntro(course.getIntroduction());
            courseVo.setStartTeamTime(format.format(course.getTeamStartTime()));
            courseVo.setEndTeamTime(format.format(course.getTeamEndTime()));
            MemberLimitStrategy memberLimitStrategy = strategyService.selectMemberLimitStrategyByCourseId(courseId);
            if (memberLimitStrategy!=null) {
                if (memberLimitStrategy.getMinMember() != null)
                    courseVo.setMinMemberNumber(memberLimitStrategy.getMinMember());
                else courseVo.setMinMemberNumber(Byte.parseByte("-1"));
                courseVo.setPresentationWeight(course.getPresentationPercentage());
                if (memberLimitStrategy.getMaxMember() != null)
                    courseVo.setMaxMemberNumber(memberLimitStrategy.getMaxMember());
                else
                    courseVo.setMaxMemberNumber(Byte.parseByte("100"));
            }
            else {
                courseVo.setMinMemberNumber(Byte.parseByte("-1"));
                courseVo.setMaxMemberNumber(Byte.parseByte("100"));
            }
            courseVo.setPresentationWeight(course.getPresentationPercentage());
            courseVo.setReportWeight(course.getReportPercentage());
            courseVo.setQuestionWeight(course.getQuestionPercentage());
            String json = JSON.toJSONString(courseVo);
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().write(json);
        }
    }

    @DeleteMapping("/course/{courseId}")
    public void delectCourse(@PathVariable("courseId") Long courseId,HttpServletResponse httpServletResponse)
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        courseService.deleteCourseByCourseId(courseId);
        httpServletResponse.setStatus(204);
    }

    @GetMapping("/course/{courseId}/team")
    public  void getTeams(@PathVariable("courseId") Long courseId,HttpServletResponse httpServletResponse)throws  IOException,CourseNotFoundException
    {   httpServletResponse.setContentType("application/json;charset=utf-8");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Team> teams = new ArrayList<Team>();
        Course course = courseService.selectCourseByCourseId(courseId);
        if (course.getTeamMainCourseId()==null) {
            teams.addAll(teamService.selectTeamByCourseId(courseId));
        }
        else {
            teams.addAll(teamService.selectTeamBySubCourseId(courseId));
        }
        //System.out.println(teams);
        for (int i=0;i<teams.size();i++)
        {
            Map map = new HashMap(4);
            List<Student> members = teamService.selectMemberByTeam(teams.get(i));
            StudentVo leader =new StudentVo();
            leader.setAccount(teams.get(i).getLeader().getAccount());
            leader.setId(teams.get(i).getLeader().getId());
            leader.setName(teams.get(i).getLeader().getStudentName());
            List<StudentVo> memberVos = new ArrayList<StudentVo>();
            for (int j=0;j<members.size();j++)
            {
                StudentVo member = new StudentVo();
                member.setName(members.get(j).getStudentName());
                member.setAccount(members.get(j).getAccount());
                member.setId(members.get(j).getId());
                memberVos.add(member);
            }
            map.put("teamId",teams.get(i).getId());
            map.put("name",teams.get(i).getTeamName());
            map.put("valid",teams.get(i).getStatus());
            map.put("leader",leader);
            map.put("members",memberVos);
            list.add(map);
        }
        String json =JSON.toJSONString(list);
        httpServletResponse.setStatus(200);
        httpServletResponse.getWriter().write(json);
    }
    @GetMapping("/course/{courseId}/myTeam")
    public void getMyTeam(@PathVariable("courseId") Long courseId,HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)request.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        Team team=teamService.selectTeamByCourseIdAndStudentAccount(courseId,account);
        if (team==null) {response.setStatus(404);}
        else{
            Map map = new HashMap(4);
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
        map.put("teamId",team.getId());
        map.put("name",team.getTeamName());
        map.put("valid",team.getStatus());
        map.put("leader",leader);
        map.put("members",memberVos);
        String json =JSON.toJSONString(map);
        response.setStatus(200);
        response.getWriter().write(json);
        }
    }
    @GetMapping("/course/{courseId}/noTeam")
    public void getNoTeam(@PathVariable("courseId") Long courseId,HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json;charset=utf-8");
        List<Student> members = teamService.selectNoTeamStudentByCourseId(courseId);
        List<StudentVo> memberVos = new ArrayList<StudentVo>();
        for (int j=0;j<members.size();j++)
        {
            StudentVo member = new StudentVo();
            member.setName(members.get(j).getStudentName());
            member.setAccount(members.get(j).getAccount());
            member.setId(members.get(j).getId());
            memberVos.add(member);
        }
        String json =JSON.toJSONString(memberVos);
        response.setStatus(200);
        response.getWriter().write(json);
    }
    @PostMapping("/course/{courseId}/class")
    public void createClass(@PathVariable("courseId") Long courseId, HttpServletRequest request, HttpServletResponse response,@RequestBody ClassVo classVo) throws  IOException ,CourseNotFoundException{
        response.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)request.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        if(role.equals("student")){
            response.setStatus(400);
        }
        else{
            Long id= klassService.insertKlassbyClassVo(courseId,classVo);
            response.setStatus(200);
            Map map = new HashMap(1);
            map.put("id",id);
            String json =JSON.toJSONString(map);
            response.getWriter().write(json);
        }
    }
    @GetMapping("/course/{courseId}/class")
    public void getClass(@PathVariable("courseId") Long courseId,HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        List<Klass> klasses=klassService.selectClassByCourseId(courseId);
        List<ClassVo> classVos = new ArrayList<ClassVo>();
        for(int i=0;i<klasses.size();i++)
        {
            ClassVo classVo = new ClassVo();
            classVo.setClassroom(klasses.get(i).getKlassLocation());
            classVo.setTime(klasses.get(i).getKlassTime());
            classVo.setId(klasses.get(i).getId());
            String name=klasses.get(i).getGrade().toString()+"-"+klasses.get(i).getKlassSerial().toString();
            classVo.setName(name);
            classVos.add(classVo);
        }
        httpServletResponse.setStatus(200);
        String json = JSON.toJSONString(classVos);
        httpServletResponse.getWriter().write(json);
    }

    @PostMapping("/course/{courseId}/class/{classId}/team")
    public void creatTeam(@PathVariable("courseId") Long courseId,@PathVariable("classId") Long classId,@RequestBody CreatTeamVo creatTeamVo, HttpServletResponse httpServletResponse)throws CourseNotFoundException,IOException
    {
        Long id= teamService.creatTeam(creatTeamVo);
        httpServletResponse.setStatus(201);
        String json = JSON.toJSONString(id);
        httpServletResponse.getWriter().write(json);
    }

    @GetMapping("/course/{courseId}/share")
    public void getTeamShare(@PathVariable("courseId") Long courseId,HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Byte status = 1;
        List<ShareTeamApplication>shareTeamApplications=courseService.selectShareTeamApplicationByCourseIdAndStatus(courseId,status);
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (ShareTeamApplication shareTeamApplication:shareTeamApplications){
            Map map = new HashMap(3);
            map.put("teamShareId",shareTeamApplication.getId());
            Map mainCourse = new HashMap(3);
            mainCourse.put("masterCourseId",shareTeamApplication.getMainCourse().getId());
            mainCourse.put("masterCourseName",shareTeamApplication.getMainCourse().getCourseName());
            mainCourse.put("TeacherName",shareTeamApplication.getMainCourse().getTeacher().getTeacherName());
            Map subCourse = new HashMap(3);
            subCourse.put("recieveCourseId",shareTeamApplication.getSubCourse().getId());
            subCourse.put("recieveCourseName",shareTeamApplication.getSubCourse().getCourseName());
            map.put("masterCourse",mainCourse);
            map.put("recieveCourse",subCourse);
            list1.add(map);
        }
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        List<ShareSeminarApplication>shareSeminarApplications=courseService.selectShareSeminarApplicationByCourseIdAndStatus(courseId,status);
        for (ShareSeminarApplication shareSeminarApplication:shareSeminarApplications){
            Map map = new HashMap(3);
            map.put("seminarShareId",shareSeminarApplication.getId());
            Map mainCourse = new HashMap(3);
            mainCourse.put("masterCourseId",shareSeminarApplication.getMainCourse().getId());
            mainCourse.put("masterCourseName",shareSeminarApplication.getMainCourse().getCourseName());
            mainCourse.put("TeacherName",shareSeminarApplication.getMainCourse().getTeacher().getTeacherName());
            Map subCourse = new HashMap(3);
            subCourse.put("recieveCourseId",shareSeminarApplication.getSubCourse().getId());
            subCourse.put("recieveCourseName",shareSeminarApplication.getSubCourse().getCourseName());
            map.put("masterCourse",mainCourse);
            map.put("recieveCourse",subCourse);
            list2.add(map);
        }
        Map mapAll =new HashMap(2);
        mapAll.put("teamShares",list1);
        mapAll.put("seminarShares",list2);
        String json =JSON.toJSONString(mapAll);
        httpServletResponse.setStatus(200);
        httpServletResponse.getWriter().write(json);
    }




    @DeleteMapping("/course/teamshare/{teamshareId}")
    public void deleteTeamShare(@PathVariable("teamshareId") Long teamshareId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)throws IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        if(role.equals("student")){
            httpServletResponse.setStatus(403);
        }
        else {
            int status= courseService.deleteTeamShare(teamshareId,account);
            httpServletResponse.setStatus(status);
        }
    }

    @DeleteMapping("/course/seminarshare/{seminarshareId}")
    public void deleteSeminarShare(@PathVariable("seminarshareId") Long teamshareId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)throws IOException
    {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        if(role.equals("student")){
            httpServletResponse.setStatus(403);
        }
        else {
            int status= courseService.deleteTeamShare(teamshareId,account);
            httpServletResponse.setStatus(status);
        }
    }

    @PostMapping("/course/{courseId}/teamsharerequest")
    public void teamShareRequest(@PathVariable("courseId")Long courseId, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException ,CourseNotFoundException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        Long subCourseId= Long.parseLong(httpServletRequest.getParameter("subCourseId"));
        if(role.equals("student")){
            httpServletResponse.setStatus(403);
        }
        else {
            Long teamShareId=courseService.creatTeamShareRequestByCourseId(courseId,subCourseId);
            if (teamShareId==null) httpServletResponse.setStatus(404);
            else {
                Map map = new HashMap(1);
                map.put("teamShareRequestId",teamShareId);
                String json =JSON.toJSONString(map);
                httpServletResponse.setStatus(201);
                httpServletResponse.getWriter().write(json);

            }
        }
    }

    @PostMapping("/course/{courseId}/seminarsharerequest")
    public void seminarShareRequest(@PathVariable("courseId")Long courseId, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException ,CourseNotFoundException{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JwtPayload jwtPayload = (JwtPayload)httpServletRequest.getAttribute("jwtPayload");
        String account=jwtPayload.getAccount();
        String role = jwtPayload.getRole();
        Long subCourseId= Long.parseLong(httpServletRequest.getParameter("subCourseId"));
        if(role.equals("student")){
            httpServletResponse.setStatus(403);
        }
        else {
            Long seminarShareId=courseService.creatSeminarShareRequestByCourseId(courseId,subCourseId);
            if (seminarShareId==null) httpServletResponse.setStatus(404);
            else {
                Map map = new HashMap(1);
                map.put("seminarShareRequestId",seminarShareId);
                String json =JSON.toJSONString(map);
                httpServletResponse.setStatus(201);
                httpServletResponse.getWriter().write(json);

            }
        }
    }

    @GetMapping("/course/{courseId}/teamstrategy")
    public void getTeamStrategy(@PathVariable("courseId")Long courseId, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException ,CourseNotFoundException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Map mapAll = new HashMap(3);
        MemberLimitStrategy memberLimitStrategy = strategyService.selectMemberLimitStrategyByCourseId(courseId);
        if (memberLimitStrategy != null) {
            Map mapMLS = new HashMap(2);
            mapMLS.put("maxMemberNumber", memberLimitStrategy.getMaxMember());
            mapMLS.put("minMemberNumber", memberLimitStrategy.getMinMember());
            mapAll.put("memberLimitStrategy", mapMLS);
        }
        List<CourseMemberLimitStrategy> courseMemberLimitStrategies = strategyService.selectCourseMemberLimitStrategyByCourseId(courseId).getCourseMemberLimitStrategies();
        if (courseMemberLimitStrategies != null) {
            List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
            for (CourseMemberLimitStrategy courseMemberLimitStrategy : courseMemberLimitStrategies) {
                Map mapCMLS = new HashMap();
                mapCMLS.put("courseId", courseMemberLimitStrategy.getCourse().getId());
                if (courseMemberLimitStrategy.getMaxMember()==null) mapCMLS.put("maxMemberNumber",Long.parseLong("10000"));
                else mapCMLS.put("maxMemberNumber", courseMemberLimitStrategy.getMaxMember());
                mapCMLS.put("minMemberNumber", courseMemberLimitStrategy.getMinMember());
                list1.add(mapCMLS);
            }
            mapAll.put("courseMemberLimitStrategies", list1);
        }
        List<TeamStrategy>teamStrategies=strategyService.selectTeamStrategyByCourseIdAndName(courseId,"ConflictCourseStrategy");
        List  listConflictCourseStrategys = new ArrayList<>();
        for (TeamStrategy teamStrategy:teamStrategies)
        {
            List<ConflictCourseStrategy> conflictCourseStrategies = strategyService.selectConflictCourseStrategiesByStrategyId(teamStrategy.getStrategyId());
            List<Map<String, Object>> listConflictCourses = new ArrayList<Map<String, Object>>();
            for (int i=0;i<conflictCourseStrategies.size();i++)
            {
                Map mapCCS = new HashMap(3);
                mapCCS.put("courseName",conflictCourseStrategies.get(i).getCourse().getCourseName());
                mapCCS.put("courseTeacherName",conflictCourseStrategies.get(i).getCourse().getTeacher().getTeacherName());
                listConflictCourses.add(mapCCS);
            }
            listConflictCourseStrategys.add(listConflictCourses);
        }
        mapAll.put("conflictCourseStrategys",listConflictCourseStrategys);
        String json =JSON.toJSONString(mapAll);
        httpServletResponse.setStatus(200);
        httpServletResponse.getWriter().write(json);
    }



    @GetMapping("/course/{courseId}/allscore")
    public void getCourseAllScore(@PathVariable("courseId") Long courseId,HttpServletResponse httpServletResponse)throws IOException
    {
        List<Map<String, Object>> listAll = new ArrayList<Map<String, Object>>();

        List<Team> teams = teamService.selectTeamByCourseId(courseId);

        List<Round> rounds = roundService.selectRoundByCourseId(courseId);
        for (Round round:rounds)
        {
            List<Map<String, Object>> listTeam = new ArrayList<Map<String, Object>>();
            for (Team team:teams) {
                Map mapTeam = new HashMap(5);
                RoundScore roundScore = roundService.selectRoundScoreByRoundIdAndTeamId(round.getId(),team.getId());
                List<Seminar> seminars = seminarService.selectSeminarByRoundId(round.getId());
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (Seminar seminar : seminars) {
                    SeminarScore seminarScore = seminarService.selectSeminarScoreBySeminarIdAndTeamId(seminar.getId(), team.getId());
                    Map map = new HashMap(5);
                    if (seminarScore != null) {
                        if (seminarScore.getQuestionScore() == null)
                            seminarScore.setQuestionScore(BigDecimal.valueOf(0));
                        if (seminarScore.getPresentationScore() == null)
                            seminarScore.setPresentationScore(BigDecimal.valueOf(0));
                        if (seminarScore.getReportScore() == null) seminarScore.setReportScore(BigDecimal.valueOf(0));
                        if (seminarScore.getTotalScore() == null) {
                            BigDecimal totalScore = new BigDecimal(roundService.calculateTotalScore(seminarScore.getPresentationScore(), seminarScore.getQuestionScore(), seminarScore.getReportScore(), team.getCourse()).toString());
                            seminarScore.setTotalScore(totalScore.setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                        map.put("seminarName", seminar.getSeminarName());
                        map.put("questionScore", seminarScore.getQuestionScore());
                        map.put("preScore", seminarScore.getPresentationScore());
                        map.put("reportScore", seminarScore.getReportScore());
                        map.put("totalScore", seminarScore.getTotalScore());
                        list.add(map);
                    }
                    else {
                        map.put("seminarName", seminar.getSeminarName());
                        map.put("questionScore",0);
                        map.put("preScore", 0);
                        map.put("reportScore", 0);
                        map.put("totalScore", 0);
                        list.add(map);
                    }
                }
                Map mapRoundScore = new HashMap(5);
                if (roundScore != null) {
                    if (roundScore.getQuestionScore() == null) roundScore.setQuestionScore(BigDecimal.valueOf(0));
                    if (roundScore.getPresentationScore() == null)
                        roundScore.setPresentationScore(BigDecimal.valueOf(0));
                    if (roundScore.getReportScore() == null) roundScore.setReportScore(BigDecimal.valueOf(0));
                    if (roundScore.getTotalScore() == null) {
                        BigDecimal totalScore = new BigDecimal(roundService.calculateTotalScore(roundScore.getPresentationScore(), roundScore.getQuestionScore(), roundScore.getReportScore(), team.getCourse()).toString());
                        roundScore.setTotalScore(totalScore.setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                    mapRoundScore.put("questionScore", roundScore.getQuestionScore());
                    mapRoundScore.put("preScore", roundScore.getPresentationScore());
                    mapRoundScore.put("reportScore", roundScore.getReportScore());
                    mapRoundScore.put("totalScore", roundScore.getTotalScore());
                    mapTeam.put("seminarScores", list);
                    mapTeam.put("roundScore", mapRoundScore);
                    mapTeam.put("teamName", team.getKlassSerial() + "-" + team.getTeamSerial());
                }
                listTeam.add(mapTeam);
            }

            Map mapRound = new HashMap(2);
            mapRound.put("roundSerial",round.getRoundSerial());
            mapRound.put("allTeamRoundScore",listTeam);
            listAll.add(mapRound);
        }
        String json =JSON.toJSONString(listAll);
        httpServletResponse.setStatus(200);
        httpServletResponse.getWriter().write(json);
    }
    /*
    *
    *
    *request:
    *   path:courseId
    *
    *
    *
    * */
    @PutMapping("/course/{courseId}/score")
    public void getScoreExcel(@PathVariable("courseId")Long courseId, HttpServletRequest request,HttpServletResponse response) throws IOException, CourseNotFoundException {
        courseService.getScoreExcel(response,courseId);
    }

}
