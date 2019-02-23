package com.example.rockclass.service;

import com.example.rockclass.dao.*;
import com.example.rockclass.entity.Klass;
import com.example.rockclass.entity.KlassSeminar;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class KlassService {

    @Autowired
    private KlassDao klassDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private KlassStudentDao klassStudentDao;

    @Autowired
    private KlassSeminarDao klassSeminarDao;

    public Long insertKlassbyClassVo(Long courseId,ClassVo classVo)throws CourseNotFoundException {
        Klass klass = new Klass();
        String klassName=classVo.getName();
        String gradeStr =klassName.substring(0,4);
        String klassSerialStr =klassName.substring(5);
        klass.setGrade(Integer.parseInt(gradeStr));
        klass.setKlassSerial(Byte.parseByte(klassSerialStr));
        klass.setKlassLocation(classVo.getClassroom());
        klass.setKlassTime(classVo.getTime());
        klass.setCourse(courseDao.selectCourseById(courseId));
        return klassDao.insert(klass);
    }

    public List<Klass> selectClassByCourseId(Long courseId){
        return klassDao.selectKlassByCourseId(courseId);
    }

    public Klass selectClassByClassId(Long classId){return  klassDao.selectKlassByKlassId(classId);}

    public int importClassStudentExcel(MultipartFile file, Long classId)throws IOException {
        Klass klass = klassDao.selectKlassByKlassId(classId);
        String fileName = file.getOriginalFilename();
        //System.out.println(fileName);
        return studentDao.importAllClassStudent(fileName,file,klass);
    }

    public void deleteClass(Long classId)
    {
        klassDao.deleteByPrimaryKey(classId);
        klassStudentDao.deleteByKlassId(classId);
    }

    public KlassSeminar selectKlassSeminarByKlassIdAndSeminarId(Long classId, Long seminarId) {
        return klassSeminarDao.selectByKlassIdAndSeminarId(classId,seminarId);
    }
}