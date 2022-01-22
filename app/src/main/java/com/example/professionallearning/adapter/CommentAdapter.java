package com.example.professionallearning.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<Comment> mComments = new ArrayList<>();
    private Activity mActivity;

    public void setComments(List<Comment> comments) {
        Log.d("mLog","comments-->" + comments.get(0).getComment());
        this.mComments = comments;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.comment_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Comment comment = mComments.get(position);
        holder.nameTv.setText(comment.getReviewer());
        holder.contentTv.setText(comment.getComment());
        holder.timeTv.setText(comment.getDate());
        if (comment.getReplyNum() == 0) {
            holder.replyTv.setText("回复");
        } else {
            holder.replyTv.setText(String.format(mActivity.getResources().getString(R.string.reply_num),
                    comment.getReplyNum()));
        }

        if (comment.getPraiseNum() == 0) {
            holder.praiseTv.setVisibility(View.GONE);
            holder.praiseIv.setImageResource(R.drawable.ic_praise);
        } else {
            holder.praiseTv.setVisibility(View.VISIBLE);
            holder.praiseTv.setText(String.valueOf(comment.getPraiseNum()));
            holder.praiseIv.setImageResource(R.drawable.ic_praised);
        }

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,contentTv,timeTv,replyTv,praiseTv;
        ImageView praiseIv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.textView81);
            contentTv = itemView.findViewById(R.id.textView82);
            timeTv = itemView.findViewById(R.id.textView83);
            replyTv = itemView.findViewById(R.id.textView84);
            praiseTv = itemView.findViewById(R.id.textView85);
            praiseIv = itemView.findViewById(R.id.imageView16);
        }
    }
}
