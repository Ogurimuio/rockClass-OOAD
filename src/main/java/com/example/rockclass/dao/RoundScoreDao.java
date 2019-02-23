package com.example.rockclass.dao;

import com.example.rockclass.entity.RoundScore;
import com.example.rockclass.mapper.RoundScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoundScoreDao {

    @Autowired
    private RoundScoreMapper roundScoreMapper;

    public int deleteByPrimaryKey(Long roundId, Long teamId){
        return  roundScoreMapper.deleteByPrimaryKey(roundId,teamId);
    };

    public int insert(RoundScore record){
        return roundScoreMapper.insert(record);
    };

    public RoundScore selectByPrimaryKey(Long roundId, Long teamId){
        return roundScoreMapper.selectByPrimaryKey(roundId,teamId);
    };

    public List<RoundScore> selectByRoundId(Long roundId){
        return roundScoreMapper.selectByRoundId(roundId);
    };

    public List<RoundScore> selectAll(){
        return roundScoreMapper.selectAll();
    };

    public int updateByPrimaryKey(RoundScore record){
        return roundScoreMapper.updateByPrimaryKey(record);
    };
}
