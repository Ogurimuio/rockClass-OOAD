package com.example.rockclass.entity;

public class TeamStrategy {
    private Course course;

    private Long strategyId;

    private Byte strategySerial;

    private String strategyName;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Byte getStrategySerial() {
        return strategySerial;
    }

    public void setStrategySerial(Byte strategySerial) {
        this.strategySerial = strategySerial;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }
}
