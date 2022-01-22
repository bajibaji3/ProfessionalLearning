package com.example.professionallearning.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuestionDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_QUESTION = "create table Question ("
            +"id integer primary key autoincrement,"
            +"title text,"
            +"optionA text,"
            +"optionB text,"
            +"optionC text,"
            +"optionD text,"
            +"answer1 text,"
            +"answer2 text,"
            +"answer3 text,"
            +"answer4 text,"
            +"analysis text,"
            +"type text,"
            +"start text,"
            +"specialTitle text,"
            +"answerType text,"
            +"week text)";

    private static final String CREATE_USER = "create table User ("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"isLogin text,"
            +"phone text)";

    private static final String CREATE_ARTICLE = "create table Article ("
            +"id integer primary key autoincrement,"
            +"title text,"
            +"detail text,"
            +"author text,"
            +"firstSentence text)";

    public QuestionDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUESTION);
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_ARTICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
