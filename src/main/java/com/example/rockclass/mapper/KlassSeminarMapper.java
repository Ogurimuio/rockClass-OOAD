package com.example.rockclass.mapper;

import com.example.rockclass.entity.KlassSeminar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KlassSeminarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KlassSeminar record);

    List<KlassSeminar> selectAll();

    int updateByPrimaryKey(KlassSeminar record);

    KlassSeminar selectByKlassIdAndSeminarId(@Param("klassId")Long classId, @Param("seminarId")Long seminarId);

    KlassSeminar selectByPrimaryKey(Long id);
}