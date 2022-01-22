package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.professionallearning.view.ILoginCallback;

public class LoginActivity extends AppCompatActivity implements ILoginCallback {
    private EditText editPhone, editPas;
    private Button buttonLogin;
    private ConstraintLayout mConstraintLayout;
    private TextView netMes;
    private ImageView wifi;
    private String mPhone;
    private String mPas;
    private UserDao mUserDao;
    private IUSerPresenter userPresenter;
    private State currentState;

    public enum State {
        LOADING, ERROR, SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonRegister = findViewById(R.id.button15);
        TextView findPas = findViewById(R.id.textView65);
        buttonLogin = findViewById(R.id.button14);
        editPhone = findViewById(R.id.editText);
        editPas = findViewById(R.id.editText2);
        mConstraintLayout = findViewById(R.id.constraintLayout23);
        netMes = findViewById(R.id.textView80);
        wifi = findViewById(R.id.imageView9);
        mUserDao = new UserDao(this);
        buttonLogin.setEnabled(false);

        initPresenter();

        buttonRegister.setOnClickListener(view -> {
            //设置一个标记，区分是注册还是找回密码
            SharedPreferences shp = getSharedPreferences("REGISTER_FLAG", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shp.edit();
            editor.putInt("IsRegister",0);
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        findPas.setOnClickListener(view -> {
            //设置一个标记，区分是注册还是找回密码
            SharedPreferences shp = getSharedPreferences("REGISTER_FLAG", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shp.edit();
            editor.putInt("IsRegister",1);
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = editPhone.getText().toString().trim();
                String pas = editPas.getText().toString().trim();
                buttonLogin.setEnabled(!phone.isEmpty() && !pas.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editPhone.addTextChangedListener(textWatcher);
        editPas.addTextChangedListener(textWatcher);

        buttonLogin.setOnClickListener(view -> {
            mPhone = editPhone.getText().toString();
            mPas = editPas.getText().toString();
            login();
        });

        wifi.setOnClickListener(view -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (userPresenter != null) {
            login();
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
        } else if (currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if (currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("网络错误，请点击重试");
        }
    }

    private void initPresenter() {
        //创建Presenter
        userPresenter = new USerPresenterImpl();
        userPresenter.registerLoginCallback(this);
    }

    private void login() {
        userPresenter.getUserInLogin(mPhone);
    }

    @Override
    public void loadUser(User user) {
        setState(State.SUCCESS);
        User user1 = new User();
        user1.setIsLogin("1");
        user1.setPhone(user.getPhone());
        user1.setName(user.getName());
        if (mPas.equals(user.getPassword())) {
            if (mUserDao.findUser().size() == 0) {
                //如果本地数据库User表中没有数据
                mUserDao.insertUser(user1);
            } else {
                //如果有数据，先把之前的数据删除再添加
                mUserDao.deleteUser();
                mUserDao.insertUser(user1);
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setState(State.SUCCESS);
        Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
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
        if (userPresenter != null) {
            userPresenter.unRegisterLoginCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
