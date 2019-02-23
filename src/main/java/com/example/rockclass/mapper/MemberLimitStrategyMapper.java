package com.example.rockclass.mapper;

import com.example.rockclass.entity.MemberLimitStrategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberLimitStrategyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberLimitStrategy record);

    MemberLimitStrategy selectByPrimaryKey(Long id);

    List<MemberLimitStrategy> selectAll();

    int updateByPrimaryKey(MemberLimitStrategy record);
}