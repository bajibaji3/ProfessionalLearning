package com.example.professionallearning.ui.fragment;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.professionallearning.dao.ArticleDao;
import com.example.professionallearning.presenter.IArticlePresenter;
import com.example.professionallearning.presenter.impl.ArticlePresenterImpl;
import com.example.professionallearning.view.IArticleCallback;
import com.example.professionallearning.adapter.MyAdapter;
import com.example.professionallearning.model.bean.Article;
import com.example.professionallearning.R;

import java.util.List;
import java.util.Objects;

public class LearnFragment extends Fragment implements IArticleCallback {
    private IArticlePresenter presenter;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ConstraintLayout mConstraintLayout;
    private TextView netMes;
    private ArticleDao mArticleDao;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.learn_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.videoRecyclerView);
        mConstraintLayout = getView().findViewById(R.id.cons2);
        netMes = getView().findViewById(R.id.textView70);
        refreshLayout = getView().findViewById(R.id.refreshLayout);
        mArticleDao = new ArticleDao(getContext());
        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

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
        } else if(currentState == State.LOADING) {
            mConstraintLayout.setVisibility(View.GONE);
            netMes.setVisibility(View.VISIBLE);
            netMes.setText("加载中。。。");
        } else if(currentState == State.NONE) {
            mConstraintLayout.setVisibility(View.GONE);
            netMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        presenter = new ArticlePresenterImpl();
        presenter.registerGetArticleCallback(this);
    }
    private void loadData() {
        presenter.getArticle();
    }

    @Override
    public void loadArticle(List<Article> articles) {
        setState(State.SUCCESS);
        mArticleDao.deleteArticle();
        mArticleDao.insertArticle(articles);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        recyclerView.setAdapter(myAdapter);
        myAdapter.setArticles(articles);
    }

    @Override
    public void onError() {
        setState(State.SUCCESS);
        if (mArticleDao.findArticle().size() == 0) {
            Toast.makeText(getContext(), "刷新失败，请检查网络设置", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView.setAdapter(myAdapter);
            myAdapter.setArticles(mArticleDao.findArticle());
            Toast.makeText(getContext(), "刷新失败，请检查网络设置", Toast.LENGTH_SHORT).show();
        }
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
            presenter.unRegisterGetArticleCallback(this);
        }
    }

}
