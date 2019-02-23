package com.example.rockclass.dao;

import com.example.rockclass.entity.KlassSeminar;
import com.example.rockclass.mapper.KlassSeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KlassSeminarDao {

    @Autowired
    KlassSeminarMapper klassSeminarMapper;

    public int deleteByPrimaryKey(Long id){
        return klassSeminarMapper.deleteByPrimaryKey(id);
    }

    public int insert(KlassSeminar record){
        return klassSeminarMapper.insert(record);
    }

    public List<KlassSeminar> selectAll(){
        return klassSeminarMapper.selectAll();
    }

    public int updateByPrimaryKey(KlassSeminar record){
        return klassSeminarMapper.updateByPrimaryKey(record);
    }

    public KlassSeminar selectByKlassIdAndSeminarId(Long classId, Long seminarId) {
        return klassSeminarMapper.selectByKlassIdAndSeminarId(classId,seminarId);
    }

    public KlassSeminar selectByPrimaryKey(Long id) {
        return klassSeminarMapper.selectByPrimaryKey(id);
    }
}
