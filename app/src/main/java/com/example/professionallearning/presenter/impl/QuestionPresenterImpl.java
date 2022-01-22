package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;


import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.EverydayPerformance;
import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.SpecialList;
import com.example.professionallearning.model.bean.UserAnswer;
import com.example.professionallearning.model.bean.WeekList;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.IAnalysisSpecialCallback;
import com.example.professionallearning.view.IChallengeCallback;
import com.example.professionallearning.view.IChallengeResultCallback;
import com.example.professionallearning.view.IDailyQuestionCallback;
import com.example.professionallearning.view.IDailyResultCallback;
import com.example.professionallearning.view.IQuestionCallBack;
import com.example.professionallearning.view.ISpecialCallback;
import com.example.professionallearning.view.ISpecialQuestionCallback;
import com.example.professionallearning.view.ISpecialResultCallback;
import com.example.professionallearning.view.IWeekCallback;
import com.example.professionallearning.view.IWeekQuestionCallback;
import com.example.professionallearning.view.IWeekResultCallback;
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

public class QuestionPresenterImpl implements IQuestionPresenter {
    private IQuestionCallBack mCallback = null;
    private IWeekResultCallback mWeekResultCallback = null;
    private IChallengeCallback mChallengeCallback = null;
    private IChallengeResultCallback mChallengeResultCallback = null;
    private IWeekQuestionCallback mWeekQuestionCallback = null;
    private IWeekCallback mWeekCallback = null;
    private IDailyQuestionCallback mDailyQuestionCallback = null;
    private ISpecialCallback mSpecialCallback = null;
    private ISpecialQuestionCallback mSpecialQuestionCallback = null;
    private ISpecialResultCallback mSpecialResultCallback = null;
    private IAnalysisSpecialCallback mAnalysisSpecialCallback = null;
    private IDailyResultCallback mDailyResultCallback = null;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "QuestionPresenterImpl";

    @Override
    public void getPerformanceInAnswerType(String userId) {
        if (mCallback != null){
            mCallback.onLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findPerformance(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call,@Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getPerformanceInAnswerType-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Performance performance = gson.fromJson(response.body().string(), Performance.class);
                        if (mCallback != null) {
                            if (performance == null) {
                                mCallback.onEmpty();
                            } else {
                                mCallback.onPerformanceLoaded(performance);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mCallback != null) {
                        mCallback.onNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mCallback != null) {
                    mCallback.onNetworkError();
                }
            }
        });
    }

