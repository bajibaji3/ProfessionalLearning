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
import com.example.professionallearning.model.bean.UserAnswer;
import com.example.professionallearning.ui.view.AnalysisFillView;
import com.example.professionallearning.ui.view.AnalysisMultipleView;
import com.example.professionallearning.ui.view.AnalysisSingleView;

import java.util.List;

public class AnalysisPagerAdapter extends PagerAdapter {
    private List<Question> mList;
    private UserAnswer mUserAnswer;
//    String TAG="mTag";
    private Context mContext;
    protected Activity activity;

    public AnalysisPagerAdapter(Context context, List<Question> list, UserAnswer userAnswer, Activity activity) {
        this.mContext = context;
        this.mList = list;
        this.mUserAnswer = userAnswer;
        this.activity = activity;
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
        Question question = mList.get(position);
        View view = switchQuestionWidget(question, position + 1, mList.size());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private View switchQuestionWidget(Question question, int index, int totalNum) {
        int type = Integer.parseInt(question.getType());
//        Log.d(TAG,"!!!!!!!!!!!!题目类型："+type);
        if (type == 0){
            AnalysisFillView mWidget;
            mWidget = (AnalysisFillView) LayoutInflater.from(mContext).inflate(R.layout.analysis_fill_item,null);
            mWidget.setData(question,mUserAnswer,index,totalNum,activity);
            return mWidget;
        } else if (type == 1){
            AnalysisSingleView mWidget;
            mWidget = (AnalysisSingleView) LayoutInflater.from(mContext).inflate(R.layout.analysis_single_item, null);
            mWidget.setData(question,mUserAnswer, index, totalNum);
            return mWidget;
        } else {
            AnalysisMultipleView mWidget;
            mWidget = (AnalysisMultipleView) LayoutInflater.from(mContext).inflate(R.layout.analysis_multiple_item,null);
            mWidget.setData(question,mUserAnswer,index,totalNum);
            return mWidget;
        }
    }
}
