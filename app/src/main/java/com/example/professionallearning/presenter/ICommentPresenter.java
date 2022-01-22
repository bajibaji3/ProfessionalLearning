package com.example.professionallearning.presenter;

import com.example.professionallearning.model.bean.Comment;
import com.example.professionallearning.view.ILearnDetailCallback;

public interface ICommentPresenter {
    //LearnDetailActivity
    void getCommentInLe(int owner);
    //LearnDetailActivity
    void addCommentInArticle(Comment comment,int articleId);
    //LearnDetailActivity
    void findArticleCollect(int articleId,String userId);
    //LearnDetailActivity
    void cancelCollected(int articleId,String userId);
    //LearnDetailActivity
    void addCollected(int articleId,String userId,String articleTitle,String date);
    //LearnDetailActivity
    void registerLearnDetailCallback(ILearnDetailCallback callBack);
    //LearnDetailActivity
    void unRegisterLearnDetailCallback(ILearnDetailCallback callBack);
}
