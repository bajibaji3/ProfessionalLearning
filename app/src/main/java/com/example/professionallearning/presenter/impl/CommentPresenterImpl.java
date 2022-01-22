package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.Comment;
import com.example.professionallearning.model.bean.UserArticle;
import com.example.professionallearning.presenter.ICommentPresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.ILearnDetailCallback;
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

public class CommentPresenterImpl implements ICommentPresenter {
    private ILearnDetailCallback mLearnDetailCallback = null;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "CommentPresenterImpl";

    @Override
    public void getCommentInLe(int owner) {
        if (mLearnDetailCallback != null){
            mLearnDetailCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findCommentToArticle(owner,1);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getArticle-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Comment>>() {}.getType();
                        List<Comment> lists = gson.fromJson(response.body().string(), type);
                        if (mLearnDetailCallback != null) {
                            if (lists.size() == 0) {
                                mLearnDetailCallback.onGetEmpty();
                            } else {
                                mLearnDetailCallback.loadComment(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnDetailCallback != null) {
                    mLearnDetailCallback.onError();
                }
            }
        });
    }

    @Override
    public void addCommentInArticle(Comment comment, int articleId) {
        if (mLearnDetailCallback != null){
            mLearnDetailCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.addCommentToArticle(comment.getReviewer(),comment.getComment(),comment.getDate(),articleId,1);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "addCommentInArticle-->" + response.code());
                if (response.code() == 200) {
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.addComment();
                    }
                } else {
                    //请求失败
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnDetailCallback != null) {
                    mLearnDetailCallback.onError();
                }
            }
        });
    }

    @Override
    public void findArticleCollect(int articleId, String userId) {
        if (mLearnDetailCallback != null){
            mLearnDetailCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findUserArticle(articleId,userId,1);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getArticle-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        UserArticle userArticle = gson.fromJson(response.body().string(), UserArticle.class);
                        if (mLearnDetailCallback != null) {
                            if (userArticle == null) {
                                mLearnDetailCallback.onCollectEmpty();
                            } else {
                                mLearnDetailCallback.loadUserArticle();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnDetailCallback != null) {
                    mLearnDetailCallback.onError();
                }
            }
        });
    }

    @Override
    public void cancelCollected(int articleId, String userId) {
        if (mLearnDetailCallback != null){
            mLearnDetailCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.deleteUserArticle(articleId,userId,1);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "cancelCollected-->" + response.code());
                if (response.code() == 200) {
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.cancelCollect();
                    }
                } else {
                    //请求失败
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnDetailCallback != null) {
                    mLearnDetailCallback.onError();
                }
            }
        });
    }

    @Override
    public void addCollected(int articleId, String userId,String articleTitle,String date) {
        if (mLearnDetailCallback != null){
            mLearnDetailCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.insertUserArticle(articleId,userId,1,articleTitle,date);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "addCollected-->" + response.code());
                if (response.code() == 200) {
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.addCollect();
                    }
                } else {
                    //请求失败
                    if (mLearnDetailCallback != null) {
                        mLearnDetailCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLearnDetailCallback != null) {
                    mLearnDetailCallback.onError();
                }
            }
        });
    }

    @Override
    public void registerLearnDetailCallback(ILearnDetailCallback callBack) {
        mLearnDetailCallback = callBack;
    }

    @Override
    public void unRegisterLearnDetailCallback(ILearnDetailCallback callBack) {
        mLearnDetailCallback = null;
    }
}
