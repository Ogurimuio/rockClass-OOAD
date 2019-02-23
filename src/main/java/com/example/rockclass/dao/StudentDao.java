package com.example.rockclass.dao;

import com.example.rockclass.entity.Course;
import com.example.rockclass.entity.Klass;
import com.example.rockclass.entity.KlassStudent;
import com.example.rockclass.entity.Student;
import com.example.rockclass.mapper.CourseMapper;
import com.example.rockclass.mapper.KlassMapper;
import com.example.rockclass.mapper.KlassStudentMapper;
import com.example.rockclass.mapper.StudentMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDao {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private KlassStudentMapper klassStudentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private KlassMapper klassMapper;

    public Student selectByAccount(String account){
        return studentMapper.selectByAccount(account);
    }

    public List<Student> students(){return studentMapper.selectAll();}

    public void updatePassword(String account,String password){
        Student student=studentMapper.selectByAccount(account);
        student.setPassword(password);
        studentMapper.updateByAccount(student);
    }
    public void updateEmail(String account,String email){
        Student student=studentMapper.selectByAccount(account);
        student.setEmail(email);
        studentMapper.updateByAccount(student);
    }

    public Student active(String account, String password, String email) {
        Student student = studentMapper.selectByAccount(account);
        student.setPassword(password);
        student.setEmail(email);
        student.setIsActive((byte)1);
        studentMapper.updateByAccount(student);
        return student;
    }


    /*后续添加*/


    public Long insert(Student student){
        studentMapper.insert(student);
        return  student.getId();
    }

    public Student selectByStudentId(Long studentId){
        return studentMapper.selectByStudentId(studentId);
    }
    public List<Course> selectCourseByStudentAccount(String studentAccount){
        Student student = studentMapper.selectByAccount(studentAccount);
        List <KlassStudent> klassStudents =klassStudentMapper.selectByStudentId(student.getId());
        List <Course> courses=new ArrayList<Course>();
        for(int i = 0 ; i < klassStudents.size() ; i++) {
            courses.add(klassStudents.get(i).getCourse());
        }
        return courses;
    }
    public List<Klass> selectKlassByStudentAccount(String studentAccount){
        Student student = studentMapper.selectByAccount(studentAccount);
        List <KlassStudent> klassStudents =klassStudentMapper.selectByStudentId(student.getId());
        List <Klass> klasses=new ArrayList<Klass>();
        for(int i = 0 ; i < klassStudents.size() ; i++) {
            klasses.add(klassStudents.get(i).getKlass());
        }
        return klasses;
    }

    public int importAllClassStudent(String fileName, MultipartFile file, Klass klass)throws IOException {
        if (file == null) return 400;
        boolean notNull = false;
        boolean isExcel2003 = true;
        if (!fileName.matches(".+\\.(?i)(xlsx)") && !fileName.matches(".+\\.(?i)(xls)")) {
            return 400;
        }
        if (fileName.matches(".+\\.(?i)(xlsx)"))
            isExcel2003 = false;
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else wb = new XSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        if (sheet!=null) notNull=true;
        List<Student> students = new ArrayList<Student>();
        List<KlassStudent> klassStudents = new ArrayList<KlassStudent>();
        for (int r=1;r<=sheet.getLastRowNum();r++){
            Row row = sheet.getRow(r);
            if(row == null){
                continue;
            }
            Student student = new Student();
            if (row.getCell(0)==null) continue;;
            student.setAccount(row.getCell(0).getStringCellValue().replaceAll("[\\s\\u00A0]+","").trim());
            student.setPassword("123456");
            student.setStudentName(row.getCell(1).getStringCellValue().replaceAll("[\\s\\u00A0]+","").trim());
            student.setIsActive(Byte.parseByte("0"));
            students.add(student);
        }
        if (klassStudentMapper.selectByKlassId(klass.getId())!=null){
        klassStudentMapper.deleteByKlassId(klass.getId());}
        for (Student student:students){
            KlassStudent klassStudent = new KlassStudent();
            if (studentMapper.selectByAccount(student.getAccount())==null) {
                studentMapper.insert(student);}
                klassStudent.setStudent(studentMapper.selectByAccount(student.getAccount()));
                klassStudent.setKlass(klass);
                klassStudent.setCourse(klass.getCourse());
                klassStudentMapper.insert(klassStudent);
            }
        return 200;
    }
}
