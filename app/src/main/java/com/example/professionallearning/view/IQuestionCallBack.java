package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.SpecialList;
import com.example.professionallearning.model.bean.WeekList;

import java.util.List;

public interface IQuestionCallBack {
    void onPerformanceLoaded(Performance performance);
    void onNetworkError();
    void onLoading();
    void onEmpty();
}
