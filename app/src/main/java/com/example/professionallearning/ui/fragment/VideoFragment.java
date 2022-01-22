package com.example.professionallearning.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.VideoAdapter;
import com.example.professionallearning.model.bean.VideoUri;
import com.example.professionallearning.presenter.IVideoPresenter;
import com.example.professionallearning.presenter.impl.VideoPresenterImpl;
import com.example.professionallearning.view.IVideoCallback;

import java.util.List;
import java.util.Objects;

public class VideoFragment extends Fragment implements IVideoCallback {
    private SwipeRefreshLayout refreshLayout;
    private ConstraintLayout mConstraintLayout;
    private TextView netMes;
    private ImageView wifi;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private IVideoPresenter presenter;
    private State currentState = State.NONE;

    public enum State {
        NONE, LOADING, ERROR, SUCCESS
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.videoRecyclerView);
        mConstraintLayout = getView().findViewById(R.id.constraintLayout26);
        netMes = getView().findViewById(R.id.textView94);
        wifi = getView().findViewById(R.id.imageView14);
        refreshLayout = getView().findViewById(R.id.frameLayout);
        videoAdapter = new VideoAdapter(getContext(), getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setState(State.NONE);
        initPresenter();
        loadData();

        refreshLayout.setOnRefreshListener(this::loadData);
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
        } else if (currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if (currentState == State.ERROR) {
            mConstraintLayout.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("网络错误，请点击重试");
        } else if (currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            netMes.setVisibility(View.GONE);
            wifi.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        presenter = new VideoPresenterImpl();
        presenter.registerGetVideoCallback(this);
    }

    private void loadData() {
        presenter.getVideoUri();
    }

    @Override
    public void loadVideo(List<VideoUri> videoUris) {
        setState(State.SUCCESS);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.setUris(videoUris);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
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
        if (presenter != null) {
            presenter.unRegisterGetVideoCallback(this);
        }
    }

    //        VideoUri videoUri1 = new VideoUri();
    //        videoUri1.setUri("android.resource://" + Objects.requireNonNull(getActivity()).getPackageName() + "/" + R.raw.one);
    //        videoUri1.setVideoFlag("1");
    //        mUris.add(videoUri1);
}
