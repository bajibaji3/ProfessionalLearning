package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.User;
import com.example.professionallearning.model.bean.UserAnswer;

import java.util.List;

public interface IUserMesCallback {
    void updateUserName();
    void loadUser(User user);
    void onError();
    void onLoading();
}
