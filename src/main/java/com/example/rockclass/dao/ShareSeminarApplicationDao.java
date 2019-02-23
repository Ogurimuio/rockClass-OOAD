package com.example.rockclass.dao;

import com.example.rockclass.entity.ShareSeminarApplication;
import com.example.rockclass.mapper.ShareSeminarApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
@Component
public class ShareSeminarApplicationDao {
    @Autowired
    private ShareSeminarApplicationMapper shareSeminarApplicationMapper;

    public Long deleteByPrimaryKey(Long id){return  shareSeminarApplicationMapper.deleteByPrimaryKey(id);}

    public Long insert(ShareSeminarApplication record){shareSeminarApplicationMapper.insert(record);return record.getId();}

    public ShareSeminarApplication selectByPrimaryKey(Long id){return  shareSeminarApplicationMapper.selectByPrimaryKey(id);};

    public List<ShareSeminarApplication> selectAll(){return selectAll();};

    public Long updateByPrimaryKey(ShareSeminarApplication record){return shareSeminarApplicationMapper.updateByPrimaryKey(record);}

    public List<ShareSeminarApplication> selectByCourseId(Long courseId,Byte status){

        if (status.equals(Byte.parseByte("2"))) {
            List<ShareSeminarApplication> mySSAs = new ArrayList<ShareSeminarApplication>();
            List<ShareSeminarApplication>shareSeminarApplications = shareSeminarApplicationMapper.selectAll();
            for (ShareSeminarApplication shareSeminarApplication:shareSeminarApplications)
                if (shareSeminarApplication.getStatus()==null&&(shareSeminarApplication.getSubCourse().getId()==courseId||shareSeminarApplication.getMainCourse().getId()==courseId))
                    mySSAs.add(shareSeminarApplication);
            return  mySSAs;
        }
        List<ShareSeminarApplication> shareSeminarApplicationsMain= shareSeminarApplicationMapper.selectByMainCourseIdAndStatus(courseId,status);
        List<ShareSeminarApplication> shareSeminarApplicationsSub =shareSeminarApplicationMapper.selectBySubCourseIdAndStatus(courseId,status);
        for (ShareSeminarApplication shareSeminarApplication:shareSeminarApplicationsSub)
        {
            shareSeminarApplicationsMain.add(shareSeminarApplication);
        }
        return  shareSeminarApplicationsMain;
    }

    public List<ShareSeminarApplication> selectBySubTeacherIdAndStatus(Long teacherId,Byte status) {
        if (status.equals(Byte.parseByte("2"))) {
            List<ShareSeminarApplication> mySSAs = new ArrayList<ShareSeminarApplication>();
            List<ShareSeminarApplication>shareSeminarApplications = shareSeminarApplicationMapper.selectAll();
            for (ShareSeminarApplication shareSeminarApplication:shareSeminarApplications)
                if (shareSeminarApplication.getStatus()==null&&shareSeminarApplication.getSubCourseTeacher().getId()==teacherId)
                    mySSAs.add(shareSeminarApplication);
            return  mySSAs;
        }
        return shareSeminarApplicationMapper.selectBySubCourseTeacherIdAndStatus(teacherId,status);
    }
}
