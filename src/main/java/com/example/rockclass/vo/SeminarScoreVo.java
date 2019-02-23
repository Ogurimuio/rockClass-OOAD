package com.example.rockclass.vo;

import java.math.BigDecimal;

public class SeminarScoreVo {
    Long teamId;
    BigDecimal preScore;
    BigDecimal reportScore;
    BigDecimal questionScore;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public BigDecimal getPreScore() {
        return preScore;
    }

    public void setPreScore(BigDecimal preScore) {
        this.preScore = preScore;
    }

    public BigDecimal getReportScore() {
        return reportScore;
    }

    public void setReportScore(BigDecimal reportScore) {
        this.reportScore = reportScore;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }
}
