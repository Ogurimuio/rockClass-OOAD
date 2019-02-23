package com.example.rockclass.dao;

import com.example.rockclass.entity.Round;
import com.example.rockclass.mapper.RoundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoundDao {

    @Autowired
    private RoundMapper roundMapper;

    public List<Round> selectRoundByCourseId(Long courseId){
        return roundMapper.selectRoundByCourseId(courseId);
    }

    public Long insert(Round round){
        roundMapper.insert(round);
        return  round.getId();
    }

    public int deleteByPrimaryKey(Long id){
        return roundMapper.deleteByPrimaryKey(id);
    };

    public Round selectByPrimaryKey(Long id){
        return roundMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(Round record){
        return roundMapper.updateByPrimaryKey(record);
    }

    public Round selectRoundByCourseIdAndRoundSerial(Long courseId,Byte roundSerial){
        return roundMapper.selectRoundByCourseIdAndRoundSerial(courseId,roundSerial);
    }
}
