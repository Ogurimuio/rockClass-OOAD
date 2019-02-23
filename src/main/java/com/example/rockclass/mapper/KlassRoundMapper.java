package com.example.rockclass.mapper;

import com.example.rockclass.entity.KlassRound;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KlassRoundMapper {
    int deleteByPrimaryKey(@Param("klassId") Long klassId, @Param("roundId") Long roundId);

    int insert(KlassRound record);

    KlassRound selectByPrimaryKey(@Param("klassId") Long klassId, @Param("roundId") Long roundId);

    List<KlassRound> selectAll();

    List<KlassRound> selectByRoundId( @Param("roundId") Long roundId);

    int updateByPrimaryKey(KlassRound record);

}