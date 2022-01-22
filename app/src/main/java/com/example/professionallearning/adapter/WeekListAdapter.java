package com.example.professionallearning.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professionallearning.R;
import com.example.professionallearning.ui.activity.WeekAnswerActivity;
import com.example.professionallearning.ui.activity.WeekAnswerListActivity;
import com.example.professionallearning.model.bean.WeekList;

import java.util.ArrayList;
import java.util.List;

public class WeekListAdapter extends RecyclerView.Adapter<WeekListAdapter.MyViewHolder> {
    private List<WeekList> weekLists=new ArrayList<>();

    public void setWeekLists(List<WeekList> weekLists) {
        this.weekLists = weekLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.week_answer_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final WeekList weekList=weekLists.get(position);
        holder.week.setText(weekList.getWeek());
        holder.isAnswer.setText(weekList.getIsAnswer());
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(WeekAnswerListActivity.weekAnswerActivity, WeekAnswerActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("week",weekList.getWeek());
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
            WeekAnswerListActivity.weekAnswerActivity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return weekLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView week,isAnswer;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            week=itemView.findViewById(R.id.textWeek);
            isAnswer=itemView.findViewById(R.id.isAnswer);
        }
    }
}
