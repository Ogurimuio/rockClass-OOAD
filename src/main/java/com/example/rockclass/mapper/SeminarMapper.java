package com.example.rockclass.mapper;

import com.example.rockclass.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeminarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Seminar record);

    Seminar selectByPrimaryKey(Long id);

    List<Seminar> selectAll();

    int updateByPrimaryKey(Seminar record);

    List<Seminar> selectSeminarByRoundId(@Param("roundId") Long roundId);

    List<Seminar> selectByCourseId(Long courseId);
}