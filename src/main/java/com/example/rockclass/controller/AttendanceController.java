package com.example.rockclass.controller;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.entity.Attendance;
import com.example.rockclass.entity.Question;
import com.example.rockclass.service.AttendanceService;
import com.example.rockclass.service.FileService;
import com.example.rockclass.service.RoundService;
import com.example.rockclass.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


@RestController
public class AttendanceController {

    @Value("${file.uploadFolder}")
    private String UPLOAD_FOLDER;


    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private FileService fileService;

    @Autowired
    private RoundService roundService;

    /*来自讨论课报名之后获得attendanceId
     * 取消报名
     * request:
     * path: attendanceId
     *
     * */
    @DeleteMapping("/attendance/{attendanceId}")
    public void deleteEnroll(@PathVariable("attendanceId") Long attendanceId, HttpServletResponse response) {
        if (attendanceService.selectByAttendanceId(attendanceId) != null) {
            attendanceService.deleteEnroll(attendanceId);
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }

    }

    /*来自讨论课报名之后获得attendanceId
     * 上传PPT
     * request:
     * path: attendanceId
     *  file
     * response:
     *  url:相对路径
     * */
    @PostMapping("/attendance/{attendanceId}/powerpoint")
    public void uploadPowerPoint(@PathVariable("attendanceId") Long attendanceId, HttpServletResponse response, MultipartFile file) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Attendance attendance = attendanceService.selectByAttendanceId(attendanceId);
        String url = UPLOAD_FOLDER+"/seminar" + attendance.getKlassSeminar().getSeminar().getSeminarSerial().toString() + "/ppt/";

        int status=attendanceService.uploadPPT(file,url,attendanceId);
        response.setStatus(status);

