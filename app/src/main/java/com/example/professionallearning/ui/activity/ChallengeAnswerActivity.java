package com.example.professionallearning.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.dao.QuestionDao;
import com.example.professionallearning.presenter.IQuestionPresenter;
import com.example.professionallearning.presenter.impl.QuestionPresenterImpl;
import com.example.professionallearning.view.IChallengeCallback;

import java.util.List;

public class ChallengeAnswerActivity extends AppCompatActivity implements IChallengeCallback {
    private TextView title;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private TextView timer;
    private ConstraintLayout challengeBody;
    private TextView mes;
    private ImageView wifi;
    private QuestionDao mQuestionDao;
    private List<Question> mList;
    private String mPhone;
    private int mIndex = 0;
    private int mScore = 0;
    private int time = 30;
    //true表示正在答题，false表示答错了
    private boolean answer_flag = true;
    private IQuestionPresenter questionPresenter;
    //0表示加载题目时的网络错误，1表示对最高分操作时的网络错误
    private int netFlag = 0;
    private State currentState = State.NONE;

    public enum State {
        NONE, LOADING, ERROR, SUCCESS, EMPTY
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                String TAG = "myTag";
                Log.d(TAG,"我还在运行。。。。");
                timer.setText(String.valueOf(time));
                time--;
                if (time == -1) {
                    answer_flag = false;
                    timer.setTextColor(android.graphics.Color.parseColor("#00000000"));
                    timer.setBackgroundResource(R.drawable.ic_time_false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeAnswerActivity.this);
                    builder.setTitle("挑战结束");
                    builder.setMessage("本次答对" + mScore + "题");
                    builder.setPositiveButton("结束本局", (dialogInterface, i) -> find());
                    builder.create();
                    builder.setCancelable(false);
                    builder.show();
                }
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_answer);
        title = findViewById(R.id.itemTitle);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        timer = findViewById(R.id.timer);
        challengeBody = findViewById(R.id.challengeBody);
        mes = findViewById(R.id.mes);
        wifi = findViewById(R.id.wifi);
        mQuestionDao = new QuestionDao(this);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        setState(State.NONE);
        initPresenter();
        getChallengeQuestions();

