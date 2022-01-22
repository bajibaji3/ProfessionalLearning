package com.example.professionallearning.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;

public class AnalysisFillView extends ConstraintLayout {
    protected TextView questionTitle,isTrue,trueAnswer,analysis;
    protected Question mQuestion;
    protected UserAnswer mUserAnswer;
    protected int mIndex;
    protected int mTotalNum;
    protected Activity activity;
    public int start;
    public AnalysisFillView(Context context) {
        super(context);
    }

    public AnalysisFillView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void initData() {
        questionTitle=this.findViewById(R.id.title);
        isTrue = this.findViewById(R.id.isTrue);
        trueAnswer = this.findViewById(R.id.trueAnswer);
        analysis = this.findViewById(R.id.analysisMes);
        questionTitle.setText(mQuestion.getTitle());
        analysis.setText(mQuestion.getAnalysis());
        judge();
    }

    /*
    接受题库数据
     */
    public void setData(Question question, UserAnswer userAnswer, int index, int totalNum, Activity activity){
        mQuestion=question;
        mUserAnswer = userAnswer;
        start=Integer.parseInt(mQuestion.getStart());
        mIndex=index;
        mTotalNum=totalNum;
        this.activity=activity;
        initData();
        judge();
    }

    private void judge() {
        switch (mIndex) {
            case 1:
                //在第一页
                trueAnswer.setText(mUserAnswer.getQuestion1());
                if (mUserAnswer.getQuestion1().equals(mUserAnswer.getUserAnswer1())) {
                    trueOrFalse(true);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer1(),true);
                } else {
                    trueOrFalse(false);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer1(),false);
                }
                break;
            case 2:
                //在第二页
                trueAnswer.setText(mUserAnswer.getQuestion2());
                if (mUserAnswer.getQuestion2().equals(mUserAnswer.getUserAnswer2())) {
                    trueOrFalse(true);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer2(),true);
                } else {
                    trueOrFalse(false);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer2(),false);
                }
                break;
            case 3:
                //在第三页
                trueAnswer.setText(mUserAnswer.getQuestion3());
                if (mUserAnswer.getQuestion3().equals(mUserAnswer.getUserAnswer3())) {
                    trueOrFalse(true);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer3(),true);
                } else {
                    trueOrFalse(false);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer3(),false);
                }
                break;
            case 4:
                //在第四页
                trueAnswer.setText(mUserAnswer.getQuestion4());
                if (mUserAnswer.getQuestion4().equals(mUserAnswer.getUserAnswer4())) {
                    trueOrFalse(true);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer4(),true);
                } else {
                    trueOrFalse(false);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer4(),false);
                }
                break;
            case 5:
                //在第五页
                trueAnswer.setText(mUserAnswer.getQuestion5());
                if (mUserAnswer.getQuestion5().equals(mUserAnswer.getUserAnswer5())) {
                    trueOrFalse(true);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer5(),true);
                } else {
                    trueOrFalse(false);
                    initSpannableStringBuilder(mUserAnswer.getUserAnswer5(),false);
                }
                break;
        }
    }

    private void initSpannableStringBuilder(String userAnswer,boolean flag) {
        if (flag) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.clear();
            spannableStringBuilder.append(mQuestion.getTitle());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#01AE66"));
            if (userAnswer.equals("")) {
                spannableStringBuilder.setSpan(colorSpan, start, start + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else {
                spannableStringBuilder.delete(start, start + 4);
                spannableStringBuilder.insert(start, userAnswer);
                spannableStringBuilder.setSpan(colorSpan, start, start + userAnswer.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            questionTitle.setText(spannableStringBuilder);
        } else {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.clear();
            spannableStringBuilder.append(mQuestion.getTitle());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F12323"));
            if (userAnswer.equals("")) {
                spannableStringBuilder.setSpan(colorSpan, start, start + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else {
                spannableStringBuilder.delete(start, start + 4);
                spannableStringBuilder.insert(start, userAnswer);
                spannableStringBuilder.setSpan(colorSpan, start, start + userAnswer.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            questionTitle.setText(spannableStringBuilder);
        }

    }

    private void trueOrFalse(boolean flag) {
        if (flag) {
            isTrue.setText("回答正确");
            isTrue.setTextColor(Color.parseColor("#01AE66"));
        } else {
            isTrue.setText("回答错误");
            isTrue.setTextColor(Color.parseColor("#F12323"));
        }
    }
}
