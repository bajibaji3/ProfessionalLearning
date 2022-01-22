package com.example.professionallearning.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.model.bean.UserAnswer;

public class AnalysisMultipleView extends ConstraintLayout {
    protected TextView questionTitle,isTrue,trueAnswer,analysis;
    protected CheckBox optionA;
    protected CheckBox optionB;
    protected CheckBox optionC;
    protected CheckBox optionD;
    protected Question mQuestion;
    protected UserAnswer mUserAnswer;
    protected int mIndex;
    protected int mTotalNum;
    public AnalysisMultipleView(Context context) {
        super(context);
    }

    public AnalysisMultipleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void initData(){
        questionTitle = this.findViewById(R.id.title);
        isTrue = this.findViewById(R.id.isTrue);
        trueAnswer = this.findViewById(R.id.trueAnswer);
        analysis = this.findViewById(R.id.analysisMes);
        optionA = this.findViewById(R.id.checkBox);
        optionB = this.findViewById(R.id.checkBox2);
        optionC = this.findViewById(R.id.checkBox3);
        optionD = this.findViewById(R.id.checkBox4);
        questionTitle.setText(mQuestion.getTitle());
        optionA.setText(mQuestion.getOptionA());
        optionB.setText(mQuestion.getOptionB());
        optionC.setText(mQuestion.getOptionC());
        optionD.setText(mQuestion.getOptionD());
        analysis.setText(mQuestion.getAnalysis());
        judge();
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
    }

