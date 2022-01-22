package com.example.professionallearning.view;

public interface IChallengeResultCallback {
    void findHighScore(String highScore);
    void loadChallengeScore(String score);
    void insertChallengeScore();
    void updateChallengeScore();
    void loadPerformance();
    void insertPerformance();
    void updatePerformance();
    void updateAnswerNum();
    void onScoreError();
    void onScoreLoading();
    void onScoreEmpty();
    void onPerformanceError();
    void onPerformanceEmpty();
    void onPerEmpty();
    void onPerformanceLoading();
}
