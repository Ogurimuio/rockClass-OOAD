package com.example.rockclass.mapper;

import com.example.rockclass.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    int deleteByAccount(String account);

    int insert(Student record);

    Student selectByAccount(String account);

    Student selectByStudentId(Long id);

    List<Student> selectAll();

    int updateByAccount(Student record);
}