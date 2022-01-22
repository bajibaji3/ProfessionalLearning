package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.User;

public interface ILoginCallback {
    void loadUser(User user);
    void onError();
    void onEmpty();
    void onLoading();
}
