package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.UserAnswer;

public interface ISpecialResultCallback {
    void loadUserAnswer(UserAnswer userAnswer);
    void updateSpecialIsAnswer();
    void updateUserTemp();
    void loadIsAnswer(String isAnswer);
    void loadSpecialScore(String score);
    void insertSpecialScore();
    void updateSpecialScore();
    void loadPerformance();
    void insertPerformance();
    void updatePerformance();
    void updateAnswerNum();
    void onUpdateError();
    void onUpdateLoading();
    void onGetError();
    void onGetEmpty();
    void onGetLoading();
    void onScoreError();
    void onScoreEmpty();
    void onPerEmpty();
    void onScoreLoading();
}
