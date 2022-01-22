package com.example.professionallearning.view;


import com.example.professionallearning.model.bean.User;

public interface IAlterPhoneCall {
    void loadUser(User user);
    void updatePhone();
    void onError();
    void onLoading();
    void onEmpty();
}
