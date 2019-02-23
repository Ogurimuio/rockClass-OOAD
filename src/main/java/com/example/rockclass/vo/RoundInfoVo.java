package com.example.rockclass.vo;

import javax.validation.constraints.Max;
import java.util.List;

public class RoundInfoVo {
    String calculatePreType;
    String calculateQueType;
    String calculateRepType;
    List<ClassRoundVo> classRound;

    public List<ClassRoundVo> getClassRound() {
        return classRound;
    }

    public void setClassRound(List<ClassRoundVo> classRound) {
        this.classRound = classRound;
    }

    public String getCalculatePreType() {
        return calculatePreType;
    }

    public void setCalculatePreType(String calculatePreType) {
        this.calculatePreType = calculatePreType;
    }

    public String getCalculateQueType() {
        return calculateQueType;
    }

    public void setCalculateQueType(String calculateQueType) {
        this.calculateQueType = calculateQueType;
    }

    public String getCalculateRepType() {
        return calculateRepType;
    }

    public void setCalculateRepType(String calculateRepType) {
        this.calculateRepType = calculateRepType;
    }


}
