package com.example.professionallearning.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Question;
import com.example.professionallearning.util.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.List;

public class FillBlankView extends ConstraintLayout {
    protected TextView questionTitle;
    protected EditText input;
    protected Button sure;
    protected Question mQuestion;
    protected int mIndex;
    protected int mTotalNum;
    protected Activity activity;
    private SpannableStringBuilder spannableStringBuilder;
    private ClickableSpan clickableSpan;
    private ForegroundColorSpan colorSpan;
    public int start;
    public int textNum = 0;
    private int index = 0;
    private List<Integer> nums = new ArrayList<>();
    String TAG="mTag";

    public FillBlankView(Context context) {
        super(context);
    }

    public FillBlankView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void initData() {
        questionTitle=this.findViewById(R.id.itemTitle);
        input=this.findViewById(R.id.input);
        sure=this.findViewById(R.id.sure);
        questionTitle.setText(mQuestion.getTitle());
    }

    /*
    接受题库数据
     */
    public void setData(Question question,int index,int totalNum,Activity activity){
        mQuestion=question;
        start=Integer.parseInt(mQuestion.getStart());
        mIndex=index;
        mTotalNum=totalNum;
        this.activity=activity;
        initData();
        initSpannableStringBuilder();
    }

    private void initSpannableStringBuilder() {
        getTextNum(input);
        spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.clear();
        spannableStringBuilder.append(mQuestion.getTitle());
        clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                input.setVisibility(View.VISIBLE);
                sure.setVisibility(View.VISIBLE);
//                Log.d(TAG, "字符个数：" + textNum);
                StringBuilder builder = new StringBuilder();
                for (int i = start; i < start + textNum; i++)
                    builder.append(spannableStringBuilder.charAt(i));

                if (spannableStringBuilder.charAt(start) == '_')
                    input.setText("");
                else
                    input.setText(builder.toString());
                input.requestFocus();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, 0);
            }
        };
        colorSpan = new ForegroundColorSpan(Color.parseColor("#B99783"));
        spannableStringBuilder.setSpan(clickableSpan, start, start+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        questionTitle.setMovementMethod(LinkMovementMethod.getInstance());
        questionTitle.setText(spannableStringBuilder);

        sure.setOnClickListener(view -> {
            Log.d(TAG, input.getText().toString());
            nums.add(textNum);
            index++;
//                    Log.d(TAG,"字符个数："+textNum);
            if (index == 1)
                spannableStringBuilder.delete(start, start+4);
            else
                spannableStringBuilder.delete(start, start + nums.get(index - 2));
            spannableStringBuilder.insert(start, input.getText().toString());
            spannableStringBuilder.setSpan(clickableSpan, start, start + textNum, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(colorSpan, start, start + textNum, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            questionTitle.setText(spannableStringBuilder);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        });

        SoftKeyBoardListener.setListener(activity, onSoftKeyBoardChangeListener);
    }

    public void getTextNum(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.d(TAG,editable.length()+"333333333333333");
                if (editable.length() == 0)
                    sure.setEnabled(false);
                else
                    sure.setEnabled(true);
                textNum = editable.length();
            }
        });
    }

    /**
     * 软键盘弹出收起监听
     */
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener = new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
        @Override
        public void keyBoardShow(int height) {

        }
        @Override
        public void keyBoardHide(int height) {
            input.setVisibility(View.GONE);
            sure.setVisibility(View.GONE);
        }
    };
}
