package com.example.professionallearning.model.bean;

import java.io.Serializable;

public class UserAnswer implements Serializable {
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;
    private String userAnswer1;
    private String userAnswer2;
    private String userAnswer3;
    private String userAnswer4;
    private String userAnswer5;
    private String specialTitle;
    private String pageNum;
    private String userId;

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getUserAnswer1() {
        return userAnswer1;
    }

    public void setUserAnswer1(String userAnswer1) {
        this.userAnswer1 = userAnswer1;
    }

    public String getUserAnswer2() {
        return userAnswer2;
    }

    public void setUserAnswer2(String userAnswer2) {
        this.userAnswer2 = userAnswer2;
    }

    public String getUserAnswer3() {
        return userAnswer3;
    }

    public void setUserAnswer3(String userAnswer3) {
        this.userAnswer3 = userAnswer3;
    }

    public String getUserAnswer4() {
        return userAnswer4;
    }

    public void setUserAnswer4(String userAnswer4) {
        this.userAnswer4 = userAnswer4;
    }

    public String getUserAnswer5() {
        return userAnswer5;
    }

    public void setUserAnswer5(String userAnswer5) {
        this.userAnswer5 = userAnswer5;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public void setSpecialTitle(String specialTitle) {
        this.specialTitle = specialTitle;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
