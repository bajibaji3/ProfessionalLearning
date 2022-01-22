package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.presenter.IPerformancePresenter;
import com.example.professionallearning.presenter.impl.PerformancePresenterImpl;
import com.example.professionallearning.view.ILearnFormCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnFormActivity extends AppCompatActivity implements ILearnFormCallback {
    private ConstraintLayout mConstraintLayout;
    private TextView netMes,totalScoreTv,rankTv,rankNumTv;
    private ImageView wifi;
    private String mPhone;
    private int mScore;
    private int mRank;
    private IPerformancePresenter perPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_form);
        mConstraintLayout = findViewById(R.id.cons1);
        netMes = findViewById(R.id.textView69);
        wifi = findViewById(R.id.imageView11);
        totalScoreTv = findViewById(R.id.textView48);
        rankTv = findViewById(R.id.textView49);
        rankNumTv = findViewById(R.id.textView51);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();

        setState(State.NONE);
        initPresenter();
        loadData();

        wifi.setOnClickListener(view -> onRetryClick());
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
            mConstraintLayout.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("网络错误，请点击重试");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        perPresenter = new PerformancePresenterImpl();
        perPresenter.registerLearnFormCallback(this);
    }

    private void loadData() {
        //找总积分
        perPresenter.findAllPerInLearnForm();
    }

    private void operatePerformance(List<Performance> performances) {
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<performances.size();i++) {
            if (performances.get(i).getUserId().equals(mPhone)) {
                mScore = Integer.parseInt(performances.get(i).getTotalScore());
            }
            list.add(Integer.valueOf(performances.get(i).getTotalScore()));
        }
        totalScoreTv.setText(String.valueOf(mScore));
        Collections.sort(list);
        Collections.reverse(list);
        for (int j=0;j<list.size();j++) {
            if (mScore == list.get(j)) {
                mRank = j + 1;
            }
        }
        rankNumTv.setText(String.valueOf(mRank));
        if (mScore < 100) {
            rankTv.setText("没有段位");
        } else if (mScore < 200) {
            rankTv.setText("一心一意");
        } else if (mScore < 300) {
            rankTv.setText("再接再厉");
        } else if (mScore < 400) {
            rankTv.setText("三省吾身");
        } else if (mScore < 500) {
            rankTv.setText("名扬四海");
        } else if (mScore < 600) {
            rankTv.setText("学富五车");
        } else if (mScore < 700) {
            rankTv.setText("六韬三略");
        } else if (mScore < 800) {
            rankTv.setText("七步才华");
        } else if (mScore < 900) {
            rankTv.setText("才高八斗");
        } else if (mScore < 1000) {
            rankTv.setText("九天揽月");
        } else {
            rankTv.setText("十年磨剑");
        }
    }

    @Override
    public void loadAllPerformance(List<Performance> performances) {
        setState(State.SUCCESS);
        operatePerformance(performances);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
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
            perPresenter.unRegisterLearnFormCallback(this);
        }
    }
}
