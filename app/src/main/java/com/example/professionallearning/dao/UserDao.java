package com.example.professionallearning.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.professionallearning.database.QuestionDatabaseHelper;
import com.example.professionallearning.model.bean.User;


import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final QuestionDatabaseHelper helper;

    public UserDao(Context context) {
        helper = new QuestionDatabaseHelper(context, "QuestionDataBase.db", null, 1);
    }

    public void deleteUser() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("User", null, null);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", user.getPhone());
        values.put("name", user.getName());
        values.put("isLogin", user.getIsLogin());
        db.insert("User", null, values);
        values.clear();
        db.close();
    }

    public List<User> findUser() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        List<User> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String isLogin = cursor.getString(cursor.getColumnIndex("isLogin"));
                User user=new User();
                user.setId(id);
                user.setPhone(phone);
                user.setName(name);
                user.setIsLogin(isLogin);
                list.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void updateUserState(String isLogin1,String phone1) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isLogin", isLogin1);
        db.update("User", values, "phone = ?", new String[] {phone1});
    }

    public void updateUserName(String phone1,String name1) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name1);
        db.update("User", values, "phone = ?", new String[] {phone1});
    }

    public void updateUserPhone(String oldPhone,String newPhone) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", newPhone);
        db.update("User", values, "phone = ?", new String[] {oldPhone});
    }
}
