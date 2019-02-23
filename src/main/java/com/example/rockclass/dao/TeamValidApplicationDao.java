package com.example.rockclass.dao;

import com.example.rockclass.entity.TeamValidApplication;
import com.example.rockclass.mapper.TeamValidApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamValidApplicationDao {
    @Autowired
    private TeamValidApplicationMapper teamValidApplicationMapper;

    public int deleteByPrimaryKey(Long id){return teamValidApplicationMapper.deleteByPrimaryKey(id);}

    public  Long insert(TeamValidApplication record){ teamValidApplicationMapper.insert(record);return  record.getId();}

    public  TeamValidApplication selectByPrimaryKey(Long id){return  teamValidApplicationMapper.selectByPrimaryKey(id);}

    public List<TeamValidApplication> selectAll(){return teamValidApplicationMapper.selectAll();}

    public List<TeamValidApplication> selectByTeacherIdAndStatus( Long teacherId,Byte status){
        if (status.equals(Byte.parseByte("2"))) {
            List<TeamValidApplication> myTeamValidApplications = new ArrayList<>();
            List<TeamValidApplication>teamValidApplications=teamValidApplicationMapper.selectAll();
            for (TeamValidApplication teamValidApplication:teamValidApplications)
                if (teamValidApplication.getStatus()==null)
                    myTeamValidApplications.add(teamValidApplication);
            return myTeamValidApplications;
        }
        return  teamValidApplicationMapper.selectByTeacherIdAndStatus(teacherId,status);
    }

    public int updateByPrimaryKey(TeamValidApplication record){return teamValidApplicationMapper.updateByPrimaryKey(record);}
}