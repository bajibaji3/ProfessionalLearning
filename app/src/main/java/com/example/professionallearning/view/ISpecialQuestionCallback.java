package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;

import java.util.List;

public interface ISpecialQuestionCallback {
    void loadSpecialQuestion(List<Question> questions);
    void updateUserAnswer();
    void updateUserAnswer5();
    void updateIsOut();
    void findUserAnswer(UserAnswer userAnswer);
    void insertUserAnswer();
    void findUserAnswer5();
    void insertUserAnswer5();
    void onGetError();
    void onGetEmpty();
    void onUserAnswerEmpty();
    void onUserAnswerEmpty5();
    void onGetLoading();
    void onUpdateError();
    void onUpdateLoading();
}
