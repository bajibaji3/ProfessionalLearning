package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Performance;

import java.util.List;

public interface ILearnFormCallback {
    void loadAllPerformance(List<Performance> performances);
    void onError();
    void onLoading();
}
