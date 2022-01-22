package com.example.professionallearning.model.bean;

public class SpecialList {
    private int id;
    //0表示没有答完过，1表示已答完但没有满分，2表示满分，默认为0
    private String isAnswer;
    //0表示第一次答题，1表示中途退出，默认为0
    private String isOut;
    private String specialTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(String isAnswer) {
        this.isAnswer = isAnswer;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public void setSpecialTitle(String specialTitle) {
        this.specialTitle = specialTitle;
    }

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }
}
