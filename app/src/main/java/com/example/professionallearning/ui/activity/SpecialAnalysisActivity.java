package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.AnalysisPagerAdapter;
import com.example.professionallearning.dao.QuestionDao;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IAnalysisSpecialCallback;

import java.util.List;

public class SpecialAnalysisActivity extends AppCompatActivity implements IAnalysisSpecialCallback {
    private ConstraintLayout mConstraintLayout;
    private TextView anaMes;
    private ImageView wifi;
    private ViewPager mViewPager;
    private Button button;
    private String specialTitle;
    private List<Question> mList;
    private String mPhone;
    private QuestionDao dao;

    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_analysis);

        mConstraintLayout = findViewById(R.id.analysisBody);
        anaMes = findViewById(R.id.anaMes);
        wifi = findViewById(R.id.wifi);
        mViewPager = findViewById(R.id.analysisPager);
        button = findViewById(R.id.button);
        dao = new QuestionDao(this);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null) {
            String specialTitle1= bundle.getString("specialTitle1");
            String specialTitle2= bundle.getString("specialTitle2");
            if (specialTitle1 == null)
                specialTitle=specialTitle2;
            else
                specialTitle=specialTitle1;
        }

        setState(State.NONE);
        initPresenter();
        loadData();
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
            mConstraintLayout.setVisibility(View.VISIBLE);
            anaMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            anaMes.setVisibility(View.VISIBLE);
            anaMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            anaMes.setVisibility(View.VISIBLE);
            anaMes.setText("网络错误，请点击重试");
        } else if(currentState == State.EMPTY) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            anaMes.setVisibility(View.VISIBLE);
            anaMes.setText("内容为空。。。");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            anaMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerSpecialAnalysisCallback(this);
    }

    private void loadData() {
        //加载数据
        questionPresenter.findSpecialQuestion();
    }

    @Override
    public void specialQuestionLoaded(List<Question> questions) {
        dao.deleteAllQuestions();
        dao.insertQuestions(questions);
        mList = dao.findQuestionsBySpecialTitle("3",specialTitle);
        questionPresenter.getUserAnswer1(specialTitle,mPhone);
    }

    @Override
    public void userAnswerLoaded(UserAnswer userAnswer) {
        setState(State.SUCCESS);
        AnalysisPagerAdapter adapter=new AnalysisPagerAdapter(getBaseContext(),mList,userAnswer,this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        setPage();
        button.setOnClickListener(View -> {
            Intent intent=new Intent(SpecialAnalysisActivity.this, SpecialAnswerListActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setState(State.EMPTY);
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
            questionPresenter.unRegisterSpecialAnalysisCallback(this);
        }
    }

    public void setPage() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(TAG,"onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
//                Log.d(TAG,"onPageSelected");
                if (position == 4) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d(TAG,"onPageScrollStateChanged");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SpecialAnalysisActivity.this, SpecialAnswerListActivity.class);
        startActivity(intent);
        finish();
    }
}
