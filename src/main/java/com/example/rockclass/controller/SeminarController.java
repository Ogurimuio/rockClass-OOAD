package com.example.rockclass.controller;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.config.JwtPayload;


import com.example.rockclass.dao.TeamStudentDao;
import com.example.rockclass.entity.*;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.service.*;
import com.example.rockclass.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class SeminarController {

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeamStudentDao teamStudentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private KlassService klassService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private RoundService roundService;

    /*新建讨论课
     * request:
     *   seminarName;
     *   introduction;
     *   roundSerial
     *   isVisible
     *   maxTeam;
     *   enrollStartTime;
     *   enrollEndTime;
     *   courseId
     *
     * response:
     *   seminarId
     */

    @PostMapping("/seminar")
    public void createSeminar(HttpServletRequest request, HttpServletResponse response, @RequestBody SeminarVO seminarVO) throws IOException, CourseNotFoundException {
        response.setContentType("application/json;charset=utf-8");
        Long seminarId = seminarService.createSeminar(seminarVO);
        response.setStatus(200);
        Map map = new HashMap();
        map.put("seminarId",seminarId);
        response.getWriter().write(JSON.toJSONString(map));

    }

    /*
     * request:
     * path: seminarId
     *
     * response:
     *   id;   //班级id
     *   name;   //班级名
     *
     *
     * */

    @GetMapping("/seminar/{seminarId}/class")
    public void getSeminarClass(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId) throws IOException {
        response.setContentType("application/json;charset=utf-8");

        Course course = seminarService.selectSeminarById(seminarId).getCourse();
        List<SeminarClassVO> seminarClassVOS = seminarService.getSeminarClassByCourse(course);
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(seminarClassVOS));
    }

    /*
     * request:
     * path: seminarId
     *   seminarName;
     *   introduction;
     *   seminarSerial;
     *   isVisible
     *   roundSerial
     *   maxTeam;
     *   enrollStartTime;
     *   enrollEndTime;
     */

    @PutMapping("/seminar/{seminarId}")
    public void updateSeminar(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId, @RequestBody SeminarVO seminarVO) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if (seminarService.selectSeminarById(seminarId)!= null) {
           // System.out.println("update"+seminarId);
            //System.out.println("update"+seminarVO.toString());

            seminarService.updateSeminar(seminarId, seminarVO);
            response.setStatus(200);
        }
        else {
            response.setStatus(400);
            response.getWriter().write("错误的ID格式");
        }
    }

     /*
     * request:
     * path: seminarId
     *
     */

    @DeleteMapping("/seminar/{seminarId}")
    public void deleteSeminar(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId) throws IOException {
        response.setContentType("application/json;charset=utf-8");

        if (seminarService.deleteSeminar(seminarId)==1){
            response.setStatus(200);
        }
        else {
            response.setStatus(400);
            response.getWriter().write("未找到");
        }

    }

    /*
     * request:
     * path: seminarId
     *
     * response:
     *   id;
     *   seminarName;
     *   introduction;
     *   seminarSerial;
     *   maxTeam;
     *   enrollStartTime;
     *   enrollEndTime;
     *
     *
     * */

    @GetMapping("/seminar/{seminarId}")
    public void getSeminar(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId) throws IOException {
        response.setContentType("application/json;charset=utf-8");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Seminar seminar=seminarService.selectSeminarById(seminarId);
        if(seminar!=null){
            response.setStatus(200);
            Map map = new HashMap();
            map.put("id",seminar.getId());
            map.put("seminarName",seminar.getSeminarName());
            map.put("introduction",seminar.getIntroduction());
            map.put("seminarSerial",seminar.getSeminarSerial());
            map.put("maxTeam",seminar.getMaxTeam());
            map.put("enrollStartTime",format.format(seminar.getEnrollStartTime()));
            map.put("enrollEndTime",format.format(seminar.getEnrollEndTime()));
            response.getWriter().write(JSON.toJSONString(map));
        }
        else response.setStatus(400);
    }

    /*讨论课结束，设置提交报告截止时间
     * request:
     * path: seminarId
     * path: classId
     *   reportDDL:这个的格式是yyyy-MM-dd HH:mm:ss，中间不加T
     * request:
     * reportDDL
     * */

    @PutMapping("/seminar/{seminarId}/class/{classId}")
    public void setReportDDL(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId, @PathVariable("classId")Long classId) throws ParseException {
        String reportDDL=request.getParameter("repotDDL");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date reportDdl = format.parse(reportDDL);
        seminarService.setReportDDL(seminarId,classId,reportDdl);
        response.setStatus(200);
    }


    //@DeleteMapping("/seminar/{seminarId}/class/{classId}")找不到

    /*获取班级讨论课信息
    * request:
    * path: seminarId
    * path: classId
    *
    * response:
    *   id;
    *   seminarName;
    *   introduction;
    *   status;
    *   seminarSerial;
    *   maxTeam;
    *   reportDDL;
    *   enrollStartTime;
    *   enrollEndTime;
    *
    * */
    @GetMapping("/seminar/{seminarId}/class/{classId}")
    public void getClassSeminar(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("classId")Long classId)throws IOException{
        response.setContentType("application/json;charset=utf-8");

        KlassSeminarVO klassSeminarVO=seminarService.getClassSeminar(classId,seminarId);
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(klassSeminarVO));
    }

    /*修改讨论课状态——开始讨论课
     * request:
     * path: seminarId
     *   classId
     *   status
     * */

    @PutMapping("/seminar/{seminarId}/status")
    public void setSeminarStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@RequestParam("classId") Long classId,@RequestParam("status") Byte status)throws IOException {
        seminarService.setSeminarStatus(seminarId,classId,status);
        response.setStatus(200);
    }

    /*
     * request:
     * path: seminarId
     * path: teamId
     *
     * response:
     *   seminarInfo
     *   preScore
     *   reportScore
     *   questionScore
     *   totalScore
     *
     * */

    @GetMapping("/seminar/{seminarId}/team/{teamId}/seminarscore")
    public void getTeamSeminarScore(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("teamId") Long teamId) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        SeminarScore seminarScore=seminarService.selectSeminarScoreBySeminarIdAndTeamId(seminarId,teamId);
        KlassSeminarVO klassSeminarVO=seminarService.getClassSeminar(seminarScore.getKlassSeminar().getKlass().getId(),seminarId);
        if(seminarScore.getQuestionScore()==null) seminarScore.setQuestionScore(BigDecimal.valueOf(0));
        if(seminarScore.getPresentationScore()==null) seminarScore.setPresentationScore(BigDecimal.valueOf(0));
        if(seminarScore.getReportScore()==null) seminarScore.setReportScore(BigDecimal.valueOf(0));
        if(seminarScore.getTotalScore()==null) {
            BigDecimal presentationPercentage= new BigDecimal(seminarScore.getTeam().getCourse().getPresentationPercentage()/100.0);
            BigDecimal reportPercentage= new BigDecimal(seminarScore.getTeam().getCourse().getReportPercentage()/100.0);
            BigDecimal questionPercentage= new BigDecimal(seminarScore.getTeam().getCourse().getQuestionPercentage()/100.0);
            BigDecimal totalScore = presentationPercentage.multiply(seminarScore.getPresentationScore());
            totalScore=totalScore.add(reportPercentage.multiply(seminarScore.getReportScore()));
            totalScore=totalScore.add(questionPercentage.multiply(seminarScore.getQuestionScore()));
            seminarScore.setTotalScore(totalScore.setScale(2,BigDecimal.ROUND_HALF_UP));
            seminarService.updateSeminarScore(seminarScore);
        }
        Map map = new HashMap();
        map.put("seminarInfo",klassSeminarVO);
        map.put("questionScore",seminarScore.getQuestionScore());
        map.put("preScore",seminarScore.getPresentationScore());
        map.put("reportScore",seminarScore.getReportScore());
        map.put("totalScore",seminarScore.getTotalScore());
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(map));
    }

    @GetMapping("/seminar/{seminarId}/class/{classId}/seminarscore")
    public void getClassSeminarScore(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("classId") Long classId) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        List<SeminarScore> seminarScores = seminarService.selectSeminarScoreBySeminarIdAndKlassId(seminarId,classId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SeminarScore seminarScore:seminarScores) {
            if (seminarScore.getQuestionScore() == null) seminarScore.setQuestionScore(BigDecimal.valueOf(0));
            if (seminarScore.getPresentationScore() == null) seminarScore.setPresentationScore(BigDecimal.valueOf(0));
            if (seminarScore.getReportScore() == null) seminarScore.setReportScore(BigDecimal.valueOf(0));
            if (seminarScore.getTotalScore() == null) {
                BigDecimal presentationPercentage = new BigDecimal(seminarScore.getTeam().getCourse().getPresentationPercentage() / 100.0);
                BigDecimal reportPercentage = new BigDecimal(seminarScore.getTeam().getCourse().getReportPercentage() / 100.0);
                BigDecimal questionPercentage = new BigDecimal(seminarScore.getTeam().getCourse().getQuestionPercentage() / 100.0);
                BigDecimal totalScore = presentationPercentage.multiply(seminarScore.getPresentationScore());
                totalScore = totalScore.add(reportPercentage.multiply(seminarScore.getReportScore()));
                totalScore = totalScore.add(questionPercentage.multiply(seminarScore.getQuestionScore()));
                seminarScore.setTotalScore(totalScore.setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            Map map = new HashMap();
            map.put("teamName",seminarScore.getTeam().getKlassSerial()+"-"+seminarScore.getTeam().getTeamSerial());
            map.put("questionScore", seminarScore.getQuestionScore());
            map.put("preScore", seminarScore.getPresentationScore());
            map.put("reportScore", seminarScore.getReportScore());
            map.put("totalScore", seminarScore.getTotalScore());
            list.add(map);
        }
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(list));
    }
    @PutMapping("/seminar/{seminarId}/seminarscores")
    public void putAllTeamSeminarScore(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@RequestBody SeminarScoresVo seminarScoresVo) {
        response.setContentType("application/json;charset=utf-8");
        for (SeminarScoreVo seminarScoreVo:seminarScoresVo.getSeminarScoreVos())
        {
            SeminarScore seminarScore = seminarService.selectSeminarScoreBySeminarIdAndTeamId(seminarId,seminarScoreVo.getTeamId());
            BigDecimal report = new BigDecimal("0");
            BigDecimal pre    =  new BigDecimal("0");
            BigDecimal question = new BigDecimal("0");
            //System.out.println(seminarScoreVo.toString());
            if (seminarScoreVo.getReportScore()!=null){
                //System.out.println("tabushikong");
                report=BigDecimal.valueOf(Double.valueOf(seminarScoreVo.getReportScore().toString()));
                seminarScore.setReportScore(report);
            }
            if (seminarScoreVo.getQuestionScore()!=null){
                question=BigDecimal.valueOf(Double.valueOf(seminarScoreVo.getQuestionScore().toString()));
                seminarScore.setQuestionScore(question);
            }
            if (seminarScoreVo.getPreScore()!=null){
                pre=BigDecimal.valueOf(Double.valueOf(seminarScoreVo.getPreScore().toString()));
                seminarScore.setPresentationScore(pre);
            }
            seminarScore.setTotalScore(roundService.calculateTotalScore(pre,question,report,seminarScore.getTeam().getCourse()));
            if (seminarScore==null)
            {
                seminarScore.setTotalScore(roundService.calculateTotalScore(pre,question,report,seminarScore.getTeam().getCourse()));
                seminarService.insertSeminarScore(seminarScore);
            }
            else {
                if (seminarScore.getReportScore()!=null) report=seminarScore.getReportScore();
                if (seminarScore.getPresentationScore()!=null) pre=seminarScore.getPresentationScore();
                if (seminarScore.getQuestionScore()!=null) question = seminarScore.getQuestionScore();
                seminarScore.setTotalScore(roundService.calculateTotalScore(pre,question,report,seminarScore.getTeam().getCourse()));
                seminarService.updateSeminarScore(seminarScore);
            }
            Seminar seminar = seminarService.selectSeminarById(seminarId);
            Team team = teamService.selectTeamByTeamId(seminarScoreVo.getTeamId());

            roundService.calculateNewRoundScore(seminar.getRound(),team);
        }
    }
    /*
     * request:
     * path: seminarId
     * path: teamId
     *   preScore
     *   reportScore
     *   questionScore
     *
     * */
    @PutMapping("/seminar/{seminarId}/team/{teamId}/seminarscore")
    public void putTeamSeminarScore(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("teamId") Long teamId,@RequestBody SeminarScoreVo seminarScoreVo) {
        response.setContentType("application/json;charset=utf-8");
        SeminarScore seminarScore = seminarService.selectSeminarScoreBySeminarIdAndTeamId(seminarId,teamId);
        BigDecimal report = new BigDecimal("0");
        BigDecimal pre    =  new BigDecimal("0");
        BigDecimal question = new BigDecimal("0");
        //System.out.println(seminarScoreVo.toString());
        if (seminarScoreVo.getReportScore()!=null){
            //System.out.println("tabushikong");
            report=BigDecimal.valueOf(Double.valueOf(seminarScoreVo.getReportScore().toString()));
            seminarScore.setReportScore(report);
        }
        if (seminarScoreVo.getQuestionScore()!=null){
            question=BigDecimal.valueOf(Double.valueOf(seminarScoreVo.getQuestionScore().toString()));
            seminarScore.setQuestionScore(question);
        }
        if (seminarScoreVo.getPreScore()!=null){
            pre=BigDecimal.valueOf(Double.valueOf(seminarScoreVo.getPreScore().toString()));
            seminarScore.setPresentationScore(pre);
        }
        seminarScore.setTotalScore(roundService.calculateTotalScore(pre,question,report,seminarScore.getTeam().getCourse()));
        if (seminarScore==null)
        {
            seminarScore.setTotalScore(roundService.calculateTotalScore(pre,question,report,seminarScore.getTeam().getCourse()));
            seminarService.insertSeminarScore(seminarScore);
        }
        else {
            if (seminarScore.getReportScore()!=null) report=seminarScore.getReportScore();
            if (seminarScore.getPresentationScore()!=null) pre=seminarScore.getPresentationScore();
            if (seminarScore.getQuestionScore()!=null) question = seminarScore.getQuestionScore();
            seminarScore.setTotalScore(roundService.calculateTotalScore(pre,question,report,seminarScore.getTeam().getCourse()));
            seminarService.updateSeminarScore(seminarScore);
        }
        Seminar seminar = seminarService.selectSeminarById(seminarId);
        Team team = teamService.selectTeamByTeamId(teamId);

        roundService.calculateNewRoundScore(seminar.getRound(),team);
    }





    /*报名讨论课
     * request:
     * path: seminarId
     * path: classId
     *   teamId
     *   teamOrder
     *
     * response:
     *   attendanceId;
     *
     *
     * */
    @PostMapping("/seminar/{seminarId}/class/{classId}/attendance")
    public void enrollSeminar(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("classId") Long classId,@RequestBody TeamOrderVO teamOrderVO) throws IOException {
        response.setContentType("application/json;charset=utf-8");

        Seminar seminar = seminarService.selectSeminarById(seminarId);
        Date enrollStartTime= seminar.getEnrollStartTime();
        Date enrollEndTime= seminar.getEnrollEndTime();
        Date now = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(now);
        Calendar begin = Calendar.getInstance();
        begin.setTime(enrollStartTime);
        Calendar end = Calendar.getInstance();
        end.setTime(enrollEndTime);
        if (date.after(begin) && date.before(end)) {
            Long attendanceId = seminarService.enrollSeminar(seminarId, classId, teamOrderVO);
            if(!attendanceId.equals((long)0)) {
                response.setStatus(200);
                Map map = new HashMap();
                map.put("attendanceId", attendanceId);
                response.getWriter().write(JSON.toJSONString(map));

            }
            else {
                response.setStatus(403);
                Map map = new HashMap();
                map.put("information", "本轮报名次数已满");
                response.getWriter().write(JSON.toJSONString(map));
            }
        }else {
            response.setStatus(400);
            Map map=new HashMap();
            map.put("information","不在报名时间内！");
            response.getWriter().write(JSON.toJSONString(map));
        }
    }


    /*查看讨论课报名情况
     * request:
     * path: seminarId
     * path: classId
     *
     * response:
     *   attendanceId;
     *   teamId;
     *   leader;
     *   teamOrder;
     *   classId;
     *   serial;   组序列 例：1-5
     *   present;      队伍   0:未开始,，1:正在进行
     *   pptName;
     *   pptUrl;
     *   reportName;
     *   reportUrl;
     *   seminarStatus;  0:未开始，1:正在进行  2:已结束
     *   teamStatus 0：未报名，1：已报名
     *   role :
     *      学生
     *           myTeamId
     *           myId
     *      老师
     *
    *seminarStatus为0
    * 如果是老师，只能查看而不能报名，如果有空位是未报名
    * 如果是学生，有空位的话是显示可报名
    * 如果pptUrl为空的话，显示serial 未提交，不为空的话显示serial
    *seminarStatus为1
    *  显示的是pptName，点击的话会下载ppt  ——》GET /attendance/{attendanceId}/powerpoint
    *seminarStatus为2 显示serial
    * */

    @GetMapping("/seminar/{seminarId}/class/{classId}/attendance")
    public void getSeminarEnroll(HttpServletRequest request, HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("classId") Long classId) throws IOException {
        JwtPayload jwtPayload=(JwtPayload)request.getAttribute("jwtPayload");

        KlassSeminar klassSeminar = klassService.selectKlassSeminarByKlassIdAndSeminarId(classId,seminarId);
        Byte seminarStatus = klassSeminar.getStatus();
        Byte teamStatus = (byte)0;
        List<TeamAttendanceVO> teamAttendanceVOS=seminarService.getSeminarEnroll(seminarId,classId);
        Long myTeamId=(long)0;
        Map map=new HashMap();

        if(jwtPayload.getRole().equals("student")) {
            String account = jwtPayload.getAccount();
            Student student = userService.selectByStudentAccount(account);
            List<TeamStudent> teamStudents = teamService.selectTeamStudentByStudentId(student.getId());
            TeamStudent teamStudent = teamStudents.get(0);
            myTeamId = teamStudent.getTeam().getId();
            map.put("myId",teamStudent.getStudent().getId());
            map.put("myTeamId",myTeamId);
            for(TeamAttendanceVO teamAttendanceVO:teamAttendanceVOS){
                if(teamAttendanceVO.getTeamId().equals(myTeamId)){
                    teamStatus = (byte)1;
                }
            }
        }

        for(int i=0;i<teamAttendanceVOS.size();i++){
            TeamAttendanceVO teamAttendanceVO=teamAttendanceVOS.get(i);
            map.put("teamAttendance"+i,teamAttendanceVO);
        }
        response.setStatus(200);
        map.put("seminarStatus",seminarStatus);
        map.put("teamStatus",teamStatus);
        map.put("role",jwtPayload.getRole());
        response.getWriter().write(JSON.toJSONString(map));
        //response.getWriter().write(JSON.toJSONString(teamAttendanceVOS));
    }

    /*开始讨论课之后的展示组状态修改（点击开始讨论课和下组展示的操作对应API）
     * request:
     * path: seminarId
     * path: classId
     * order  开始讨论课：0，下组展示：order++
    */
    @PutMapping("/seminar/{seminarId}/class/{classId}/attendance")
    public void selectTeamAttendance(HttpServletRequest request,HttpServletResponse response, @PathVariable("seminarId")Long seminarId,@PathVariable("classId") Long classId) throws IOException {
        Byte order=(byte)(Integer.parseInt(request.getParameter("order")));
        List<TeamAttendanceVO> teamAttendanceVOS=seminarService.seminarStart(seminarId,classId,order);
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(teamAttendanceVOS));
    }


}


