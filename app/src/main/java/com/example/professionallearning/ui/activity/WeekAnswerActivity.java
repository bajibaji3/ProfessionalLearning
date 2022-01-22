package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.MyPagerAdapter;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.dao.QuestionDao;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IWeekQuestionCallback;

import java.util.List;

public class WeekAnswerActivity extends AppCompatActivity implements IWeekQuestionCallback {
    private ViewPager mViewPager;
    private Button buttonSure;
    private ConstraintLayout mConstraintLayout;
    private TextView weekMes;
    private ImageView wifi;
    private int mScore = 0;
    private int mIndex;
    private List<Question> mList;
    private QuestionDao dao;
    private boolean[] mTags={false,false,false,false,false};
    private String week;
    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_answer);

        mViewPager = findViewById(R.id.viewPager);
        buttonSure = findViewById(R.id.buttonSure);
        mConstraintLayout = findViewById(R.id.constraintLayout2);
        weekMes = findViewById(R.id.weekMes);
        wifi = findViewById(R.id.wifi);
        Button buttonTips = findViewById(R.id.buttonTips);
        dao=new QuestionDao(this);

        setState(State.NONE);
        initPresenter();
        loadData();

        buttonTips.setOnClickListener(View -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(WeekAnswerActivity.this);
            builder.setTitle("提示");
            builder.setMessage(mList.get(mIndex).getAnalysis());
            builder.setPositiveButton("确定", (dialogInterface, i) -> {

            });
            builder.create();
            builder.show();
        });

        wifi.setOnClickListener(View ->onRetryClick());
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
        questionPresenter.registerWeekQuestionCallback(this);
    }

    private void loadData() {
        //加载题目
        questionPresenter.getQuestionByWeek();
    }

    @Override
    public void weekQuestionLoaded(List<Question> questions) {
        setState(State.SUCCESS);
        dao.deleteAllQuestions();
        dao.insertQuestions(questions);
        MyPagerAdapter adapter=new MyPagerAdapter(getBaseContext(),setData(),this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        setPage();
        clickOptions();
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
            questionPresenter.unRegisterWeekQuestionCallback(this);
        }
    }

    public List<Question> setData() {
        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null) {
            String week1= bundle.getString("week");
            String week2= bundle.getString("weekAgain");
            if (week1==null)
                week=week2;
            else
                week=week1;
        }
        mList=dao.findQuestionsByWeek("2",week);
        return mList;
    }

    public void clickOptions() {
        buttonSure.setOnClickListener(view -> {
            if (Integer.parseInt(mList.get(mIndex).getType())==0) {
                fillBlank();
            }else if (Integer.parseInt(mList.get(mIndex).getType())==1){
                //单选题
                singleChoice();
            } else {
                //多选题
                multipleChoice();
            }
            mTags[mIndex]=true;
            if (mIndex <= 3) {
                mViewPager.setCurrentItem(mIndex + 1);
            } else {
                Intent intent = new Intent(WeekAnswerActivity.this, WeekAnswerResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("score", mScore);
                bundle.putString("week",week);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    /**********
     * 单选题逻辑
     */
    public void singleChoice() {
        RadioButton option1 = mViewPager.getChildAt(mIndex).findViewById(R.id.option1);
        RadioButton option2 = mViewPager.getChildAt(mIndex).findViewById(R.id.option2);
        RadioButton option3 = mViewPager.getChildAt(mIndex).findViewById(R.id.option3);
        RadioButton option4 = mViewPager.getChildAt(mIndex).findViewById(R.id.option4);

        switch (mList.get(mIndex).getAnswer1()) {
            case "1":
                if (option1.isChecked()) {
                    if (!mTags[mIndex])
                        mScore++;
                    option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option1.setCompoundDrawables(drawable, null, null, null);
                } else if (option2.isChecked()) {
                    setOptions(option1, option2);

                } else if (option3.isChecked()) {
                    setOptions(option1, option3);

                } else if (option4.isChecked()) {
                    setOptions(option1, option4);
                }
                break;
            case "2":
                if (option2.isChecked()) {
                    if (!mTags[mIndex])
                        mScore++;
                    option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option2.setCompoundDrawables(drawable, null, null, null);
                } else if (option1.isChecked()) {
                    setOptions(option2, option1);

                } else if (option3.isChecked()) {
                    setOptions(option2, option3);

                } else if (option4.isChecked()) {
                    setOptions(option2, option4);
                }
                break;
            case "3":
                if (option3.isChecked()) {
                    if (!mTags[mIndex])
                        mScore++;
                    option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option3.setCompoundDrawables(drawable, null, null, null);
                } else if (option2.isChecked()) {
                    setOptions(option3, option2);

                } else if (option1.isChecked()) {
                    setOptions(option3, option1);

                } else if (option4.isChecked()) {
                    setOptions(option3, option4);
                }
                break;
            case "4":
                if (option4.isChecked()) {
                    if (!mTags[mIndex])
                        mScore++;
                    option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option4.setCompoundDrawables(drawable, null, null, null);
                } else if (option2.isChecked()) {
                    setOptions(option4, option2);

                } else if (option3.isChecked()) {
                    setOptions(option4, option3);

                } else if (option1.isChecked()) {
                    setOptions(option4, option1);
                }
                break;
        }

        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }

    /**********
     * 多选题逻辑
     */
    public void multipleChoice() {
        final CheckBox option1 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionA);
        final CheckBox option2 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionB);
        final CheckBox option3 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionC);
        final CheckBox option4 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionD);
        switch (mList.get(mIndex).getType()) {
            case "2":
                if (mList.get(mIndex).getAnswer1().equals("1")&&mList.get(mIndex).getAnswer2().equals("2")) {
                    if (option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option1.setCompoundDrawables(drawable, null, null, null);
                        option2.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("1")&&mList.get(mIndex).getAnswer2().equals("3")) {
                    if (option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option1.setCompoundDrawables(drawable, null, null, null);
                        option3.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("1")&&mList.get(mIndex).getAnswer2().equals("4")) {
                    if (option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option1.setCompoundDrawables(drawable, null, null, null);
                        option4.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("2")&&mList.get(mIndex).getAnswer2().equals("3")) {
                    if (option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option3.setCompoundDrawables(drawable, null, null, null);
                        option2.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("2")&&mList.get(mIndex).getAnswer2().equals("4")) {
                    if (option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option4.setCompoundDrawables(drawable, null, null, null);
                        option2.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("3")&&mList.get(mIndex).getAnswer2().equals("4")) {
                    if (option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option3.setCompoundDrawables(drawable, null, null, null);
                        option4.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option3);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                }
                break;
            case "3":
                if (mList.get(mIndex).getAnswer1().equals("1")&&mList.get(mIndex).getAnswer2().equals("2")&&mList.get(mIndex).getAnswer3().equals("3")) {
                    if (option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option1.setCompoundDrawables(drawable, null, null, null);
                        option2.setCompoundDrawables(drawable, null, null, null);
                        option3.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("1")&&mList.get(mIndex).getAnswer2().equals("2")&&mList.get(mIndex).getAnswer3().equals("4")) {
                    if (option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option1.setCompoundDrawables(drawable, null, null, null);
                        option2.setCompoundDrawables(drawable, null, null, null);
                        option4.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                } else if (mList.get(mIndex).getAnswer1().equals("2")&&mList.get(mIndex).getAnswer2().equals("3")&&mList.get(mIndex).getAnswer3().equals("4")) {
                    if (option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                        if (!mTags[mIndex])
                            mScore++;
                        option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                drawable.getMinimumHeight());
                        option3.setCompoundDrawables(drawable, null, null, null);
                        option2.setCompoundDrawables(drawable, null, null, null);
                        option4.setCompoundDrawables(drawable, null, null, null);
                    } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option1);
                    } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option2);
                    } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionOne(option3);
                    } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionOne(option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option2);
                    } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option1,option3);
                    } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option1,option4);
                    } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                        setOptionTwo(option2,option3);
                    } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                        setOptionTwo(option2,option4);
                    } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                        setOptionTwo(option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                        setOptionThree(option1,option2,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                        setOptionThree(option2,option3,option4);
                    } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                        setOptionFour(option1,option2,option3,option4);
                    }
                }
                break;
            case "4":
                if (option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&option4.isChecked()) {
                    if (!mTags[mIndex])
                        mScore++;
                    option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option1.setCompoundDrawables(drawable, null, null, null);
                    option2.setCompoundDrawables(drawable, null, null, null);
                    option3.setCompoundDrawables(drawable, null, null, null);
                    option4.setCompoundDrawables(drawable, null, null, null);
                } else if(option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                    setOptionOne(option1);
                } else if(option2.isChecked()&&!option1.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                    setOptionOne(option2);
                } else if(option3.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                    setOptionOne(option3);
                } else if(option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                    setOptionOne(option4);
                } else if(option1.isChecked()&&option2.isChecked()&&!option3.isChecked()&&!option4.isChecked()) {
                    setOptionTwo(option1,option2);
                } else if(option1.isChecked()&&option3.isChecked()&&!option2.isChecked()&&!option4.isChecked()) {
                    setOptionTwo(option1,option3);
                } else if(option1.isChecked()&&option4.isChecked()&&!option2.isChecked()&&!option3.isChecked()) {
                    setOptionTwo(option1,option4);
                } else if(option2.isChecked()&&option3.isChecked()&&!option1.isChecked()&&!option4.isChecked()) {
                    setOptionTwo(option2,option3);
                } else if(option2.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option3.isChecked()) {
                    setOptionTwo(option2,option4);
                } else if(option3.isChecked()&&option4.isChecked()&&!option1.isChecked()&&!option2.isChecked()) {
                    setOptionTwo(option3,option4);
                } else if(option1.isChecked()&&option2.isChecked()&&option3.isChecked()&&!option4.isChecked()) {
                    setOptionThree(option1,option2,option4);
                } else if(option1.isChecked()&&option2.isChecked()&&option4.isChecked()&&!option3.isChecked()) {
                    setOptionThree(option1,option2,option4);
                } else if(option2.isChecked()&&option3.isChecked()&&option4.isChecked()&&!option1.isChecked()) {
                    setOptionThree(option2,option3,option4);
                }
                break;
        }
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);

    }

    /**********
     * 填空题逻辑
     */
    public void fillBlank() {
        EditText input = mViewPager.getChildAt(mIndex).findViewById(R.id.input);
        TextView text = mViewPager.getChildAt(mIndex).findViewById(R.id.itemTitle);
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder();
        ForegroundColorSpan colorSpan;
        spannableStringBuilder.append(text.getText().toString());
        if (input.getText().toString().equals(mList.get(mIndex).getAnswer1())) {
            if (!mTags[mIndex])
                mScore++;
            colorSpan = new ForegroundColorSpan(Color.parseColor("#01AE66"));
        } else {
            colorSpan = new ForegroundColorSpan(Color.parseColor("#F12323"));
        }
        spannableStringBuilder.setSpan(colorSpan,Integer.parseInt(mList.get(mIndex).getStart()),Integer.parseInt(mList.get(mIndex).getStart())+input.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableStringBuilder);
        text.setEnabled(false);
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
                mIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d(TAG,"onPageScrollStateChanged");
            }
        });
    }

    public void setOptions(RadioButton radioButton1, RadioButton radioButton2) {
        radioButton1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        radioButton1.setCompoundDrawables(drawable, null, null, null);

        radioButton2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable1 = getResources().getDrawable(R.drawable.ic_false);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                drawable1.getMinimumHeight());
        radioButton2.setCompoundDrawables(drawable1, null, null, null);

    }

    public void setOptionOne(CheckBox option) {
        option.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        option.setCompoundDrawables(drawable, null, null, null);
    }

    public void setOptionTwo(CheckBox option1,CheckBox option2) {
        option1.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        option2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        option1.setCompoundDrawables(drawable, null, null, null);
        option2.setCompoundDrawables(drawable, null, null, null);
    }

    public void setOptionThree(CheckBox option1,CheckBox option2,CheckBox option3) {
        option1.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        option2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        option3.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        option1.setCompoundDrawables(drawable, null, null, null);
        option2.setCompoundDrawables(drawable, null, null, null);
        option3.setCompoundDrawables(drawable, null, null, null);
    }

    public void setOptionFour(CheckBox option1,CheckBox option2,CheckBox option3,CheckBox option4) {
        option1.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        option2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        option3.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        option4.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        option1.setCompoundDrawables(drawable, null, null, null);
        option2.setCompoundDrawables(drawable, null, null, null);
        option3.setCompoundDrawables(drawable, null, null, null);
        option4.setCompoundDrawables(drawable, null, null, null);
    }

    /*****
     * 处理返回键
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeekAnswerActivity.this);
        builder.setTitle("确定要退出答题？");
        builder.setMessage("退出后作答历史将不会保存");
        builder.setPositiveButton("继续", (dialogInterface, i) -> {

        });
        builder.setNegativeButton("退出", (dialogInterface, i) -> {
            Intent intent=new Intent(WeekAnswerActivity.this, WeekAnswerListActivity.class);
            startActivity(intent);
            finish();
        });
        builder.create();
        builder.show();
    }
}
