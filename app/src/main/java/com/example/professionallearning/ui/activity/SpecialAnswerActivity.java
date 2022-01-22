package com.example.professionallearning.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.MyPagerAdapter;
import com.example.professionallearning.dao.QuestionDao;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.util.SoftKeyBoardListener;
import com.example.professionallearning.view.ISpecialQuestionCallback;

import java.util.ArrayList;
import java.util.List;

public class SpecialAnswerActivity extends AppCompatActivity implements ISpecialQuestionCallback {
    private ViewPager mViewPager;
    private Button buttonSure;
    private ConstraintLayout mConstraintLayout;
    private TextView speMes;
    private ImageView wifi;
    private int mIndex;
    private List<Question> mList;
    private List<String> userAnswers = new ArrayList<>();
    private String mPhone;
    private QuestionDao dao;
    private boolean[] mTags = {false, false, false, false, false};
    private String specialTitle;
    //0表示获取时的网络错误，1表示更新时的网络错误
    private int netFlag = 0;
    //1表示在第一个页面中途退出时网络错误，2表示全部答完跳转时网络错误
    private int updateFlag = 1;
    private IQuestionPresenter questionPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE, LOADING, ERROR, SUCCESS, EMPTY
    }
    private SpannableStringBuilder spannableStringBuilder;
    private ClickableSpan clickableSpan;
    private ForegroundColorSpan colorSpan;
    private int textNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_answer);

        mViewPager = findViewById(R.id.viewPager);
        buttonSure = findViewById(R.id.buttonSure);
        Button buttonTips = findViewById(R.id.buttonTips);
        mConstraintLayout = findViewById(R.id.constraintLayout5);
        speMes = findViewById(R.id.speMes);
        wifi = findViewById(R.id.wifi);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        dao = new QuestionDao(this);
        userAnswers.add(0,"");
        userAnswers.add(1,"");
        userAnswers.add(2,"");
        userAnswers.add(3,"");
        userAnswers.add(4,"");

        setState(State.NONE);
        initPresenter();
        loadData();

        buttonTips.setOnClickListener(View -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SpecialAnswerActivity.this);
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
//        Log.d(TAG,"netFlag-->" + netFlag);
//        Log.d(TAG,"updateFlag-->" + updateFlag);
        //网络错误，点击了重试
        //重新加载数据
        if (questionPresenter != null) {
            if (netFlag == 0) {
                questionPresenter.getSpecialQuestion();
            } else {
                if (updateFlag == 1) {
                    update();
                } else {
                    questionPresenter.findUserAnswer5(specialTitle,mPhone);
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
            speMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if (currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            speMes.setVisibility(View.VISIBLE);
            speMes.setText("加载中。。。");
        } else if (currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            speMes.setVisibility(View.VISIBLE);
            speMes.setText("网络错误，请点击重试");
        } else if (currentState == State.EMPTY) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            speMes.setVisibility(View.VISIBLE);
            speMes.setText("内容为空。。。");
        } else if (currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            speMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerSpecialQuestionCallback(this);
    }

    private void loadData() {
        //加载题目
        questionPresenter.getSpecialQuestion();
    }

    private List<String> getQuestionAnswers(){
        List<String> questionAnswers = new ArrayList<>();
        for (int i=0;i<mList.size();i++) {
            switch (mList.get(i).getType()) {
                case "0":
                case "1":
                    //单选题
                    //填空题
                    questionAnswers.add(i,mList.get(i).getAnswer1());
                    break;
                case "2":
                    questionAnswers.add(i,mList.get(i).getAnswer1()+mList.get(i).getAnswer2());
                    break;
                case "3":
                    questionAnswers.add(i,mList.get(i).getAnswer1()+mList.get(i).getAnswer2()+mList.get(i).getAnswer3());
                    break;
                case "4":
                    questionAnswers.add(i,mList.get(i).getAnswer1()+mList.get(i).getAnswer2()+mList.get(i).getAnswer3()+mList.get(i).getAnswer4());
                    break;
            }
        }
        return questionAnswers;
    }

    private void update5() {
        questionPresenter.updateUserAnswer5(userAnswers, specialTitle,mPhone);
    }

    private void update() {
        updateFlag = 1;
        questionPresenter.updateUserAnswer(userAnswers.get(0),userAnswers.get(1),userAnswers.get(2),userAnswers.get(3),userAnswers.get(4),String.valueOf(mIndex),specialTitle,mPhone);
    }

    @Override
    public void loadSpecialQuestion(List<Question> questions) {
        dao.deleteAllQuestions();
        dao.insertQuestions(questions);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            String specialTitle1 = bundle.getString("specialTitle");
            String specialTitle2 = bundle.getString("specialTitleAgain");
            if (specialTitle1 == null)
                specialTitle = specialTitle2;
            else
                specialTitle = specialTitle1;
        }
        questionPresenter.findUserAnswer(specialTitle,mPhone);
    }

    @Override
    public void updateUserAnswer() {
        questionPresenter.updateIsOut("1",specialTitle,mPhone);
    }

    @Override
    public void updateUserAnswer5() {
        setState(State.SUCCESS);
        Intent intent = new Intent(SpecialAnswerActivity.this, SpecialAnswerResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("specialTitle", specialTitle);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateIsOut() {
        setState(State.SUCCESS);
        Intent intent = new Intent(SpecialAnswerActivity.this,SpecialAnswerListActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void findUserAnswer(UserAnswer userAnswer) {
        setState(State.SUCCESS);
        MyPagerAdapter adapter = new MyPagerAdapter(getBaseContext(), setData(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        switch (userAnswer.getPageNum()) {
            case "0":
                //中途从第1页退出，再进来后不用做什么
                mViewPager.setCurrentItem(0);
                mIndex = 0;
                break;
            case "1":
                //中途从第2页退出，再进来后对第1页进行处理
                mViewPager.setCurrentItem(1);
                mIndex = 1;
                userAnswers.set(0, userAnswer.getUserAnswer1());
                //处理第1页
                switch (mList.get(0).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer1(),0);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer1(),0);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer1(),0);
                        break;
                }
                break;
            case "2":
                //中途从第3页退出，再进来后对第1,2页进行处理
                mViewPager.setCurrentItem(2);
                mIndex = 2;
                userAnswers.set(0, userAnswer.getUserAnswer1());
                userAnswers.set(1, userAnswer.getUserAnswer2());
                //处理第1页
                switch (mList.get(0).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer1(),0);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer1(),0);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer1(),0);
                        break;
                }
                //处理第2页
                switch (mList.get(1).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer2(),1);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer2(),1);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer2(),1);
                        break;
                }
                break;
            case "3":
                //中途从第4页退出，再进来后对第1,2,3页进行处理
                mViewPager.setCurrentItem(3);
                mIndex = 3;
                userAnswers.set(0, userAnswer.getUserAnswer1());
                userAnswers.set(1, userAnswer.getUserAnswer2());
                userAnswers.set(2, userAnswer.getUserAnswer3());
                //处理第1页
                switch (mList.get(0).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer1(),0);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer1(),0);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer1(),0);
                        break;
                }
                //处理第2页
                switch (mList.get(1).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer2(),1);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer2(),1);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer2(),1);
                        break;
                }
                //处理第3页
                switch (mList.get(2).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer3(),2);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer3(),2);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer3(),2);
                        break;
                }
                break;
            case "4":
                //中途从第5页退出，再进来后对第1,2,3,4页进行处理
                mViewPager.setCurrentItem(4);
                mIndex = 4;
                userAnswers.set(0, userAnswer.getUserAnswer1());
                userAnswers.set(1, userAnswer.getUserAnswer2());
                userAnswers.set(2, userAnswer.getUserAnswer3());
                userAnswers.set(3, userAnswer.getUserAnswer4());
                //处理第1页
                switch (mList.get(0).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer1(),0);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer1(),0);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer1(),0);
                        break;
                }
                //处理第2页
                switch (mList.get(1).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer2(),1);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer2(),1);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer2(),1);
                        break;
                }
                //处理第3页
                switch (mList.get(2).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer3(),2);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer3(),2);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer3(),2);
                        break;
                }
                //处理第4页
                switch (mList.get(3).getType()) {
                    case "0":
                        //如果是填空题
                        operateFillBlank(userAnswer.getUserAnswer4(),3);
                        break;
                    case "1":
                        //如果是单选题
                        operateSingle(userAnswer.getUserAnswer4(),3);
                        break;
                    default:
                        //如果是多选题
                        operateMultiple(userAnswer.getUserAnswer4(),3);
                        break;
                }
                break;
        }

        setPage();
        clickOptions();
    }

    @Override
    public void insertUserAnswer() {
        setState(State.SUCCESS);
        MyPagerAdapter adapter = new MyPagerAdapter(getBaseContext(), setData(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(0);
        mIndex = 0;
        setPage();
        clickOptions();
    }

    @Override
    public void findUserAnswer5() {
        update5();
    }

    @Override
    public void insertUserAnswer5() {
        setState(State.SUCCESS);
        Intent intent = new Intent(SpecialAnswerActivity.this, SpecialAnswerResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("specialTitle", specialTitle);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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
    public void onUserAnswerEmpty() {
        questionPresenter.insertUserAnswer(userAnswers,specialTitle,"0",mPhone);
    }

    @Override
    public void onUserAnswerEmpty5() {
        questionPresenter.insertUserAnswer5(getQuestionAnswers(),userAnswers,specialTitle,mPhone);
    }

    @Override
    public void onGetLoading() {
        setState(State.LOADING);
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

    private void operateFillBlank(String userAnswer,int index) {
        EditText input = mViewPager.getChildAt(index).findViewById(R.id.input);
        TextView text = mViewPager.getChildAt(index).findViewById(R.id.itemTitle);
        Button sure = mViewPager.getChildAt(index).findViewById(R.id.sure);
        textNum = userAnswer.length();
        final int[] flag = {0};
        List<Integer> nums = new ArrayList<>();
        getTextNum(input,sure);
        spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.clear();
        spannableStringBuilder.append(text.getText().toString());
        int start = Integer.parseInt(mList.get(index).getStart());
        if (!userAnswer.equals("")) {
            spannableStringBuilder.delete(start,start+4);
            spannableStringBuilder.insert(start,userAnswer);
        }
        clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                input.setVisibility(View.VISIBLE);
                sure.setVisibility(View.VISIBLE);
//                Log.d(TAG, "字符个数：" + textNum);
                StringBuilder builder = new StringBuilder();
                for (int i = start; i < start + textNum; i++)
                    builder.append(spannableStringBuilder.charAt(i));

                if (spannableStringBuilder.charAt(start) == '_')
                    input.setText("");
                else
                    input.setText(builder.toString());
                input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, 0);
            }
        };
        colorSpan = new ForegroundColorSpan(Color.parseColor("#B99783"));
        if (userAnswer.equals(""))
            spannableStringBuilder.setSpan(clickableSpan, start, start+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        else
            spannableStringBuilder.setSpan(clickableSpan, start, start+userAnswer.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(spannableStringBuilder);

        sure.setOnClickListener(view -> {
//            Log.d(TAG, input.getText().toString());
            nums.add(textNum);
            flag[0]++;
//                    Log.d(TAG,"字符个数："+textNum);
            if (flag[0] == 1) {
                if (userAnswer.equals("")) {
                    spannableStringBuilder.delete(start, start+4);
                } else {
                    spannableStringBuilder.delete(start, start+userAnswer.length());
                }

            } else
                spannableStringBuilder.delete(start, start + nums.get(flag[0] - 2));
            spannableStringBuilder.insert(start, input.getText().toString());
            spannableStringBuilder.setSpan(clickableSpan, start, start + textNum, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(colorSpan, start, start + textNum, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setText(spannableStringBuilder);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        });

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                input.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
            }
        });
    }

    public void getTextNum(final EditText editText, Button sure) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.d(TAG,editable.length()+"333333333333333");
                if (editable.length() == 0)
                    sure.setEnabled(false);
                else
                    sure.setEnabled(true);
                textNum = editable.length();
            }
        });
    }


    private void operateSingle(String userAnswer,int index) {
        RadioButton option1 = mViewPager.getChildAt(index).findViewById(R.id.option1);
        RadioButton option2 = mViewPager.getChildAt(index).findViewById(R.id.option2);
        RadioButton option3 = mViewPager.getChildAt(index).findViewById(R.id.option3);
        RadioButton option4 = mViewPager.getChildAt(index).findViewById(R.id.option4);
        switch (userAnswer) {
            case "1":
                option1.setChecked(true);
                break;
            case "2":
                option2.setChecked(true);
                break;
            case "3":
                option3.setChecked(true);
                break;
            case "4":
                option4.setChecked(true);
                break;
        }
    }

    private void operateMultiple(String userAnswer,int index) {
        CheckBox option1 = mViewPager.getChildAt(index).findViewById(R.id.optionA);
        CheckBox option2 = mViewPager.getChildAt(index).findViewById(R.id.optionB);
        CheckBox option3 = mViewPager.getChildAt(index).findViewById(R.id.optionC);
        CheckBox option4 = mViewPager.getChildAt(index).findViewById(R.id.optionD);
        switch (userAnswer) {
            case "1":
                option1.setChecked(true);
                break;
            case "2":
                option2.setChecked(true);
                break;
            case "3":
                option3.setChecked(true);
                break;
            case "4":
                option4.setChecked(true);
                break;
            case "12":
                option1.setChecked(true);
                option2.setChecked(true);
                break;
            case "13":
                option1.setChecked(true);
                option3.setChecked(true);
                break;
            case "14":
                option1.setChecked(true);
                option4.setChecked(true);
                break;
            case "23":
                option2.setChecked(true);
                option3.setChecked(true);
                break;
            case "24":
                option2.setChecked(true);
                option4.setChecked(true);
                break;
            case "34":
                option3.setChecked(true);
                option4.setChecked(true);
                break;
            case "123":
                option1.setChecked(true);
                option2.setChecked(true);
                option3.setChecked(true);
                break;
            case "124":
                option1.setChecked(true);
                option2.setChecked(true);
                option4.setChecked(true);
                break;
            case "134":
                option1.setChecked(true);
                option3.setChecked(true);
                option4.setChecked(true);
                break;
            case "234":
                option2.setChecked(true);
                option3.setChecked(true);
                option4.setChecked(true);
                break;
            case "1234":
                option1.setChecked(true);
                option2.setChecked(true);
                option3.setChecked(true);
                option4.setChecked(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (questionPresenter != null) {
            questionPresenter.unRegisterSpecialQuestionCallback(this);
        }
    }

    public List<Question> setData() {
        mList = dao.findQuestionsBySpecialTitle("3", specialTitle);
        return mList;
    }

    public void clickOptions() {
        buttonSure.setOnClickListener(View -> {
            if (Integer.parseInt(mList.get(mIndex).getType()) == 0) {
                fillBlank();
            } else if (Integer.parseInt(mList.get(mIndex).getType()) == 1) {
                //单选题
                singleChoice();
            } else {
                //多选题
                multipleChoice();
            }
            mTags[mIndex] = true;
            if (mIndex <= 3) {
                mViewPager.setCurrentItem(mIndex + 1);
            } else {
                //先查找，若没有插入数据，若有更新数据
                updateFlag = 2;
                questionPresenter.findUserAnswer5(specialTitle,mPhone);
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
        if (option1.isChecked()) {
            userAnswers.set(mIndex,"1");
        } else if (option2.isChecked()) {
            userAnswers.set(mIndex,"2");

        } else if (option3.isChecked()) {
            userAnswers.set(mIndex,"3");

        } else if (option4.isChecked()) {
            userAnswers.set(mIndex,"4");
        }
    }

    /**********
     * 多选题逻辑
     */
    public void multipleChoice() {
        final CheckBox option1 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionA);
        final CheckBox option2 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionB);
        final CheckBox option3 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionC);
        final CheckBox option4 = mViewPager.getChildAt(mIndex).findViewById(R.id.optionD);
        if (option1.isChecked() && option2.isChecked() && !option3.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex,"12");
        } else if (option1.isChecked() && !option2.isChecked() && !option3.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex,"1");
        } else if (option2.isChecked() && !option1.isChecked() && !option3.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex,"2");
        } else if (option3.isChecked() && !option1.isChecked() && !option2.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex,"3");
        } else if (option4.isChecked() && !option1.isChecked() && !option2.isChecked() && !option3.isChecked()) {
            userAnswers.set(mIndex,"4");
        } else if (option1.isChecked() && option3.isChecked() && !option2.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex, "13");
        } else if (option1.isChecked() && option4.isChecked() && !option2.isChecked() && !option3.isChecked()) {
            userAnswers.set(mIndex, "14");
        } else if (option2.isChecked() && option3.isChecked() && !option1.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex, "23");
        } else if (option2.isChecked() && option4.isChecked() && !option1.isChecked() && !option3.isChecked()) {
            userAnswers.set(mIndex, "24");
        } else if (option3.isChecked() && option4.isChecked() && !option1.isChecked() && !option2.isChecked()) {
            userAnswers.set(mIndex, "34");
        } else if (option1.isChecked() && option2.isChecked() && option3.isChecked() && !option4.isChecked()) {
            userAnswers.set(mIndex, "123");
        } else if (option1.isChecked() && option2.isChecked() && option4.isChecked() && !option3.isChecked()) {
            userAnswers.set(mIndex, "124");
        } else if (option2.isChecked() && option3.isChecked() && option4.isChecked() && !option1.isChecked()) {
            userAnswers.set(mIndex, "234");
        } else if (option1.isChecked() && option2.isChecked() && option3.isChecked() && option4.isChecked()) {
            userAnswers.set(mIndex, "1234");
        }
    }

    /**********
     * 填空题逻辑
     */
    public void fillBlank() {
        EditText input = mViewPager.getChildAt(mIndex).findViewById(R.id.input);
        userAnswers.set(mIndex, input.getText().toString());
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

    /*****
     * 处理返回键
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SpecialAnswerActivity.this);
        builder.setTitle("确定要退出答题？");
        builder.setPositiveButton("继续", (dialogInterface, i) -> {

        });
        builder.setNegativeButton("退出", (dialogInterface, i) -> update());
        builder.create();
        builder.show();
//        super.onBackPressed();
    }
}
