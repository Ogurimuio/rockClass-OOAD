package com.example.rockclass.mapper;

import com.example.rockclass.entity.Klass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KlassMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Klass record);

    Klass selectByPrimaryKey(Long id);

    List<Klass> selectAll();

    List<Klass> selectKlassByCourseId(@Param("courseId") Long courseId);

    int updateByPrimaryKey(Klass record);
}