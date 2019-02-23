package com.example.rockclass.dao;

import com.example.rockclass.entity.TeamStrategy;
import com.example.rockclass.mapper.TeamStrategyMapper;
import com.example.rockclass.mapper.TeamStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamStrategyDao {

    @Autowired
    private TeamStrategyMapper teamStrategyMapper;

    public int deleteByPrimaryKey(Long courseId,  Long strategyId,  String strategyName){
        return  teamStrategyMapper.deleteByPrimaryKey(courseId,strategyId,strategyName);
    }

    public int insert(TeamStrategy record){return  teamStrategyMapper.insert(record);}

    public List<TeamStrategy> selectByCourseIdAndStrategyName(Long courseId,String strategyName){return teamStrategyMapper.selectByCourseIdAndStrategyName(courseId,strategyName);}
    public List<TeamStrategy> selectAll() {
        return teamStrategyMapper.selectAll();
    }

    public Byte selectCourseMaxSerialByCourseId(Long courseId){

        List<TeamStrategy> teamStrategies =teamStrategyMapper.selectByCourseId(courseId);
        Byte maxSerial = 0;
        for(int i=0;i<teamStrategies.size();i++)
        {
            if (maxSerial<teamStrategies.get(i).getStrategyId())
                maxSerial=teamStrategies.get(i).getStrategySerial();
        }
        return maxSerial;}
}

