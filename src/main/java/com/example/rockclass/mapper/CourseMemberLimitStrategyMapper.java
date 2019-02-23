package com.example.rockclass.mapper;

import com.example.rockclass.entity.CourseMemberLimitStrategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMemberLimitStrategyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CourseMemberLimitStrategy record);

    CourseMemberLimitStrategy selectByPrimaryKey(Long id);

    List<CourseMemberLimitStrategy> selectAll();

    int updateByPrimaryKey(CourseMemberLimitStrategy record);
}