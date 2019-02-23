package com.example.rockclass.mapper;

import com.example.rockclass.entity.ConflictCourseStrategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConflictCourseStrategyMapper {
    int deleteById(Long id);

    int insert(ConflictCourseStrategy record);

    List<ConflictCourseStrategy> selectById(Long id);

    List<ConflictCourseStrategy> selectAll();
}