package com.example.professionallearning.presenter;

import com.example.professionallearning.model.bean.Comment;
import com.example.professionallearning.view.IVideoDetailCallback;

public interface IVideoCommentPresenter {
    //VideoActivity
    void getComment(int owner);
    //VideoActivity
    void addCommentInArticle(Comment comment, int articleId);
    //VideoActivity
    void findVideoCollect(int articleId, String userId);
    //VideoActivity
    void cancelCollected(int articleId, String userId);
    //VideoActivity
    void addCollected(int articleId, String userId, String articleTitle, String date);
    //VideoActivity
    void registerVideoActivityCallback(IVideoDetailCallback callBack);
    //VideoActivity
    void unRegisterVideoActivityCallback(IVideoDetailCallback callBack);
}
