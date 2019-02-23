package com.example.rockclass.entity;

public class Attendance {
    private Long id;

    private KlassSeminar klassSeminar;

    private Team team;

    private Byte teamOrder;

    private Byte isPresent;

    private String reportName;

    private String reportUrl;

    private String pptName;

    private String pptUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KlassSeminar getKlassSeminar() {
        return klassSeminar;
    }

    public void setKlassSeminar(KlassSeminar klassSeminar) {
        this.klassSeminar = klassSeminar;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Byte getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(Byte teamOrder) {
        this.teamOrder = teamOrder;
    }

    public Byte getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Byte isPresent) {
        this.isPresent = isPresent;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl == null ? null : reportUrl.trim();
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName == null ? null : pptName.trim();
    }

    public String getPptUrl() {
        return pptUrl;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl == null ? null : pptUrl.trim();
    }
}