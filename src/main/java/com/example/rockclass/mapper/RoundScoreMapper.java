package com.example.rockclass.mapper;

import com.example.rockclass.entity.RoundScore;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoundScoreMapper {
    int deleteByPrimaryKey(@Param("roundId") Long roundId, @Param("teamId") Long teamId);

    int insert(RoundScore record);

    RoundScore selectByPrimaryKey(@Param("roundId") Long roundId, @Param("teamId") Long teamId);

    List<RoundScore> selectByRoundId(@Param("roundId") Long roundId);

    List<RoundScore> selectAll();

    int updateByPrimaryKey(RoundScore record);
}