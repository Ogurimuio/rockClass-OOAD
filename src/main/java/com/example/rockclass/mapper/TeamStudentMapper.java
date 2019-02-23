package com.example.rockclass.mapper;


import com.example.rockclass.entity.TeamStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamStudentMapper {
    int deleteByPrimaryKey( @Param("teamId") Long teamId,@Param("studentId") Long studentId);

    int insert(TeamStudent record);

    int deleteByTeamId( @Param("teamId") Long teamId);

    List<TeamStudent> selectByTeamId(@Param("teamId") Long teamId);

    List<TeamStudent> selectByStudentId(@Param("studentId") Long studentId);

    List<TeamStudent> selectAll();

}
