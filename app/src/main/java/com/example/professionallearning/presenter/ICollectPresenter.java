package com.example.professionallearning.presenter;

import com.example.professionallearning.view.ICollectActivityCall;

public interface ICollectPresenter {
    //CollectedActivity
    void findAllCollect(String userId);
    //CollectedActivity
    void deleteUserCollect(int id);
    //CollectedActivity
    void addUserCollected(int articleId,String userId,String articleTitle,int articleType,String date);
    //CollectedActivity
    void registerCollectedActivityCall(ICollectActivityCall callBack);
    //CollectedActivity
    void unRegisterCollectedActivityCall(ICollectActivityCall callBack);
}
