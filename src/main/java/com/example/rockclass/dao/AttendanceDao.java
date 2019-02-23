package com.example.rockclass.dao;

import com.example.rockclass.entity.Attendance;
import com.example.rockclass.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendanceDao {

    @Autowired
    private AttendanceMapper attendanceMapper;

    public int deleteByPrimaryKey(Long id){
       return attendanceMapper.deleteByPrimaryKey(id);
    }

    public Long insert(Attendance record){
        attendanceMapper.insert(record);
        return  record.getId();
    }

    public Attendance selectByPrimaryKey(Long id){
        return attendanceMapper.selectByPrimaryKey(id);
    }

    public List<Attendance> selectAll(){
        return attendanceMapper.selectAll();
    }

    public int updateByPrimaryKey(Attendance record){
        return attendanceMapper.updateByPrimaryKey(record);
    }

    public List<Attendance> selectByKlassSeminarId(Long id) {
        return attendanceMapper.selectByKlassSeminarId(id);
    }
}
