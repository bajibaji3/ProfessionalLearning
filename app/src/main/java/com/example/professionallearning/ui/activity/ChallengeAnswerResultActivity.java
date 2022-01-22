package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IChallengeResultCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChallengeAnswerResultActivity extends AppCompatActivity implements IChallengeResultCallback {
    private IQuestionPresenter questionPresenter;
    private TextView textView1,textView2,resultMes;
    private ConstraintLayout resultBody;
    private ImageView wifi;
    private int currentScore;
    private int oldScore;
    private String mPhone;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }
    private String TAG = "Challenge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_answer_result);
        textView1 = findViewById(R.id.textView14);
        textView2 = findViewById(R.id.textView15);
        resultMes = findViewById(R.id.resultMes);
        resultBody = findViewById(R.id.resultBody);
        wifi = findViewById(R.id.wifi);
        Button button = findViewById(R.id.button2);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        setState(State.NONE);
        initPresenter();
        loadData();
        button.setOnClickListener(View -> {
            Intent intent=new Intent(ChallengeAnswerResultActivity.this, ChallengeAnswerActivity.class);
            startActivity(intent);
            finish();
        });

        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            questionPresenter.getScoreResult(mPhone);
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            resultBody.setVisibility(View.VISIBLE);
            resultMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            resultBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            resultMes.setVisibility(View.VISIBLE);
            resultMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            resultBody.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            resultMes.setVisibility(View.VISIBLE);
            resultMes.setText("网络错误，请点击重试");
        } else if(currentState == State.EMPTY) {
            resultBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            resultMes.setVisibility(View.VISIBLE);
            resultMes.setText("内容为空。。。");
        } else if(currentState == State.NONE) {
            resultBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            resultMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerChallengeResultCallback(this);
    }

    private void loadData() {
        Log.d(TAG,"loadData");


        //加载最高分
        questionPresenter.getScoreResult(mPhone);
    }

    @Override
    public void findHighScore(String highScore) {
        Log.d(TAG,"findHighScore");


        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null) {
            currentScore=bundle.getInt("score");
        }
        String str1 = getString(R.string.challenge_current_score);
        String str2 = getString(R.string.challenge_high_score);
        String currentStr = String.format(str1,currentScore);
        String highStr = String.format(str2,Integer.valueOf(highScore));
        textView1.setText(currentStr);
        textView2.setText(highStr);
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.getChallengeScore(dateStr,mPhone);
    }

    @Override
    public void loadChallengeScore(String score) {
        Log.d(TAG,"loadChallengeScore");


        if (score == null) {
            oldScore = 0;
        } else {
            oldScore = Integer.parseInt(score);
        }
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.updateChallengeScore(dateStr,mPhone,String.valueOf(currentScore));
    }

    @Override
    public void insertChallengeScore() {
        Log.d(TAG,"insertChallengeScore");


        questionPresenter.getPerformanceInChallenge(mPhone);
    }

    @Override
    public void updateChallengeScore() {
        Log.d(TAG,"updateChallengeScore");


        questionPresenter.getPerformanceInChallenge(mPhone);
    }

    @Override
    public void loadPerformance() {
        Log.d(TAG,"loadPerformance");



        if (oldScore < 5) {
            if (oldScore+currentScore <= 5)
                questionPresenter.updatePerformanceInChallenge(String.valueOf(currentScore),mPhone);
            else
                questionPresenter.updatePerformanceInChallenge(String.valueOf(5-oldScore),mPhone);
        } else {
            questionPresenter.updateAnswerNumInChallenge(mPhone);
        }
    }

    @Override
    public void insertPerformance() {
        Log.d(TAG,"insertPerformance");

        setState(State.SUCCESS);
    }

    @Override
    public void updatePerformance() {
        Log.d(TAG,"updatePerformance");


        questionPresenter.updateAnswerNumInChallenge(mPhone);
    }

    @Override
    public void updateAnswerNum() {
        Log.d(TAG,"updateAnswerNum");


        setState(State.SUCCESS);
    }

    @Override
    public void onScoreError() {
        setState(State.ERROR);
    }

    @Override
    public void onScoreLoading() {
        Log.d(TAG,"onScoreLoading");


        setState(State.LOADING);
    }

    @Override
    public void onScoreEmpty() {
        setState(State.EMPTY);
    }

    @Override
    public void onPerformanceError() {
        setState(State.ERROR);
    }

    @Override
    public void onPerformanceEmpty() {
        Log.d(TAG,"onPerformanceEmpty");


        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.insertChallengeScore(dateStr,mPhone,String.valueOf(currentScore));
    }

    @Override
    public void onPerEmpty() {
        Log.d(TAG,"onPerEmpty");


        questionPresenter.insertPerInChallenge(String.valueOf(currentScore),"1",String.valueOf(currentScore),mPhone);
    }

    @Override
    public void onPerformanceLoading() {
        Log.d(TAG,"onPerformanceLoading");


        setState(State.LOADING);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (questionPresenter != null) {
            questionPresenter.unRegisterChallengeResultCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("MyFlag",0);
        if (myFlag == 0) {
            Intent intent = new Intent(ChallengeAnswerResultActivity.this,AnswerTypeActivity.class);
            startActivity(intent);
        } else if (myFlag == 1) {
            Intent intent = new Intent(ChallengeAnswerResultActivity.this,LearnPerformanceActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
