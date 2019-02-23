package com.example.rockclass.mapper;

import com.example.rockclass.entity.TeamStrategy;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TeamStrategyMapper {
    int deleteByPrimaryKey(@Param("courseId") Long courseId, @Param("strategyId") Long strategyId, @Param("strategyName") String strategyName);

    int insert(TeamStrategy record);

    List <TeamStrategy> selectByCourseIdAndStrategyName(@Param("courseId")Long courseId,@Param("strategyName") String strategyName);

    List<TeamStrategy> selectAll();

    List<TeamStrategy> selectByCourseId(@Param("courseId") Long courseId);
}