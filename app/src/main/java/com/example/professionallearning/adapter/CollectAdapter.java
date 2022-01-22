package com.example.professionallearning.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professionallearning.R;
import com.example.professionallearning.model.HttpApi;
import com.example.professionallearning.model.bean.Article;
import com.example.professionallearning.model.bean.UserArticle;
import com.example.professionallearning.model.bean.VideoUri;
import com.example.professionallearning.ui.activity.CollectedActivity;
import com.example.professionallearning.ui.activity.LearnDetailActivity;
import com.example.professionallearning.ui.activity.VideoActivity;
import com.example.professionallearning.util.RetrofitManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.MyViewHolder> {
    private List<UserArticle> collect=new ArrayList<>();
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private HttpApi api = retrofit.create(HttpApi.class);
    private String TAG = "CollectAdapter";


    public void setCollect(List<UserArticle> collect) {
        this.collect = collect;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.collect_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserArticle userArticle = collect.get(position);
        holder.textViewTitle.setText(userArticle.getArticleTitle());
        holder.collectTime.setText(userArticle.getDate());
        holder.itemView.setOnClickListener(view -> {
            if (userArticle.getArticleType() == 1) {
                loadArticle(userArticle.getArticleId(),holder);
            } else {
                loadVideo(userArticle.getArticleId(),holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collect.size();
    }

    private void loadArticle(int articleId,MyViewHolder holder) {
        Call<ResponseBody> task = api.findArticleById(articleId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getArticle-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Article article = gson.fromJson(response.body().string(), Article.class);
                        Intent intent=new Intent(CollectedActivity.collectedActivity, LearnDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("poem",article);
                        intent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
            }
        });
    }

    private void loadVideo(int articleId,MyViewHolder holder) {
        Call<ResponseBody> task = api.findVideoById(articleId);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                Log.d(TAG, "getArticle-->" + response.code());
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        VideoUri videoUri = gson.fromJson(response.body().string(), VideoUri.class);
                        Intent intent=new Intent(CollectedActivity.collectedActivity, VideoActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("videoUri",videoUri);
                        intent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,@Nullable Throwable t) {
                Log.d(TAG, "onFailure-->" + t.toString());
            }
        });
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle,collectTime;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView91);
            collectTime = itemView.findViewById(R.id.textView93);
        }
    }
}
