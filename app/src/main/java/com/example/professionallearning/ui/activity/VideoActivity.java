package com.example.professionallearning.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.professionallearning.R;
import com.example.professionallearning.adapter.CommentAdapter;
import com.example.professionallearning.dao.UserDao;
import com.example.professionallearning.model.bean.Comment;
import com.example.professionallearning.model.bean.VideoUri;
import com.example.professionallearning.presenter.IVideoCommentPresenter;
import com.example.professionallearning.presenter.impl.VideoCommentPresenterImpl;
import com.example.professionallearning.util.SoftKeyBoardListener;
import com.example.professionallearning.view.IVideoDetailCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VideoActivity extends AppCompatActivity implements IVideoDetailCallback {
    private ConstraintLayout videoLayout,mConstraintLayout;
    private VideoView videoView;
    private IVideoCommentPresenter presenter;
    private TextView currentTime,totalTime,videoTitle,releaseTv,cancelTv,netMes;
    private Button releaseBt;
    private EditText releaseEt;
    private ImageView playOrStop,fullScreen,wifi,reviewIv,collectIv;
    private RecyclerView mRecyclerView;
    private SeekBar seekBar;
    private VideoUri mVideoUri;
    private boolean isCollected = false;
    private int articleId;
    private String mName;
    private String mPhone;
    private CommentAdapter mAdapter;
    private boolean isFullScreen=false;
    private State currentState = State.NONE;
    public enum State {
        NONE,LOADING,ERROR,SUCCESS
    }

    @SuppressLint({"ClickableViewAccessibility", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            VideoUri videoUri= (VideoUri) bundle.getSerializable("videoUri");
            if (videoUri != null) {
                mVideoUri = videoUri;
                articleId = videoUri.getId();
            }
        }
        UserDao userDao = new UserDao(this);
        mName = userDao.findUser().get(0).getName();
        mPhone = userDao.findUser().get(0).getPhone();
        mAdapter = new CommentAdapter();
        videoTitle.setText(mVideoUri.getTitle());
        videoView.setVideoURI(Uri.parse(mVideoUri.getUri()));
        videoView.setOnPreparedListener(mediaPlayer -> {
            int total=videoView.getDuration();
            updateTime(total,totalTime);
            //不在这里调用这一句上面的handle里的方法不会执行
            handler.sendEmptyMessage(1);
            seekBar.setMax(total);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                SimpleTarget<Drawable> target = (new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        videoView.setBackground(resource);
                    }
                });
                Glide.with(this).load(mVideoUri.getVideoFlag()).into(target);
            }
            mediaPlayer.setOnInfoListener((mediaPlayer1, i, i1) -> {
                if (i==MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                    videoView.setBackgroundColor(Color.TRANSPARENT);
                return true;
            });
        });

        videoView.requestFocus();

        videoView.setOnCompletionListener(mediaPlayer -> playOrStop.setImageResource(R.drawable.ic_play));

        playOrStop.setVisibility(View.GONE);
        currentTime.setVisibility(View.GONE);
        totalTime.setVisibility(View.GONE);
        seekBar.setVisibility(View.GONE);
        fullScreen.setVisibility(View.GONE);

        videoView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector;

            @SuppressLint("HandlerLeak")
            Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    playOrStop.setVisibility(View.GONE);
                    currentTime.setVisibility(View.GONE);
                    totalTime.setVisibility(View.GONE);
                    seekBar.setVisibility(View.GONE);
                    fullScreen.setVisibility(View.GONE);
                }
            };

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gestureDetector==null){
                    gestureDetector=new GestureDetector(getBaseContext(),new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e) {
                            if (seekBar.getVisibility()==View.VISIBLE){
                                playOrStop.setVisibility(View.GONE);
                                currentTime.setVisibility(View.GONE);
                                totalTime.setVisibility(View.GONE);
                                seekBar.setVisibility(View.GONE);
                                fullScreen.setVisibility(View.GONE);
                            } else {
                                playOrStop.setVisibility(View.VISIBLE);
                                currentTime.setVisibility(View.VISIBLE);
                                totalTime.setVisibility(View.VISIBLE);
                                seekBar.setVisibility(View.VISIBLE);
                                fullScreen.setVisibility(View.VISIBLE);
                            }
                            return true;
//                            return super.onDown(e);
                        }
                    });
                }

                //定时关闭控制器
                if (seekBar.getVisibility() == View.VISIBLE) {
                    if (mHandler != null) {
                        mHandler.removeMessages(0);
                        mHandler.sendEmptyMessageDelayed(0, 4000);
                    }
                }
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        playOrStop.setOnClickListener(view -> {
            if (videoView.isPlaying()){
                videoView.pause();
                playOrStop.setImageResource(R.drawable.ic_play);
            } else {
                videoView.start();
                playOrStop.setImageResource(R.drawable.ic_stop);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekBar.getProgress();
                if (videoView!=null&&videoView.isPlaying())
                    videoView.seekTo(progress);
            }
        });

        /*
         * 切换横竖屏
         */
        fullScreen.setOnClickListener(view -> {
            if (isFullScreen) {
                //此时是全屏
                reviewIv.setVisibility(View.VISIBLE);
                collectIv.setVisibility(View.VISIBLE);
                releaseBt.setVisibility(View.VISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreen.setImageResource(R.drawable.ic_full_screen);
            } else {
                //此时是竖屏
                reviewIv.setVisibility(View.GONE);
                collectIv.setVisibility(View.GONE);
                releaseBt.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullScreen.setImageResource(R.drawable.ic_small_screen);
            }
        });


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
                presenter.addCollected(articleId,mPhone,mVideoUri.getTitle(),dateStr);
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
        presenter = new VideoCommentPresenterImpl();
        presenter.registerVideoActivityCallback(this);
    }
    private void loadData() {
        //先根据视频的id查找对于该视频的评论
        presenter.getComment(articleId);
    }

    @Override
    public void loadComment(List<Comment> comments) {
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setComments(comments);
        mAdapter.setActivity(this);
        //查找该视频是否被该用户收藏
        presenter.findVideoCollect(articleId,mPhone);
    }

    @Override
    public void addComment() {
        //评论成功
        loadData();
    }

    @Override
    public void loadUserArticle() {
        //该视频已经被该用户收藏
        setState(State.SUCCESS);
        isCollected = true;
        collectIv.setImageResource(R.drawable.ic_collected);
    }

    @Override
    public void cancelCollect() {
        //该用户收藏取消了收藏
        setState(State.SUCCESS);
        isCollected = false;
        collectIv.setImageResource(R.drawable.ic_collect);
    }

    @Override
    public void addCollect() {
        //该用户收藏收藏了该视频
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
        //该视频下面没有评论
        presenter.findVideoCollect(articleId,mPhone);
    }

    @Override
    public void onCollectEmpty() {
        //该用户之前没有收藏该视频
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
            presenter.unRegisterVideoActivityCallback(this);
        }
    }

    private void initUI() {
        videoLayout = findViewById(R.id.videoLayout);
        mConstraintLayout = findViewById(R.id.constraintLayout27);
        videoView = findViewById(R.id.videoView);
        currentTime = findViewById(R.id.currentTime);
        totalTime = findViewById(R.id.totalTime);
        videoTitle = findViewById(R.id.videoTitle);
        mRecyclerView = findViewById(R.id.recyclerView2);
        releaseTv = findViewById(R.id.textView96);
        cancelTv = findViewById(R.id.textView95);
        releaseBt = findViewById(R.id.button22);
        releaseEt = findViewById(R.id.editText6);
        wifi = findViewById(R.id.imageView20);
        netMes = findViewById(R.id.textView97);
        reviewIv = findViewById(R.id.imageView18);
        collectIv = findViewById(R.id.imageView19);
        playOrStop = findViewById(R.id.playOrStop);
        fullScreen = findViewById(R.id.fullScreen);
        seekBar = findViewById(R.id.seekBar);
    }

    private void setVideoViewScale(int height) {
        ViewGroup.LayoutParams layoutParams=videoView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = height;
        videoView.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1=videoLayout.getLayoutParams();
        layoutParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams1.height = height;
        videoLayout.setLayoutParams(layoutParams1);
    }

    /*********
     * 处理返回键
     */
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            finish();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                int current=videoView.getCurrentPosition();
                seekBar.setProgress(current);
                updateTime(current,currentTime);
                handler.sendEmptyMessageDelayed(1, 500);
            }
        }
    };

    @SuppressLint("DefaultLocale")
    public void updateTime(int position, TextView textView) {
        int second = position/1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String str;
        if(hh!=0){
            str = String.format("%02d:%02d:%02d",hh,mm,ss);
        }else {
            str = String.format("%02d:%02d",mm,ss);
        }
        textView.setText(str);
    }

    /**********
     * 监听屏幕方向改变
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /*
         * 当屏幕方向为横屏的时候
         */
        if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT);
            isFullScreen=true;
        } else {
            setVideoViewScale(ViewGroup.LayoutParams.WRAP_CONTENT);
            isFullScreen=false;
        }
    }
}
