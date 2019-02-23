package com.example.rockclass.mapper;

import com.example.rockclass.entity.Round;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoundMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Round record);

    Round selectByPrimaryKey(Long id);

    List<Round> selectRoundByCourseId(@Param("courseId") Long courseId);

    List<Round> selectAll();

    int updateByPrimaryKey(Round record);

    Round selectRoundByCourseIdAndRoundSerial(@Param("courseId")Long courseId, @Param("roundSerial")Byte roundSerial);
}