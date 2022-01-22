package com.example.professionallearning.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Performance;
import com.example.professionallearning.presenter.IPerformancePresenter;
import com.example.professionallearning.presenter.impl.PerformancePresenterImpl;
import com.example.professionallearning.ui.activity.AnswerTypeActivity;
import com.example.professionallearning.R;
import com.example.professionallearning.ui.activity.CollectedActivity;
import com.example.professionallearning.ui.activity.LearnFormActivity;
import com.example.professionallearning.ui.activity.LearnPerformanceActivity;
import com.example.professionallearning.ui.activity.UserMesActivity;
import com.example.professionallearning.view.IAnswerFragmentCallback;

import java.util.Objects;

public class AnswerFragment extends Fragment implements IAnswerFragmentCallback {
    private ConstraintLayout mConstraintLayout;
    private TextView netMes,totalScoreTv,rankingTv,headTv,nameTv;
    private UserDao userDao;
    private ImageView wifi;
    private String mPhone;
    private IPerformancePresenter perPresenter;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.answer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button buttonAnswer,buttonScore,buttonForm,buttonCollect;

        buttonAnswer = Objects.requireNonNull(getView()).findViewById(R.id.buttonAnswer);
        buttonScore = getView().findViewById(R.id.button3);
        buttonForm = getView().findViewById(R.id.button4);
        buttonCollect = getView().findViewById(R.id.button21);
        headTv = getView().findViewById(R.id.textView45);
        nameTv = getView().findViewById(R.id.textView52);
        ImageView imageEnter = getView().findViewById(R.id.imageView5);
        mConstraintLayout = getView().findViewById(R.id.constraintLayout24);
        netMes  = getView().findViewById(R.id.textView68);
        wifi = getView().findViewById(R.id.imageView10);
        totalScoreTv = getView().findViewById(R.id.textView4);
        rankingTv = getView().findViewById(R.id.textView53);
        userDao = new UserDao(getContext());
        mPhone = userDao.findUser().get(0).getPhone();

        setState(State.NONE);
        initPresenter();
        loadData();

        buttonAnswer.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), AnswerTypeActivity.class);
            startActivity(intent);
        });

        buttonScore.setOnClickListener(View -> {
            Intent intent=new Intent(getActivity(), LearnPerformanceActivity.class);
            startActivity(intent);
        });

        buttonForm.setOnClickListener(View -> {
            Intent intent=new Intent(getActivity(), LearnFormActivity.class);
            startActivity(intent);
        });

        buttonCollect.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), CollectedActivity.class);
            startActivity(intent);
        });

        imageEnter.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), UserMesActivity.class);
            startActivity(intent);
        });

        wifi.setOnClickListener(view -> onRetryClick());
    }

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (perPresenter != null) {
            loadData();
        }
    }

    private void setState(State state) {
        this.currentState = state;
        loadStateView();
    }

    private void loadStateView() {
        if (currentState == State.SUCCESS) {
            mConstraintLayout.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if(currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("网络错误，请点击重试");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        //创建Presenter
        perPresenter = new PerformancePresenterImpl();
        perPresenter.registerAnswerFragmentCallback(this);
    }

    private void loadData() {
        //找总积分
        perPresenter.findPerInAnswerFragment(mPhone);
    }

    @Override
    public void loadPerformance(Performance performance) {
        setState(State.SUCCESS);
        String str = Objects.requireNonNull(getActivity()).getResources().getString(R.string.rank_str);
        String rankStr = "";
        totalScoreTv.setText(performance.getTotalScore());
        if (Integer.parseInt(performance.getTotalScore()) < 100) {
            rankStr = String.format(str,"没有段位");
        } else if (Integer.parseInt(performance.getTotalScore()) < 200 && Integer.parseInt(performance.getTotalScore()) >= 100) {
            rankStr = String.format(str,"一心一意");
        } else if (Integer.parseInt(performance.getTotalScore()) < 300 && Integer.parseInt(performance.getTotalScore()) >= 200) {
            rankStr = String.format(str,"再接再厉");
        } else if (Integer.parseInt(performance.getTotalScore()) < 400 && Integer.parseInt(performance.getTotalScore()) >= 300) {
            rankStr = String.format(str,"三省吾身");
        } else if (Integer.parseInt(performance.getTotalScore()) < 500 && Integer.parseInt(performance.getTotalScore()) >= 400) {
            rankStr = String.format(str,"名扬四海");
        } else if (Integer.parseInt(performance.getTotalScore()) < 600 && Integer.parseInt(performance.getTotalScore()) >= 500) {
            rankStr = String.format(str,"学富五车");
        } else if (Integer.parseInt(performance.getTotalScore()) < 700 && Integer.parseInt(performance.getTotalScore()) >= 600) {
            rankStr = String.format(str,"六韬三略");
        } else if (Integer.parseInt(performance.getTotalScore()) < 800 && Integer.parseInt(performance.getTotalScore()) >= 700) {
            rankStr = String.format(str,"七步才华");
        } else if (Integer.parseInt(performance.getTotalScore()) < 900 && Integer.parseInt(performance.getTotalScore()) >= 800) {
            rankStr = String.format(str,"才高八斗");
        } else if (Integer.parseInt(performance.getTotalScore()) < 1000 && Integer.parseInt(performance.getTotalScore()) >= 900) {
            rankStr = String.format(str,"九天揽月");
        } else if (Integer.parseInt(performance.getTotalScore()) >= 1000) {
            rankStr = String.format(str,"十年磨剑");
        }
        rankingTv.setText(rankStr);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setState(State.SUCCESS);
        totalScoreTv.setText("0");
        rankingTv.setText("没有段位");
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (perPresenter != null) {
            perPresenter.unRegisterAnswerFragmentCallback(this);
        }
    }

    /******
     *设置按下返回键直接返回
     */
    @Override
    public void onResume() {
        super.onResume();
        String name = userDao.findUser().get(0).getName();
        nameTv.setText(name);
        headTv.setText(name.substring(name.length()-2));
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                Objects.requireNonNull(getActivity()).finish();
                return true;
            }
            return false;
        });
    }

}
