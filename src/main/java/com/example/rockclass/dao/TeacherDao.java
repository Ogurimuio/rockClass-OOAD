package com.example.rockclass.dao;

import com.example.rockclass.entity.Teacher;
import com.example.rockclass.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherDao {

    @Autowired
    private TeacherMapper teacherMapper;

    public Teacher selectByAccount(String account){
        return teacherMapper.selectByAccount(account);
    }

    public void updatePassword(String account,String password){
        Teacher teacher=teacherMapper.selectByAccount(account);
        teacher.setPassword(password);
        teacherMapper.updateByAccount(teacher);
    }
    public void updateEmail(String account,String email){
        Teacher teacher=teacherMapper.selectByAccount(account);
        teacher.setEmail(email);
        teacherMapper.updateByAccount(teacher);
    }
    public Teacher active(String account, String password) {
        Teacher teacher = teacherMapper.selectByAccount(account);
        teacher.setPassword(password);
        teacher.setIsActive((byte)1);
        teacherMapper.updateByAccount(teacher);
        return teacher;
    }
}
