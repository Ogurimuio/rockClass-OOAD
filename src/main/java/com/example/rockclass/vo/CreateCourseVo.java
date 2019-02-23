package com.example.rockclass.vo;

import java.util.Date;
import java.util.List;

public class CreateCourseVo {
    private String name;

    private  String intro;

    private Byte presentationWeight;

    private Byte questionWeight;

    private Byte reportWeight;

    private Byte minMemberNumber;

    private  Byte maxMemberNumber;

    private Date startTeamTime;

    private Date endTeamTime;

    private Byte courseMemberLimitStrategyIsConflict;
    private List<CourseMemberLimitStrategyVo> courseMemberLimitStrategys;

    private  List<List<Long>> conflictCourseStrategys;

    public List<CourseMemberLimitStrategyVo> getCourseMemberLimitStrategys() {
        return courseMemberLimitStrategys;
    }

    public void setCourseMemberLimitStrategys(List<CourseMemberLimitStrategyVo> courseMemberLimitStrategys) {
        this.courseMemberLimitStrategys = courseMemberLimitStrategys;
    }

    public List<List<Long>> getConflictCourseStrategys() {
        return conflictCourseStrategys;
    }

    public void setConflictCourseStrategys(List<List<Long>> conflictCourseStrategys) {
        this.conflictCourseStrategys = conflictCourseStrategys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Byte getPresentationWeight() {
        return presentationWeight;
    }

    public void setPresentationWeight(Byte presentationWeight) {
        this.presentationWeight = presentationWeight;
    }

    public Byte getQuestionWeight() {
        return questionWeight;
    }

    public void setQuestionWeight(Byte questionWeight) {


        this.questionWeight = questionWeight;
    }

    public Byte getReportWeight() {
        return reportWeight;
    }

    public void setReportWeight(Byte reportWeight) {
        this.reportWeight = reportWeight;
    }

    public Byte getMinMemberNumber() {
        return minMemberNumber;
    }

    public void setMinMemberNumber(Byte minMemberNumber) {
        this.minMemberNumber = minMemberNumber;
    }

    public Byte getMaxMemberNumber() {
        return maxMemberNumber;
    }

    public void setMaxMemberNumber(Byte maxMemberNumber) {
        this.maxMemberNumber = maxMemberNumber;
    }

    public Date getStartTeamTime() {
        return startTeamTime;
    }

    public void setStartTeamTime(Date startTeamTime) {
        this.startTeamTime = startTeamTime;
    }

    public Date getEndTeamTime() {
        return endTeamTime;
    }

    public void setEndTeamTime(Date endTeamTime) {
        this.endTeamTime = endTeamTime;
    }

    public Byte getCourseMemberLimitStrategyIsConflict() {
        return courseMemberLimitStrategyIsConflict;
    }

    public void setCourseMemberLimitStrategyIsConflict(Byte courseMemberLimitStrategyIsConflict) {
        this.courseMemberLimitStrategyIsConflict = courseMemberLimitStrategyIsConflict;
    }
}