        option1.setOnClickListener(view -> {
            switch (mList.get(mIndex).getAnswer1()) {
                case "1":
                    mScore++;
                    option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option1.setCompoundDrawables(drawable, null, null, null);
                    mIndex++;
                    timer.setTextColor(android.graphics.Color.parseColor("#00000000"));
                    timer.setBackgroundResource(R.drawable.ic_time_true);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        updateView();
                        time = 30;
                    }, 500);//0.5秒后执行Runnable中的run方法

                    break;
                case "2":
                    answerFalse(option2, option1);
                    break;
                case "3":
                    answerFalse(option3, option1);
                    break;
                default:
                    answerFalse(option4, option1);
                    break;
            }
        });

        option2.setOnClickListener(view -> {
            switch (mList.get(mIndex).getAnswer1()) {
                case "2":
                    mScore++;
                    option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option2.setCompoundDrawables(drawable, null, null, null);
                    mIndex++;
                    timer.setTextColor(android.graphics.Color.parseColor("#00000000"));
                    timer.setBackgroundResource(R.drawable.ic_time_true);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        updateView();
                        time = 30;
                    }, 500);//0.5秒后执行Runnable中的run方法

                    break;
                case "1":
                    answerFalse(option1, option2);
                    break;
                case "3":
                    answerFalse(option3, option2);
                    break;
                default:
                    answerFalse(option4, option2);
                    break;
            }
        });

        option3.setOnClickListener(view -> {
            switch (mList.get(mIndex).getAnswer1()) {
                case "3":
                    mScore++;
                    option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option3.setCompoundDrawables(drawable, null, null, null);
                    mIndex++;
                    timer.setTextColor(android.graphics.Color.parseColor("#00000000"));
                    timer.setBackgroundResource(R.drawable.ic_time_true);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        updateView();
                        time = 30;
                    }, 500);//0.5秒后执行Runnable中的run方法

                    break;
                case "2":
                    answerFalse(option2, option3);
                    break;
                case "1":
                    answerFalse(option1, option3);
                    break;
                default:
                    answerFalse(option4, option3);
                    break;
            }
        });

        option4.setOnClickListener(view -> {
            switch (mList.get(mIndex).getAnswer1()) {
                case "4":
                    mScore++;
                    option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    option4.setCompoundDrawables(drawable, null, null, null);
                    mIndex++;
                    timer.setTextColor(android.graphics.Color.parseColor("#00000000"));
                    timer.setBackgroundResource(R.drawable.ic_time_true);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        updateView();
                        time = 30;
                    }, 500);//0.5秒后执行Runnable中的run方法

                    break;
                case "2":
                    answerFalse(option2, option4);
                    break;
                case "3":
                    answerFalse(option3, option4);
                    break;
                default:
                    answerFalse(option1, option4);
                    break;
            }
        });

        wifi.setOnClickListener(View -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载题目数据
        if (questionPresenter != null) {
            if (netFlag == 0) {
                questionPresenter.findAllChallengeQuestion();
            } else {
                find();
            }
        }
    }

    public void answerFalse(Button button1, Button button2) {
        answer_flag = false;
        timer.setTextColor(android.graphics.Color.parseColor("#00000000"));
        timer.setBackgroundResource(R.drawable.ic_time_false);
        button1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        button1.setCompoundDrawables(drawable, null, null, null);
        button2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable1 = getResources().getDrawable(R.drawable.ic_false);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                drawable1.getMinimumHeight());
        button2.setCompoundDrawables(drawable1, null, null, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeAnswerActivity.this);
        builder.setTitle("挑战结束");
        builder.setMessage("本次答对" + mScore + "题");
        builder.setPositiveButton("结束本局", (dialogInterface, i) -> find());
        builder.create();
        builder.setCancelable(false);
        builder.show();
    }

    public void updateView() {
        timer.setTextColor(android.graphics.Color.parseColor("#000000"));
        timer.setBackgroundResource(R.drawable.time_bg);
        option1.setBackgroundColor(android.graphics.Color.parseColor("#F0F0F0"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_a);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        option1.setCompoundDrawables(drawable, null, null, null);
        option2.setBackgroundColor(android.graphics.Color.parseColor("#F0F0F0"));
        Drawable drawable1 = getResources().getDrawable(R.drawable.ic_b);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                drawable1.getMinimumHeight());
        option2.setCompoundDrawables(drawable1, null, null, null);
        option3.setBackgroundColor(android.graphics.Color.parseColor("#F0F0F0"));
        Drawable drawable2 = getResources().getDrawable(R.drawable.ic_c);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
                drawable2.getMinimumHeight());
        option3.setCompoundDrawables(drawable2, null, null, null);
        option4.setBackgroundColor(android.graphics.Color.parseColor("#F0F0F0"));
        Drawable drawable3 = getResources().getDrawable(R.drawable.ic_d);
        drawable3.setBounds(0, 0, drawable3.getMinimumWidth(),
                drawable3.getMinimumHeight());
        option4.setCompoundDrawables(drawable3, null, null, null);

        title.setText(mList.get(mIndex).getTitle());
        option1.setText(mList.get(mIndex).getOptionA());
        option2.setText(mList.get(mIndex).getOptionB());
        option3.setText(mList.get(mIndex).getOptionC());
        option4.setText(mList.get(mIndex).getOptionD());
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            challengeBody.setVisibility(View.VISIBLE);
            mes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if (currentState == State.LOADING) {
            challengeBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            mes.setVisibility(View.VISIBLE);
            mes.setText("加载中。。。");
        } else if (currentState == State.ERROR) {
            challengeBody.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            mes.setVisibility(View.VISIBLE);
            mes.setText("网络错误，请点击重试");
        } else if (currentState == State.EMPTY) {
            challengeBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            mes.setVisibility(View.VISIBLE);
            mes.setText("内容为空。。。");
        } else if (currentState == State.NONE) {
            challengeBody.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            mes.setVisibility(View.GONE);
        }
    }

    private void judge(int highScore) {
        if (highScore < mScore) {
            update();
        } else {
            Intent intent = new Intent(ChallengeAnswerActivity.this, ChallengeAnswerResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("score", mScore);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    private void initPresenter() {
        //创建Presenter
        questionPresenter = new QuestionPresenterImpl();
        questionPresenter.registerChallengeCallback(this);
    }

    private void find() {
        questionPresenter.findHighScore(mPhone);
    }

    private void insert() {
        questionPresenter.insertHighScore(String.valueOf(mScore),mPhone);
    }

    private void update() {
        questionPresenter.updateHighScore(String.valueOf(mScore),mPhone);
    }

    private void getChallengeQuestions() {
        questionPresenter.findAllChallengeQuestion();
    }

    @Override
    public void loadAllChallengeQuestion(List<Question> questions) {
        setState(State.SUCCESS);
        mHandler.sendEmptyMessage(1);
        mQuestionDao.deleteAllQuestions();
        mQuestionDao.insertQuestions(questions);
        mList = mQuestionDao.findAllQuestions();
        updateView();
    }

    @Override
    public void insertHighScore() {
        Intent intent = new Intent(ChallengeAnswerActivity.this, ChallengeAnswerResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", mScore);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void findHighScore(String highScore) {
        //后台有数据，判断是否对后台数据进行更新
        judge(Integer.parseInt(highScore));
    }

    @Override
    public void updateHighScore() {
        Intent intent = new Intent(ChallengeAnswerActivity.this, ChallengeAnswerResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", mScore);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onQuestionError() {
        setState(State.ERROR);
    }

    @Override
    public void onQuestionLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onQuestionEmpty() {
        setState(State.EMPTY);
    }

    @Override
    public void onScoreError() {
        netFlag = 1;
        setState(State.ERROR);
    }

    @Override
    public void onScoreLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onScoreEmpty() {
        //后台没有数据，将数据写入后台
        insert();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        release();
    }

    private void release() {
        //取消注册
        if (questionPresenter != null) {
            questionPresenter.unRegisterChallengeCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        //如果正在答题
        if (answer_flag) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeAnswerActivity.this);
            builder.setTitle("确定要退出答题？");
            builder.setMessage("退出后作答历史将不会保存");
            builder.setPositiveButton("继续", (dialogInterface, i) -> {

            });
            builder.setNegativeButton("退出", (dialogInterface, i) -> {
                SharedPreferences shp = getSharedPreferences("ACTIVITY_FLAG", Context.MODE_PRIVATE);
                int myFlag = shp.getInt("MyFlag",0);
                if (myFlag == 0) {
                    Intent intent = new Intent(ChallengeAnswerActivity.this,AnswerTypeActivity.class);
                    startActivity(intent);
                } else if (myFlag == 1) {
                    Intent intent = new Intent(ChallengeAnswerActivity.this,LearnPerformanceActivity.class);
                    startActivity(intent);
                }
                finish();
            });
            builder.create();
            builder.show();
        }
    }
}
