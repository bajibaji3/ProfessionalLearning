package com.example.professionallearning.presenter;

import com.example.professionallearning.view.IAnswerFragmentCallback;
import com.example.professionallearning.view.ILearnFormCallback;
import com.example.professionallearning.view.ILearnPerformanceCallback;

public interface IPerformancePresenter {
    //
    void findPerformanceInLearnPer(String userId);
    //
    void findEverydayPerInLearnPer(String date,String userId);
    //
    void findPerInAnswerFragment(String userId);
    //LearnFormActivity
    void findAllPerInLearnForm();
    //LearnPerformanceActivity
    void registerLearnPerCallback(ILearnPerformanceCallback callBack);
    //LearnPerformanceActivity
    void unRegisterLearnPerCallback(ILearnPerformanceCallback callBack);
    //AnswerFragment
    void registerAnswerFragmentCallback(IAnswerFragmentCallback callBack);
    //AnswerFragment
    void unRegisterAnswerFragmentCallback(IAnswerFragmentCallback callBack);
    //LearnFormActivity
    void registerLearnFormCallback(ILearnFormCallback callBack);
    //LearnFormActivity
    void unRegisterLearnFormCallback(ILearnFormCallback callBack);
}
