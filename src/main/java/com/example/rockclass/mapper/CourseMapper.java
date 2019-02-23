package com.example.rockclass.mapper;

import com.example.rockclass.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Course record);

    Course selectByPrimaryKey(Long id);

    List<Course> selectAll();

    int updateByPrimaryKey(Course record);

    List<Course> selectByTeacherId (@Param("teacherId") Long teacherId );

}