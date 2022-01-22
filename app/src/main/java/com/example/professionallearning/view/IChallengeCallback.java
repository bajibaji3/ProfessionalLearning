package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Question;

import java.util.List;

public interface IChallengeCallback {
    void loadAllChallengeQuestion(List<Question> questions);
    void insertHighScore();
    void findHighScore(String highScore);
    void updateHighScore();
    void onQuestionError();
    void onQuestionLoading();
    void onQuestionEmpty();
    void onScoreError();
    void onScoreLoading();
    void onScoreEmpty();
}
