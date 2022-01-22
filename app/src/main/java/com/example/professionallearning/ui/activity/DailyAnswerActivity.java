package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.professionallearning.dao.QuestionDao;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IDailyQuestionCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyAnswerActivity extends AppCompatActivity implements IDailyQuestionCallback {
    private ViewPager mViewPager;
    private Button buttonSure;
    private ConstraintLayout mConstraintLayout;
    private TextView dailyMes;
    private ImageView wifi;
    private int mScore = 0;
    private int mIndex;
    private List<Question> mList;
    private QuestionDao dao;
    private boolean[] mTags={false,false,false,false,false};
    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS,EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_answer);

        mViewPager=findViewById(R.id.viewPager);
        buttonSure=findViewById(R.id.buttonSure);
        mConstraintLayout = findViewById(R.id.constraintLayout3);
        dailyMes = findViewById(R.id.dailyMes);
        wifi = findViewById(R.id.wifi);
        Button buttonTips = findViewById(R.id.buttonTips);
        dao=new QuestionDao(this);

        setState(State.NONE);
        initPresenter();
        loadData();

        buttonTips.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(DailyAnswerActivity.this);
            builder.setTitle("提示");
            builder.setMessage(mList.get(mIndex).getAnalysis());
            builder.setPositiveButton("确定", (dialogInterface, i) -> {

            });
            builder.create();
            builder.show();
        });

        wifi.setOnClickListener(View -> onRetryClick());

    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            questionPresenter.getDailyQuestion();
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            mConstraintLayout.setVisibility(View.VISIBLE);
            dailyMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            dailyMes.setVisibility(View.VISIBLE);
            dailyMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            dailyMes.setVisibility(View.VISIBLE);
            dailyMes.setText("网络错误，请点击重试");
        } else if(currentState == State.EMPTY) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            dailyMes.setVisibility(View.VISIBLE);
            dailyMes.setText("内容为空。。。");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            dailyMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerDailyQuestionCallback(this);
    }

    private void loadData() {
        //加载题目
        questionPresenter.getDailyQuestion();
    }

    @Override
    public void loadDailyQuestion(List<Question> questions) {
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
            questionPresenter.unRegisterDailyQuestionCallback(this);
        }
    }

    public List<Question> setData() {
        List<Question> list=getData();
        //从数据库中随机获取5个题目
        int[] a = new int[5];
        Random r = new Random();
        a[0] = r.nextInt(list.size());
        for(int i = 1;i < 5;i++){
            //数组存入5个数
            a[i] = r.nextInt(list.size());
            for(int j = 0;j < i;j++){
                //如果满足条件就存入
                if(a[j]==a[i]){
                    i--;
                    break;
                }
            }
        }
        List<Question> list1=new ArrayList<>();
        for (int j=0;j<5;j++) {
            list1.add(list.get(a[j]));
        }
        mList=list1;
        return mList;
    }

    public List<Question> getData() {
        return dao.findQuestionsByAnswerType("1");
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
            if (mIndex<=3){
                mViewPager.setCurrentItem(mIndex+1);
            } else {
                Intent intent=new Intent(DailyAnswerActivity.this,AnswerResultActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("score",mScore);
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

            }

            @Override
            public void onPageSelected(int position) {
                mIndex=position;
                mViewPager.setTag(mIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setOptions(RadioButton radioButton1,RadioButton radioButton2) {
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
        AlertDialog.Builder builder=new AlertDialog.Builder(DailyAnswerActivity.this);
        builder.setTitle("确定要退出答题？");
        builder.setMessage("退出后作答历史将不会保存");
        builder.setPositiveButton("继续", (dialogInterface, i) -> {

        });
        builder.setNegativeButton("退出", (dialogInterface, i) -> {
            SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
            int myFlag = shp.getInt("MyFlag",0);
            if (myFlag == 0) {
                Intent intent = new Intent(DailyAnswerActivity.this,AnswerTypeActivity.class);
                startActivity(intent);
            } else if (myFlag == 1) {
                Intent intent = new Intent(DailyAnswerActivity.this,LearnPerformanceActivity.class);
                startActivity(intent);
            }
            finish();
        });
        builder.create();
        builder.show();
//        super.onBackPressed();
    }

}
