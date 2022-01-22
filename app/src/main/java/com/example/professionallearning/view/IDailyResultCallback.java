package com.example.professionallearning.view;

public interface IDailyResultCallback {
    void loadDailyScore(String score);
    void insertDailyScore();
    void updateDailyScore();
    void loadPerformance();
    void insertPerformance();
    void updatePerformance();
    void updateAnswerNum();
    void onError();
    void onEmpty();
    void onPerEmpty();
    void onLoading();
}
