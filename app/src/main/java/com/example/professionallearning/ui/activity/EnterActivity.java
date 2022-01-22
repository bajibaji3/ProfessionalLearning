package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.professionallearning.R;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.User;

import java.util.List;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        UserDao userDao = new UserDao(this);
        List<User> list = userDao.findUser();
        if (list.size() == 0) {
            Intent intent = new Intent(EnterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (list.get(0).getIsLogin().equals("0")) {
                Intent intent = new Intent(EnterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (list.get(0).getIsLogin().equals("1")){
                Intent intent = new Intent(EnterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
