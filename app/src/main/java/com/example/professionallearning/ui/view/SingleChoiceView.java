package com.example.professionallearning.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;


public class SingleChoiceView extends ConstraintLayout implements View.OnClickListener{
    protected TextView questionTitle;
    protected RadioGroup radioGroup;
    protected RadioButton option1;
    protected RadioButton option2;
    protected RadioButton option3;
    protected RadioButton option4;
    protected Question mQuestion;
    protected int mIndex;
    protected int mTotalNum;
    protected Context mContext;

    public SingleChoiceView(Context context) {
        super(context);
        mContext=context;
    }

    public SingleChoiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }


    /**
     * 初始化数据
     */
    protected void initData(){
        questionTitle=this.findViewById(R.id.itemTitle);
        radioGroup=this.findViewById(R.id.radioGroup);
        option1=this.findViewById(R.id.option1);
        option2=this.findViewById(R.id.option2);
        option3=this.findViewById(R.id.option3);
        option4=this.findViewById(R.id.option4);
        questionTitle.setText(mQuestion.getTitle());
        option1.setText(mQuestion.getOptionA());
        option2.setText(mQuestion.getOptionB());
        option3.setText(mQuestion.getOptionC());
        option4.setText(mQuestion.getOptionD());
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


    @Override
    public void onClick(View view) {

    }

}