    private void judge() {
        switch (mIndex) {
            case 1:
                //在第一页
                switch (mUserAnswer.getQuestion1()) {
                    case "12":
                        //正确答案为AB
                        trueAnswer.setText("A B");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionB,true);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "13":
                        //正确答案为AC
                        trueAnswer.setText("A C");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionC,true);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "14":
                        //正确答案为AD
                        trueAnswer.setText("A D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionD,true);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "23":
                        //正确答案为BC
                        trueAnswer.setText("B C");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionC,true);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "24":
                        //正确答案为BD
                        trueAnswer.setText("B D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionD,true);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "34":
                        //正确答案为CD
                        trueAnswer.setText("C D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(true);
                                setOptionTwo(optionD,optionC,true);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "123":
                        //正确答案为ABC
                        trueAnswer.setText("A B C");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionC,true);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "124":
                        //正确答案为ABD
                        trueAnswer.setText("A B D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionD,true);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "134":
                        //正确答案为ACD
                        trueAnswer.setText("A C D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionD,optionC,true);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "234":
                        //正确答案为BCD
                        trueAnswer.setText("B C D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(true);
                                setOptionThree(optionD,optionB,optionC,true);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "1234":
                        //正确答案为ABCD
                        trueAnswer.setText("A B C D");
                        switch (mUserAnswer.getUserAnswer1()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(true);
                                setOptionFour(optionA,optionB,optionC,optionD,true);
                                break;
                        }
                        break;
                }
                break;
            case 2:
                //在第二页
                switch (mUserAnswer.getQuestion2()) {
                    case "12":
                        //正确答案为AB
                        trueAnswer.setText("A B");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionB,true);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "13":
                        //正确答案为AC
                        trueAnswer.setText("A C");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionC,true);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "14":
                        //正确答案为AD
                        trueAnswer.setText("A D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionD,true);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "23":
                        //正确答案为BC
                        trueAnswer.setText("B C");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionC,true);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "24":
                        //正确答案为BD
                        trueAnswer.setText("B D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionD,true);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "34":
                        //正确答案为CD
                        trueAnswer.setText("C D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(true);
                                setOptionTwo(optionD,optionC,true);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "123":
                        //正确答案为ABC
                        trueAnswer.setText("A B C");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionC,true);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "124":
                        //正确答案为ABD
                        trueAnswer.setText("A B D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionD,true);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "134":
                        //正确答案为ACD
                        trueAnswer.setText("A C D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionD,optionC,true);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "234":
                        //正确答案为BCD
                        trueAnswer.setText("B C D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(true);
                                setOptionThree(optionD,optionB,optionC,true);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "1234":
                        //正确答案为ABCD
                        trueAnswer.setText("A B C D");
                        switch (mUserAnswer.getUserAnswer2()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(true);
                                setOptionFour(optionA,optionB,optionC,optionD,true);
                                break;
                        }
                        break;
                }
                break;
            case 3:
                //在第三页
                switch (mUserAnswer.getQuestion3()) {
                    case "12":
                        //正确答案为AB
                        trueAnswer.setText("A B");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionB,true);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "13":
                        //正确答案为AC
                        trueAnswer.setText("A C");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionC,true);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "14":
                        //正确答案为AD
                        trueAnswer.setText("A D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionD,true);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "23":
                        //正确答案为BC
                        trueAnswer.setText("B C");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionC,true);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "24":
                        //正确答案为BD
                        trueAnswer.setText("B D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionD,true);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "34":
                        //正确答案为CD
                        trueAnswer.setText("C D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(true);
                                setOptionTwo(optionD,optionC,true);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "123":
                        //正确答案为ABC
                        trueAnswer.setText("A B C");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionC,true);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "124":
                        //正确答案为ABD
                        trueAnswer.setText("A B D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionD,true);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "134":
                        //正确答案为ACD
                        trueAnswer.setText("A C D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionD,optionC,true);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "234":
                        //正确答案为BCD
                        trueAnswer.setText("B C D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(true);
                                setOptionThree(optionD,optionB,optionC,true);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "1234":
                        //正确答案为ABCD
                        trueAnswer.setText("A B C D");
                        switch (mUserAnswer.getUserAnswer3()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(true);
                                setOptionFour(optionA,optionB,optionC,optionD,true);
                                break;
                        }
                        break;
                }
                break;
            case 4:
                //在第四页
                switch (mUserAnswer.getQuestion4()) {
                    case "12":
                        //正确答案为AB
                        trueAnswer.setText("A B");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionB,true);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "13":
                        //正确答案为AC
                        trueAnswer.setText("A C");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionC,true);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "14":
                        //正确答案为AD
                        trueAnswer.setText("A D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionD,true);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "23":
                        //正确答案为BC
                        trueAnswer.setText("B C");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionC,true);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "24":
                        //正确答案为BD
                        trueAnswer.setText("B D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionD,true);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "34":
                        //正确答案为CD
                        trueAnswer.setText("C D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(true);
                                setOptionTwo(optionD,optionC,true);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "123":
                        //正确答案为ABC
                        trueAnswer.setText("A B C");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionC,true);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "124":
                        //正确答案为ABD
                        trueAnswer.setText("A B D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionD,true);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "134":
                        //正确答案为ACD
                        trueAnswer.setText("A C D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionD,optionC,true);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "234":
                        //正确答案为BCD
                        trueAnswer.setText("B C D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(true);
                                setOptionThree(optionD,optionB,optionC,true);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "1234":
                        //正确答案为ABCD
                        trueAnswer.setText("A B C D");
                        switch (mUserAnswer.getUserAnswer4()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(true);
                                setOptionFour(optionA,optionB,optionC,optionD,true);
                                break;
                        }
                        break;
                }
                break;
            case 5:
                //在第五页
                switch (mUserAnswer.getQuestion5()) {
                    case "12":
                        //正确答案为AB
                        trueAnswer.setText("A B");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionB,true);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "13":
                        //正确答案为AC
                        trueAnswer.setText("A C");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionC,true);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "14":
                        //正确答案为AD
                        trueAnswer.setText("A D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(true);
                                setOptionTwo(optionA,optionD,true);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "23":
                        //正确答案为BC
                        trueAnswer.setText("B C");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionC,true);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "24":
                        //正确答案为BD
                        trueAnswer.setText("B D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(true);
                                setOptionTwo(optionB,optionD,true);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "34":
                        //正确答案为CD
                        trueAnswer.setText("C D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(true);
                                setOptionTwo(optionD,optionC,true);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "123":
                        //正确答案为ABC
                        trueAnswer.setText("A B C");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionC,true);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "124":
                        //正确答案为ABD
                        trueAnswer.setText("A B D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionB,optionD,true);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "134":
                        //正确答案为ACD
                        trueAnswer.setText("A C D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(true);
                                setOptionThree(optionA,optionD,optionC,true);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "234":
                        //正确答案为BCD
                        trueAnswer.setText("B C D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(true);
                                setOptionThree(optionD,optionB,optionC,true);
                                break;
                            case "1234":
                                trueOrFalse(false);
                                setOptionFour(optionA,optionB,optionC,optionD,false);
                                break;
                        }
                        break;
                    case "1234":
                        //正确答案为ABCD
                        trueAnswer.setText("A B C D");
                        switch (mUserAnswer.getUserAnswer5()) {
                            case "1":
                                trueOrFalse(false);
                                setOptionOne(optionA);
                                break;
                            case "2":
                                trueOrFalse(false);
                                setOptionOne(optionB);
                                break;
                            case "3":
                                trueOrFalse(false);
                                setOptionOne(optionC);
                                break;
                            case "4":
                                trueOrFalse(false);
                                setOptionOne(optionD);
                                break;
                            case "12":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionB,false);
                                break;
                            case "13":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionC,false);
                                break;
                            case "14":
                                trueOrFalse(false);
                                setOptionTwo(optionA,optionD,false);
                                break;
                            case "23":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionC,false);
                                break;
                            case "24":
                                trueOrFalse(false);
                                setOptionTwo(optionB,optionD,false);
                                break;
                            case "34":
                                trueOrFalse(false);
                                setOptionTwo(optionD,optionC,false);
                                break;
                            case "123":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionC,false);
                                break;
                            case "124":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionB,optionD,false);
                                break;
                            case "134":
                                trueOrFalse(false);
                                setOptionThree(optionA,optionD,optionC,false);
                                break;
                            case "234":
                                trueOrFalse(false);
                                setOptionThree(optionD,optionB,optionC,false);
                                break;
                            case "1234":
                                trueOrFalse(true);
                                setOptionFour(optionA,optionB,optionC,optionD,true);
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

    public void setOptionOne(CheckBox option) {
        option.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        option.setCompoundDrawables(drawable, null, null, null);
    }

    public void setOptionTwo(CheckBox option1,CheckBox option2,boolean flag) {
        if (flag) {
            option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            option1.setCompoundDrawables(drawable, null, null, null);
            option2.setCompoundDrawables(drawable, null, null, null);
        } else {
            option1.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            option2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            option1.setCompoundDrawables(drawable, null, null, null);
            option2.setCompoundDrawables(drawable, null, null, null);
        }

    }

    public void setOptionThree(CheckBox option1,CheckBox option2,CheckBox option3,boolean flag) {
        if (flag) {
            option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            option1.setCompoundDrawables(drawable, null, null, null);
            option2.setCompoundDrawables(drawable, null, null, null);
            option3.setCompoundDrawables(drawable, null, null, null);
        } else {
            option1.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            option2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            option3.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            option1.setCompoundDrawables(drawable, null, null, null);
            option2.setCompoundDrawables(drawable, null, null, null);
            option3.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public void setOptionFour(CheckBox option1,CheckBox option2,CheckBox option3,CheckBox option4,boolean flag) {
        if (flag) {
            option1.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            option2.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            option3.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            option4.setBackgroundColor(android.graphics.Color.parseColor("#01AE66"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_true);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            option1.setCompoundDrawables(drawable, null, null, null);
            option2.setCompoundDrawables(drawable, null, null, null);
            option3.setCompoundDrawables(drawable, null, null, null);
            option4.setCompoundDrawables(drawable, null, null, null);
        } else {
            option1.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            option2.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            option3.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            option4.setBackgroundColor(android.graphics.Color.parseColor("#F12323"));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_false);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            option1.setCompoundDrawables(drawable, null, null, null);
            option2.setCompoundDrawables(drawable, null, null, null);
            option3.setCompoundDrawables(drawable, null, null, null);
            option4.setCompoundDrawables(drawable, null, null, null);
        }
    }

    /*
    接受题库数据
     */
    public void setData(Question question, UserAnswer userAnswer, int index, int totalNum){
        mQuestion = question;
        this.mUserAnswer = userAnswer;
        mIndex = index;
        mTotalNum = totalNum;
        initData();
    }

}
