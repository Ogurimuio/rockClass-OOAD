package com.example.rockclass.dao;

import com.example.rockclass.entity.KlassRound;
import com.example.rockclass.mapper.KlassRoundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KlassRoundDao {
    @Autowired
    KlassRoundMapper klassRoundMapper;
    public int deleteByPrimaryKey(Long klassId,Long roundId){ return klassRoundMapper.deleteByPrimaryKey(klassId,roundId);};

    public void insert(KlassRound record){klassRoundMapper.insert(record);}

    public KlassRound selectByPrimaryKey(Long klassId,Long roundId){return  klassRoundMapper.selectByPrimaryKey(klassId,roundId);}

    public List<KlassRound> selectByRoundId(Long roundId) { List<KlassRound> klassRounds=klassRoundMapper.selectByRoundId(roundId);
        return  klassRounds;}

    public int updateByPrimaryKey(KlassRound record){return  klassRoundMapper.updateByPrimaryKey(record);}

}