        if(status==200){
            Map map = new HashMap();
            map.put("url",url+file.getOriginalFilename());
            response.getWriter().write(JSON.toJSONString(map));
        }
    }


    /*来自讨论课报名之后获得attendanceId
     * 上传报告
     * request:
     * path: attendanceId
     *  file
     * response:
     *  url:相对路径
     * */
    @PostMapping("/attendance/{attendanceId}/report")
    public void uploadReport(@PathVariable("attendanceId") Long attendanceId, HttpServletResponse response, MultipartFile file) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Attendance attendance = attendanceService.selectByAttendanceId(attendanceId);


        Date now = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(now);
        Date ReportDdl= attendance.getKlassSeminar().getReportDdl();
        Calendar ddl = Calendar.getInstance();
        ddl.setTime(ReportDdl);
        if (date.after(ddl)){
            response.setStatus(400);
            Map map=new HashMap();
            map.put("information","报告提交已截止！");
            response.getWriter().write(JSON.toJSONString(map));
            return;
        }

        String url = UPLOAD_FOLDER+"/seminar" + attendance.getKlassSeminar().getSeminar().getSeminarSerial().toString() + "/report/";
        int status=attendanceService.uploadReport(file,url,attendanceId);
        response.setStatus(status);
        if(status==200){
            Map map = new HashMap();
            map.put("url",url+file.getOriginalFilename());
            response.getWriter().write(JSON.toJSONString(map));
        }
    }


    /*来自讨论课报名之后获得attendanceId
     * 重传ppt
     * request:
     * path: attendanceId
     *
     *
     *
     * response:
     *  url
     * */

    @PutMapping("/attendance/{attendanceId}/powerpoint")
    public void updatePowerPoint(@PathVariable("attendanceId") Long attendanceId, HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws IOException {
        response.setContentType("application/json;charset=utf-8");

        Attendance attendance = attendanceService.selectByAttendanceId(attendanceId);
        String url = UPLOAD_FOLDER+"/seminar" + attendance.getKlassSeminar().getSeminar().getSeminarSerial().toString() + "/ppt/";
        int status=attendanceService.updatePPT(file,url,attendanceId);
        response.setStatus(status);
        if(status==200){
            Map map = new HashMap();
            map.put("url",url+file.getOriginalFilename());
            response.getWriter().write(JSON.toJSONString(map));
        }
    }

    /*来自讨论课报名之后获得attendanceId
     * 重传报告
     * request:
     * path: attendanceId
     *
     *
     * response:
     *  url
     * */

    @PutMapping("/attendance/{attendanceId}/report")
    public void updateReport(@PathVariable("attendanceId") Long attendanceId, HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Attendance attendance = attendanceService.selectByAttendanceId(attendanceId);
        Date now = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(now);
        Date ReportDdl= attendance.getKlassSeminar().getReportDdl();
        Calendar ddl = Calendar.getInstance();
        ddl.setTime(ReportDdl);
        if (date.after(ddl)){
            response.setStatus(400);
            Map map=new HashMap();
            map.put("information","报告提交已截止！");
            response.getWriter().write(JSON.toJSONString(map));
            return;
        }
        String url = UPLOAD_FOLDER+"/seminar" + attendance.getKlassSeminar().getSeminar().getSeminarSerial().toString() + "/report/";
        int status=attendanceService.updateReport(file,url,attendanceId);
        response.setStatus(status);
        if(status==200){
            Map map = new HashMap();
            map.put("url",url+file.getOriginalFilename());
            response.getWriter().write(JSON.toJSONString(map));
        }
    }

    /*来自讨论课报名之后获得attendanceId
     * 下载PPT
     * request:
     * path: attendanceId
     * response:
     *  url
     * */

    @GetMapping("/attendance/{attendanceId}/powerpoint")
    public void downloadPowerPoint(@PathVariable("attendanceId") Long attendanceId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String pptUrl = attendanceService.selectByAttendanceId(attendanceId).getPptUrl();

        String fileName = pptUrl.substring(17);
        fileService.download(response,pptUrl,fileName);
       /* Map map = new HashMap();
        map.put("filePath",pptUrl);
        map.put("fileName",fileName);
        response.getWriter().write(JSON.toJSONString(map));*/
    }


    /*来自讨论课报名之后获得attendanceId
     * 下载报告
     * request:
     * path: attendanceId
     * response:
     *  url
     * */

    @GetMapping("/attendance/{attendanceId}/report")
    public void downloadReport(@PathVariable("attendanceId") Long attendanceId, HttpServletResponse response, String filePath) throws IOException {
        response.setContentType("application/json;charset=utf-8");


        String reportUrl = attendanceService.selectByAttendanceId(attendanceId).getReportUrl();
        String fileName = reportUrl.substring(21);
        fileService.download(response,UPLOAD_FOLDER+filePath,fileName);
        /*Map map = new HashMap();
        map.put("filePath",UPLOAD_FOLDER+reportUrl);
        map.put("fileName",fileName);
        response.getWriter().write(JSON.toJSONString(map));*/
    }



    /*
     * 显示教师下载ppt页面 ——》下载的话直接跳转到@GetMapping("/attendance/{attendanceId}/report")
     * request:
     * path: seminarId
     * path: classId
     *
     * response:
     *  attendanceId
     *  teamId
     *  teamOrder
     *  reportName
     *  reportUrl
     * */
    @GetMapping("/seminar/{seminarId}/class/{classId}/report")
    public void getReportList(@PathVariable("seminarId") Long seminarId, @PathVariable("classId") Long classId, HttpServletResponse response) throws IOException {
        List<Attendance> attendanceList = attendanceService.getReportList(seminarId,classId);
        if (attendanceList==null){
            response.setStatus(400);
        }
        else {
            List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
            for (Attendance attendance:attendanceList){
                Map map = new HashMap();
                map.put("attendanceId",attendance.getId());
                map.put("teamId",attendance.getTeam().getId());
                map.put("teamOrder",attendance.getTeamOrder());
                map.put("reportName",attendance.getReportName());
                map.put("reportUrl",attendance.getReportUrl());
                list1.add(map);
            }
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(list1));
        }
    }


    /*开始讨论课之后的学生提问
     * request:
     * path: attendanceId (前端根据小组的present传送)
     * myTeamId :当前登录人的小组Id
     * myId    :当前登录人的Id
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/attendance/{attendanceId}/question")
    public void postQuestion(HttpServletRequest request, HttpServletResponse response, @PathVariable("attendanceId")Long attendanceId) throws IOException {
        Attendance attendance = attendanceService.selectByAttendanceId(attendanceId);
        Long teamId = (long)(Integer.parseInt(request.getParameter("myTeamId")));
        Long myId = (long)(Integer.parseInt(request.getParameter("myId")));
        if(teamId.equals(attendance.getTeam().getId())){
            response.setStatus(400);
            response.getWriter().write("自己队伍不能提问");
        }
        else {
            Long questionId = attendanceService.postQuestion(attendance.getKlassSeminar().getId(),attendanceId,teamId,myId);
            response.setStatus(200);
            Map map = new HashMap();
            map.put("questionId",questionId);
            response.getWriter().write(JSON.toJSONString(map));
        }
    }


    /*
    * request:
    * path: attendanceId (前端根据小组的present传送)
    *
    * response:
    *   questionId
    *   teamId
    *   serial   例:1-5
    * */
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping("/attendance/{attendanceId}/question")
    public void selectQuestion(HttpServletResponse response, @PathVariable("attendanceId")Long attendanceId) throws IOException {
        Question question = attendanceService.selectQuestion(attendanceId);
        if (question.getId()!=null) {
            Map map = new HashMap();
            map.put("questionId",question.getId());
            map.put("teamId",question.getTeam().getId());
            map.put("serial",question.getTeam().getKlass().getKlassSerial()+"-"+question.getTeam().getTeamSerial());
            response.setStatus(200);
            response.getWriter().write(JSON.toJSONString(map));
        }
        else {
            response.setStatus(400);
            response.getWriter().write("目前无提问");
        }
    }


    @PutMapping("/question/{questionId}")
    public void putQuestionScore(@PathVariable("questionId") Long questionId,HttpServletRequest httpServletRequest,HttpServletResponse response)
    {
        BigDecimal questionScore = new BigDecimal(httpServletRequest.getParameter("questionScore"));
        roundService.putQuestionScore(questionId,questionScore);
        response.setStatus(200);
    }
    @GetMapping("/attendance/{attendanceId}/questionlist")
    public void questionList(HttpServletResponse response, @PathVariable("attendanceId")Long attendanceId) throws IOException {
        List<QuestionVO> questions =attendanceService.questionList(attendanceId);
        response.getWriter().write(JSON.toJSONString(questions));
        response.setStatus(200);
    }


}