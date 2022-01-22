package com.example.professionallearning.model.bean;

public class Performance {
    private String totalScore;
    private String answerNum;
    private String answerScore;
    private String userId;

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(String answerNum) {
        this.answerNum = answerNum;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
