package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IQuestionCallBack;

public class AnswerTypeActivity extends AppCompatActivity implements IQuestionCallBack {
    private TextView netMes,answerNum,award;
    private ImageView wifi;
    private ConstraintLayout constraintLayout;
    private String mPhone;
    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_type);
        SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt("MyFlag",0);
        editor.apply();
        answerNum = findViewById(R.id.answerNum);
        award = findViewById(R.id.award);
        CardView dailyAnswer=findViewById(R.id.dailyAnswer);
        CardView weekAnswer=findViewById(R.id.weekAnswer);
        CardView specialAnswer=findViewById(R.id.specialAnswer);
        CardView challengeAnswer=findViewById(R.id.challengeAnswer);
        netMes = findViewById(R.id.netMes);
        wifi = findViewById(R.id.wifi);
        constraintLayout = findViewById(R.id.con);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();

        setState(State.NONE);
        initPresenter();
        loadData();

        dailyAnswer.setOnClickListener(View -> {
            Intent intent=new Intent(AnswerTypeActivity.this, DailyAnswerActivity.class);
            startActivity(intent);
            finish();
        });

        weekAnswer.setOnClickListener(View -> {
            Intent intent=new Intent(AnswerTypeActivity.this, WeekAnswerListActivity.class);
            startActivity(intent);
            finish();
        });

        specialAnswer.setOnClickListener(View -> {
            Intent intent=new Intent(AnswerTypeActivity.this, SpecialAnswerListActivity.class);
            startActivity(intent);
            finish();
        });

        challengeAnswer.setOnClickListener(View -> {
            Intent intent=new Intent(AnswerTypeActivity.this, ChallengeAnswerActivity.class);
            startActivity(intent);
            finish();
        });

        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            loadData();
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            constraintLayout.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            constraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            constraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("网络错误，请点击重试");
        } else if(currentState == State.NONE) {
            constraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerQuestionCallback(this);
    }

    private void loadData() {
        //加载答题分数等数据
        questionPresenter.getPerformanceInAnswerType(mPhone);
    }

    @Override
    public void onPerformanceLoaded(Performance performance) {
        setState(State.SUCCESS);
        answerNum.setText(performance.getAnswerNum());
        award.setText(performance.getAnswerScore());
    }

    @Override
    public void onNetworkError() {
        setState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setState(State.SUCCESS);
        answerNum.setText("0");
        award.setText("0");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (questionPresenter != null) {
            questionPresenter.unRegisterQuestionCallback(this);
        }
    }
}
