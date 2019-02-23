package com.example.rockclass.entity;

import java.math.BigDecimal;

public class Question {
    private Long id;

    private KlassSeminar klassSeminar;

    private Attendance attendance;

    private Team team;

    private Student student;

    private Byte isSelected;

    private BigDecimal score;

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

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Byte getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Byte isSelected) {
        this.isSelected = isSelected;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", klassSeminar=" + klassSeminar +
                ", attendance=" + attendance +
                ", team=" + team +
                ", student=" + student +
                ", isSelected=" + isSelected +
                ", score=" + score +
                '}';
    }
}