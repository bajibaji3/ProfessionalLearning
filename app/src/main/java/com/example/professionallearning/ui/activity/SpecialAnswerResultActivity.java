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
import com.example.professionallearning.model.bean.UserAnswer;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.ISpecialResultCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpecialAnswerResultActivity extends AppCompatActivity implements ISpecialResultCallback {
    private TextView score;
    private Button again;
    private CardView mConstraintLayout;
    private TextView speResMes;
    private ImageView wifi;
    private String specialTitle;
    private String isAnswer;
    private String mPhone;
    private int mScore = 0;
    private int oldScore;
    //0表示加载网络错误，1表示更新网络错误
    private int netFlag = 0;
    //
    private int updateFlag = 0;
    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_answer_result);
        score = findViewById(R.id.score);
        again = findViewById(R.id.again);
        Button analysis = findViewById(R.id.analysis);
        mConstraintLayout = findViewById(R.id.cardView8);
        speResMes = findViewById(R.id.speResMes);
        wifi = findViewById(R.id.wifi);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null){
            specialTitle=bundle.getString("specialTitle");
        }

        setState(State.NONE);
        initPresenter();
        loadData();

        again.setOnClickListener(view -> {
            Intent intent=new Intent(SpecialAnswerResultActivity.this, SpecialAnswerActivity.class);
            Bundle bundle1=new Bundle();
            bundle1.putString("specialTitleAgain",specialTitle);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        });

        analysis.setOnClickListener(View -> {
            Intent intent=new Intent(SpecialAnswerResultActivity.this, SpecialAnalysisActivity.class);
            Bundle bundle2=new Bundle();
            bundle2.putString("specialTitle2",specialTitle);
            intent.putExtras(bundle2);
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
                loadData();
            } else {
                if (updateFlag == 1) {
                    questionPresenter.updateSpecialIsAnswer("1","0",specialTitle,mPhone);
                } else {
                    questionPresenter.updateSpecialIsAnswer("2","0",specialTitle,mPhone);
                }
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
            speResMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            speResMes.setVisibility(View.VISIBLE);
            speResMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            speResMes.setVisibility(View.VISIBLE);
            speResMes.setText("网络错误，请点击重试");
        } else if(currentState == State.EMPTY) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            speResMes.setVisibility(View.VISIBLE);
            speResMes.setText("内容为空。。。");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            speResMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerSpecialResultCallback(this);
    }

    private void loadData() {
        //加载数据
        questionPresenter.getIsAnswerBySpecial(specialTitle,mPhone);
    }

    private void judge(UserAnswer userAnswer) {
        if (userAnswer.getUserAnswer1().equals(userAnswer.getQuestion1())) {
            mScore++;
        }
        if (userAnswer.getUserAnswer2().equals(userAnswer.getQuestion2())) {
            mScore++;
        }
        if (userAnswer.getUserAnswer3().equals(userAnswer.getQuestion3())) {
            mScore++;
        }
        if (userAnswer.getUserAnswer4().equals(userAnswer.getQuestion4())) {
            mScore++;
        }
        if (userAnswer.getUserAnswer5().equals(userAnswer.getQuestion5())) {
            mScore++;
        }
        if (mScore == 5) {
            updateFlag = 2;
            again.setVisibility(View.GONE);
            questionPresenter.updateSpecialIsAnswer("2","0",specialTitle,mPhone);
        } else {
            updateFlag = 1;
            questionPresenter.updateSpecialIsAnswer("1","0",specialTitle,mPhone);
        }
        score.setText(String.valueOf(mScore*20));
    }

    @Override
    public void loadUserAnswer(UserAnswer userAnswer) {
        judge(userAnswer);
    }

    @Override
    public void updateSpecialIsAnswer() {
        questionPresenter.updateUserTemp("","","","","","0",specialTitle,mPhone);
    }

    @Override
    public void updateUserTemp() {
        if (isAnswer.equals("0")) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);
            questionPresenter.getSpecialScore(dateStr,mPhone);
        } else {
            questionPresenter.updateAnswerNumInSpecial(mPhone);
        }
    }

    @Override
    public void loadIsAnswer(String isAnswer) {
        this.isAnswer = isAnswer;
        questionPresenter.getUserAnswer(specialTitle,mPhone);
    }

    @Override
    public void loadSpecialScore(String score) {
        if (score == null) {
            oldScore = 0;
        } else {
            oldScore = Integer.parseInt(score);
        }
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.updateSpecialScore(dateStr,mPhone,String.valueOf(mScore));
    }

    @Override
    public void insertSpecialScore() {
        questionPresenter.getPerformanceInSpecial(mPhone);
    }

    @Override
    public void updateSpecialScore() {
        questionPresenter.getPerformanceInSpecial(mPhone);
    }

    @Override
    public void loadPerformance() {
        if (oldScore < 5) {
            if (oldScore+mScore <= 5)
                questionPresenter.updatePerformanceInSpecial(String.valueOf(mScore),mPhone);
            else
                questionPresenter.updatePerformanceInSpecial(String.valueOf(5-oldScore),mPhone);
        } else {
            questionPresenter.updateAnswerNumInSpecial(mPhone);
        }
    }

    @Override
    public void insertPerformance() {
        setState(State.SUCCESS);
    }

    @Override
    public void updatePerformance() {
        questionPresenter.updateAnswerNumInSpecial(mPhone);
    }

    @Override
    public void updateAnswerNum() {
        setState(State.SUCCESS);
    }

    @Override
    public void onUpdateError() {
        netFlag = 1;
        setState(State.ERROR);
    }

    @Override
    public void onUpdateLoading() {
        setState(State.LOADING);
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
    public void onScoreError() {
        setState(State.ERROR);
    }

    @Override
    public void onScoreEmpty() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        questionPresenter.insertSpecialScore(dateStr,mPhone,String.valueOf(mScore));
    }

    @Override
    public void onPerEmpty() {
        questionPresenter.insertPerInSpecial(String.valueOf(mScore),"1",String.valueOf(mScore),mPhone);
    }

    @Override
    public void onScoreLoading() {
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
            questionPresenter.unRegisterSpecialResultCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SpecialAnswerResultActivity.this, SpecialAnswerListActivity.class);
        startActivity(intent);
        finish();
    }
}
