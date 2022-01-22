package com.example.professionallearning.util;

import retrofit2.Retrofit;

public class RetrofitManager {
    private static final RetrofitManager ourInstance = new RetrofitManager();
    private final Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.86:8080")
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}
