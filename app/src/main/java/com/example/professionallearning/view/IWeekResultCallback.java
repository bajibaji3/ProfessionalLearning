package com.example.professionallearning.view;

public interface IWeekResultCallback {
    void updateIsAnswerByWeek();
    void loadIsAnswerByWeek(String isAnswer);
    void loadWeekScore(String score);
    void insertWeekScore();
    void updateWeekScore();
    void loadPerformance();
    void insertPerformance();
    void updatePerformance();
    void updateAnswerNum();
    void onGetError();
    void onGetEmpty();
    void onGetLoading();
    void onUpdateError();
    void onScoreError();
    void onScoreEmpty();
    void onPerEmpty();
    void onScoreLoading();
}
