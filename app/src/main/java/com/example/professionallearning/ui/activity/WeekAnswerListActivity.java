package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.WeekListAdapter;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.WeekList;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IWeekCallback;

import java.util.List;

public class WeekAnswerListActivity extends AppCompatActivity implements IWeekCallback {
    public static WeekAnswerListActivity weekAnswerActivity;//传递给非activity的类使用
    private WeekListAdapter adapter;
    private IQuestionPresenter questionPresenter;
    private ConstraintLayout recyclerBody;
    private TextView recyclerMes;
    private ImageView wifi;
    private String mPhone;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_answer_list);
        weekAnswerActivity=this;

        RecyclerView weekRecycler=findViewById(R.id.weekRecycler);
        recyclerBody = findViewById(R.id.recyclerBody);
        recyclerMes = findViewById(R.id.recyclerMes);
        wifi = findViewById(R.id.wifi);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        adapter=new WeekListAdapter();
        weekRecycler.setLayoutManager(new LinearLayoutManager(this));
        weekRecycler.setAdapter(adapter);
        setState(State.NONE);
        initPresenter();
        loadData();
        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            questionPresenter.getWeek(mPhone);
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            recyclerBody.setVisibility(View.VISIBLE);
            recyclerMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            recyclerBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            recyclerMes.setVisibility(View.VISIBLE);
            recyclerMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            recyclerBody.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            recyclerMes.setVisibility(View.VISIBLE);
            recyclerMes.setText("网络错误，请点击重试");
        }  else if(currentState == State.NONE) {
            recyclerBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            recyclerMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerWeekListCallback(this);
    }

    private void loadData() {
        //加载本用户对应的weekList表，如果表中有数据传给adapter，如果没有向里面插入数据
        questionPresenter.getWeek(mPhone);
    }

    @Override
    public void weekListLoaded(List<WeekList> weekLists) {
        setState(State.SUCCESS);
        adapter.setWeekLists(weekLists);
    }

    @Override
    public void initUserWeek() {
        questionPresenter.getWeek(mPhone);
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
    public void onEmpty() {
        //本用户对应的weekList表为空，向里面插入数据
        questionPresenter.initUserWeek(mPhone);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (questionPresenter != null) {
            questionPresenter.unRegisterWeekListCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("MyFlag",0);
        if (myFlag == 0) {
            Intent intent = new Intent(WeekAnswerListActivity.this,AnswerTypeActivity.class);
            startActivity(intent);
        } else if (myFlag == 1) {
            Intent intent = new Intent(WeekAnswerListActivity.this,LearnPerformanceActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
