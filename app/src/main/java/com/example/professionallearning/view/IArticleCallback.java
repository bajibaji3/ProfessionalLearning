package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Article;

import java.util.List;

public interface IArticleCallback {
    void loadArticle(List<Article> articles);
    void onError();
    void onLoading();
}
