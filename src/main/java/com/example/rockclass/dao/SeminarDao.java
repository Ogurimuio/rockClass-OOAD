package com.example.rockclass.dao;

import com.example.rockclass.entity.Seminar;
import com.example.rockclass.mapper.SeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeminarDao {

    @Autowired
    private SeminarMapper seminarMapper;
    public int deleteByPrimaryKey(Long id){
        return seminarMapper.deleteByPrimaryKey(id);
    }

    public Long insert(Seminar record){
        seminarMapper.insert(record);
        return record.getId();
    }

    public Seminar selectByPrimaryKey(Long id){
        return seminarMapper.selectByPrimaryKey(id);
    };

    public List<Seminar> selectAll(){
        return seminarMapper.selectAll();
    }

    public List<Seminar> selectByCourseId(Long courseId){
        return seminarMapper.selectByCourseId(courseId);
    }

    public int updateByPrimaryKey(Seminar record){
        return seminarMapper.updateByPrimaryKey(record);
    }

    public List<Seminar> selectSeminarByRoundId(Long roundId){
        return seminarMapper. selectSeminarByRoundId(roundId);
    }
}
