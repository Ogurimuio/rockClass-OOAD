package com.example.rockclass.vo;

public class QuestionVO {
    private Long questionId;
    private String teamSerial;
    private String teamName;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(String teamSerial) {
        this.teamSerial = teamSerial;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
