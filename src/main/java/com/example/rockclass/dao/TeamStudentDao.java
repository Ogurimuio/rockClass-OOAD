package com.example.rockclass.dao;

import com.example.rockclass.entity.TeamStudent;
import com.example.rockclass.mapper.TeamStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamStudentDao {

    @Autowired
    private TeamStudentMapper teamStudentMapper;

    public int deleteByPrimaryKey(Long teamId, Long studentId){
        return teamStudentMapper.deleteByPrimaryKey(teamId,studentId);
    }

    public int insert(TeamStudent record){
        return teamStudentMapper.insert(record);
    };

    public int deleteByTeamId(Long teamId){
        return teamStudentMapper.deleteByTeamId(teamId);
    }

    public List<TeamStudent> selectByTeamId(Long teamId){
        return teamStudentMapper.selectByTeamId(teamId);
    }

    public List<TeamStudent> selectByStudentId(Long studentId){
        return teamStudentMapper.selectByStudentId(studentId);
    }

    public List<TeamStudent> selectAll(){
        return teamStudentMapper.selectAll();
    }

}
