package com.example.rockclass.mapper;

import com.example.rockclass.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Question record);

    Question selectByPrimaryKey(Long id);

    List<Question> selectAll();

    int updateByPrimaryKey(Question record);

    List<Question> selectByAttendanceId(Long attendanceId);

    List<Question> selectByKlassSeminarIdAndTeamId(@Param("klassSeminarId") Long klassSeminarId, @Param("teamId") Long teamId);

}