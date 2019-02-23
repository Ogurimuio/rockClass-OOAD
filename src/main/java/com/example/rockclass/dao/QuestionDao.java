package com.example.rockclass.dao;

import com.example.rockclass.entity.Question;
import com.example.rockclass.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDao {

    @Autowired
    private QuestionMapper questionMapper;

    public Long insert(Question record){
        questionMapper.insert(record);
        return record.getId();
    }

    public int updateByPrimaryKey(Question record){
        return questionMapper.updateByPrimaryKey(record);
    }


    public Question selectByPrimaryKey(Long id){
        return questionMapper.selectByPrimaryKey(id);
    }

    public List<Question> selectAll(){
        return questionMapper.selectAll();
    }

    public List<Question> selectByAttendanceId(Long attendanceId){
        return questionMapper.selectByAttendanceId(attendanceId);
    }

    public List<Question> selectByKlassSeminarIdAndTeamId(Long klassSeminarId,Long teamId){
        return questionMapper.selectByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
    }
}
