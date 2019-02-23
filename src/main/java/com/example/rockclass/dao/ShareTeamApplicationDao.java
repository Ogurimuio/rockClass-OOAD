package com.example.rockclass.dao;

import com.example.rockclass.entity.ShareTeamApplication;
import com.example.rockclass.mapper.ShareTeamApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShareTeamApplicationDao {
    @Autowired
    private ShareTeamApplicationMapper shareTeamApplicationMapper;
    public int deleteByPrimaryKey(Long id){return shareTeamApplicationMapper.deleteByPrimaryKey(id);}

    public Long insert(ShareTeamApplication record){shareTeamApplicationMapper.insert(record);return record.getId();}

    public ShareTeamApplication selectByPrimaryKey(Long id){return shareTeamApplicationMapper.selectByPrimaryKey(id);}

    public List<ShareTeamApplication> selectAll(){return shareTeamApplicationMapper.selectAll();}

    public int updateByPrimaryKey(ShareTeamApplication record){return shareTeamApplicationMapper.updateByPrimaryKey(record);}

    public List<ShareTeamApplication> selectByCourseId(Long courseId,Byte status){
        if (status.equals(Byte.parseByte("2"))) {
            List<ShareTeamApplication> mySTAs = new ArrayList<ShareTeamApplication>();
            List<ShareTeamApplication>shareTeamApplications = shareTeamApplicationMapper.selectAll();
            for (ShareTeamApplication shareTeamApplication:shareTeamApplications)
                if (shareTeamApplication.getStatus()==null&&(shareTeamApplication.getSubCourse().getId()==courseId||shareTeamApplication.getMainCourse().getId()==courseId))
                    mySTAs.add(shareTeamApplication);
            return  mySTAs;
        }
        List<ShareTeamApplication> shareTeamApplicationsMain= shareTeamApplicationMapper.selectByMainCourseIdAndStatus(courseId,status);
        List<ShareTeamApplication> shareTeamApplicationsSub =shareTeamApplicationMapper.selectBySubCourseIdAndStatus(courseId,status);
        if (shareTeamApplicationsSub!=null)
            for (ShareTeamApplication shareTeamApplication:shareTeamApplicationsSub)
            {
                shareTeamApplicationsMain.add(shareTeamApplication);
            }
        return  shareTeamApplicationsMain;
    }
    public List<ShareTeamApplication> selectBySubTeacherIdAndStatus(Long teacherId,Byte status){
        if (status.equals(Byte.parseByte("2"))) {
            List<ShareTeamApplication> mySTAs = new ArrayList<ShareTeamApplication>();
            List<ShareTeamApplication>shareTeamApplications = shareTeamApplicationMapper.selectAll();
            for (ShareTeamApplication shareTeamApplication:shareTeamApplications)
                if (shareTeamApplication.getStatus()==null&&shareTeamApplication.getSubCourseTeacher().getId()==teacherId)
                    mySTAs.add(shareTeamApplication);
            return  mySTAs;
        }
        return  shareTeamApplicationMapper.selectBySubCourseTeacherIdAndStatus(teacherId,status);
    }
}