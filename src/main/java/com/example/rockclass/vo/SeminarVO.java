package com.example.rockclass.vo;

//import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SeminarVO {

    private Long courseId;

    private Byte roundSerial;

    private String seminarName;

    private String introduction;

    private Byte maxTeam;

    private Byte isVisible;

    private Byte seminarSerial;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enrollStartTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enrollEndTime;


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Byte getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(Byte roundSerial) {
        this.roundSerial = roundSerial;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
        return "SeminarVO{" +
                "courseId=" + courseId +
                ", roundSerial=" + roundSerial +
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
