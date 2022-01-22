package com.example.professionallearning.model.bean;

public class EverydayPerformance {
    private String date;
    private String dailyScore;
    private String weekScore;
    private String specialScore;
    private String challengeScore;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDailyScore() {
        return dailyScore;
    }

    public void setDailyScore(String dailyScore) {
        this.dailyScore = dailyScore;
    }

    public String getWeekScore() {
        return weekScore;
    }

    public void setWeekScore(String weekScore) {
        this.weekScore = weekScore;
    }

    public String getSpecialScore() {
        return specialScore;
    }

    public void setSpecialScore(String specialScore) {
        this.specialScore = specialScore;
    }

    public String getChallengeScore() {
        return challengeScore;
    }

    public void setChallengeScore(String challengeScore) {
        this.challengeScore = challengeScore;
    }
}
