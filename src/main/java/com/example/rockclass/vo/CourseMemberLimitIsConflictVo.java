package com.example.rockclass.vo;

import com.example.rockclass.entity.CourseMemberLimitStrategy;

import java.util.List;

public class CourseMemberLimitIsConflictVo {
    private List<CourseMemberLimitStrategy> courseMemberLimitStrategies;
    private Byte isConflict;

    public List<CourseMemberLimitStrategy> getCourseMemberLimitStrategies() {
        return courseMemberLimitStrategies;
    }

    public void setCourseMemberLimitStrategies(List<CourseMemberLimitStrategy> courseMemberLimitStrategies) {
        this.courseMemberLimitStrategies = courseMemberLimitStrategies;
    }

    public Byte getIsConflict() {
        return isConflict;
    }

    public void setIsConflict(Byte isConflict) {
        this.isConflict = isConflict;
    }
}
