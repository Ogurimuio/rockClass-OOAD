package com.example.rockclass.dao;

import com.example.rockclass.entity.SeminarScore;
import com.example.rockclass.mapper.SeminarScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SeminarScoreDao {

    @Autowired
    private SeminarScoreMapper seminarScoreMapper;
    public int deleteByPrimaryKey( Long klassSeminarId,  Long teamId){return seminarScoreMapper.deleteByPrimaryKey(klassSeminarId,teamId);}

    public int insert(SeminarScore record){return  seminarScoreMapper.insert(record);}

    public SeminarScore selectByPrimaryKey( Long klassSeminarId, Long teamId){return seminarScoreMapper.selectByPrimaryKey(klassSeminarId,teamId);}

    public List<SeminarScore> selectAll(){return seminarScoreMapper.selectAll();}

    public int updateByPrimaryKey(SeminarScore record){return seminarScoreMapper.updateByPrimaryKey(record);}
}
