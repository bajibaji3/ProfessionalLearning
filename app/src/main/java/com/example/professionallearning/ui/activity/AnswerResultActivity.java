package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.example.professionallearning.model.bean.User;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IDailyResultCallback;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnswerResultActivity extends AppCompatActivity implements IDailyResultCallback {
    private IQuestionPresenter questionPresenter;
    private CardView mConstraintLayout;
    private TextView dailyResMes,addPer;
    private ImageView wifi;
    private int mScore;
    private int oldScore;
    private List<User> list;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_result);
        mConstraintLayout = findViewById(R.id.cardView1);
        dailyResMes = findViewById(R.id.dailyResMes);
        wifi = findViewById(R.id.wifi);
        addPer = findViewById(R.id.textView57);
        TextView score=findViewById(R.id.score);
        TextView accuracy = findViewById(R.id.textView55);
        TextView falseNum = findViewById(R.id.textView56);
        Button buttonAgain=findViewById(R.id.buttonAgain);
        Button buttonBack=findViewById(R.id.button12);
        UserDao mUserDao = new UserDao(this);
        list = mUserDao.findUser();
        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null) {
            mScore=bundle.getInt("score");
        }
        score.setText(String.valueOf(mScore));
        String str1 = getString(R.string.answer_accuracy);
        String str2 = getString(R.string.answer_false_num);
        if (mScore == 0) {
            String currentStr = String.format(str1,"0");
            accuracy.setText(currentStr);
        } else {
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMaximumFractionDigits(2);
            String currentStr = String.format(str1,nf.format(mScore/5.0));
            accuracy.setText(currentStr);
        }
        String falseNumStr = String.format(str2,5-mScore);
        falseNum.setText(falseNumStr);

        setState(State.NONE);
        initPresenter();
        loadData();

        buttonAgain.setOnClickListener(view -> {
            Intent intent=new Intent(AnswerResultActivity.this, DailyAnswerActivity.class);
            startActivity(intent);
            finish();
        });

        buttonBack.setOnClickListener(view -> {
            SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
            int myFlag = shp.getInt("MyFlag",0);
            if (myFlag == 0) {
                Intent intent = new Intent(AnswerResultActivity.this,AnswerTypeActivity.class);
                startActivity(intent);
            } else if (myFlag == 1) {
                Intent intent = new Intent(AnswerResultActivity.this,LearnPerformanceActivity.class);
                startActivity(intent);
            }
            finish();
        });

        wifi.setOnClickListener(view -> onRetryClick());
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
            mConstraintLayout.setVisibility(View.VISIBLE);
            dailyResMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            dailyResMes.setVisibility(View.VISIBLE);
            dailyResMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            dailyResMes.setVisibility(View.VISIBLE);
            dailyResMes.setText("网络错误，请点击重试");
        } else if(currentState == State.EMPTY) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            dailyResMes.setVisibility(View.VISIBLE);
            dailyResMes.setText("内容为空。。。");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            dailyResMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerDailyResultCallback(this);
    }

    private void loadData() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.getDailyScore(dateStr,list.get(0).getPhone());
    }

    @Override
    public void loadDailyScore(String score) {
        if (score == null) {
            oldScore = 0;
        } else {
            oldScore = Integer.parseInt(score);
        }
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.updateDailyScore(dateStr,list.get(0).getPhone(),String.valueOf(mScore));
    }

    @Override
    public void insertDailyScore() {
        questionPresenter.getPerformanceInDaily(list.get(0).getPhone());
    }

    @Override
    public void updateDailyScore() {
        questionPresenter.getPerformanceInDaily(list.get(0).getPhone());
    }

    @Override
    public void loadPerformance() {
        if (oldScore < 5) {
            if (oldScore+mScore <= 5) {
                String str = getString(R.string.answer_per);
                String add = String.format(str,mScore);
                addPer.setText(add);
                questionPresenter.updatePerformanceInDaily(String.valueOf(mScore),list.get(0).getPhone());
            }
            else {
                String str = getString(R.string.answer_per);
                String add = String.format(str,5-oldScore);
                addPer.setText(add);
                questionPresenter.updatePerformanceInDaily(String.valueOf(5-oldScore),list.get(0).getPhone());
            }
        } else {
            String str = getString(R.string.answer_per);
            String add = String.format(str,0);
            addPer.setText(add);
            questionPresenter.updateAnswerNumInDaily(list.get(0).getPhone());
        }
    }

    @Override
    public void insertPerformance() {
        setState(State.SUCCESS);
    }

    @Override
    public void updatePerformance() {
        questionPresenter.updateAnswerNumInDaily(list.get(0).getPhone());
    }

    @Override
    public void updateAnswerNum() {
        setState(State.SUCCESS);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.insertDailyScore(dateStr,list.get(0).getPhone(),String.valueOf(mScore));
    }

    @Override
    public void onPerEmpty() {
        questionPresenter.insertPerInDaily(String.valueOf(mScore),"1",String.valueOf(mScore),list.get(0).getPhone());
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
            questionPresenter.unRegisterDailyResultCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("MyFlag",0);
        if (myFlag == 0) {
            Intent intent = new Intent(AnswerResultActivity.this,AnswerTypeActivity.class);
            startActivity(intent);
        } else if (myFlag == 1) {
            Intent intent = new Intent(AnswerResultActivity.this,LearnPerformanceActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
