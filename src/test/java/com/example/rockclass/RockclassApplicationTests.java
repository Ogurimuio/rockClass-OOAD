package com.example.rockclass;

import com.alibaba.fastjson.JSON;
import com.example.rockclass.mapper.AttendanceMapper;
import com.example.rockclass.mapper.KlassSeminarMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RockclassApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private KlassSeminarMapper klassSeminarMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Test
    public  void select(){
        System.out.println(JSON.toJSONString(attendanceMapper.selectByPrimaryKey(Long.parseLong("1"))));
    }
}

