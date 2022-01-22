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
import android.widget.TextView;
import android.widget.Toast;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.presenter.IUSerPresenter;
import com.example.professionallearning.presenter.impl.USerPresenterImpl;
import com.example.professionallearning.view.IAlterPasCall;

public class AlterPasActivity extends AppCompatActivity implements IAlterPasCall {
    private String mPhone;
    private EditText editPas;
    private Button buttonNext;
    private TextView netMes;
    private ConstraintLayout mConstraintLayout;
    private UserDao userDao;
    private IUSerPresenter userPresenter;
    private State currentState;
    public enum State {
        LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pas);

        editPas = findViewById(R.id.editText4);
        buttonNext = findViewById(R.id.button17);
        buttonNext.setEnabled(false);
        netMes = findViewById(R.id.textView105);
        mConstraintLayout = findViewById(R.id.pasBody);
        userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();

        initPresenter();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pas = editPas.getText().toString().trim();
                buttonNext.setEnabled(!pas.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editPas.addTextChangedListener(textWatcher);

        buttonNext.setOnClickListener(view -> {
            if (isLetterDigit(editPas.getText().toString())) {
                if (editPas.getText().toString().length() < 6 || editPas.getText().toString().length() > 20) {
                    Toast.makeText(AlterPasActivity.this, "密码必须为6到20位", Toast.LENGTH_SHORT).show();
                } else {
                    userPresenter.updateUserPas(mPhone,editPas.getText().toString());
                }
            } else {
                Toast.makeText(AlterPasActivity.this, "密码必须为字母或数字", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            mConstraintLayout.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        }
    }

    private void initPresenter() {
        //创建Presenter
        userPresenter = new USerPresenterImpl();
        userPresenter.registerAlterPasCall(this);
    }

    @Override
    public void updatePas() {
        SharedPreferences shp = getSharedPreferences("REGISTER_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("IsRegister",0);
        if (myFlag == 0) {
            //从修改密码过来的
            Intent intent = new Intent(AlterPasActivity.this,UserMesActivity.class);
            startActivity(intent);
            finish();
        } else if (myFlag == 1) {
            //从找回密码过来的
            userDao.updateUserState("1",mPhone);
            Intent intent = new Intent(AlterPasActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onError() {
        setState(State.SUCCESS);
        Toast.makeText(AlterPasActivity.this, "操作失败，请重试", Toast.LENGTH_SHORT).show();
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
            userPresenter.unRegisterAlterPasCall(this);
        }
    }

    //判断密码中是不是只含有数字和字母
    private boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    @Override
    public void onBackPressed() {

        SharedPreferences shp = getSharedPreferences("REGISTER_FLAG", Context.MODE_PRIVATE);
        int myFlag = shp.getInt("IsRegister",0);
        if (myFlag == 0) {
            //从修改密码过来的
            Intent intent = new Intent(AlterPasActivity.this,UserMesActivity.class);
            startActivity(intent);
            finish();
        } else if (myFlag == 1) {
            //从找回密码过来的
            Intent intent = new Intent(AlterPasActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