    @Override
    public void updateIsAnswerByWeek(String week,String userId) {
        Call<ResponseBody> task = api.updateIsAnswerByWeek(week,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateIsAnswerByWeek-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.updateIsAnswerByWeek();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void getIsAnswerByWeek(String week,String userId) {
        if (mWeekResultCallback != null){
            mWeekResultCallback.onGetLoading();
        }
        //加载周次信息
        Call<ResponseBody> task = api.findIsAnswerByWeek(week,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getIsAnswerByWeek-->" + response.code());
                if (response.code() == 200) {
                    try {
                        String isAnswer = response.body().string();
                        if (mWeekResultCallback != null) {
                            if (isAnswer.equals("")) {
                                mWeekResultCallback.onGetEmpty();
                            } else {
                                mWeekResultCallback.loadIsAnswerByWeek(isAnswer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onGetError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onGetError();
                }
            }
        });
    }

    @Override
    public void getWeekScore(String date,String userId) {
        if (mWeekResultCallback != null){
            mWeekResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.findEverydayScore(date,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getWeekScore-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        EverydayPerformance performance = gson.fromJson(response.body().string(),EverydayPerformance.class);
                        if (mWeekResultCallback != null) {
                            if (performance == null) {
                                mWeekResultCallback.onScoreEmpty();
                            } else {
                                mWeekResultCallback.loadWeekScore(performance.getWeekScore());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void insertWeekScore(String date,String userId,String score) {
        if (mWeekResultCallback != null) {
            mWeekResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.insertWeekScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertWeekScore-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.insertWeekScore();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updateWeekScore(String date,String userId,String score) {
        if (mWeekResultCallback != null) {
            mWeekResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.updateWeekScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateWeekScore-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.updateWeekScore();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void getPerformanceInWeek(String userId) {
        if (mWeekResultCallback != null){
            mWeekResultCallback.onScoreLoading();
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
                        if (mWeekResultCallback != null) {
                            if (performance == null) {
                                mWeekResultCallback.onPerEmpty();
                            } else {
                                mWeekResultCallback.loadPerformance();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void insertPerInWeek(String totalScore, String answerNum, String answerScore, String userId) {
        if (mWeekResultCallback != null) {
            mWeekResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.insertPerformance(totalScore,answerNum,answerScore,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateDailyScore-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.insertPerformance();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updatePerformanceInWeek(String score,String userId) {
        if (mWeekResultCallback != null) {
            mWeekResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.updatePerformanceInDaily(score,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInWeek-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.updatePerformance();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updateAnswerNumInWeek(String userId) {
        if (mWeekResultCallback != null) {
            mWeekResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.updateAnswerNum(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInWeek-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.updateAnswerNum();
                    }
                } else {
                    //请求失败
                    if (mWeekResultCallback != null) {
                        mWeekResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekResultCallback != null) {
                    mWeekResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void findAllChallengeQuestion() {
        if (mChallengeCallback != null){
            mChallengeCallback.onQuestionLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findAllChallengeQuestion();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findAllChallengeQuestion-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Question>>() {}.getType();
                        List<Question> lists = gson.fromJson(response.body().string(), type);
                        if (mChallengeCallback != null) {
                            if (lists.size() == 0) {
                                mChallengeCallback.onQuestionEmpty();
                            } else {
                                mChallengeCallback.loadAllChallengeQuestion(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mChallengeCallback != null) {
                        mChallengeCallback.onQuestionError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeCallback != null) {
                    mChallengeCallback.onQuestionError();
                }
            }
        });
    }

    @Override
    public void insertHighScore(String highScore,String userId) {
        Call<ResponseBody> task = api.insertHighScore(highScore,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertHighScore-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeCallback != null) {
                        mChallengeCallback.insertHighScore();
                    }
                } else {
                    //请求失败
                    if (mChallengeCallback != null) {
                        mChallengeCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeCallback != null) {
                    mChallengeCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void findHighScore(String userId) {
        Call<ResponseBody> task = api.findHighScore(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findHighScore-->" + response.code());
                if (response.code() == 200) {
                    try {
                        String highScore = response.body().string();
                        if (mChallengeCallback != null) {
                            if (highScore.equals("")) {
                                mChallengeCallback.onScoreEmpty();
                            } else {
                                mChallengeCallback.findHighScore(highScore);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mChallengeCallback != null) {
                        mChallengeCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeCallback != null) {
                    mChallengeCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updateHighScore(String highScore,String userId) {
        Call<ResponseBody> task = api.updateHighScore(highScore,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateHighScore-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeCallback != null) {
                        mChallengeCallback.updateHighScore();
                    }
                } else {
                    //请求失败
                    if (mChallengeCallback != null) {
                        mChallengeCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeCallback != null) {
                    mChallengeCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void getScoreResult(String userId) {
        if (mChallengeResultCallback != null) {
            mChallengeResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.findHighScore(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getScoreResult-->" + response.code());
                if (response.code() == 200) {
                    try {
                        String highScore = response.body().string();
                        if (mChallengeResultCallback != null) {
                            if (highScore.equals("")) {
                                mChallengeResultCallback.onScoreEmpty();
                            } else {
                                mChallengeResultCallback.findHighScore(highScore);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void getChallengeScore(String date,String userId) {
        if (mChallengeResultCallback != null){
            mChallengeResultCallback.onPerformanceLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findEverydayScore(date,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getChallengeScore-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        EverydayPerformance performance = gson.fromJson(response.body().string(),EverydayPerformance.class);
                        if (mChallengeResultCallback != null) {
                            if (performance == null) {
                                mChallengeResultCallback.onPerformanceEmpty();
                            } else {
                                mChallengeResultCallback.loadChallengeScore(performance.getChallengeScore());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onPerformanceError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onPerformanceError();
                }
            }
        });
    }

    @Override
    public void insertChallengeScore(String date,String userId, String score) {
        if (mChallengeResultCallback != null) {
            mChallengeResultCallback.onPerformanceLoading();
        }
        Call<ResponseBody> task = api.insertChallengeScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertChallengeScore-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.insertChallengeScore();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onPerformanceError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onPerformanceError();
                }
            }
        });
    }

    @Override
    public void updateChallengeScore(String date,String userId, String score) {
        if (mChallengeResultCallback != null) {
            mChallengeResultCallback.onPerformanceLoading();
        }
        Call<ResponseBody> task = api.updateChallengeScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateDailyScore-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.updateChallengeScore();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onPerformanceError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onPerformanceError();
                }
            }
        });
    }

    @Override
    public void getPerformanceInChallenge(String userId) {
        if (mChallengeResultCallback != null){
            mChallengeResultCallback.onScoreLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findPerformance(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getPerformanceInChallenge-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Performance performance = gson.fromJson(response.body().string(), Performance.class);
                        if (mChallengeResultCallback != null) {
                            if (performance == null) {
                                mChallengeResultCallback.onPerEmpty();
                            } else {
                                mChallengeResultCallback.loadPerformance();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void insertPerInChallenge(String totalScore, String answerNum, String answerScore, String userId) {
        if (mChallengeResultCallback != null) {
            mChallengeResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.insertPerformance(totalScore,answerNum,answerScore,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertPerInSpecial-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.insertPerformance();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updatePerformanceInChallenge(String score,String userId) {
        if (mChallengeResultCallback != null) {
            mChallengeResultCallback.onPerformanceLoading();
        }
        Call<ResponseBody> task = api.updatePerformanceInDaily(score,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInDaily-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.updatePerformance();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onPerformanceError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onPerformanceError();
                }
            }
        });
    }

    @Override
    public void updateAnswerNumInChallenge(String userId) {
        if (mChallengeResultCallback != null) {
            mChallengeResultCallback.onPerformanceLoading();
        }
        Call<ResponseBody> task = api.updateAnswerNum(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInDaily-->" + response.code());
                if (response.code() == 200) {
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.updateAnswerNum();
                    }
                } else {
                    //请求失败
                    if (mChallengeResultCallback != null) {
                        mChallengeResultCallback.onPerformanceError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mChallengeResultCallback != null) {
                    mChallengeResultCallback.onPerformanceError();
                }
            }
        });
    }

    @Override
    public void getWeek(String userId) {
        if (mWeekCallback != null){
            mWeekCallback.onLoading();
        }
        //加载周次信息
        Call<ResponseBody> task = api.getWeekList(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getWeek-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<WeekList>>() {}.getType();
                        List<WeekList> lists = gson.fromJson(response.body().string(), type);
                        if (mWeekCallback != null) {
                            if (lists.size() == 0) {
                                mWeekCallback.onEmpty();
                            } else {
                                mWeekCallback.weekListLoaded(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mWeekCallback != null) {
                        mWeekCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekCallback != null) {
                    mWeekCallback.onError();
                }
            }
        });
    }

    @Override
    public void initUserWeek(String userId) {
        if (mWeekCallback != null) {
            mWeekCallback.onLoading();
        }
        Call<ResponseBody> task = api.initUserWeek(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "initUserWeek-->" + response.code());
                if (response.code() == 200) {
                    if (mWeekCallback != null) {
                        mWeekCallback.initUserWeek();
                    }
                } else {
                    //请求失败
                    if (mWeekCallback != null) {
                        mWeekCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekCallback != null) {
                    mWeekCallback.onError();
                }
            }
        });
    }

    @Override
    public void getQuestionByWeek() {
        if (mWeekQuestionCallback != null){
            mWeekQuestionCallback.onLoading();
        }
        //加载每周答题题目
        Call<ResponseBody> task = api.findAllWeekQuestion();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getQuestionByWeek-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Question>>() {}.getType();
                        List<Question> lists = gson.fromJson(response.body().string(), type);
                        if (mWeekQuestionCallback != null) {
                            if (lists.size() == 0) {
                                mWeekQuestionCallback.onEmpty();
                            } else {
                                mWeekQuestionCallback.weekQuestionLoaded(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mWeekQuestionCallback != null) {
                        mWeekQuestionCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mWeekQuestionCallback != null) {
                    mWeekQuestionCallback.onError();
                }
            }
        });
    }

    @Override
    public void getDailyQuestion() {
        if (mDailyQuestionCallback != null){
            mDailyQuestionCallback.onLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findAllDailyQuestion();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getDailyQuestion-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Question>>() {}.getType();
                        List<Question> lists = gson.fromJson(response.body().string(), type);
                        if (mDailyQuestionCallback != null) {
                            if (lists.size() == 0) {
                                mDailyQuestionCallback.onEmpty();
                            } else {
                                mDailyQuestionCallback.loadDailyQuestion(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mDailyQuestionCallback != null) {
                        mDailyQuestionCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyQuestionCallback != null) {
                    mDailyQuestionCallback.onError();
                }
            }
        });
    }

    @Override
    public void getSpecial(String userId) {
        if (mSpecialCallback != null){
            mSpecialCallback.onLoading();
        }
        //加载专题信息
        Call<ResponseBody> task = api.getSpecialList(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getSpecial-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<SpecialList>>() {}.getType();
                        List<SpecialList> lists = gson.fromJson(response.body().string(), type);
                        if (mSpecialCallback != null) {
                            if (lists.size() == 0) {
                                mSpecialCallback.onEmpty();
                            } else {
                                mSpecialCallback.loadSpecialList(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialCallback != null) {
                        mSpecialCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialCallback != null) {
                    mSpecialCallback.onError();
                }
            }
        });
    }

    @Override
    public void initUserSpecial(String userId) {
        if (mSpecialCallback != null) {
            mSpecialCallback.onLoading();
        }
        Call<ResponseBody> task = api.initUserSpecial(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "initUserSpecial-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialCallback != null) {
                        mSpecialCallback.initSpecial();
                    }
                } else {
                    //请求失败
                    if (mSpecialCallback != null) {
                        mSpecialCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialCallback != null) {
                    mSpecialCallback.onError();
                }
            }
        });
    }

    @Override
    public void getSpecialQuestion() {
        if (mSpecialQuestionCallback != null){
            mSpecialQuestionCallback.onGetLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findAllSpecialQuestion();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getSpecialQuestion-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Question>>() {}.getType();
                        List<Question> lists = gson.fromJson(response.body().string(), type);
                        if (mSpecialQuestionCallback != null) {
                            if (lists.size() == 0) {
                                mSpecialQuestionCallback.onGetEmpty();
                            } else {
                                mSpecialQuestionCallback.loadSpecialQuestion(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onGetError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onGetError();
                }
            }
        });
    }

    @Override
    public void updateUserAnswer(String userAnswer1, String userAnswer2, String userAnswer3, String userAnswer4, String userAnswer5, String pageNum, String specialTitle,String userId) {
        if (mSpecialQuestionCallback != null) {
            mSpecialQuestionCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.updateUserAnswer(userAnswer1,userAnswer2,userAnswer3,userAnswer4,userAnswer5,pageNum,specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateUserAnswer-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.updateUserAnswer();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void updateUserAnswer5(List<String> userAnswers, String specialTitle,String userId) {
        if (mSpecialQuestionCallback != null) {
            mSpecialQuestionCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.updateUserAnswer5(userAnswers.get(0),userAnswers.get(1),userAnswers.get(2),userAnswers.get(3),userAnswers.get(4),specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateUserAnswer5-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.updateUserAnswer5();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void updateIsOut(String isOut, String specialTitle,String userId) {
        if (mSpecialQuestionCallback != null) {
            mSpecialQuestionCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.updateSpecialIsOut(isOut,specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateIsOut-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.updateIsOut();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onUpdateError();
                }
            }
        });
    }


    @Override
    public void findUserAnswer(String specialTitle,String userId) {
        if (mSpecialQuestionCallback != null){
            mSpecialQuestionCallback.onGetLoading();
        }
        //加载
        Call<ResponseBody> task = api.findUserAnswer(specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findUserAnswer-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        UserAnswer userAnswer = gson.fromJson(response.body().string(), UserAnswer.class);
                        if (mSpecialQuestionCallback != null) {
                            if (userAnswer == null) {
                                mSpecialQuestionCallback.onUserAnswerEmpty();
                            } else {
                                mSpecialQuestionCallback.findUserAnswer(userAnswer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onGetError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onGetError();
                }
            }
        });
    }

    @Override
    public void findUserAnswer5(String specialTitle, String userId) {
        if (mSpecialQuestionCallback != null){
            mSpecialQuestionCallback.onGetLoading();
        }
        //加载
        Call<ResponseBody> task = api.findUserAnswer5(specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findUserAnswer-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        UserAnswer userAnswer = gson.fromJson(response.body().string(), UserAnswer.class);
                        if (mSpecialQuestionCallback != null) {
                            if (userAnswer == null) {
                                mSpecialQuestionCallback.onUserAnswerEmpty5();
                            } else {
                                mSpecialQuestionCallback.findUserAnswer5();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onGetError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onGetError();
                }
            }
        });
    }

    @Override
    public void insertUserAnswer(List<String> answers, String specialTitle, String pageNum, String userId) {
        if (mSpecialQuestionCallback != null) {
            mSpecialQuestionCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.insertUserAnswer(answers.get(0),
                answers.get(1),
                answers.get(2),
                answers.get(3),
                answers.get(4),
                specialTitle,
                pageNum,
                userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateSpecialIsAnswer-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.insertUserAnswer();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void insertUserAnswer5(List<String> questionAnswers, List<String> userAnswers, String specialTitle, String userId) {
        if (mSpecialQuestionCallback != null) {
            mSpecialQuestionCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.insertUserAnswer5(questionAnswers.get(0),
                questionAnswers.get(1),
                questionAnswers.get(2),
                questionAnswers.get(3),
                questionAnswers.get(4),
                userAnswers.get(0),
                userAnswers.get(1),
                userAnswers.get(2),
                userAnswers.get(3),
                userAnswers.get(4),
                specialTitle,
                userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateSpecialIsAnswer-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.insertUserAnswer5();
                    }
                } else {
                    //请求失败
                    if (mSpecialQuestionCallback != null) {
                        mSpecialQuestionCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialQuestionCallback != null) {
                    mSpecialQuestionCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void getUserAnswer(String specialTitle,String userId) {
        if (mSpecialResultCallback != null){
            mSpecialResultCallback.onGetLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findUserAnswer5(specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getUserAnswer-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        UserAnswer userAnswer = gson.fromJson(response.body().string(), UserAnswer.class);
                        if (mSpecialResultCallback != null) {
                            if (userAnswer == null) {
                                mSpecialResultCallback.onGetEmpty();
                            } else {
                                mSpecialResultCallback.loadUserAnswer(userAnswer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onGetError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onGetError();
                }
            }
        });
    }

    @Override
    public void updateSpecialIsAnswer(String isAnswer,String isOut,String specialTitle,String userId) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.updateSpecialIsAnswer(isAnswer,isOut,specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateSpecialIsAnswer-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.updateSpecialIsAnswer();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void updateUserTemp(String userAnswer1, String userAnswer2, String userAnswer3, String userAnswer4, String userAnswer5, String pageNum, String specialTitle,String userId) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onUpdateLoading();
        }
        Call<ResponseBody> task = api.updateUserAnswer(userAnswer1,userAnswer2,userAnswer3,userAnswer4,userAnswer5,pageNum,specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateUserTemp-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.updateUserTemp();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onUpdateError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onUpdateError();
                }
            }
        });
    }

    @Override
    public void getIsAnswerBySpecial(String special,String userId) {
        if (mSpecialResultCallback != null){
            mSpecialResultCallback.onGetLoading();
        }
        Call<ResponseBody> task = api.findIsAnswerBySpecial(special,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getSpecialScore-->" + response.code());
                if (response.code() == 200) {
                    try {
                        String isAnswer = response.body().string();
                        if (mSpecialResultCallback != null) {
                            if (isAnswer.equals("")) {
                                mSpecialResultCallback.onGetEmpty();
                            } else {
                                mSpecialResultCallback.loadIsAnswer(isAnswer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onGetError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onGetError();
                }
            }
        });
    }

    @Override
    public void getSpecialScore(String date,String userId) {
        if (mSpecialResultCallback != null){
            mSpecialResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.findEverydayScore(date,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getSpecialScore-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        EverydayPerformance performance = gson.fromJson(response.body().string(),EverydayPerformance.class);
                        if (mSpecialResultCallback != null) {
                            if (performance == null) {
                                mSpecialResultCallback.onScoreEmpty();
                            } else {
                                mSpecialResultCallback.loadSpecialScore(performance.getWeekScore());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void insertSpecialScore(String date,String userId, String score) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.insertSpecialScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertSpecialScore-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.insertSpecialScore();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updateSpecialScore(String date, String userId,String score) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.updateSpecialScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateSpecialScore-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.updateSpecialScore();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void getPerformanceInSpecial(String userId) {
        if (mSpecialResultCallback != null){
            mSpecialResultCallback.onScoreLoading();
        }
        //加载数据
        Call<ResponseBody> task = api.findPerformance(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getPerformanceInSpecial-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Performance performance = gson.fromJson(response.body().string(), Performance.class);
                        if (mSpecialResultCallback != null) {
                            if (performance == null) {
                                mSpecialResultCallback.onPerEmpty();
                            } else {
                                mSpecialResultCallback.loadPerformance();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void insertPerInSpecial(String totalScore, String answerNum, String answerScore, String userId) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.insertPerformance(totalScore,answerNum,answerScore,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertPerInSpecial-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.insertPerformance();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updatePerformanceInSpecial(String score,String userId) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.updatePerformanceInDaily(score,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInSpecial-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.updatePerformance();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void updateAnswerNumInSpecial(String userId) {
        if (mSpecialResultCallback != null) {
            mSpecialResultCallback.onScoreLoading();
        }
        Call<ResponseBody> task = api.updateAnswerNum(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateAnswerNumInSpecial-->" + response.code());
                if (response.code() == 200) {
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.updateAnswerNum();
                    }
                } else {
                    //请求失败
                    if (mSpecialResultCallback != null) {
                        mSpecialResultCallback.onScoreError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mSpecialResultCallback != null) {
                    mSpecialResultCallback.onScoreError();
                }
            }
        });
    }

    @Override
    public void findSpecialQuestion() {
        if (mAnalysisSpecialCallback != null){
            mAnalysisSpecialCallback.onLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findAllSpecialQuestion();
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "findSpecialQuestion-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Question>>() {}.getType();
                        List<Question> lists = gson.fromJson(response.body().string(), type);
                        if (mAnalysisSpecialCallback != null) {
                            if (lists.size() == 0) {
                                mAnalysisSpecialCallback.onEmpty();
                            } else {
                                mAnalysisSpecialCallback.specialQuestionLoaded(lists);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mAnalysisSpecialCallback != null) {
                        mAnalysisSpecialCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mAnalysisSpecialCallback != null) {
                    mAnalysisSpecialCallback.onError();
                }
            }
        });
    }

    @Override
    public void getUserAnswer1(String specialTitle,String userId) {
        if (mAnalysisSpecialCallback != null){
            mAnalysisSpecialCallback.onLoading();
        }
        //加载挑战答题题目
        Call<ResponseBody> task = api.findUserAnswer5(specialTitle,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getUserAnswer1-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        UserAnswer userAnswer = gson.fromJson(response.body().string(), UserAnswer.class);
                        if (mAnalysisSpecialCallback != null) {
                            if (userAnswer == null) {
                                mAnalysisSpecialCallback.onEmpty();
                            } else {
                                mAnalysisSpecialCallback.userAnswerLoaded(userAnswer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mAnalysisSpecialCallback != null) {
                        mAnalysisSpecialCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mAnalysisSpecialCallback != null) {
                    mAnalysisSpecialCallback.onError();
                }
            }
        });
    }

    @Override
    public void getDailyScore(String date,String userId) {
        if (mDailyResultCallback != null){
            mDailyResultCallback.onLoading();
        }
        Call<ResponseBody> task = api.findEverydayScore(date,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getDailyScore-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        EverydayPerformance performance = gson.fromJson(response.body().string(),EverydayPerformance.class);
                        if (mDailyResultCallback != null) {
                            if (performance == null) {
                                mDailyResultCallback.onEmpty();
                            } else {
                                mDailyResultCallback.loadDailyScore(performance.getDailyScore());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void insertDailyScore(String date, String userId,String score) {
        if (mDailyResultCallback != null) {
            mDailyResultCallback.onLoading();
        }
        Call<ResponseBody> task = api.insertDailyScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertDailyScore-->" + response.code());
                if (response.code() == 200) {
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.insertDailyScore();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void updateDailyScore(String date,String userId, String score) {
        if (mDailyResultCallback != null) {
            mDailyResultCallback.onLoading();
        }
        Call<ResponseBody> task = api.updateDailyScore(date,userId,score);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateDailyScore-->" + response.code());
                if (response.code() == 200) {
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.updateDailyScore();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void getPerformanceInDaily(String userId) {
        if (mDailyResultCallback != null){
            mDailyResultCallback.onLoading();
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
                        if (mDailyResultCallback != null) {
                            if (performance == null) {
                                mDailyResultCallback.onPerEmpty();
                            } else {
                                mDailyResultCallback.loadPerformance();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void insertPerInDaily(String totalScore, String answerNum, String answerScore, String userId) {
        if (mDailyResultCallback != null) {
            mDailyResultCallback.onLoading();
        }
        Call<ResponseBody> task = api.insertPerformance(totalScore,answerNum,answerScore,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateDailyScore-->" + response.code());
                if (response.code() == 200) {
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.insertPerformance();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void updatePerformanceInDaily(String score,String userId) {
        if (mDailyResultCallback != null) {
            mDailyResultCallback.onLoading();
        }
        Call<ResponseBody> task = api.updatePerformanceInDaily(score,userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInDaily-->" + response.code());
                if (response.code() == 200) {
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.updatePerformance();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void updateAnswerNumInDaily(String userId) {
        if (mDailyResultCallback != null) {
            mDailyResultCallback.onLoading();
        }
        Call<ResponseBody> task = api.updateAnswerNum(userId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updatePerformanceInDaily-->" + response.code());
                if (response.code() == 200) {
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.updateAnswerNum();
                    }
                } else {
                    //请求失败
                    if (mDailyResultCallback != null) {
                        mDailyResultCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mDailyResultCallback != null) {
                    mDailyResultCallback.onError();
                }
            }
        });
    }

    @Override
    public void registerQuestionCallback(IQuestionCallBack callBack) {
        this.mCallback = callBack;
    }

    @Override
    public void unRegisterQuestionCallback(IQuestionCallBack callBack) {
        this.mCallback = null;
    }

    @Override
    public void registerWeekCallback(IWeekResultCallback callBack) {
        this.mWeekResultCallback = callBack;
    }

    @Override
    public void unRegisterWeekCallback(IWeekResultCallback callBack) {
        this.mWeekResultCallback = null;
    }

    @Override
    public void registerChallengeCallback(IChallengeCallback callBack) {
        mChallengeCallback = callBack;
    }

    @Override
    public void unRegisterChallengeCallback(IChallengeCallback callBack) {
        mChallengeCallback = null;
    }

    @Override
    public void registerChallengeResultCallback(IChallengeResultCallback callBack) {
        mChallengeResultCallback = callBack;
    }

    @Override
    public void unRegisterChallengeResultCallback(IChallengeResultCallback callBack) {
        mChallengeResultCallback = null;
    }

    @Override
    public void registerWeekListCallback(IWeekCallback callBack) {
        mWeekCallback = callBack;
    }

    @Override
    public void unRegisterWeekListCallback(IWeekCallback callBack) {
        mWeekCallback = null;
    }

    @Override
    public void registerWeekQuestionCallback(IWeekQuestionCallback callBack) {
        mWeekQuestionCallback = callBack;
    }

    @Override
    public void unRegisterWeekQuestionCallback(IWeekQuestionCallback callBack) {
        mWeekQuestionCallback = null;
    }

    @Override
    public void registerDailyQuestionCallback(IDailyQuestionCallback callBack) {
        mDailyQuestionCallback = callBack;
    }

    @Override
    public void unRegisterDailyQuestionCallback(IDailyQuestionCallback callBack) {
        mDailyQuestionCallback = null;
    }

    @Override
    public void registerSpecialCallback(ISpecialCallback callBack) {
        mSpecialCallback = callBack;
    }

    @Override
    public void unRegisterSpecialCallback(ISpecialCallback callBack) {
        mSpecialCallback = null;
    }

    @Override
    public void registerSpecialQuestionCallback(ISpecialQuestionCallback callBack) {
        mSpecialQuestionCallback = callBack;
    }

    @Override
    public void unRegisterSpecialQuestionCallback(ISpecialQuestionCallback callBack) {
        mSpecialQuestionCallback = null;
    }

    @Override
    public void registerSpecialResultCallback(ISpecialResultCallback callBack) {
        mSpecialResultCallback = callBack;
    }

    @Override
    public void unRegisterSpecialResultCallback(ISpecialResultCallback callBack) {
        mSpecialResultCallback = null;
    }

    @Override
    public void registerSpecialAnalysisCallback(IAnalysisSpecialCallback callBack) {
        mAnalysisSpecialCallback = callBack;
    }

    @Override
    public void unRegisterSpecialAnalysisCallback(IAnalysisSpecialCallback callBack) {
        mAnalysisSpecialCallback = null;
    }

    @Override
    public void registerDailyResultCallback(IDailyResultCallback callBack) {
        mDailyResultCallback = callBack;
    }

    @Override
    public void unRegisterDailyResultCallback(IDailyResultCallback callBack) {
        mDailyResultCallback = null;
    }
}
