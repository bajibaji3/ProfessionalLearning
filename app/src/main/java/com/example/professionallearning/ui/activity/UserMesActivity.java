package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.User;
import com.example.professionallearning.presenter.IUSerPresenter;
import com.example.professionallearning.presenter.impl.USerPresenterImpl;
import com.example.professionallearning.view.IUserMesCallback;

public class UserMesActivity extends AppCompatActivity implements IUserMesCallback {
    private ConstraintLayout mConstraintLayout;
    private TextView nameTv,netMes;
    private UserDao userDao;
    private String mPhone;
    private String mName;
    private String newName;
    private String mPassword;
    //true表示更新手机号，false表示更新密码
    private boolean updateFlag;
    private IUSerPresenter userPresenter;
    private State currentState;
    public enum State {
        LOADING, SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mes);
        CardView cardName = findViewById(R.id.cardView9);
        CardView cardPhone = findViewById(R.id.cardView10);
        CardView cardPas = findViewById(R.id.cardView11);
        nameTv = findViewById(R.id.textView99);
        TextView phoneTv = findViewById(R.id.textView101);
        Button buttonExit = findViewById(R.id.button23);
        mConstraintLayout = findViewById(R.id.constraintLayout28);
        netMes = findViewById(R.id.textView102);
        userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        mName = userDao.findUser().get(0).getName();

        initPresenter();

        nameTv.setText(mName);
        phoneTv.setText(mPhone);

        buttonExit.setOnClickListener(view -> {
            userDao.updateUserState("0", mPhone);

            if (MainActivity.mainActivity == null ||
                    MainActivity.mainActivity.isFinishing() ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && MainActivity.mainActivity.isDestroyed())) {
                Intent intent = new Intent(UserMesActivity.this,LoginActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                MainActivity.mainActivity.finish();
                Intent intent = new Intent(UserMesActivity.this,LoginActivity.class);
                startActivity(intent);
                this.finish();
            }
        });

        cardName.setOnClickListener(view -> {
            EditText editText = new EditText(this);
            editText.setText(mName);
            AlertDialog.Builder builder=new AlertDialog.Builder(UserMesActivity.this);
            builder.setTitle("修改昵称");
            builder.setView(editText);
            builder.setCancelable(false);
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                newName = editText.getText().toString();
                updateName();
            });
            builder.setNegativeButton("取消", (dialogInterface, i) -> {

            });
            builder.create();
            builder.show();
        });

        cardPhone.setOnClickListener(view -> {
            updateFlag = true;
            EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setTransformationMethod(new PasswordTransformationMethod());
            AlertDialog.Builder builder=new AlertDialog.Builder(UserMesActivity.this);
            builder.setTitle("修改手机号前需要验证登录密码");
            builder.setMessage("如果您忘记了原密码，请先退出登录，通过找回密码功能重新设置密码。");
            builder.setView(editText);
            builder.setCancelable(false);
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                mPassword = editText.getText().toString();
                userPresenter.getUserInUserMes(mPhone);
            });
            builder.setNegativeButton("取消", (dialogInterface, i) -> {

            });
            builder.create();
            builder.show();
        });

        cardPas.setOnClickListener(view -> {
            updateFlag = false;
            EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setTransformationMethod(new PasswordTransformationMethod());
            AlertDialog.Builder builder=new AlertDialog.Builder(UserMesActivity.this);
            builder.setTitle("修改密码前需要验证登录密码");
            builder.setMessage("如果您忘记了原密码，请先退出登录，通过找回密码功能重新设置密码。");
            builder.setView(editText);
            builder.setCancelable(false);
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                mPassword = editText.getText().toString();
                userPresenter.getUserInUserMes(mPhone);
            });
            builder.setNegativeButton("取消", (dialogInterface, i) -> {

            });
            builder.create();
            builder.show();
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
        } else if (currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        }
    }

    private void initPresenter() {
        //创建Presenter
        userPresenter = new USerPresenterImpl();
        userPresenter.registerUserMesCall(this);
    }

    private void updateName() {
        userPresenter.updateUserName(mPhone,newName);
    }

    @Override
    public void updateUserName() {
        setState(State.SUCCESS);
        userDao.updateUserName(mPhone,newName);
        nameTv.setText(newName);
    }

    @Override
    public void loadUser(User user) {
        if (mPassword.equals(user.getPassword())) {
            if (updateFlag) {
                //进入更新手机号
                Intent intent = new Intent(UserMesActivity.this,AlterPhoneActivity.class);
                startActivity(intent);
                finish();
            } else {
                //进入更新密码
                Intent intent = new Intent(UserMesActivity.this,AlterPasActivity.class);
                startActivity(intent);
                finish();
            }

        } else {
            setState(State.SUCCESS);
            Toast.makeText(UserMesActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError() {
        setState(State.SUCCESS);
        Toast.makeText(UserMesActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
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
            userPresenter.unRegisterUserMesCall(this);
        }
    }
}
