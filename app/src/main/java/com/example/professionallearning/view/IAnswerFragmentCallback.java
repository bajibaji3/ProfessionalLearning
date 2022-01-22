package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Performance;

public interface IAnswerFragmentCallback {
    void loadPerformance(Performance performance);
    void onError();
    void onEmpty();
    void onLoading();
}
