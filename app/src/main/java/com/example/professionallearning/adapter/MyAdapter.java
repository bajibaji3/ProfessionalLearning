package com.example.professionallearning.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professionallearning.ui.activity.LearnDetailActivity;
import com.example.professionallearning.ui.activity.MainActivity;
import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.Article;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Article> articles=new ArrayList<>();

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.cell_normal,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Article article=articles.get(position);
        holder.textViewTitle.setText(article.getTitle());
        holder.textViewFirstSentence.setText(article.getFirstSentence());
        holder.textViewAuthor.setText(article.getAuthor());
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.mainActivity,LearnDetailActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("poem",article);
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle,textViewFirstSentence,textViewAuthor;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.title);
            textViewFirstSentence=itemView.findViewById(R.id.firstSentence);
            textViewAuthor=itemView.findViewById(R.id.author);
        }
    }
}
