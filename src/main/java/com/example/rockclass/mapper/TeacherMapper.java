package com.example.rockclass.mapper;

import com.example.rockclass.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {

    Teacher selectByAccount(String account);

    List<Teacher> selectAll();

    Teacher selectByTeacherId(Long id);

    int updateByAccount(Teacher record);
}