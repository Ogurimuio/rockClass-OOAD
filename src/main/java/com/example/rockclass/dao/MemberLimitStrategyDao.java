package com.example.rockclass.dao;

import com.example.rockclass.entity.MemberLimitStrategy;
import com.example.rockclass.mapper.MemberLimitStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberLimitStrategyDao {

    @Autowired
    MemberLimitStrategyMapper memberLimitStrategyMapper;

    public int deleteByPrimaryKey(Long id){
        return memberLimitStrategyMapper.deleteByPrimaryKey(id);
    }

    public Long insert(MemberLimitStrategy record){
        memberLimitStrategyMapper.insert(record);return record.getId();
    }

    public MemberLimitStrategy selectByPrimaryKey(Long id){
        return memberLimitStrategyMapper.selectByPrimaryKey(id);
    }

    public List<MemberLimitStrategy> selectAll(){
        return memberLimitStrategyMapper.selectAll();
    }

    public int updateByPrimaryKey(MemberLimitStrategy record)
    {
        return memberLimitStrategyMapper.updateByPrimaryKey(record);
    }
}