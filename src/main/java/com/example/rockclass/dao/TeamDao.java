package com.example.rockclass.dao;

import com.example.rockclass.entity.Klass;
import com.example.rockclass.entity.KlassStudent;
import com.example.rockclass.entity.Student;
import com.example.rockclass.entity.Team;
import com.example.rockclass.mapper.KlassStudentMapper;
import com.example.rockclass.mapper.StudentMapper;
import com.example.rockclass.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamDao {

    @Autowired
    private  TeamMapper teamMapper;
    @Autowired
    private KlassStudentMapper klassStudentMapper;

    public Long deleteByPrimaryKey(Long id){
        return teamMapper.deleteByPrimaryKey(id);
    }

    public Long insert(Team record){
        teamMapper.insert(record);return record.getId();
    };

    public Team selectByPrimaryKey(Long id){
        return teamMapper.selectByPrimaryKey(id);
    }

    public List<Team> selectByClassId(Long classId){
        return teamMapper.selectByKlassId(classId);
    }

    public KlassStudent selectKlassStudentByCourseIdAndStudentId(Long courseId,Long studentId){
        return klassStudentMapper.selectByCourseIdAndStudentId(courseId,studentId);
    }

    public List<Team> selectByCourseId(Long courseId){
        return teamMapper.selectByCourseId(courseId);
    }

    public List<KlassStudent> selectKlassStudentByCourseId(Long courseId){
        return klassStudentMapper.selectByCourseId(courseId);
    }

    public Long updateByPrimaryKey(Team record){
        return teamMapper.updateByPrimaryKey(record);
    }
}
