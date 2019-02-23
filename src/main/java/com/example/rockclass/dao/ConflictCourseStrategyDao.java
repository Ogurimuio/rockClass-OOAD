package com.example.rockclass.dao;

import com.example.rockclass.entity.ConflictCourseStrategy;
import com.example.rockclass.mapper.ConflictCourseStrategyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConflictCourseStrategyDao {
    @Autowired
    ConflictCourseStrategyMapper conflictCourseStrategyMapper;
    public int deleteById(Long id){return conflictCourseStrategyMapper.deleteById(id);}

    public int insert(ConflictCourseStrategy record){return conflictCourseStrategyMapper.insert(record);}

    public List<ConflictCourseStrategy> selectById(Long id){return conflictCourseStrategyMapper.selectById(id);}

    public List<ConflictCourseStrategy> selectAll(){return conflictCourseStrategyMapper.selectAll();}

    public Long selectMaxId() {List<ConflictCourseStrategy> conflictCourseStrategies=conflictCourseStrategyMapper.selectAll();
        Long maxId = new Long(0);
        for(int i=0;i<conflictCourseStrategies.size();i++)
        {
            if (maxId<conflictCourseStrategies.get(i).getId())
                maxId=conflictCourseStrategies.get(i).getId();
        }
        return maxId;}
}
