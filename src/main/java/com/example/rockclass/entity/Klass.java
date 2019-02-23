package com.example.rockclass.entity;

public class Klass {
    private Long id;

    private Course course;

    private Integer grade;

    private Byte klassSerial;

    private String klassTime;

    private String klassLocation;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Byte getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Byte klassSerial) {
        this.klassSerial = klassSerial;
    }

    public String getKlassTime() {
        return klassTime;
    }

    public void setKlassTime(String klassTime) {
        this.klassTime = klassTime == null ? null : klassTime.trim();
    }

    public String getKlassLocation() {
        return klassLocation;
    }

    public void setKlassLocation(String klassLocation) {
        this.klassLocation = klassLocation == null ? null : klassLocation.trim();
    }
}