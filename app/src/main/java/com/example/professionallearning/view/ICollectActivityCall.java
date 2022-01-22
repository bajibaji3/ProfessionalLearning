package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.UserArticle;

import java.util.List;

public interface ICollectActivityCall {
    void loadCollect(List<UserArticle> userArticles);
    void deleteUserCollect();
    void addUserCollect();
    void onGetEmpty();
    void onLoading();
    void onError();
}
