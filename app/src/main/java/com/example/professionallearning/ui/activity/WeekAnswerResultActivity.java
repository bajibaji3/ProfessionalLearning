package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IWeekResultCallback;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeekAnswerResultActivity extends AppCompatActivity implements IWeekResultCallback {
    private String week;
    private IQuestionPresenter questionPresenter;
    private CardView mConstraintLayout;
    private TextView weekMes,addPer;
    private ImageView wifi;
    private int oldScore;
    private int mScore;
    private String mPhone;
    //0表示加载时的网络错误，1表示更新时的网络错误
    private int netFlag = 0;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_answer_result);
        TextView score=findViewById(R.id.score);
        TextView accuracy = findViewById(R.id.textView60);
        TextView falseNum = findViewById(R.id.textView62);
        Button buttonAgain=findViewById(R.id.buttonAgain);
        Button buttonBack=findViewById(R.id.button13);
        mConstraintLayout = findViewById(R.id.card2);
        weekMes = findViewById(R.id.weekResultMes);
        wifi = findViewById(R.id.wifi);
        addPer = findViewById(R.id.textView61);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null) {
            mScore=bundle.getInt("score");
            week=bundle.getString("week");
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

        buttonAgain.setOnClickListener(View -> {
            Intent intent=new Intent(WeekAnswerResultActivity.this, WeekAnswerActivity.class);
            Bundle bundle1=new Bundle();
//                Log.d(TAG,week);
            bundle1.putString("weekAgain",week);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        });

        buttonBack.setOnClickListener(view -> {
            Intent intent=new Intent(WeekAnswerResultActivity.this, WeekAnswerListActivity.class);
            startActivity(intent);
            finish();
        });

        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            if (netFlag == 0) {
                questionPresenter.getIsAnswerByWeek(week,mPhone);
            } else {
                questionPresenter.updateIsAnswerByWeek(week,mPhone);
            }
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            mConstraintLayout.setVisibility(View.VISIBLE);
            weekMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            weekMes.setVisibility(View.VISIBLE);
            weekMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            weekMes.setVisibility(View.VISIBLE);
            weekMes.setText("网络错误，请点击重试");
        } else if(currentState == State.EMPTY) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            weekMes.setVisibility(View.VISIBLE);
            weekMes.setText("内容为空。。。");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            weekMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerWeekCallback(this);
    }

    public void loadData() {
        questionPresenter.getIsAnswerByWeek(week,mPhone);
    }

    public void updateData() {
        questionPresenter.updateIsAnswerByWeek(week,mPhone);
    }

    @Override
    public void updateIsAnswerByWeek() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.getWeekScore(dateStr,mPhone);
    }

    @Override
    public void loadIsAnswerByWeek(String isAnswer) {
        judge(isAnswer);
    }

    @Override
    public void loadWeekScore(String score) {
        if (score == null) {
            oldScore = 0;
        } else {
            oldScore = Integer.parseInt(score);
        }
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.updateWeekScore(dateStr,mPhone,String.valueOf(mScore));
    }

    @Override
    public void insertWeekScore() {
        questionPresenter.getPerformanceInWeek(mPhone);
    }

    @Override
    public void updateWeekScore() {
        questionPresenter.getPerformanceInWeek(mPhone);
    }

    @Override
    public void loadPerformance() {
        if (oldScore < 5) {
            if (oldScore+mScore <= 5) {
                String str = getString(R.string.answer_per);
                String add = String.format(str,mScore);
                addPer.setText(add);
                questionPresenter.updatePerformanceInWeek(String.valueOf(mScore),mPhone);
            }
            else {
                String str = getString(R.string.answer_per);
                String add = String.format(str,5-oldScore);
                addPer.setText(add);
                questionPresenter.updatePerformanceInWeek(String.valueOf(5-oldScore),mPhone);
            }
        } else {
            String str = getString(R.string.answer_per);
            String add = String.format(str,0);
            addPer.setText(add);
            questionPresenter.updateAnswerNumInWeek(mPhone);
        }
    }

    @Override
    public void insertPerformance() {
        setState(State.SUCCESS);
    }

    @Override
    public void updatePerformance() {
        questionPresenter.updateAnswerNumInWeek(mPhone);
    }

    @Override
    public void updateAnswerNum() {
        setState(State.SUCCESS);
    }

    @Override
    public void onGetError() {
        setState(State.ERROR);
    }

    @Override
    public void onGetEmpty() {
        setState(State.EMPTY);
    }

    @Override
    public void onGetLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onUpdateError() {
        setState(State.ERROR);
        netFlag = 1;
    }

    @Override
    public void onScoreError() {
        setState(State.ERROR);
    }

    @Override
    public void onScoreEmpty() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.insertWeekScore(dateStr,mPhone,String.valueOf(mScore));
    }

    @Override
    public void onPerEmpty() {
        questionPresenter.insertPerInWeek(String.valueOf(mScore),"1",String.valueOf(mScore),mPhone);
    }

    @Override
    public void onScoreLoading() {
        setState(State.LOADING);
    }


    private void judge(String isAnswer) {
        if (isAnswer.equals("未答题")) {
            updateData();
        } else {
            String str = getString(R.string.answer_per);
            String add = String.format(str,0);
            addPer.setText(add);
            questionPresenter.updateAnswerNumInWeek(mPhone);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(WeekAnswerResultActivity.this, WeekAnswerListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (questionPresenter != null) {
            questionPresenter.unRegisterWeekCallback(this);
        }
    }
}
