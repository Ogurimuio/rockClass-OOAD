package com.example.rockclass.entity;

public class Round {
    private Long id;

    private Course course;

    private Byte roundSerial;

    private Byte presentationScoreMethod;

    private Byte reportScoreMethod;

    private Byte questionScoreMethod;

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

    public Byte getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(Byte roundSerial) {
        this.roundSerial = roundSerial;
    }

    public Byte getPresentationScoreMethod() {
        return presentationScoreMethod;
    }

    public void setPresentationScoreMethod(Byte presentationScoreMethod) {
        this.presentationScoreMethod = presentationScoreMethod;
    }

    public Byte getReportScoreMethod() {
        return reportScoreMethod;
    }

    public void setReportScoreMethod(Byte reportScoreMethod) {
        this.reportScoreMethod = reportScoreMethod;
    }

    public Byte getQuestionScoreMethod() {
        return questionScoreMethod;
    }

    public void setQuestionScoreMethod(Byte questionScoreMethod) {
        this.questionScoreMethod = questionScoreMethod;
    }
}