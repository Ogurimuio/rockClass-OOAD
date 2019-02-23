package com.example.rockclass.vo;

public class TeamAttendanceVO {
    private Long attendanceId;
    private Long teamId;
    private String leader;
    private Byte teamOrder;
    private Long classId;
    private String serial;
    private Byte present;
    private String pptName;
    private String pptUrl;
    private String reportName;
    private String reportUrl;
    private Byte seminarStatus;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Byte getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(Byte teamOrder) {
        this.teamOrder = teamOrder;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Byte getPresent() {
        return present;
    }

    public void setPresent(Byte present) {
        this.present = present;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName;
    }

    public String getPptUrl() {
        return pptUrl;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public Byte getSeminarStatus() {
        return seminarStatus;
    }

    public void setSeminarStatus(Byte seminarStatus) {
        this.seminarStatus = seminarStatus;
    }

}
