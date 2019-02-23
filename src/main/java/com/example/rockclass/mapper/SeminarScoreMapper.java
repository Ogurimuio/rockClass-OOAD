package com.example.rockclass.mapper;

import com.example.rockclass.entity.SeminarScore;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeminarScoreMapper {
    int deleteByPrimaryKey(@Param("klassSeminarId") Long klassSeminarId, @Param("teamId") Long teamId);

    int insert(SeminarScore record);

    SeminarScore selectByPrimaryKey(@Param("klassSeminarId") Long klassSeminarId, @Param("teamId") Long teamId);

    List<SeminarScore> selectAll();

    int updateByPrimaryKey(SeminarScore record);
}