package com.example.professionallearning.presenter;

import com.example.professionallearning.view.IVideoCallback;

public interface IVideoPresenter {
    //LearnViewModel
    void getVideoUri();
    //LearnViewModel
    void registerGetVideoCallback(IVideoCallback callBack);
    //LearnViewModel
    void unRegisterGetVideoCallback(IVideoCallback callBack);
}
