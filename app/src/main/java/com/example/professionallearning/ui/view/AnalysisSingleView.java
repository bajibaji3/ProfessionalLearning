package com.example.professionallearning.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;

public class AnalysisSingleView extends ConstraintLayout implements View.OnClickListener{
    protected TextView questionTitle,isTrue,trueAnswer,analysis;
    protected RadioGroup radioGroup;
    protected RadioButton option1;
    protected RadioButton option2;
    protected RadioButton option3;
    protected RadioButton option4;
    protected Question mQuestion;
    protected UserAnswer mUserAnswer;
    protected int mIndex;
    protected int mTotalNum;
    protected Context mContext;

    public AnalysisSingleView(Context context) {
        super(context);
        mContext=context;
    }

    public AnalysisSingleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    /**
     * 初始化数据
     */
    protected void initData(){
        questionTitle = this.findViewById(R.id.title);
        isTrue = this.findViewById(R.id.isTrue);
        trueAnswer = this.findViewById(R.id.trueAnswer);
        analysis = this.findViewById(R.id.analysisMes);
        radioGroup = this.findViewById(R.id.radioGroup2);
        option1 = this.findViewById(R.id.radioButton);
        option2 = this.findViewById(R.id.radioButton2);
        option3 = this.findViewById(R.id.radioButton3);
        option4 = this.findViewById(R.id.radioButton4);
        questionTitle.setText(mQuestion.getTitle());
        option1.setText(mQuestion.getOptionA());
        option2.setText(mQuestion.getOptionB());
        option3.setText(mQuestion.getOptionC());
        option4.setText(mQuestion.getOptionD());
        analysis.setText(mQuestion.getAnalysis());
        judge();
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }

    private void judge() {
        switch (mIndex) {
            case 1:
                //在第一页
                switch (mUserAnswer.getQuestion1()) {
                    case "1":
                        //正确答案为A
                        trueAnswer.setText("A");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(true);
                                setOptions(option1, true);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "2":
                        //正确答案为B
                        trueAnswer.setText("B");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(true);
                                setOptions(option2, true);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "3":
                        //正确答案为C
                        trueAnswer.setText("C");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(true);
                                setOptions(option3, true);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "4":
                        //正确答案为D
                        trueAnswer.setText("D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(true);
                                setOptions(option4, true);
                                break;
                        }
                        break;
                }
                break;
            case 2:
                //在第二页
                switch (mUserAnswer.getQuestion2()) {
                    case "1":
                        //正确答案为A
                        trueAnswer.setText("A");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(true);
                                setOptions(option1, true);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "2":
                        //正确答案为B
                        trueAnswer.setText("B");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(true);
                                setOptions(option2, true);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "3":
                        //正确答案为C
                        trueAnswer.setText("C");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(true);
                                setOptions(option3, true);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "4":
                        //正确答案为D
                        trueAnswer.setText("D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(true);
                                setOptions(option4, true);
                                break;
                        }
                        break;
                }
                break;
            case 3:
                //在第三页
                switch (mUserAnswer.getQuestion3()) {
                    case "1":
                        //正确答案为A
                        trueAnswer.setText("A");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(true);
                                setOptions(option1, true);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "2":
                        //正确答案为B
                        trueAnswer.setText("B");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(true);
                                setOptions(option2, true);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "3":
                        //正确答案为C
                        trueAnswer.setText("C");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(true);
                                setOptions(option3, true);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "4":
                        //正确答案为D
                        trueAnswer.setText("D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(true);
                                setOptions(option4, true);
                                break;
                        }
                        break;
                }
                break;
            case 4:
                //在第四页
                switch (mUserAnswer.getQuestion4()) {
                    case "1":
                        //正确答案为A
                        trueAnswer.setText("A");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(true);
                                setOptions(option1, true);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "2":
                        //正确答案为B
                        trueAnswer.setText("B");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(true);
                                setOptions(option2, true);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "3":
                        //正确答案为C
                        trueAnswer.setText("C");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(true);
                                setOptions(option3, true);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "4":
                        //正确答案为D
                        trueAnswer.setText("D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(true);
                                setOptions(option4, true);
                                break;
                        }
                        break;
                }
                break;
            case 5:
                //在第五页
                switch (mUserAnswer.getQuestion5()) {
                    case "1":
                        //正确答案为A
                        trueAnswer.setText("A");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(true);
                                setOptions(option1, true);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "2":
                        //正确答案为B
                        trueAnswer.setText("B");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(true);
                                setOptions(option2, true);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "3":
                        //正确答案为C
                        trueAnswer.setText("C");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(true);
                                setOptions(option3, true);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptions(option4, false);
                                break;
                        }
                        break;
                    case "4":
                        //正确答案为D
                        trueAnswer.setText("D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptions(option1, false);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptions(option2, false);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptions(option3, false);
                                break;
                            case "4":
                                trueOrFalse(true);
                                setOptions(option4, true);
                                break;
                        }
                        break;
                }
                break;
        }
    }

    private void trueOrFalse(boolean flag) {
        if (flag) {
            isTrue.setText("回答正确");
            isTrue.setTextColor(android.graphics.Color.parseColor("#01AE66"));
        } else {
            isTrue.setText("回答错误");
            isTrue.setTextColor(android.graphics.Color.parseColor("#F12323"));
        }
    }

    public void setOptions(RadioButton radioButton,boolean flag) {
        if (flag) {
            radioButton.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            radioButton.setCompoundDrawables(drawable, null, null, null);
        } else {
            radioButton.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            Drawable drawable1 = getResources().getDrawable(R.drawable.ic_false);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                    drawable1.getMinimumHeight());
            radioButton.setCompoundDrawables(drawable1, null, null, null);
        }
    }

    /*
    接受题库数据
     */
    public void setData(Question question, UserAnswer userAnswer,int index, int totalNum){
        mQuestion = question;
        mUserAnswer = userAnswer;
        mIndex = index;
        mTotalNum = totalNum;
        initData();
    }

    @Override
    public void onClick(View view) {
    }

}
