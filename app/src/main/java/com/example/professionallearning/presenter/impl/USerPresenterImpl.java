package com.example.professionallearning.presenter.impl;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.User;
import com.example.professionallearning.presenter.IUSerPresenter;
import com.example.professionallearning.util.RetrofitManager;
import com.example.professionallearning.view.IAlterPasCall;
import com.example.professionallearning.view.IAlterPhoneCall;
import com.example.professionallearning.view.IFillBaseMesCallback;
import com.example.professionallearning.view.ILoginCallback;
import com.example.professionallearning.view.IRegisterCallback;
import com.example.professionallearning.view.IUserMesCallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class USerPresenterImpl implements IUSerPresenter {
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private IRegisterCallback mRegisterCallback = null;
    private IFillBaseMesCallback mFillBaseMesCallback = null;
    private ILoginCallback mLoginCallback = null;
    private IUserMesCallback mUserMesCallback = null;
    private IAlterPhoneCall mAlterPhoneCall = null;
    private IAlterPasCall mAlterPasCall = null;
    private String TAG = "USerPresenterImpl";

    @Override
    public void getUserInRegister(String phone) {
        if (mRegisterCallback != null) {
            mRegisterCallback.onLoading();
        }
        Call<ResponseBody> task = api.findUser(phone);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getUserInRegister-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response.body().string(), User.class);
                        if (mRegisterCallback != null) {
                            if (user == null) {
                                mRegisterCallback.onEmpty();
                            } else {
                                mRegisterCallback.loadUser(user);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mRegisterCallback != null) {
                        mRegisterCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mRegisterCallback != null) {
                    mRegisterCallback.onError();
                }
            }
        });
    }

    @Override
    public void insertUser(String phone,String name,String password) {
        if (mFillBaseMesCallback != null) {
            mFillBaseMesCallback.onLoading();
        }
        Call<ResponseBody> task = api.insertUser(phone,name,password);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "insertUser-->" + response.code());
                if (response.code() == 200) {
                    if (mFillBaseMesCallback != null) {
                        mFillBaseMesCallback.insertUser();
                    }
                } else {
                    //请求失败
                    if (mFillBaseMesCallback != null) {
                        mFillBaseMesCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mFillBaseMesCallback != null) {
                    mFillBaseMesCallback.onError();
                }
            }
        });
    }

    @Override
    public void getUserInLogin(String phone) {
        if (mLoginCallback != null) {
            mLoginCallback.onLoading();
        }
        Call<ResponseBody> task = api.findUser(phone);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getUserInLogin-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response.body().string(), User.class);
                        if (mLoginCallback != null) {
                            if (user == null) {
                                mLoginCallback.onEmpty();
                            } else {
                                mLoginCallback.loadUser(user);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mLoginCallback != null) {
                        mLoginCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLoginCallback != null) {
                    mLoginCallback.onError();
                }
            }
        });
    }

    @Override
    public void updateUserName(String phone, String name) {
        if (mUserMesCallback != null) {
            mUserMesCallback.onLoading();
        }
        Call<ResponseBody> task = api.updateUserName(phone,name);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateUserName-->" + response.code());
                if (response.code() == 200) {
                    if (mUserMesCallback != null) {
                        mUserMesCallback.updateUserName();
                    }
                } else {
                    //请求失败
                    if (mUserMesCallback != null) {
                        mUserMesCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mUserMesCallback != null) {
                    mUserMesCallback.onError();
                }
            }
        });
    }

    @Override
    public void getUserInUserMes(String phone) {
        if (mUserMesCallback != null) {
            mUserMesCallback.onLoading();
        }
        Call<ResponseBody> task = api.findUser(phone);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getUserInUserMes-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response.body().string(), User.class);
                        if (mUserMesCallback != null) {
                            if (user != null) {
                                mUserMesCallback.loadUser(user);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mUserMesCallback != null) {
                        mUserMesCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mLoginCallback != null) {
                    mLoginCallback.onError();
                }
            }
        });
    }

    @Override
    public void updateUserPhone(String oldPhone, String newPhone) {
        if (mAlterPhoneCall != null) {
            mAlterPhoneCall.onLoading();
        }
        Call<ResponseBody> task = api.updateUserPhone(newPhone,oldPhone);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateUserPhone-->" + response.code());
                if (response.code() == 200) {
                    if (mAlterPhoneCall != null) {
                        mAlterPhoneCall.updatePhone();
                    }
                } else {
                    //请求失败
                    if (mAlterPhoneCall != null) {
                        mAlterPhoneCall.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mAlterPhoneCall != null) {
                    mAlterPhoneCall.onError();
                }
            }
        });
    }

    @Override
    public void getUserInAlterPhone(String phone) {
        if (mAlterPhoneCall != null) {
            mAlterPhoneCall.onLoading();
        }
        Call<ResponseBody> task = api.findUser(phone);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getUserInRegister-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response.body().string(), User.class);
                        if (mAlterPhoneCall != null) {
                            if (user == null) {
                                mAlterPhoneCall.onEmpty();
                            } else {
                                mAlterPhoneCall.loadUser(user);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                    if (mAlterPhoneCall != null) {
                        mAlterPhoneCall.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
//                Log.d(TAG, "onFailure-->" + t.toString());
                if (mRegisterCallback != null) {
                    mRegisterCallback.onError();
                }
            }
        });
    }

    @Override
    public void updateUserPas(String phone,String pas) {
        if (mAlterPasCall != null) {
            mAlterPasCall.onLoading();
        }
        Call<ResponseBody> task = api.updateUserPas(phone,pas);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "updateUserPhone-->" + response.code());
                if (response.code() == 200) {
                    if (mAlterPasCall != null) {
                        mAlterPasCall.updatePas();
                    }
                } else {
                    //请求失败
                    if (mAlterPasCall != null) {
                        mAlterPasCall.onError();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
                if (mAlterPasCall != null) {
                    mAlterPasCall.onError();
                }
            }
        });
    }

    @Override
    public void registerUserRegisterCallback(IRegisterCallback callBack) {
        mRegisterCallback = callBack;
    }

    @Override
    public void unRegisterUserRegisterCallback(IRegisterCallback callBack) {
        mRegisterCallback = null;
    }

    @Override
    public void registerFillBaseMesCallback(IFillBaseMesCallback callBack) {
        mFillBaseMesCallback = callBack;
    }

    @Override
    public void unRegisterFillBaseMesCallback(IFillBaseMesCallback callBack) {
        mFillBaseMesCallback = null;
    }

    @Override
    public void registerLoginCallback(ILoginCallback callBack) {
        mLoginCallback = callBack;
    }

    @Override
    public void unRegisterLoginCallback(ILoginCallback callBack) {
        mLoginCallback = null;
    }

    @Override
    public void registerUserMesCall(IUserMesCallback callBack) {
        mUserMesCallback = callBack;
    }

    @Override
    public void unRegisterUserMesCall(IUserMesCallback callBack) {
        mUserMesCallback = null;
    }

    @Override
    public void registerAlterPhoneCall(IAlterPhoneCall callBack) {
        mAlterPhoneCall = callBack;
    }

    @Override
    public void unRegisterAlterPhoneCall(IAlterPhoneCall callBack) {
        mAlterPhoneCall = null;
    }

    @Override
    public void registerAlterPasCall(IAlterPasCall callBack) {
        mAlterPasCall = callBack;
    }

    @Override
    public void unRegisterAlterPasCall(IAlterPasCall callBack) {
        mAlterPasCall = null;
    }
}
