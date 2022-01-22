package com.example.professionallearning.util;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;


public class Util {

    /*
      读取question.json
     */
    public static String getStringFromAsset(Activity mContext, String jsonFile) {
        String result = "";
        try {
            //读取文件数据
            InputStream is = mContext.getResources().getAssets().open(jsonFile);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);//输出流
            result = new String(buffer, "utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*
    读取poem.json
     */
    public static String getStringFromPoem(Context mContext, String jsonFile) {
        String result = "";
        try {
            //读取文件数据
            InputStream is = mContext.getResources().getAssets().open(jsonFile);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);//输出流
            result = new String(buffer, "utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
