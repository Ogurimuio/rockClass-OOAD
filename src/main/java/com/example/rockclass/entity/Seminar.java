package com.example.rockclass.entity;

import java.util.Date;

public class Seminar {
    private Long id;

    private Course course;

    private Round round;

    private String seminarName;

    private String introduction;

    private Byte maxTeam;

    private Byte isVisible;

    private Byte seminarSerial;

    private Date enrollStartTime;

    private Date enrollEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName == null ? null : seminarName.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Byte getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Byte maxTeam) {
        this.maxTeam = maxTeam;
    }

    public Byte getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Byte isVisible) {
        this.isVisible = isVisible;
    }

    public Byte getSeminarSerial() {
        return seminarSerial;
    }

    public void setSeminarSerial(Byte seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public Date getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Date enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Date getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Date enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    @Override
    public String toString() {
        return "Seminar{" +
                "id=" + id +
                ", course=" + course +
                ", round=" + round +
                ", seminarName='" + seminarName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", maxTeam=" + maxTeam +
                ", isVisible=" + isVisible +
                ", seminarSerial=" + seminarSerial +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollEndTime=" + enrollEndTime +
                '}';
    }
}