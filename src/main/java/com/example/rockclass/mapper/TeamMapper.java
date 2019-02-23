package com.example.rockclass.mapper;

import com.example.rockclass.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamMapper {
    Long deleteByPrimaryKey(Long id);

    Long insert(Team record);

    Team selectByPrimaryKey(Long id);

    List<Team> selectByCourseId(@Param("courseId") Long courseId);

    List<Team>  selectByKlassId(@Param("klassId") Long klassId);

    List<Team> selectAll();

    Long updateByPrimaryKey(Team record);
}