package com.example.rockclass.vo;

public class CourseMemberLimitStrategyVo {
    Long courseId;
    Byte minMemberNumber;
    Byte maxMemberNumber;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Byte getMinMemberNumber() {
        return minMemberNumber;
    }

    public void setMinMemberNumber(Byte minMemberNumber) {
        this.minMemberNumber = minMemberNumber;
    }

    public Byte getMaxMemberNumber() {
        return maxMemberNumber;
    }

    public void setMaxMemberNumber(Byte maxMemberNumber) {
        this.maxMemberNumber = maxMemberNumber;
    }
}
