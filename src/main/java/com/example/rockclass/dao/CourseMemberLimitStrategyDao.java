package com.example.rockclass.dao;

import com.example.rockclass.entity.CourseMemberLimitStrategy;
import com.example.rockclass.mapper.CourseMemberLimitStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CourseMemberLimitStrategyDao {

    @Autowired
    private CourseMemberLimitStrategyMapper courseMemberLimitStrategyMapper;
    public int deleteByPrimaryKey(Long id){return  courseMemberLimitStrategyMapper.deleteByPrimaryKey(id);}

    public Long insert(CourseMemberLimitStrategy record){courseMemberLimitStrategyMapper.insert(record);
        return  record.getId();}

    public CourseMemberLimitStrategy selectByPrimaryKey(Long id){return courseMemberLimitStrategyMapper.selectByPrimaryKey(id);}

    public List<CourseMemberLimitStrategy> selectAll(){return courseMemberLimitStrategyMapper.selectAll();}

    public int updateByPrimaryKey(CourseMemberLimitStrategy record){return courseMemberLimitStrategyMapper.updateByPrimaryKey(record);}
}
