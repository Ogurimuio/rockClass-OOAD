package com.example.rockclass.service;

import com.example.rockclass.dao.*;
import com.example.rockclass.entity.*;
import com.example.rockclass.mapper.TeamStrategyMapper;
import com.example.rockclass.vo.CourseMemberLimitIsConflictVo;
import com.example.rockclass.vo.CourseMemberLimitStrategyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StrategyService {

    @Autowired
    private TeamStrategyDao teamStrategyDao;

    @Autowired
    private TeamOrStrategyDao teamOrStrategyDao;

    @Autowired
    private TeamAndStrategyDao teamAndStrategyDao;

    @Autowired
    private MemberLimitStrategyDao memberLimitStrategyDao;
    @Autowired
    private CourseMemberLimitStrategyDao courseMemberLimitStrategyDao;

    @Autowired
    private ConflictCourseStrategyDao conflictCourseStrategyDao;
    public MemberLimitStrategy selectMemberLimitStrategyByCourseId(Long courseId) {
        List<TeamStrategy> teamStrategyMember = teamStrategyDao.selectByCourseIdAndStrategyName(courseId, "MemberLimitStrategy");
        if (teamStrategyMember.size() == 1)
            return memberLimitStrategyDao.selectByPrimaryKey(teamStrategyMember.get(0).getStrategyId());
        List<TeamStrategy> teamStrategAnd = teamStrategyDao.selectByCourseIdAndStrategyName(courseId, "TeamAndStrategy");
        if (teamStrategAnd.size() != 0) {
            List<TeamAndStrategy> teamAndStrategys = teamAndStrategyDao.selectById(teamStrategAnd.get(0).getStrategyId());
            for (TeamAndStrategy teamAndStrategy : teamAndStrategys) {
                if (teamAndStrategy.getStrategyName().equals("MemberLimitStrategy"))
                    return memberLimitStrategyDao.selectByPrimaryKey(teamAndStrategy.getStrategyId());
                if (teamAndStrategy.getStrategyName().equals("TeamOrStrategy")) {
                    List<TeamOrStrategy> teamOrStrategies = teamOrStrategyDao.selectById(teamAndStrategy.getStrategyId());
                    for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
                        if (teamAndStrategy.getStrategyName().equals("MemberLimitStrategy"))
                            return memberLimitStrategyDao.selectByPrimaryKey(teamOrStrategy.getStrategyId());
                    }
                }
            }
        } else {
            List<TeamStrategy> teamStrategyOr = teamStrategyDao.selectByCourseIdAndStrategyName(courseId, "TeamOrStrategy");
            if (teamStrategyOr.size() != 0) {
                List<TeamOrStrategy> teamOrStrategies = teamOrStrategyDao.selectById(teamStrategyOr.get(0).getStrategyId());
                for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
                    if (teamOrStrategy.getStrategyName().equals("MemberLimitStrategy"))
                        return memberLimitStrategyDao.selectByPrimaryKey(teamOrStrategy.getStrategyId());
                    if (teamOrStrategy.getStrategyName().equals("TeamAndStrategy")) {
                        List<TeamAndStrategy> teamAndStrategies = teamAndStrategyDao.selectById(teamOrStrategy.getStrategyId());
                        for (TeamAndStrategy teamAndStrategy : teamAndStrategies) {
                            if (teamAndStrategy.getStrategyName().equals("MemberLimitStrategy"))
                                return memberLimitStrategyDao.selectByPrimaryKey(teamAndStrategy.getStrategyId());
                        }
                    }
                }
            }
        }
        return null;
    }


    public CourseMemberLimitIsConflictVo selectCourseMemberLimitStrategyByCourseId(Long courseId) {

        CourseMemberLimitIsConflictVo courseMemberLimitIsConflictVo = new CourseMemberLimitIsConflictVo();
        List<TeamStrategy> teamStrategies = teamStrategyDao.selectByCourseIdAndStrategyName(courseId, "TeamAndStrategy");
        List<TeamAndStrategy> teamAndStrategiesOR = teamAndStrategyDao.selectByIdAndStrategyName(teamStrategies.get(0).getStrategyId(), "TeamOrStrategy");
        List<TeamAndStrategy> teamAndStrategiesCMLS= teamAndStrategyDao.selectByIdAndStrategyName(teamStrategies.get(0).getStrategyId(),"CourseMemberLimitStrategy");
        List<CourseMemberLimitStrategy> courseMemberLimitStrategies = new ArrayList<CourseMemberLimitStrategy>();
        Byte isConflict =0;
        if (teamAndStrategiesOR != null)
        {   for (TeamAndStrategy teamAndStrategy : teamAndStrategiesOR) {
            List<TeamOrStrategy> teamOrStrategies = teamOrStrategyDao.selectById(teamAndStrategy.getStrategyId());
            for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
                courseMemberLimitStrategies.add(courseMemberLimitStrategyDao.selectByPrimaryKey(teamOrStrategy.getStrategyId()));
            }
        }
            isConflict=1;
        }
        else {

            if (teamAndStrategiesCMLS!=null)
                for (TeamAndStrategy teamAndStrategy : teamAndStrategiesCMLS) {
                    List<TeamOrStrategy> teamOrStrategies = teamOrStrategyDao.selectById(teamAndStrategy.getStrategyId());
                    for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
                        courseMemberLimitStrategies.add(courseMemberLimitStrategyDao.selectByPrimaryKey(teamOrStrategy.getStrategyId()));
                    }
                }
        }
        if (teamAndStrategiesOR==null&&teamAndStrategiesCMLS==null)return null;
        courseMemberLimitIsConflictVo.setCourseMemberLimitStrategies(courseMemberLimitStrategies);
        courseMemberLimitIsConflictVo.setIsConflict(isConflict);
        return courseMemberLimitIsConflictVo;
    }

    public List<TeamStrategy> selectTeamStrategyByCourseIdAndName(Long courseId,String strategyName) {
        return teamStrategyDao.selectByCourseIdAndStrategyName(courseId, strategyName);
    }

    public List<ConflictCourseStrategy> selectConflictCourseStrategiesByStrategyId(Long strategyId)
    {return conflictCourseStrategyDao.selectById(strategyId);}
}


