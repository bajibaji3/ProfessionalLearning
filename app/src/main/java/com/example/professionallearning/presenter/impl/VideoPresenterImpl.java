package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.VideoUri;
import com.example.professionallearning.presenter.IVideoPresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.IVideoCallback;
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

public class VideoPresenterImpl implements IVideoPresenter {
    private IVideoCallback mVideoCallback = null;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "VideoPresenterImpl";

    @Override
    public void getVideoUri() {
        if (mVideoCallback != null){
            mVideoCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.getVideoUri();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getArticle-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<VideoUri>>() {}.getType();
                        List<VideoUri> lists = gson.fromJson(response.body().string(), type);
                        if (mVideoCallback != null) {
                            mVideoCallback.loadVideo(lists);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mVideoCallback != null) {
                        mVideoCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mVideoCallback != null) {
                    mVideoCallback.onError();
                }
            }
        });
    }

    @Override
    public void registerGetVideoCallback(IVideoCallback callBack) {
        mVideoCallback = callBack;
    }

    @Override
    public void unRegisterGetVideoCallback(IVideoCallback callBack) {
        mVideoCallback = null;
    }
}
