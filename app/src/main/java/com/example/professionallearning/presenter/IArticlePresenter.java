package com.example.professionallearning.presenter;

import com.example.professionallearning.view.IArticleCallback;

public interface IArticlePresenter {
    //LearnViewModel
    void getArticle();
    //LearnViewModel
    void registerGetArticleCallback(IArticleCallback callBack);
    //LearnViewModel
    void unRegisterGetArticleCallback(IArticleCallback callBack);
}
