package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.EverydayPerformance;
import com.example.professionallearning.model.bean.Performance;

public interface ILearnPerformanceCallback {
    void loadPerformance(Performance performance);
    void loadEverydayPerformance(EverydayPerformance everydayPerformance);
    void onError();
    void onEmpty();
    void onEverydayEmpty();
    void onLoading();
}
