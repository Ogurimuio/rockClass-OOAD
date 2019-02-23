package com.example.rockclass.entity;

import java.util.Date;

public class KlassSeminar {
    private Long id;

    private Klass klass;

    private Seminar seminar;

    private Date reportDdl;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public Date getReportDdl() {
        return reportDdl;
    }

    public void setReportDdl(Date reportDdl) {
        this.reportDdl = reportDdl;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}