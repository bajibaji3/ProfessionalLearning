package com.example.professionallearning.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.professionallearning.model.bean.VideoUri;
import com.example.professionallearning.ui.activity.MainActivity;
import com.example.professionallearning.R;
import com.example.professionallearning.ui.activity.VideoActivity;

import java.util.ArrayList;
import java.util.List;

/******
 * 如果继承自ListAdapter，会在后台对提交的列表数据进行差异比较
 * B站词汇记录第三部分
 *
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private List<VideoUri> uris = new ArrayList<>();
    private Context context;
    private Activity activity;
    private Update update;

    public VideoAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setUris(List<VideoUri> uris) {
        this.uris = uris;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.video_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        update = new Update(holder.currentTime, holder.videoView, holder.seekBar);
        update.handler.sendEmptyMessage(1);
        return holder;
//        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final VideoUri uri = uris.get(position);
        holder.videoTitle.setText(uris.get(position).getTitle());
        holder.videoView.setVideoURI(Uri.parse(uri.getUri()));
        holder.videoView.setOnPreparedListener(mediaPlayer -> {
            int total = holder.videoView.getDuration();
            update.updateTime(total, holder.totalTime);
            holder.seekBar.setMax(total);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                holder.videoView.setBackground(MainActivity.mContext.getResources().getDrawable(R.drawable.video1));
                SimpleTarget<Drawable> target = (new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.videoView.setBackground(resource);
                    }
                });
                Glide.with(activity).load(uri.getVideoFlag()).into(target);
            }
            mediaPlayer.setOnInfoListener((mediaPlayer1, i, i1) -> {
                if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                    holder.videoView.setBackgroundColor(Color.TRANSPARENT);
                return true;
            });
        });

        holder.videoView.requestFocus();

        holder.videoView.setOnCompletionListener(mediaPlayer -> holder.playOrStop.setImageResource(R.drawable.ic_play));

        holder.playOrStop.setVisibility(View.GONE);
        holder.currentTime.setVisibility(View.GONE);
        holder.totalTime.setVisibility(View.GONE);
        holder.seekBar.setVisibility(View.GONE);

        holder.videoView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector;

            @SuppressLint("HandlerLeak")
            Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    holder.playOrStop.setVisibility(View.GONE);
                    holder.currentTime.setVisibility(View.GONE);
                    holder.totalTime.setVisibility(View.GONE);
                    holder.seekBar.setVisibility(View.GONE);
                }
            };

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gestureDetector == null) {
                    gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            if (holder.seekBar.getVisibility() == View.VISIBLE) {
                                holder.playOrStop.setVisibility(View.GONE);
                                holder.currentTime.setVisibility(View.GONE);
                                holder.totalTime.setVisibility(View.GONE);
                                holder.seekBar.setVisibility(View.GONE);
                            } else {
                                holder.playOrStop.setVisibility(View.VISIBLE);
                                holder.currentTime.setVisibility(View.VISIBLE);
                                holder.totalTime.setVisibility(View.VISIBLE);
                                holder.seekBar.setVisibility(View.VISIBLE);
                            }
                            return true;
//                            return super.onDown(e);
                        }
                    });
                }

                //定时关闭控制器
                if (holder.seekBar.getVisibility() == View.VISIBLE) {
                    if (mHandler != null) {
                        mHandler.removeMessages(0);
                        mHandler.sendEmptyMessageDelayed(0, 4000);
                    }
                }
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        holder.playOrStop.setOnClickListener(view -> {
            if (holder.videoView.isPlaying()) {
                holder.videoView.pause();
                holder.playOrStop.setImageResource(R.drawable.ic_play);
            } else {
                holder.videoView.start();
                holder.playOrStop.setImageResource(R.drawable.ic_stop);
            }
        });

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (holder.videoView != null && holder.videoView.isPlaying())
                    holder.videoView.seekTo(progress);
            }
        });

        holder.videoTitle.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.mainActivity, VideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("videoUri",uri);
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView currentTime, totalTime, videoTitle;
        ImageView playOrStop;
        SeekBar seekBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            currentTime = itemView.findViewById(R.id.currentTime);
            totalTime = itemView.findViewById(R.id.totalTime);
            playOrStop = itemView.findViewById(R.id.playOrStop);
            seekBar = itemView.findViewById(R.id.seekBar);
            videoTitle = itemView.findViewById(R.id.videoTitle);
        }
    }
}

class Update {
    private TextView textView;
    private VideoView videoView;
    private SeekBar seekBar;

    Update(TextView textView, VideoView videoView, SeekBar seekBar) {
        this.textView = textView;
        this.videoView = videoView;
        this.seekBar = seekBar;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int position = videoView.getCurrentPosition();
                seekBar.setProgress(position);
                updateTime(position, textView);
                handler.sendEmptyMessageDelayed(1, 500);
            }
        }
    };

    @SuppressLint("DefaultLocale")
    void updateTime(int position, TextView textView) {
        int second = position / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String str = null;
        if (hh != 0) {
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }
}