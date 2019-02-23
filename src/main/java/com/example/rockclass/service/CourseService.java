package com.example.rockclass.service;


import com.example.rockclass.dao.*;
import com.example.rockclass.entity.*;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.vo.CourseInfoVo;
import com.example.rockclass.vo.CourseMemberLimitStrategyVo;
import com.example.rockclass.vo.CreateCourseVo;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RoundScoreDao roundScoreDao;


    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private KlassDao klassDao;
    @Autowired
    private RoundDao roundDao;
    @Autowired
    private TeamStrategyDao teamStrategyDao;
    @Autowired
    private MemberLimitStrategyDao memberLimitStrategyDao;
    @Autowired
    private TeamAndStrategyDao teamAndStrategyDao;
    @Autowired
    private TeamOrStrategyDao teamOrStrategyDao;
    @Autowired
    private ConflictCourseStrategyDao conflictCourseStrategyDao;
    @Autowired
    private CourseMemberLimitStrategyDao courseMemberLimitStrategyDao;
    @Autowired
    private ShareTeamApplicationDao shareTeamApplicationDao;
    @Autowired
    private ShareSeminarApplicationDao shareSeminarApplicationDao;

    public Long insertCourseByTeacherAccount(String teacherAccount, Course course) throws IllegalArgumentException {

        Teacher teacher = teacherDao.selectByAccount(teacherAccount);
        course.setTeacher(teacher);
        return courseDao.insert(course);
    }

    public List<Course> selectAllCourses()
    {
        return courseDao.selectAllCourse();
    }

    public List<CourseInfoVo> selectCourseVoByAccount(String account, String role) throws IllegalArgumentException
    {
        List<CourseInfoVo> courseInfoVos = new ArrayList<CourseInfoVo>();


        if (role.equals("student")) {
            List <Course>courses= studentDao.selectCourseByStudentAccount(account);
            List <Klass> klasses=studentDao.selectKlassByStudentAccount(account);
            for (int i=0;i<courses.size();i++)
            {
                CourseInfoVo courseInfoVo =new CourseInfoVo();
                courseInfoVo.setId(courses.get(i).getId());
                courseInfoVo.setName(courses.get(i).getCourseName());
                if(courses.get(i).getSeminarMainCourseId()!=null)
                    courseInfoVo.setShareSeminar(true);
                else  courseInfoVo.setShareSeminar(false);
                if (courses.get(i).getTeamMainCourseId()!=null)
                    courseInfoVo.setShareTeam(true);
                else  courseInfoVo.setShareTeam(false);
                courseInfoVo.setTeacherName(courses.get(i).getTeacher().getTeacherName());
                courseInfoVo.setClassId(klasses.get(i).getId());
                String  className=klasses.get(i).getGrade().toString()+"-"+klasses.get(i).getKlassSerial().toString();
                courseInfoVo.setClassName(className);
                courseInfoVos.add(courseInfoVo);
            }
        }
        else {
            Teacher teacher=teacherDao.selectByAccount(account);
            List <Course> courses= courseDao.listCourseByTeacherId(teacher.getId());
            List <Klass> klasses = new ArrayList<Klass>();
            for (int i=0;i<courses.size();i++)
            {
                List<Klass> klasses1 = klassDao.selectKlassByCourseId(courses.get(i).getId());
                if (klasses1.size()==0){
                    CourseInfoVo courseInfoVo =new CourseInfoVo();
                    courseInfoVo.setName(courses.get(i).getCourseName());
                    courseInfoVo.setId(courses.get(i).getId());
                    courseInfoVos.add(courseInfoVo);
                }
                for(Klass klass:klasses1)
                    klasses.add(klass);
            }
            for (int i=0;i<klasses.size();i++)
            {
                CourseInfoVo courseInfoVo =new CourseInfoVo();
                courseInfoVo.setId(klasses.get(i).getCourse().getId());
                courseInfoVo.setName(klasses.get(i).getCourse().getCourseName());
                if(klasses.get(i).getCourse().getSeminarMainCourseId()!=null)
                    courseInfoVo.setShareSeminar(true);
                else  courseInfoVo.setShareSeminar(false);
                if (klasses.get(i).getCourse().getTeamMainCourseId()!=null)
                    courseInfoVo.setShareTeam(true);
                else  courseInfoVo.setShareTeam(false);
                courseInfoVo.setTeacherName(teacher.getTeacherName());
                courseInfoVo.setClassId(klasses.get(i).getId());
                String  className=klasses.get(i).getGrade().toString()+"-"+klasses.get(i).getKlassSerial().toString();
                courseInfoVo.setClassName(className);
                courseInfoVos.add(courseInfoVo);
            }
        }
        return  courseInfoVos;
    }

    public List<Round> selectRoundByCourseId(Long courseId){
        return   roundDao.selectRoundByCourseId(courseId);
    }

    public Course selectCourseByCourseId(Long courseId) throws CourseNotFoundException {
        try {
            Course course= courseDao.selectCourseById(courseId);
            return course;
        }
        catch (CourseNotFoundException e){
            return null;
        }

    }

    public void deleteCourseByCourseId(Long courseId)
    {
        courseDao.deleteCourseById(courseId);
    }


    public Long createCourseByCourseVo(CreateCourseVo createCourseVo, String teacherAccount) throws CourseNotFoundException {


        Course course = new Course();
        course.setCourseName(createCourseVo.getName());
        course.setIntroduction(createCourseVo.getIntro());
        course.setTeamStartTime(createCourseVo.getStartTeamTime());
        course.setTeamEndTime(createCourseVo.getEndTeamTime());
        course.setPresentationPercentage(createCourseVo.getPresentationWeight());
        course.setReportPercentage(createCourseVo.getReportWeight());
        course.setQuestionPercentage(createCourseVo.getQuestionWeight());
        Teacher teacher = teacherDao.selectByAccount(teacherAccount);
        course.setTeacher(teacher);
        Long courseId=courseDao.insert(course);
        course.setId(courseId);

        MemberLimitStrategy memberLimitStrategy = new MemberLimitStrategy();
        if(createCourseVo.getMinMemberNumber()!=null) memberLimitStrategy.setMinMember(createCourseVo.getMinMemberNumber());
        if(createCourseVo.getMaxMemberNumber()!=null) memberLimitStrategy.setMaxMember(createCourseVo.getMaxMemberNumber());
        Long teamAndStrategyId = teamAndStrategyDao.selectMaxId()+(long)1;
        if (memberLimitStrategy!=null) {
            Long memberLimitId = memberLimitStrategyDao.insert(memberLimitStrategy);
            TeamAndStrategy teamAndStrategyML = new TeamAndStrategy();
            teamAndStrategyML.setStrategyId(memberLimitId);
            teamAndStrategyML.setStrategyName("MemberLimitStrategy");
            teamAndStrategyML.setId(teamAndStrategyId);
            teamAndStrategyDao.insert(teamAndStrategyML);}
        //从课程人数限制

        Long teamOrStrategyId = teamOrStrategyDao.selectMaxId()+(long)1;
        if (createCourseVo.getCourseMemberLimitStrategys()!=null) {
            for (CourseMemberLimitStrategyVo courseMemberLimitStrategyVo : createCourseVo.getCourseMemberLimitStrategys()) {
                CourseMemberLimitStrategy courseMemberLimitStrategy = new CourseMemberLimitStrategy();
                courseMemberLimitStrategy.setCourse(courseDao.selectCourseById(courseMemberLimitStrategyVo.getCourseId()));
                courseMemberLimitStrategy.setMaxMember(courseMemberLimitStrategyVo.getMaxMemberNumber());
                courseMemberLimitStrategy.setMinMember(courseMemberLimitStrategyVo.getMinMemberNumber());
                Long id = courseMemberLimitStrategyDao.insert(courseMemberLimitStrategy);
                if (createCourseVo.getCourseMemberLimitStrategyIsConflict().equals(Byte.parseByte("1"))) {
                    TeamOrStrategy teamOrStrategy = new TeamOrStrategy();
                    teamOrStrategy.setId(teamOrStrategyId);
                    teamOrStrategy.setStrategyId(id);
                    teamOrStrategy.setStrategyName("CourseMemberLimitStrategy");
                    teamOrStrategyDao.insert(teamOrStrategy);
                    TeamAndStrategy teamAndStrategyOR = new TeamAndStrategy();
                    teamAndStrategyOR.setId(teamAndStrategyId);
                    teamAndStrategyOR.setStrategyName("TeamOrStrategy");
                    teamAndStrategyOR.setStrategyId(teamOrStrategyId);
                    teamAndStrategyDao.insert(teamAndStrategyOR);
                } else {
                    TeamAndStrategy teamAndStrategyCMLS = new TeamAndStrategy();
                    teamAndStrategyCMLS.setStrategyId(id);
                    teamAndStrategyCMLS.setStrategyName("CourseMemberLimitStrategy");
                    teamAndStrategyCMLS.setId(teamAndStrategyId);
                    teamAndStrategyDao.insert(teamAndStrategyCMLS);
                }
                TeamStrategy teamStrategyAnd = new TeamStrategy();
                teamStrategyAnd.setStrategyId(teamAndStrategyId);
                teamStrategyAnd.setCourse(course);
                teamStrategyAnd.setStrategyName("TeamAndStrategy");
                Byte serial = teamStrategyDao.selectCourseMaxSerialByCourseId(courseId);
                Byte mySerial = (byte) (serial.byteValue() + (byte) 1);
                teamStrategyAnd.setStrategySerial(mySerial);
                teamStrategyDao.insert(teamStrategyAnd);
            }
        }

        if(createCourseVo.getConflictCourseStrategys()!=null)
        //插入冲突课程策略
        {
            for(int i = 0; i< createCourseVo.getConflictCourseStrategys().size(); i++) {
                Long conflictCourseStrategyId = conflictCourseStrategyDao.selectMaxId() + 1;
                for (int j = 0; j < createCourseVo.getConflictCourseStrategys().get(i).size(); j++) {
                    ConflictCourseStrategy conflictCourseStrategy = new ConflictCourseStrategy();
                    conflictCourseStrategy.setCourse(courseDao.selectCourseById(createCourseVo.getConflictCourseStrategys().get(i).get(j)));
                    conflictCourseStrategy.setId(conflictCourseStrategyId);
                    conflictCourseStrategyDao.insert(conflictCourseStrategy);
                }
                TeamStrategy teamStrategyCCS = new TeamStrategy();
                teamStrategyCCS.setStrategyName("ConflictCourseStrategy");
                teamStrategyCCS.setStrategyId(conflictCourseStrategyId);
                teamStrategyCCS.setCourse(course);
                Byte serial = teamStrategyDao.selectCourseMaxSerialByCourseId(courseId);
                Byte mySerial = (byte) (serial.byteValue() + (byte) 1);
                teamStrategyCCS.setStrategySerial(mySerial);
                teamStrategyDao.insert(teamStrategyCCS);
            }
        }
        return courseId;
    }

    public List<ShareTeamApplication> selectShareTeamApplicationByCourseIdAndStatus(Long courseId,Byte status){
        return shareTeamApplicationDao.selectByCourseId(courseId,status);
    }

    public List<ShareSeminarApplication> selectShareSeminarApplicationByCourseIdAndStatus(Long courseId,Byte status){
        return shareSeminarApplicationDao.selectByCourseId(courseId,status);
    }

    public int deleteTeamShare(Long teamShareId,String teacherAccount)
    {
        Long teacherId=teacherDao.selectByAccount(teacherAccount).getId();
        ShareTeamApplication shareTeamApplication=shareTeamApplicationDao.selectByPrimaryKey(teamShareId);
        if(shareTeamApplication==null) return 404;
        if((!shareTeamApplication.getMainCourse().getTeacher().getId().equals(teacherId))&&
                !(shareTeamApplication.getSubCourseTeacher().getId().equals(teacherId)))
            return 403;
        shareTeamApplicationDao.deleteByPrimaryKey(teamShareId);
        return 204;
    }

    public int deleteSeminarShare(Long seminarShareId,String teacherAccount)
    {
        Long teacherId=teacherDao.selectByAccount(teacherAccount).getId();
        ShareSeminarApplication shareSeminarApplication=shareSeminarApplicationDao.selectByPrimaryKey(seminarShareId);
        if(shareSeminarApplication==null) return 404;
        if((!shareSeminarApplication.getMainCourse().getTeacher().getId().equals(teacherId))&&
                !(shareSeminarApplication.getSubCourseTeacher().getId().equals(teacherId)))
            return 403;
        shareTeamApplicationDao.deleteByPrimaryKey(seminarShareId);
        return 204;
    }
    public  Long creatTeamShareRequestByCourseId(Long mainCourseId,Long subCourseId)throws CourseNotFoundException{
        Course mainCourse=courseDao.selectCourseById(mainCourseId);
        Course subCourse = courseDao.selectCourseById(subCourseId);
        if (subCourse==null) return null;
        ShareTeamApplication shareTeamApplication = new ShareTeamApplication();
        shareTeamApplication.setMainCourse(mainCourse);
        shareTeamApplication.setSubCourse(subCourse);
        shareTeamApplication.setStatus(Byte.parseByte("0"));
        shareTeamApplication.setSubCourseTeacher(subCourse.getTeacher());
        return shareTeamApplicationDao.insert(shareTeamApplication);
    }
    public  Long creatSeminarShareRequestByCourseId(Long mainCourseId,Long subCourseId)throws CourseNotFoundException{
        Course mainCourse=courseDao.selectCourseById(mainCourseId);
        Course subCourse = courseDao.selectCourseById(subCourseId);
        if (subCourse==null) return null;
        ShareSeminarApplication shareSeminarApplication = new ShareSeminarApplication();
        shareSeminarApplication.setMainCourse(mainCourse);
        shareSeminarApplication.setSubCourse(subCourse);
        shareSeminarApplication.setStatus(Byte.parseByte("0"));
        shareSeminarApplication.setSubCourseTeacher(subCourse.getTeacher());
        return shareSeminarApplicationDao.insert(shareSeminarApplication);
    }


    public void getScoreExcel(HttpServletResponse response,Long courseId) throws CourseNotFoundException, IOException {
        List<Round> rounds= roundDao.selectRoundByCourseId(courseId);
        List<RoundScore> roundScores= new ArrayList<RoundScore>();
        int teamNumber=0;
        int roundNumber=rounds.size();
        for(Round round:rounds) {
            List<RoundScore> roundScore = roundScoreDao.selectByRoundId(round.getId());
            teamNumber=roundScore.size();
            roundScores.addAll(roundScore);
        }

        String fileName=courseDao.selectCourseById(courseId).getCourseName()+"讨论课成绩表.xls";
        response.setContentType("application/excel");
        response.setHeader("Content-disposition","attachment;filename=" + fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        /////////response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"),"iso-8859-1")


        HSSFWorkbook workbook = new HSSFWorkbook();
        //获取文档信息，并配置
        DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();


        /*// 生成一种样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = workbook.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);*/


        HSSFSheet sheet = workbook.createSheet("讨论课成绩表");
        String[] headerList1={"round","team","totalScore","presentationScore","QuestionScore","ReportScore"};


        // 生成表格的第一行
        // 第一行表头
        HSSFRow row = sheet.createRow(0);

        for(int i = 0; i < headerList1.length; i++) {

            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headerList1[i]);
            sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
        }
        for(int j=1;j<teamNumber*roundNumber+1;j++){
            HSSFRow rowJ = sheet.createRow(j);
            HSSFCell cell0 = rowJ.createCell(0);
            cell0.setCellValue("第"+roundScores.get(j-1).getRound().getRoundSerial()+"轮");

            HSSFCell cell1 = rowJ.createCell(1);
            cell1.setCellValue(roundScores.get(j-1).getTeam().getKlassSerial()+"-"+roundScores.get(j-1).getTeam().getTeamSerial());

            HSSFCell cell2 = rowJ.createCell(2);
            cell2.setCellValue(roundScores.get(j-1).getTotalScore().toString());

            HSSFCell cell3 = rowJ.createCell(3);
            cell3.setCellValue(roundScores.get(j-1).getPresentationScore().toString());

            HSSFCell cell4 = rowJ.createCell(4);
            cell4.setCellValue(roundScores.get(j-1).getQuestionScore().toString());

            HSSFCell cell5 = rowJ.createCell(5);
            cell5.setCellValue(roundScores.get(j-1).getReportScore().toString());

        }

        workbook.write(response.getOutputStream());

    }
}