package com.example.professionallearning.presenter;

import com.example.professionallearning.view.IAlterPasCall;
import com.example.professionallearning.view.IAlterPhoneCall;
import com.example.professionallearning.view.IFillBaseMesCallback;
import com.example.professionallearning.view.ILoginCallback;
import com.example.professionallearning.view.IRegisterCallback;
import com.example.professionallearning.view.IUserMesCallback;

public interface IUSerPresenter {
    //RegisterActivity
    void getUserInRegister(String phone);
    //FillBaseMesActivity
    void insertUser(String phone,String name,String password);
    //LoginActivity
    void getUserInLogin(String phone);
    //UserMesActivity
    void updateUserName(String phone,String name);
    //UserMesActivity
    void getUserInUserMes(String phone);
    //AlterPhoneActivity
    void updateUserPhone(String oldPhone,String newPhone);
    //AlterPhoneActivity
    void getUserInAlterPhone(String phone);
    //
    void updateUserPas(String phone,String pas);
    //RegisterActivity
    void registerUserRegisterCallback(IRegisterCallback callBack);
    //RegisterActivity
    void unRegisterUserRegisterCallback(IRegisterCallback callBack);
    //FillBaseMesActivity
    void registerFillBaseMesCallback(IFillBaseMesCallback callBack);
    //FillBaseMesActivity
    void unRegisterFillBaseMesCallback(IFillBaseMesCallback callBack);
    //LoginActivity
    void registerLoginCallback(ILoginCallback callBack);
    //LoginActivity
    void unRegisterLoginCallback(ILoginCallback callBack);
    //UserMesActivity
    void registerUserMesCall(IUserMesCallback callBack);
    //UserMesActivity
    void unRegisterUserMesCall(IUserMesCallback callBack);
    //AlterPhoneActivity
    void registerAlterPhoneCall(IAlterPhoneCall callBack);
    //AlterPhoneActivity
    void unRegisterAlterPhoneCall(IAlterPhoneCall callBack);
    //AlterPasActivity
    void registerAlterPasCall(IAlterPasCall callBack);
    //AlterPasActivity
    void unRegisterAlterPasCall(IAlterPasCall callBack);
}
