package com.example.rockclass.dao;

import com.example.rockclass.entity.Klass;
import com.example.rockclass.mapper.KlassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KlassDao {


    @Autowired
    private KlassMapper klassMapper;


    public Long insert(Klass klass){ klassMapper.insert(klass);return klass.getId();}
    public  List<Klass> selectKlassByCourseId(Long courseId){
        return klassMapper.selectKlassByCourseId(courseId);
    }
    public  Klass selectKlassByKlassId(Long klassId){return  klassMapper.selectByPrimaryKey(klassId);}
    public  List<Klass> selectAll(){
        return klassMapper.selectAll();
    }
    public int deleteByPrimaryKey(Long id){return klassMapper.deleteByPrimaryKey(id);}




}
