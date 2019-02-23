package com.example.rockclass.dao;

import com.example.rockclass.entity.Student;
import com.example.rockclass.entity.Teacher;
import com.example.rockclass.entity.User;
import com.example.rockclass.mapper.StudentMapper;
import com.example.rockclass.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    public User findByAccount(String account){
        User user = new User();

        if (teacherMapper.selectByAccount(account)!=null){

            Teacher teacher=teacherMapper.selectByAccount(account);
            user.setId(teacher.getId());
            user.setAccount(teacher.getAccount());
            user.setPassword(teacher.getPassword());
            user.setRole("teacher");
            user.setName(teacher.getTeacherName());
            user.setActive(teacher.getIsActive());
        }
        else if (studentMapper.selectByAccount(account)!=null){
            Student student=studentMapper.selectByAccount(account);
            user.setId(student.getId());
            user.setAccount(student.getAccount());
            user.setPassword(student.getPassword());
            user.setRole("student");
            user.setName(student.getStudentName());
            user.setActive(student.getIsActive());
        }
        return user;
    }
}
