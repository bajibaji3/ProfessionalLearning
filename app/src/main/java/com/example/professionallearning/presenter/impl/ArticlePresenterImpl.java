package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.Article;
import com.example.professionallearning.presenter.IArticlePresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.IArticleCallback;
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

public class ArticlePresenterImpl implements IArticlePresenter {
    private IArticleCallback mArticleCallback = null;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "ArticlePresenterImpl";

    @Override
    public void getArticle() {
        if (mArticleCallback != null){
            mArticleCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findArticle();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getArticle-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Article>>() {}.getType();
                        List<Article> lists = gson.fromJson(response.body().string(), type);
                        if (mArticleCallback != null) {
                            mArticleCallback.loadArticle(lists);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mArticleCallback != null) {
                        mArticleCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mArticleCallback != null) {
                    mArticleCallback.onError();
                }
            }
        });
    }

    @Override
    public void registerGetArticleCallback(IArticleCallback callBack) {
        mArticleCallback = callBack;
    }

    @Override
    public void unRegisterGetArticleCallback(IArticleCallback callBack) {
        mArticleCallback = null;
    }
}
