package com.example.rockclass.dao;

import com.example.rockclass.entity.TeamAndStrategy;
import com.example.rockclass.mapper.TeamAndStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamAndStrategyDao {

    @Autowired
    TeamAndStrategyMapper teamAndStrategyMapper;
    public  int deleteById(Long id){return teamAndStrategyMapper.deleteById(id); }

    public  int insert(TeamAndStrategy record){return teamAndStrategyMapper.insert(record);}

    public  List<TeamAndStrategy> selectById(Long id){return  teamAndStrategyMapper.selectById(id);}

    public  List<TeamAndStrategy> selectByIdAndStrategyName(Long id,String strategyName){return  teamAndStrategyMapper.selectByIdAndStrategyName(id,strategyName);}
    public   List<TeamAndStrategy> selectAll(){return  teamAndStrategyMapper.selectAll();}

    public Long selectMaxId(){List<TeamAndStrategy> teamAndStrategies=teamAndStrategyMapper.selectAll();
        Long maxId = new Long(0);
        for(int i=0;i<teamAndStrategies.size();i++)
        {
            if (maxId<teamAndStrategies.get(i).getId())
                maxId=teamAndStrategies.get(i).getId();
        }
        return maxId;}
}
