package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.User;
import com.example.professionallearning.presenter.IUSerPresenter;
import com.example.professionallearning.presenter.impl.USerPresenterImpl;
import com.example.professionallearning.view.IFillBaseMesCallback;

public class FillBaseMesActivity extends AppCompatActivity implements IFillBaseMesCallback {
    private ConstraintLayout mConstraintLayout;
    private TextView netMes;
    private ImageView wifi;
    private EditText editName;
    private Button buttonEnter;
    private String mName;
    private String mPhone;
    private String mPas;
    private UserDao mUserDao;
    private IUSerPresenter userPresenter;
    private State currentState;
    public enum State {
        LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_base_mes);
        mConstraintLayout = findViewById(R.id.constraintLayout22);
        netMes = findViewById(R.id.textView79);
        wifi = findViewById(R.id.imageView8);
        editName = findViewById(R.id.editText9);
        buttonEnter = findViewById(R.id.button18);
        mUserDao = new UserDao(this);
        buttonEnter.setEnabled(false);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            mPhone = bundle.getString("mPhone");
            mPas = bundle.getString("password");
        }

        initPresenter();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = editName.getText().toString().trim();
                buttonEnter.setEnabled(!name.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editName.addTextChangedListener(textWatcher);

        buttonEnter.setOnClickListener(view -> {
            mName = editName.getText().toString();
            User user = new User();
            user.setName(mName);
            user.setPhone(mPhone);
            user.setIsLogin("1");
            if (mUserDao.findUser().size() == 0) {
                //如果本地数据库User表中没有数据
                mUserDao.insertUser(user);
            } else {
                //如果有数据，先把之前的数据删除再添加
                mUserDao.deleteUser();
                mUserDao.insertUser(user);
            }
            insertData();
        });

        wifi.setOnClickListener(view -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (userPresenter != null) {
            userPresenter.insertUser(mPhone,mName,mPas);
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
        userPresenter.registerFillBaseMesCallback(this);
    }

    private void insertData() {
        //加载答题分数等数据
        userPresenter.insertUser(mPhone,mName,mPas);
    }

    @Override
    public void insertUser() {
        setState(State.SUCCESS);
        Intent intent = new Intent(FillBaseMesActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError() {
        setState(State.ERROR);
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
            userPresenter.unRegisterFillBaseMesCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FillBaseMesActivity.this,SetPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("phoneBack",mPhone);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
