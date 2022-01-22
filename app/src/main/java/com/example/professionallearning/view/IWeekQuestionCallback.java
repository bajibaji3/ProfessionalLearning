package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Question;

import java.util.List;

public interface IWeekQuestionCallback {
    void weekQuestionLoaded(List<Question> questions);
    void onError();
    void onEmpty();
    void onLoading();
}
