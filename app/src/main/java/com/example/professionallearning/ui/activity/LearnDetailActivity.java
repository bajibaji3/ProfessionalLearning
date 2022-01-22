package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.CommentAdapter;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Article;
import com.example.professionallearning.model.bean.Comment;
import com.example.professionallearning.presenter.ICommentPresenter;
import com.example.professionallearning.presenter.impl.CommentPresenterImpl;
import com.example.professionallearning.util.SoftKeyBoardListener;
import com.example.professionallearning.view.ILearnDetailCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LearnDetailActivity extends AppCompatActivity implements ILearnDetailCallback {
    private ICommentPresenter presenter;
    private ConstraintLayout mConstraintLayout;
    private TextView netMes,releaseTv,cancelTv;
    private Button releaseBt;
    private EditText releaseEt;
    private ImageView wifi,reviewIv,collectIv;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private int articleId;
    private Article mArticle;
    private String mName;
    private String mPhone;
    private boolean isCollected = false;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_detail);
        TextView title = findViewById(R.id.title);
        TextView author = findViewById(R.id.author);
        TextView detail = findViewById(R.id.detail);
        releaseTv = findViewById(R.id.textView88);
        cancelTv = findViewById(R.id.textView87);
        releaseBt = findViewById(R.id.button20);
        releaseEt = findViewById(R.id.editText5);
        reviewIv = findViewById(R.id.imageView12);
        collectIv = findViewById(R.id.imageView13);
        mRecyclerView = findViewById(R.id.commentRecycler);
        mConstraintLayout = findViewById(R.id.cons3);
        netMes = findViewById(R.id.textView75);
        wifi = findViewById(R.id.imageView15);
        UserDao userDao = new UserDao(this);
        mName = userDao.findUser().get(0).getName();
        mPhone = userDao.findUser().get(0).getPhone();
        mAdapter = new CommentAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle=this.getIntent().getExtras();
        if (bundle != null) {
            Article poem= (Article) bundle.getSerializable("poem");
            if (poem != null) {
                mArticle = poem;
                articleId = poem.getId();
            }
        }
        title.setText(mArticle.getTitle());
        author.setText(mArticle.getAuthor());
        detail.setText(mArticle.getDetail());

        setState(State.NONE);
        initPresenter();
        loadData();

        releaseBt.setOnClickListener(view -> {
            reviewIv.setVisibility(View.GONE);
            collectIv.setVisibility(View.GONE);
            releaseBt.setVisibility(View.GONE);
            releaseEt.setVisibility(View.VISIBLE);
            releaseTv.setVisibility(View.VISIBLE);
            cancelTv.setVisibility(View.VISIBLE);
            releaseEt.requestFocus();
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(releaseEt, 0);
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String comment = releaseEt.getText().toString().trim();
                releaseTv.setEnabled(!comment.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        releaseEt.addTextChangedListener(textWatcher);

        releaseTv.setOnClickListener(view -> {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);
            Comment comment = new Comment();
            comment.setReviewer(mName);
            comment.setComment(releaseEt.getText().toString());
            comment.setDate(dateStr);
            presenter.addCommentInArticle(comment,articleId);
        });

        cancelTv.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            reviewIv.setVisibility(View.VISIBLE);
            collectIv.setVisibility(View.VISIBLE);
            releaseBt.setVisibility(View.VISIBLE);
            releaseEt.setVisibility(View.GONE);
            releaseTv.setVisibility(View.GONE);
            cancelTv.setVisibility(View.GONE);
        });

        collectIv.setOnClickListener(view -> {
            if (isCollected) {
                //若已经收藏，则点击后是取消收藏
                presenter.cancelCollected(articleId,mPhone);
            } else {
                //若没有收藏，则点击后是收藏
                Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = formatter.format(date);
                presenter.addCollected(articleId,mPhone,mArticle.getTitle(),dateStr);
            }
        });

        wifi.setOnClickListener(view -> onRetryClick());

        SoftKeyBoardListener.setListener(this, onSoftKeyBoardChangeListener);
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
            reviewIv.setVisibility(View.VISIBLE);
            collectIv.setVisibility(View.VISIBLE);
            releaseBt.setVisibility(View.VISIBLE);
            releaseEt.setVisibility(View.GONE);
            releaseTv.setVisibility(View.GONE);
            cancelTv.setVisibility(View.GONE);
        }
    };

    private void onRetryClick() {
        //网络错误，点击了重试
        //重新加载数据
        if (presenter != null) {
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
        presenter = new CommentPresenterImpl();
        presenter.registerLearnDetailCallback(this);
    }
    private void loadData() {
        presenter.getCommentInLe(articleId);
    }

    @Override
    public void loadComment(List<Comment> comments) {
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setComments(comments);
        mAdapter.setActivity(this);
        presenter.findArticleCollect(articleId,mPhone);
    }

    @Override
    public void addComment() {
        loadData();
    }

    @Override
    public void loadUserArticle() {
        setState(State.SUCCESS);
        isCollected = true;
        collectIv.setImageResource(R.drawable.ic_collected);
    }

    @Override
    public void cancelCollect() {
        setState(State.SUCCESS);
        isCollected = false;
        collectIv.setImageResource(R.drawable.ic_collect);
    }

    @Override
    public void addCollect() {
        setState(State.SUCCESS);
        isCollected = true;
        collectIv.setImageResource(R.drawable.ic_collected);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onGetEmpty() {
        presenter.findArticleCollect(articleId,mPhone);
    }

    @Override
    public void onCollectEmpty() {
        setState(State.SUCCESS);
        isCollected = false;
        collectIv.setImageResource(R.drawable.ic_collect);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (presenter != null) {
            presenter.unRegisterLearnDetailCallback(this);
        }
    }
}
