package com.example.rockclass.dao;

import com.example.rockclass.entity.Course;
import com.example.rockclass.exception.CourseNotFoundException;
import com.example.rockclass.mapper.CourseMapper;
import com.example.rockclass.mapper.KlassStudentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDao {
    @Autowired
    CourseMapper coursemapper;

    @Autowired
    KlassStudentMapper klassStudentMapper;

    public List<Course> listCourseByTeacherId(long teacherId)throws IllegalArgumentException {
        List<Course> courses =coursemapper.selectByTeacherId(teacherId);
        return  courses;

    }
    public List<Course> selectAllCourse()throws IllegalArgumentException {
        List<Course> courses =coursemapper.selectAll();
        return  courses;
    }

    public Long insert( com.example.rockclass.entity.Course course) throws  IllegalArgumentException{
        coursemapper.insert(course);
        return  course.getId();


    }

    public Course selectCourseById(long id) throws IllegalArgumentException,CourseNotFoundException{
        Course course =coursemapper.selectByPrimaryKey(id);
        if (course ==null){
            throw  new CourseNotFoundException();
        }
        else {
            return course;
        }
    }

    public void deleteCourseById(long id){
        coursemapper.deleteByPrimaryKey(id);
    }

    public void updateCourse(Course course){coursemapper.updateByPrimaryKey(course);}
}

