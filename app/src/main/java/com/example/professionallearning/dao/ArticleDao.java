package com.example.professionallearning.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.professionallearning.database.QuestionDatabaseHelper;
import com.example.professionallearning.model.bean.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    private final QuestionDatabaseHelper helper;

    public ArticleDao(Context context) {
        helper = new QuestionDatabaseHelper(context, "QuestionDataBase.db", null, 1);
    }

    public void deleteArticle() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("Article", null, null);
    }

    public void insertArticle(List<Article> articles) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0;i<articles.size();i++) {
            values.put("title", articles.get(i).getTitle());
            values.put("detail", articles.get(i).getDetail());
            values.put("author", articles.get(i).getAuthor());
            values.put("firstSentence", articles.get(i).getFirstSentence());
            db.insert("Article", null, values);
            values.clear();
        }
        db.close();
    }

    public List<Article> findArticle() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("Article", null, null, null, null, null, null);
        List<Article> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String detail = cursor.getString(cursor.getColumnIndex("detail"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String firstSentence = cursor.getString(cursor.getColumnIndex("firstSentence"));
                Article article=new Article();
                article.setId(id);
                article.setAuthor(author);
                article.setDetail(detail);
                article.setTitle(title);
                article.setFirstSentence(firstSentence);
                list.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
