package com.example.rockclass.dao;

import com.example.rockclass.entity.TeamOrStrategy;
import com.example.rockclass.mapper.TeamOrStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamOrStrategyDao {
    @Autowired
    private TeamOrStrategyMapper teamOrStrategyMapper;
    public int deleteByPrimaryKey(Long id){return teamOrStrategyMapper.deleteById(id);}

    public int insert(TeamOrStrategy record) {return teamOrStrategyMapper.insert(record);}

    public List<TeamOrStrategy> selectById(Long id){return teamOrStrategyMapper.selectById(id);}

    public List<TeamOrStrategy> selectAll(){return teamOrStrategyMapper.selectAll();}

    public Long selectMaxId() {List<TeamOrStrategy> teamOrStrategies=teamOrStrategyMapper.selectAll();
        Long maxId = new Long(0);
        for(int i=0;i<teamOrStrategies.size();i++)
        {
            if (maxId<teamOrStrategies.get(i).getId())
                maxId=teamOrStrategies.get(i).getId();
        }
        return maxId;}

}
