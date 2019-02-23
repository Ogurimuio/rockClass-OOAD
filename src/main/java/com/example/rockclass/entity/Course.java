package com.example.rockclass.entity;

import java.util.Date;

public class Course {
    private Long id;

    private Teacher teacher;

    private String courseName;

    private String introduction;

    private Byte presentationPercentage;

    private Byte questionPercentage;

    private Byte reportPercentage;

    private Date teamStartTime;

    private Date teamEndTime;

    private Long teamMainCourseId;

    private Long seminarMainCourseId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Byte getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(Byte presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public Byte getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(Byte questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public Byte getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(Byte reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public Date getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(Date teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public Date getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(Date teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public Long getTeamMainCourseId() {
        return teamMainCourseId;
    }

    public void setTeamMainCourseId(Long teamMainCourseId) {
        this.teamMainCourseId = teamMainCourseId;
    }

    public Long getSeminarMainCourseId() {
        return seminarMainCourseId;
    }

    public void setSeminarMainCourseId(Long seminarMainCourseId) {
        this.seminarMainCourseId = seminarMainCourseId;
    }

}