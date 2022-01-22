package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;

import java.util.List;

public interface IAnalysisSpecialCallback {
    void specialQuestionLoaded(List<Question> questions);
    void userAnswerLoaded(UserAnswer userAnswer);
    void onError();
    void onEmpty();
    void onLoading();
}
