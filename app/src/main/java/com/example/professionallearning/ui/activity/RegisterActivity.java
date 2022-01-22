package com.example.professionallearning.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.User;
import com.example.professionallearning.presenter.IUSerPresenter;
import com.example.professionallearning.presenter.impl.USerPresenterImpl;
import com.example.professionallearning.view.IRegisterCallback;
import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity implements IRegisterCallback {
    private EditText phoneNum,verifyNum;
    private Button buttonNext,buttonGet;
    private ConstraintLayout mConstraintLayout;
    private TextView netMes;
    private ImageView wifi;
    private UserDao mUserDao;
    private boolean flag = true;
    private int second = 30;
    private String mPhone;
    private IUSerPresenter userPresenter;
    private State currentState;
    public enum State {
        LOADING,ERROR,SUCCESS
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (second == -1) {
                    buttonGet.setText("重新获取");
                    buttonGet.setEnabled(true);
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    buttonGet.setEnabled(false);
                    buttonGet.setText(String.valueOf(second));
                    second--;
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MobSDK.submitPolicyGrantResult(true, null);
        phoneNum = findViewById(R.id.editText3);
        verifyNum = findViewById(R.id.editText10);
        buttonNext = findViewById(R.id.button16);
        buttonGet = findViewById(R.id.button19);
        mConstraintLayout = findViewById(R.id.constraintLayout21);
        netMes = findViewById(R.id.textView78);
        wifi = findViewById(R.id.imageView7);
        mUserDao = new UserDao(this);
        buttonNext.setEnabled(false);

        initPresenter();

        buttonGet.setOnClickListener(view -> {
            if (phoneNum.getText().toString().length() != 11) {
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("手机号错误");
                builder.setMessage("输入的手机号码无效，请确认后重试！");
                builder.setPositiveButton("确定", (dialogInterface, i) -> {

                });
                builder.create();
                builder.show();
            } else {
                String phone = phoneNum.getText().toString();
                SMSSDK.getVerificationCode("86", phone);
                second = 30;
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        });

        buttonNext.setOnClickListener(view -> {
            String code = verifyNum.getText().toString();
            mPhone = phoneNum.getText().toString();
            SMSSDK.submitVerificationCode("86", mPhone, code);
            flag = false;
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = phoneNum.getText().toString().trim();
                String verify = verifyNum.getText().toString().trim();
                buttonNext.setEnabled(!phone.isEmpty() && !verify.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        phoneNum.addTextChangedListener(textWatcher);
        verifyNum.addTextChangedListener(textWatcher);

        EventHandler eventHandler = new EventHandler() {       // 操作回调
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);

        wifi.setOnClickListener(view -> onRetryClick());
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功，true为智能验证，false为普通下发短信
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证成功
                    //判断用户是否存在，若存在直接进入，否则继续注册
                    loadData();
                    Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 如果操作失败
                if (flag) {
                    Toast.makeText(RegisterActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    buttonGet.setText("重新获取");
                    mHandler.removeCallbacksAndMessages(null);
                    second = 30;
                } else {
                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (userPresenter != null) {
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
            netMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("网络错误，请点击重试");
        }
    }

    private void initPresenter() {
        //创建Presenter
        userPresenter = new USerPresenterImpl();
        userPresenter.registerUserRegisterCallback(this);
    }

    private void loadData() {
        //加载
        userPresenter.getUserInRegister(mPhone);
    }

    @Override
    public void loadUser(User user) {
        //已经有该用户了
        User user1 = new User();
        user1.setPhone(user.getPhone());
        user1.setName(user.getName());

        SharedPreferences shp = getSharedPreferences("REGISTER_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("IsRegister",0);
        if (myFlag == 0) {
            //点击注册进来的
            user1.setIsLogin("1");
            if (mUserDao.findUser().size() == 0) {
                //如果本地数据库User表中没有数据
                mUserDao.insertUser(user1);
            } else {
                //如果有数据，先把之前的数据删除再添加
                mUserDao.deleteUser();
                mUserDao.insertUser(user1);
            }
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else if (myFlag == 1) {
            //点击找回密码进来的
            //设置未登录状态防止找回密码中途退出
            user1.setIsLogin("0");
            if (mUserDao.findUser().size() == 0) {
                //如果本地数据库User表中没有数据
                mUserDao.insertUser(user1);
            } else {
                //如果有数据，先把之前的数据删除再添加
                mUserDao.deleteUser();
                mUserDao.insertUser(user1);
            }
            Intent intent = new Intent(RegisterActivity.this,AlterPasActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        //之前没有该用户
        Intent intent = new Intent(RegisterActivity.this,SetPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone",mPhone);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
        mHandler.removeCallbacksAndMessages(null);
        SMSSDK.unregisterAllEventHandler();
    }

    private void release() {
        //取消注册
        if (userPresenter != null) {
            userPresenter.unRegisterUserRegisterCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
