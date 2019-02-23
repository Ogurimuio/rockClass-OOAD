package com.example.rockclass.mapper;

import com.example.rockclass.entity.TeamOrStrategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamOrStrategyMapper {
    int deleteById(Long id);

    int insert(TeamOrStrategy record);

    List<TeamOrStrategy> selectById(Long id);

    List<TeamOrStrategy> selectAll();

}