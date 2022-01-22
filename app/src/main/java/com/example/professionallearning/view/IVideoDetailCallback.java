package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Comment;

import java.util.List;

public interface IVideoDetailCallback {
    void loadComment(List<Comment> comments);
    void addComment();
    void loadUserArticle();
    void cancelCollect();
    void addCollect();
    void onError();
    void onLoading();
    void onGetEmpty();
    void onCollectEmpty();
}
