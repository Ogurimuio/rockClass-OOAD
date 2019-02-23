package com.example.rockclass.mapper;

import com.example.rockclass.entity.TeamAndStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TeamAndStrategyMapper {
    int deleteById(Long id);

    int insert(TeamAndStrategy record);

    List<TeamAndStrategy> selectById(Long id);

    List<TeamAndStrategy> selectAll();

    List<TeamAndStrategy> selectByIdAndStrategyName(@Param("id") Long id,@Param("strategyName") String strategyName);
}