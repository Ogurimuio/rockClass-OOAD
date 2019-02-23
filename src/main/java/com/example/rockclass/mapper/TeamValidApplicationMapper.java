package com.example.rockclass.mapper;

import com.example.rockclass.entity.TeamValidApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TeamValidApplicationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TeamValidApplication record);

    TeamValidApplication selectByPrimaryKey(Long id);

    List<TeamValidApplication> selectAll();

    List<TeamValidApplication> selectByTeacherIdAndStatus(@Param("teacherId") Long teacherId,@Param("status") Byte status);

    int updateByPrimaryKey(TeamValidApplication record);
}