package com.example.rockclass.dao;

import com.example.rockclass.entity.KlassTeam;
import com.example.rockclass.mapper.KlassTeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class KlassTeamDao {

    @Autowired
    KlassTeamMapper klassTeamMapper;

    public int deleteByPrimaryKey(Long klassId,  Long teamId){
        return klassTeamMapper.deleteByPrimaryKey(klassId,teamId);
    }

    public int insert(KlassTeam record){
        return klassTeamMapper.insert(record);
    };


    public List<KlassTeam> selectByTeamId(Long teamId){
        return klassTeamMapper.selectByTeamId(teamId);
    }

    public List<KlassTeam> selectByKlassId(Long klassId){
        return klassTeamMapper.selectByKlassId(klassId);
    }

    public List<KlassTeam> selectAll(){
        return klassTeamMapper.selectAll();
    }

    public KlassTeam selectByPrimaryKey(Long klassId,  Long teamId) {return klassTeamMapper.selectByTeamIdAndKlassId(teamId,klassId);}

}
