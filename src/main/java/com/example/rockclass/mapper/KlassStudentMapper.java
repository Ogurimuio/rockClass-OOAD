package com.example.rockclass.mapper;

import com.example.rockclass.entity.KlassStudent;
import java.util.List;

import com.example.rockclass.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KlassStudentMapper {
    int deleteByPrimaryKey(@Param("klassId") Long klassId, @Param("studentId") Long studentId);

    int insert(KlassStudent record);

    KlassStudent selectByPrimaryKey(@Param("klassId") Long klassId, @Param("studentId") Long studentId);

    List<KlassStudent>  selectByCourseId(@Param("courseId") Long courseId);

    List<KlassStudent> selectByStudentId(@Param("studentId") Long studentId);
    List<KlassStudent> selectByKlassId(@Param("klassId") Long klassId);
    KlassStudent selectByCourseIdAndStudentId(@Param("courseId") Long courseId,@Param("studentId") Long studentId);

    int deleteByKlassId(@Param("klassId")Long klassId);

    List<KlassStudent> selectAll();

    int updateByPrimaryKey(KlassStudent record);
}
