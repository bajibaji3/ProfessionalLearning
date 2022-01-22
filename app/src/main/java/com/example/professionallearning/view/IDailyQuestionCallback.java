package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Question;

import java.util.List;

public interface IDailyQuestionCallback {
    void loadDailyQuestion(List<Question> questions);
    void onError();
    void onEmpty();
    void onLoading();
}
