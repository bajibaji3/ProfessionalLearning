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
import com.example.professionallearning.adapter.SpecialListAdapter;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.SpecialList;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.ISpecialCallback;

import java.util.List;

public class SpecialAnswerListActivity extends AppCompatActivity implements ISpecialCallback {
    public static SpecialAnswerListActivity specialAnswerListActivity;//传递给非activity的类使用
    private ConstraintLayout mConfiguration;
    private TextView specialMes;
    private ImageView wifi;
    private SpecialListAdapter adapter;
    private String mPhone;
    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_answer_list);
        specialAnswerListActivity=this;

        RecyclerView specialRecycler = findViewById(R.id.specialRecycler);
        mConfiguration = findViewById(R.id.constraintLayout4);
        specialMes = findViewById(R.id.specialMes);
        wifi = findViewById(R.id.wifi);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        adapter = new SpecialListAdapter();
        specialRecycler.setLayoutManager(new LinearLayoutManager(this));
        specialRecycler.setAdapter(adapter);
        setState(State.NONE);
        initPresenter();
        loadData();
        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            questionPresenter.getSpecial(mPhone);
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            mConfiguration.setVisibility(View.VISIBLE);
            specialMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConfiguration.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            specialMes.setVisibility(View.VISIBLE);
            specialMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConfiguration.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            specialMes.setVisibility(View.VISIBLE);
            specialMes.setText("网络错误，请点击重试");
        } else if(currentState == State.NONE) {
            mConfiguration.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            specialMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerSpecialCallback(this);
    }

    private void loadData() {
        //加载本用户对应的specialList表，如果表中有数据传给adapter，如果没有向里面插入数据
        questionPresenter.getSpecial(mPhone);
    }

    @Override
    public void loadSpecialList(List<SpecialList> specialLists) {
        //本用户对应的specialList表中有数据
        setState(State.SUCCESS);
        adapter.setSpecialLists(specialLists);
    }

    @Override
    public void initSpecial() {
        questionPresenter.getSpecial(mPhone);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        //本用户对应的specialList表中没有数据，先向其插入数据
        questionPresenter.initUserSpecial(mPhone);
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
        if (questionPresenter != null) {
            questionPresenter.unRegisterSpecialCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("MyFlag",0);
        if (myFlag == 0) {
            Intent intent = new Intent(SpecialAnswerListActivity.this,AnswerTypeActivity.class);
            startActivity(intent);
        } else if (myFlag == 1) {
            Intent intent = new Intent(SpecialAnswerListActivity.this,LearnPerformanceActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
