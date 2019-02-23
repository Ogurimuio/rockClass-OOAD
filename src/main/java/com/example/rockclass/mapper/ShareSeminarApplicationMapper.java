package com.example.rockclass.mapper;

import com.example.rockclass.entity.ShareSeminarApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ShareSeminarApplicationMapper {
    Long deleteByPrimaryKey(Long id);

    Long insert(ShareSeminarApplication record);

    ShareSeminarApplication selectByPrimaryKey(Long id);

    List<ShareSeminarApplication> selectAll();
    List<ShareSeminarApplication> selectByMainCourseIdAndStatus(@Param("mainCourseId") Long mainCourseId,@Param("status") Byte status);

    List<ShareSeminarApplication> selectBySubCourseIdAndStatus(@Param("subCourseId") Long subCourseId,@Param("status") Byte status);
    List<ShareSeminarApplication> selectBySubCourseTeacherIdAndStatus(@Param("subCourseTeacherId") Long subCourseTeacherId,@Param("status") Byte status);

    Long updateByPrimaryKey(ShareSeminarApplication record);
}

