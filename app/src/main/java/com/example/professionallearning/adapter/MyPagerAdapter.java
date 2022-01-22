package com.example.professionallearning.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.ui.view.FillBlankView;
import com.example.professionallearning.ui.view.MultipleChoiceView;
import com.example.professionallearning.ui.view.SingleChoiceView;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private List<Question> mList;
    private Context mContext;
    protected Activity activity;

    public MyPagerAdapter(Context context,List<Question> list,Activity activity) {
        this.mContext=context;
        this.mList = list;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Question question=mList.get(position);
        View view= switchQuestionWidget(question, position + 1, mList.size());
        container.addView(view);
//        initView(view,position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private View switchQuestionWidget(Question question, int index, int totalNum) {
        int type=Integer.parseInt(question.getType());
//        Log.d(TAG,"!!!!!!!!!!!!题目类型："+type);
        if (type==0){
            FillBlankView mWidget;
            mWidget=(FillBlankView) LayoutInflater.from(mContext).inflate(R.layout.fill_blank_item,null);
            mWidget.setData(question,index,totalNum,activity);
            return mWidget;
        } else if (type==1){
            SingleChoiceView mWidget;
            mWidget = (SingleChoiceView) LayoutInflater.from(mContext).inflate(R.layout.single_choice_item, null);
            mWidget.setData(question, index, totalNum);
            return mWidget;
        } else {
            MultipleChoiceView mWidget;
            mWidget = (MultipleChoiceView) LayoutInflater.from(mContext).inflate(R.layout.multiple_choice_item,null);
            mWidget.setData(question,index,totalNum);
            return mWidget;
        }
    }
}
