package com.example.rockclass.entity;

public class Team {
    private Long id;

    private Klass klass;

    private Course course;

    private Student leader;

    private String teamName;

    private Byte teamSerial;

    private Byte status;

    private Byte klassSerial;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public Student getLeader() {
        return leader;
    }

    public void setLeader(Student leader) {
        this.leader = leader;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public Byte getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(Byte teamSerial) {
        this.teamSerial = teamSerial;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Byte klassSerial) {
        this.klassSerial = klassSerial;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", klass=" + klass +
                ", course=" + course +
                ", leader=" + leader +
                ", teamName='" + teamName + '\'' +
                ", teamSerial=" + teamSerial +
                ", status=" + status +
                ", klassSerial=" + klassSerial +
                '}';
    }
}