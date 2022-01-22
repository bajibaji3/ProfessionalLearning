package com.example.professionallearning.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professionallearning.R;
import com.example.professionallearning.adapter.CollectAdapter;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.UserArticle;
import com.example.professionallearning.presenter.ICollectPresenter;
import com.example.professionallearning.presenter.impl.CollectPresenterImpl;
import com.example.professionallearning.view.ICollectActivityCall;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CollectedActivity extends AppCompatActivity implements ICollectActivityCall {
    public static CollectedActivity collectedActivity;
    private TextView netMes;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mConstraintLayout;
    private ImageView wifi;
    private List<UserArticle> mUserArticles = new ArrayList<>();
    private UserArticle tempCollect;
    private String mPhone;
    private boolean backFlag = false;
    private CollectAdapter mAdapter;
    private ICollectPresenter presenter;
    private State currentState = State.NONE;

    public enum State {
        NONE, LOADING, ERROR, SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected);
        collectedActivity = this;
        mRecyclerView = findViewById(R.id.recyclerView);
        mConstraintLayout = findViewById(R.id.constraintLayout25);
        netMes = findViewById(R.id.textView92);
        wifi = findViewById(R.id.imageView17);
        UserDao userDao = new UserDao(this);
        mPhone = userDao.findUser().get(0).getPhone();
        mAdapter = new CollectAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setState(State.NONE);
        initPresenter();
        loadData();

        wifi.setOnClickListener(view -> onRetryClick());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                backFlag = true;
                tempCollect = mUserArticles.get(viewHolder.getAdapterPosition());
                presenter.deleteUserCollect(mUserArticles.get(viewHolder.getAdapterPosition()).getId());
            }

            //在滑动的时候，画出浅灰色背景和垃圾桶图标，增强删除的视觉效果

            Drawable icon = ContextCompat.getDrawable(getBaseContext(),R.drawable.ic_delete);
            Drawable background = new ColorDrawable(Color.LTGRAY);
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

                int iconLeft,iconRight,iconTop,iconBottom;
                int backTop,backBottom,backLeft,backRight;
                backTop = itemView.getTop();
                backBottom = itemView.getBottom();
                iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
                iconBottom = iconTop + icon.getIntrinsicHeight();
                if (dX > 0) {
                    backLeft = itemView.getLeft();
                    backRight = itemView.getLeft() + (int)dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconLeft = itemView.getLeft() + iconMargin ;
                    iconRight = iconLeft + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                } else if (dX < 0){
                    backRight = itemView.getRight();
                    backLeft = itemView.getRight() + (int)dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconRight = itemView.getRight()  - iconMargin;
                    iconLeft = iconRight - icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                } else {
                    background.setBounds(0,0,0,0);
                    icon.setBounds(0,0,0,0);
                }
                background.draw(c);
                icon.draw(c);
            }
        }).attachToRecyclerView(mRecyclerView);



    }

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
            wifi.setVisibility(View.GONE);
            netMes.setVisibility(View.GONE);
        }
    }

    private void initPresenter() {
        presenter = new CollectPresenterImpl();
        presenter.registerCollectedActivityCall(this);
    }

    private void loadData() {
        presenter.findAllCollect(mPhone);
    }

    @Override
    public void loadCollect(List<UserArticle> userArticles) {
        setState(State.SUCCESS);
        mUserArticles = userArticles;
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setCollect(userArticles);
        if (backFlag) {
            Snackbar.make(findViewById(R.id.collectBody),"删除了一个收藏记录",Snackbar.LENGTH_LONG)
                    .setAction("撤销", v -> presenter.addUserCollected(tempCollect.getArticleId(),tempCollect.getUserId(),tempCollect.getArticleTitle(),tempCollect.getArticleType(),tempCollect.getDate()))
                    .show();
        }
        backFlag = false;
    }

    @Override
    public void deleteUserCollect() {
        loadData();
    }

    @Override
    public void addUserCollect() {
        loadData();
    }

    @Override
    public void onGetEmpty() {
        setState(State.SUCCESS);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onLoading() {
        setState(State.LOADING);
    }

    @Override
    public void onError() {
        setState(State.ERROR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        //取消注册
        if (presenter != null) {
            presenter.unRegisterCollectedActivityCall(this);
        }
    }
}
