package com.example.professionallearning.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.professionallearning.database.QuestionDatabaseHelper;
import com.example.professionallearning.model.bean.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private final QuestionDatabaseHelper helper;

    public QuestionDao(Context context) {
        helper=new QuestionDatabaseHelper(context,"QuestionDataBase.db",null,1);
    }

    public void deleteAllQuestions() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("Question", null, null);
    }

    public List<Question> findAllQuestions() {
        SQLiteDatabase db = helper.getWritableDatabase();
        // 查询Question表中所有的数据
        Cursor cursor = db.query("Question", null, null, null, null, null, null);
        List<Question> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String optionA = cursor.getString(cursor.getColumnIndex("optionA"));
                String optionB = cursor.getString(cursor.getColumnIndex("optionB"));
                String optionC = cursor.getString(cursor.getColumnIndex("optionC"));
                String optionD = cursor.getString(cursor.getColumnIndex("optionD"));
                String answer1 = cursor.getString(cursor.getColumnIndex("answer1"));
                String answer2 = cursor.getString(cursor.getColumnIndex("answer2"));
                String answer3 = cursor.getString(cursor.getColumnIndex("answer3"));
                String answer4 = cursor.getString(cursor.getColumnIndex("answer4"));
                String analysis = cursor.getString(cursor.getColumnIndex("analysis"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String start = cursor.getString(cursor.getColumnIndex("start"));
                Question question=new Question();
                question.setTitle(title);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setAnswer1(answer1);
                question.setAnswer2(answer2);
                question.setAnswer3(answer3);
                question.setAnswer4(answer4);
                question.setAnalysis(analysis);
                question.setType(type);
                question.setStart(start);
                list.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Question> findQuestionsByAnswerType(String answerType1) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("Question", null, "answerType = ?", new String[]{answerType1}, null, null, null);
        List<Question> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String optionA = cursor.getString(cursor.getColumnIndex("optionA"));
                String optionB = cursor.getString(cursor.getColumnIndex("optionB"));
                String optionC = cursor.getString(cursor.getColumnIndex("optionC"));
                String optionD = cursor.getString(cursor.getColumnIndex("optionD"));
                String answer1 = cursor.getString(cursor.getColumnIndex("answer1"));
                String answer2 = cursor.getString(cursor.getColumnIndex("answer2"));
                String answer3 = cursor.getString(cursor.getColumnIndex("answer3"));
                String answer4 = cursor.getString(cursor.getColumnIndex("answer4"));
                String analysis = cursor.getString(cursor.getColumnIndex("analysis"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String start = cursor.getString(cursor.getColumnIndex("start"));
                String answerType = cursor.getString(cursor.getColumnIndex("answerType"));
                String week = cursor.getString(cursor.getColumnIndex("week"));
                String specialTitle = cursor.getString(cursor.getColumnIndex("specialTitle"));
                Question question=new Question();
                question.setTitle(title);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setAnswer1(answer1);
                question.setAnswer2(answer2);
                question.setAnswer3(answer3);
                question.setAnswer4(answer4);
                question.setAnalysis(analysis);
                question.setType(type);
                question.setStart(start);
                question.setAnswerType(answerType);
                question.setWeek(week);
                question.setSpecialTitle(specialTitle);
                list.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Question> findQuestionsByWeek(String answerType1,String week1) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("Question", null, "answerType = ? and week = ?", new String[]{answerType1,week1}, null, null, null);
        List<Question> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String optionA = cursor.getString(cursor.getColumnIndex("optionA"));
                String optionB = cursor.getString(cursor.getColumnIndex("optionB"));
                String optionC = cursor.getString(cursor.getColumnIndex("optionC"));
                String optionD = cursor.getString(cursor.getColumnIndex("optionD"));
                String answer1 = cursor.getString(cursor.getColumnIndex("answer1"));
                String answer2 = cursor.getString(cursor.getColumnIndex("answer2"));
                String answer3 = cursor.getString(cursor.getColumnIndex("answer3"));
                String answer4 = cursor.getString(cursor.getColumnIndex("answer4"));
                String analysis = cursor.getString(cursor.getColumnIndex("analysis"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String start = cursor.getString(cursor.getColumnIndex("start"));
                String answerType = cursor.getString(cursor.getColumnIndex("answerType"));
                String week = cursor.getString(cursor.getColumnIndex("week"));
                Question question=new Question();
                question.setTitle(title);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setAnswer1(answer1);
                question.setAnswer2(answer2);
                question.setAnswer3(answer3);
                question.setAnswer4(answer4);
                question.setAnalysis(analysis);
                question.setType(type);
                question.setStart(start);
                question.setAnswerType(answerType);
                question.setWeek(week);
                list.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Question> findQuestionsBySpecialTitle(String answerType1,String specialTitle1) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("Question", null, "answerType = ? and specialTitle = ?", new String[]{answerType1,specialTitle1}, null, null, null);
        List<Question> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String optionA = cursor.getString(cursor.getColumnIndex("optionA"));
                String optionB = cursor.getString(cursor.getColumnIndex("optionB"));
                String optionC = cursor.getString(cursor.getColumnIndex("optionC"));
                String optionD = cursor.getString(cursor.getColumnIndex("optionD"));
                String answer1 = cursor.getString(cursor.getColumnIndex("answer1"));
                String answer2 = cursor.getString(cursor.getColumnIndex("answer2"));
                String answer3 = cursor.getString(cursor.getColumnIndex("answer3"));
                String answer4 = cursor.getString(cursor.getColumnIndex("answer4"));
                String analysis = cursor.getString(cursor.getColumnIndex("analysis"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String start = cursor.getString(cursor.getColumnIndex("start"));
                String answerType = cursor.getString(cursor.getColumnIndex("answerType"));
                String week = cursor.getString(cursor.getColumnIndex("week"));
                String specialTitle = cursor.getString(cursor.getColumnIndex("specialTitle"));
                Question question=new Question();
                question.setTitle(title);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setAnswer1(answer1);
                question.setAnswer2(answer2);
                question.setAnswer3(answer3);
                question.setAnswer4(answer4);
                question.setAnalysis(analysis);
                question.setType(type);
                question.setStart(start);
                question.setAnswerType(answerType);
                question.setWeek(week);
                question.setSpecialTitle(specialTitle);
                list.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void insertQuestions(List<Question> questions) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0;i<questions.size();i++) {
            values.put("title", questions.get(i).getTitle());
            values.put("optionA", questions.get(i).getOptionA());
            values.put("optionB", questions.get(i).getOptionB());
            values.put("optionC", questions.get(i).getOptionC());
            values.put("optionD", questions.get(i).getOptionD());
            values.put("answer1", questions.get(i).getAnswer1());
            values.put("answer2", questions.get(i).getAnswer2());
            values.put("answer3", questions.get(i).getAnswer3());
            values.put("answer4", questions.get(i).getAnswer4());
            values.put("analysis", questions.get(i).getAnalysis());
            values.put("type", questions.get(i).getType());
            values.put("start", questions.get(i).getStart());
            values.put("answerType", questions.get(i).getAnswerType());
            values.put("week", questions.get(i).getWeek());
            values.put("specialTitle", questions.get(i).getSpecialTitle());
            db.insert("Question", null, values);
            values.clear();
        }
        db.close();
    }
}
