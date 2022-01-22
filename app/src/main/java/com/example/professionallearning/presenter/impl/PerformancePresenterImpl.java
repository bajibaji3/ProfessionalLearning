package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.EverydayPerformance;
import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.presenter.IPerformancePresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.IAnswerFragmentCallback;
import com.example.professionallearning.view.ILearnFormCallback;
import com.example.professionallearning.view.ILearnPerformanceCallback;
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

public class PerformancePresenterImpl implements IPerformancePresenter {
    private ILearnPerformanceCallback mLearnPerformanceCallback = null;
    private IAnswerFragmentCallback mAnswerFragmentCallback = null;
    private ILearnFormCallback mLearnFormCallback = null;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "PerformancePresenterImpl";

    @Override
    public void findPerformanceInLearnPer(String userId) {
        if (mLearnPerformanceCallback != null){
            mLearnPerformanceCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findPerformance(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findPerformanceInLearnPer-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Performance performance = gson.fromJson(response.body().string(), Performance.class);
                        if (mLearnPerformanceCallback != null) {
                            if (performance == null) {
                                mLearnPerformanceCallback.onEmpty();
                            } else {
                                mLearnPerformanceCallback.loadPerformance(performance);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mLearnPerformanceCallback != null) {
                        mLearnPerformanceCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnPerformanceCallback != null) {
                    mLearnPerformanceCallback.onError();
                }
            }
        });
    }

    @Override
    public void findEverydayPerInLearnPer(String date,String userId) {
        if (mLearnPerformanceCallback != null){
            mLearnPerformanceCallback.onLoading();
        }
        Call<ResponseBody> task = api.findEverydayScore(date,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findEverydayPerInLearnPer-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        EverydayPerformance performance = gson.fromJson(response.body().string(),EverydayPerformance.class);
                        if (mLearnPerformanceCallback != null) {
                            if (performance == null) {
                                mLearnPerformanceCallback.onEverydayEmpty();
                            } else {
                                mLearnPerformanceCallback.loadEverydayPerformance(performance);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mLearnPerformanceCallback != null) {
                        mLearnPerformanceCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnPerformanceCallback != null) {
                    mLearnPerformanceCallback.onError();
                }
            }
        });
    }

    @Override
    public void findPerInAnswerFragment(String userId) {
        if (mAnswerFragmentCallback != null){
            mAnswerFragmentCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findPerformance(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findPerInAnswerFragment-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Performance performance = gson.fromJson(response.body().string(), Performance.class);
                        if (mAnswerFragmentCallback != null) {
                            if (performance == null) {
                                mAnswerFragmentCallback.onEmpty();
                            } else {
                                mAnswerFragmentCallback.loadPerformance(performance);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mAnswerFragmentCallback != null) {
                        mAnswerFragmentCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mAnswerFragmentCallback != null) {
                    mAnswerFragmentCallback.onError();
                }
            }
        });
    }

    @Override
    public void findAllPerInLearnForm() {
        if (mLearnFormCallback != null){
            mLearnFormCallback.onLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findAllPerformance();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findAllPerInLearnForm-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Performance>>() {}.getType();
                        List<Performance> lists = gson.fromJson(response.body().string(), type);
                        if (mLearnFormCallback != null) {
                            mLearnFormCallback.loadAllPerformance(lists);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mLearnFormCallback != null) {
                        mLearnFormCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnFormCallback != null) {
                    mLearnFormCallback.onError();
                }
            }
        });
    }

    @Override
    public void registerLearnPerCallback(ILearnPerformanceCallback callBack) {
        mLearnPerformanceCallback = callBack;
    }

    @Override
    public void unRegisterLearnPerCallback(ILearnPerformanceCallback callBack) {
        mLearnPerformanceCallback = null;
    }

    @Override
    public void registerAnswerFragmentCallback(IAnswerFragmentCallback callBack) {
        mAnswerFragmentCallback = callBack;
    }

    @Override
    public void unRegisterAnswerFragmentCallback(IAnswerFragmentCallback callBack) {
        mAnswerFragmentCallback = null;
    }

    @Override
    public void registerLearnFormCallback(ILearnFormCallback callBack) {
        mLearnFormCallback = callBack;
    }

    @Override
    public void unRegisterLearnFormCallback(ILearnFormCallback callBack) {
        mLearnFormCallback = null;
    }
}
