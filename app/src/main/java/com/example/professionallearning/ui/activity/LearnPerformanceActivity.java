package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.EverydayPerformance;
import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.presenter.IPerformancePresenter;
import com.example.professionallearning.presenter.impl.PerformancePresenterImpl;
import com.example.professionallearning.view.ILearnPerformanceCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LearnPerformanceActivity extends AppCompatActivity implements ILearnPerformanceCallback {
    private TextView totalTv,articleTv,videoTv,dailyTv,weekTv,specialTv,challengeTv,netMes;
    private Button articleBt,videoBt,dailyBt,weekBt,specialBt,challengeBt;
    private ImageView wifi;
    private ConstraintLayout constraintLayout;
    private String mPhone;
    private IPerformancePresenter perPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_performance);
        SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt("MyFlag",1);
        editor.apply();
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        initView();
        setState(State.NONE);
        initPresenter();
        loadData();
        dailyBt.setOnClickListener(View -> {
            Intent intent = new Intent(LearnPerformanceActivity.this,DailyAnswerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("flag",1);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });

        weekBt.setOnClickListener(View -> {
            Intent intent = new Intent(LearnPerformanceActivity.this,WeekAnswerListActivity.class);
            startActivity(intent);
            finish();
        });

        specialBt.setOnClickListener(View -> {
            Intent intent = new Intent(LearnPerformanceActivity.this,SpecialAnswerListActivity.class);
            startActivity(intent);
            finish();
        });

        challengeBt.setOnClickListener(View -> {
            Intent intent = new Intent(LearnPerformanceActivity.this,ChallengeAnswerActivity.class);
            startActivity(intent);
            finish();
        });


        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (perPresenter != null) {
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
        perPresenter = new PerformancePresenterImpl();
        perPresenter.registerLearnPerCallback(this);
    }

    private void loadData() {
        //找总积分
        perPresenter.findPerformanceInLearnPer(mPhone);
    }

    @Override
    public void loadPerformance(Performance performance) {
        totalTv.setText(performance.getTotalScore());
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        perPresenter.findEverydayPerInLearnPer(dateStr,mPhone);
    }

    @Override
    public void loadEverydayPerformance(EverydayPerformance everydayPerformance) {
        setState(State.SUCCESS);
        if (everydayPerformance.getDailyScore() == null) {
            dailyTv.setText("已获0分/每日上限5分");
        } else {
            if (Integer.parseInt(everydayPerformance.getDailyScore()) < 5) {
                String str1 = getString(R.string.everyday_per_str1);
                String str2 = String.format(str1,everydayPerformance.getDailyScore());
                dailyTv.setText(str2);
            } else {
                dailyTv.setText("已获5分/每日上限5分");
                dailyBt.setText("已完成");
                dailyBt.setBackgroundColor(android.graphics.Color.parseColor("#F2F3F5"));
                dailyBt.setTextColor(android.graphics.Color.parseColor("#ABBAB5"));
                dailyBt.setEnabled(false);
            }
        }

        if (everydayPerformance.getWeekScore() == null) {
            weekTv.setText("已获0分/每日上限5分");
        } else {
            if (Integer.parseInt(everydayPerformance.getWeekScore()) < 5) {
                String str1 = getString(R.string.everyday_per_str1);
                String str2 = String.format(str1,everydayPerformance.getWeekScore());
                weekTv.setText(str2);
            } else {
                weekTv.setText("已获5分/每日上限5分");
                weekBt.setText("已完成");
                weekBt.setBackgroundColor(android.graphics.Color.parseColor("#F2F3F5"));
                weekBt.setTextColor(android.graphics.Color.parseColor("#ABBAB5"));
                weekBt.setEnabled(false);
            }
        }

        if (everydayPerformance.getSpecialScore() == null) {
            specialTv.setText("已获0分/每日上限5分");
        } else {
            if (Integer.parseInt(everydayPerformance.getSpecialScore()) < 5) {
                String str1 = getString(R.string.everyday_per_str1);
                String str2 = String.format(str1,everydayPerformance.getSpecialScore());
                specialTv.setText(str2);
            } else {
                specialTv.setText("已获5分/每日上限5分");
                specialBt.setText("已完成");
                specialBt.setBackgroundColor(android.graphics.Color.parseColor("#F2F3F5"));
                specialBt.setTextColor(android.graphics.Color.parseColor("#ABBAB5"));
                specialBt.setEnabled(false);
            }
        }

        if (everydayPerformance.getChallengeScore() == null) {
            challengeTv.setText("已获0分/每日上限5分");
        } else {
            if (Integer.parseInt(everydayPerformance.getChallengeScore()) < 5) {
                String str1 = getString(R.string.everyday_per_str1);
                String str2 = String.format(str1,everydayPerformance.getChallengeScore());
                challengeTv.setText(str2);
            } else {
                challengeTv.setText("已获5分/每日上限5分");
                challengeBt.setText("已完成");
                challengeBt.setBackgroundColor(android.graphics.Color.parseColor("#F2F3F5"));
                challengeBt.setTextColor(android.graphics.Color.parseColor("#ABBAB5"));
                challengeBt.setEnabled(false);
            }
        }
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setState(State.SUCCESS);
        totalTv.setText("0");
    }

    @Override
    public void onEverydayEmpty() {
        setState(State.SUCCESS);
        dailyTv.setText("已获0分/每日上限5分");
        weekTv.setText("已获0分/每日上限5分");
        specialTv.setText("已获0分/每日上限5分");
        challengeTv.setText("已获0分/每日上限5分");
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (perPresenter != null) {
            perPresenter.unRegisterLearnPerCallback(this);
        }
    }

    private void initView() {
        totalTv = findViewById(R.id.textView23);
        articleTv = findViewById(R.id.textView27);
        videoTv = findViewById(R.id.textView30);
        dailyTv = findViewById(R.id.textView33);
        weekTv = findViewById(R.id.textView36);
        specialTv = findViewById(R.id.textView39);
        challengeTv = findViewById(R.id.textView41);
        articleBt = findViewById(R.id.button5);
        videoBt = findViewById(R.id.button6);
        dailyBt = findViewById(R.id.button7);
        weekBt = findViewById(R.id.button8);
        specialBt = findViewById(R.id.button9);
        challengeBt = findViewById(R.id.button10);
        netMes = findViewById(R.id.textView43);
        wifi = findViewById(R.id.imageView);
        constraintLayout = findViewById(R.id.constraintLayout17);

        articleTv.setText("已获0分/每日上限6分");
        videoTv.setText("已获0分/每日上限6分");
    }
}
