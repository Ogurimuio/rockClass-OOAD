package com.example.rockclass.vo;

import java.util.Date;

/*班级的讨论课信息*/

public class KlassSeminarVO {
    private Long id;

    private String seminarName;

    private String introduction;

    private Byte status;

    private Byte seminarSerial;

    private Byte maxTeam;

    private String reportDDL;

    private String enrollStartTime;

    private String enrollEndTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getSeminarSerial() {
        return seminarSerial;
    }

    public void setSeminarSerial(Byte seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public Byte getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Byte maxTeam) {
        this.maxTeam = maxTeam;
    }

    public String getReportDDL() {
        return reportDDL;
    }

    public void setReportDDL(String reportDDL) {
        this.reportDDL = reportDDL;
    }

    public String getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(String enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public String getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(String enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }
}
