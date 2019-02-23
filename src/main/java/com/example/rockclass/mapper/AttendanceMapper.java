package com.example.rockclass.mapper;

import com.example.rockclass.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attendance record);

    Attendance selectByPrimaryKey(@Param("id")Long id);

    List<Attendance> selectAll();

    int updateByPrimaryKey(Attendance record);


    List<Attendance> selectByKlassSeminarId(Long klassSeminarId);
}