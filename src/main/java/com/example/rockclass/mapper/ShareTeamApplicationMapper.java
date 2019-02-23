package com.example.rockclass.mapper;

import com.example.rockclass.entity.ShareTeamApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ShareTeamApplicationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShareTeamApplication record);

    ShareTeamApplication selectByPrimaryKey(Long id);

    List<ShareTeamApplication> selectAll();


    List<ShareTeamApplication> selectByMainCourseIdAndStatus(@Param("mainCourseId") Long mainCourseId,@Param("status") Byte status);

    List<ShareTeamApplication> selectBySubCourseIdAndStatus(@Param("subCourseId") Long subCourseId,@Param("status") Byte status);

    List<ShareTeamApplication> selectBySubCourseTeacherIdAndStatus(@Param("subCourseTeacherId") Long subCourseTeacherId,@Param("status") Byte status);
    int updateByPrimaryKey(ShareTeamApplication record);
}
