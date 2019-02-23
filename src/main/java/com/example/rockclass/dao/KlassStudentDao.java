package com.example.rockclass.dao;

import com.example.rockclass.entity.KlassStudent;
import com.example.rockclass.mapper.KlassStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KlassStudentDao {

    @Autowired
    KlassStudentMapper klassStudentMapper;

    public int deleteByPrimaryKey(Long klassId, Long studentId) {
        return klassStudentMapper.deleteByPrimaryKey(klassId,studentId);
    }

    public int insert(KlassStudent record){
        return klassStudentMapper.insert(record);
    }

    public KlassStudent selectByPrimaryKey(Long klassId, Long studentId){
        return klassStudentMapper.selectByPrimaryKey(klassId,studentId);
    }

    public List<KlassStudent> selectByCourseId(Long courseId){
        return klassStudentMapper.selectByCourseId(courseId);
    }

    public List<KlassStudent> selectByStudentId(Long studentId){
        return klassStudentMapper.selectByStudentId(studentId);
    }

    public KlassStudent selectByCourseIdAndStudentId(Long courseId, Long studentId){
        return klassStudentMapper.selectByCourseIdAndStudentId(courseId,studentId);
    }

    public  List<KlassStudent> selectAll(){
        return klassStudentMapper.selectAll();
    }

    public int updateByPrimaryKey(KlassStudent record){
        return klassStudentMapper.updateByPrimaryKey(record);
    }

    public  int deleteByKlassId(Long klassId){
        return klassStudentMapper.deleteByKlassId(klassId);
    }
}
