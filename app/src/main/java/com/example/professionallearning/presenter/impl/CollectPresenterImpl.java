package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.UserArticle;
import com.example.professionallearning.presenter.ICollectPresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.ICollectActivityCall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CollectPresenterImpl implements ICollectPresenter {
    private ICollectActivityCall mCollectActivityCall = null;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "CollectPresenterImpl";

    @Override
    public void findAllCollect(String userId) {
        if (mCollectActivityCall != null){
            mCollectActivityCall.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findUserCollect(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findAllCollect-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<UserArticle>>() {}.getType();
                        List<UserArticle> lists = gson.fromJson(response.body().string(), type);
                        if (mCollectActivityCall != null) {
                            if (lists.size() == 0) {
                                mCollectActivityCall.onGetEmpty();
                            } else {
                                mCollectActivityCall.loadCollect(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mCollectActivityCall != null) {
                        mCollectActivityCall.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mCollectActivityCall != null) {
                    mCollectActivityCall.onError();
                }
            }
        });
    }

    @Override
    public void deleteUserCollect(int id) {
        if (mCollectActivityCall != null){
            mCollectActivityCall.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.deleteUserCollect(id);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "cancelCollected-->" + response.code());
                if (response.code() == 200) {
                    if (mCollectActivityCall != null) {
                        mCollectActivityCall.deleteUserCollect();
                    }
                } else {
                    //请求失败
                    if (mCollectActivityCall != null) {
                        mCollectActivityCall.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mCollectActivityCall != null) {
                    mCollectActivityCall.onError();
                }
            }
        });
    }

    @Override
    public void addUserCollected(int articleId, String userId, String articleTitle, int articleType,String date) {
        if (mCollectActivityCall != null){
            mCollectActivityCall.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.insertUserArticle(articleId,userId,articleType,articleTitle,date);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "addUserCollected-->" + response.code());
                if (response.code() == 200) {
                    if (mCollectActivityCall != null) {
                        mCollectActivityCall.addUserCollect();
                    }
                } else {
                    //请求失败
                    if (mCollectActivityCall != null) {
                        mCollectActivityCall.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mCollectActivityCall != null) {
                    mCollectActivityCall.onError();
                }
            }
        });
    }

    @Override
    public void registerCollectedActivityCall(ICollectActivityCall callBack) {
        mCollectActivityCall = callBack;
    }

    @Override
    public void unRegisterCollectedActivityCall(ICollectActivityCall callBack) {
        mCollectActivityCall = null;
    }
}
