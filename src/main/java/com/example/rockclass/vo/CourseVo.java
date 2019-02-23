package com.example.rockclass.vo;

import java.util.Date;
import java.util.List;

public class CourseVo {
    private String name;

    private  String intro;

    private Byte presentationWeight;

    private Byte questionWeight;

    private Byte reportWeight;

    private Byte minMemberNumber;

    private  Byte maxMemberNumber;

    private String startTeamTime;

    private String endTeamTime;

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

    public String getStartTeamTime() {
        return startTeamTime;
    }

    public void setStartTeamTime(String startTeamTime) {
        this.startTeamTime = startTeamTime;
    }

    public String getEndTeamTime() {
        return endTeamTime;
    }

    public void setEndTeamTime(String endTeamTime) {
        this.endTeamTime = endTeamTime;
    }

    public Byte getCourseMemberLimitStrategyIsConflict() {
        return courseMemberLimitStrategyIsConflict;
    }

    public void setCourseMemberLimitStrategyIsConflict(Byte courseMemberLimitStrategyIsConflict) {
        this.courseMemberLimitStrategyIsConflict = courseMemberLimitStrategyIsConflict;
    }
}
