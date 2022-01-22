package com.example.professionallearning.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;

public class MultipleChoiceView extends ConstraintLayout {
    protected TextView questionTitle;
    protected CheckBox optionA;
    protected CheckBox optionB;
    protected CheckBox optionC;
    protected CheckBox optionD;
    protected Question mQuestion;
    protected int mIndex;
    protected int mTotalNum;

    public MultipleChoiceView(Context context) {
        super(context);
    }

    public MultipleChoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void initData(){
        questionTitle=this.findViewById(R.id.itemTitle);
        optionA=this.findViewById(R.id.optionA);
        optionB=this.findViewById(R.id.optionB);
        optionC=this.findViewById(R.id.optionC);
        optionD=this.findViewById(R.id.optionD);
        questionTitle.setText(mQuestion.getTitle());
        optionA.setText(mQuestion.getOptionA());
        optionB.setText(mQuestion.getOptionB());
        optionC.setText(mQuestion.getOptionC());
        optionD.setText(mQuestion.getOptionD());
    }

    /*
   接受题库数据
    */
    public void setData(Question question,int index,int totalNum){
        mQuestion=question;
        mIndex=index;
        mTotalNum=totalNum;
        initData();
    }
}
