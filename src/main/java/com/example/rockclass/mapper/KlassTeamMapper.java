package com.example.rockclass.mapper;

import com.example.rockclass.entity.KlassTeam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KlassTeamMapper {
    int deleteByPrimaryKey(@Param("klassId") Long klassId, @Param("teamId") Long teamId);

    int insert(KlassTeam record);


    List<KlassTeam> selectByTeamId(@Param("teamId") Long teamId);

    List<KlassTeam> selectByKlassId(@Param("klassId") Long klassId);

    List<KlassTeam> selectAll();

    KlassTeam selectByTeamIdAndKlassId(@Param("teamId") Long teamId,@Param("klassId") Long klassId);

}