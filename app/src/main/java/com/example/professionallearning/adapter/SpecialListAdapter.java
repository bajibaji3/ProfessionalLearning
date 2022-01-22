package com.example.professionallearning.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professionallearning.R;
import com.example.professionallearning.model.bean.SpecialList;
import com.example.professionallearning.ui.activity.SpecialAnalysisActivity;
import com.example.professionallearning.ui.activity.SpecialAnswerActivity;
import com.example.professionallearning.ui.activity.SpecialAnswerListActivity;

import java.util.ArrayList;
import java.util.List;

public class SpecialListAdapter extends RecyclerView.Adapter<SpecialListAdapter.MyViewHolder> {
    private List<SpecialList> specialLists=new ArrayList<>();

    public void setSpecialLists(List<SpecialList> specialLists) {
        this.specialLists = specialLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.special_answer_list_item,parent,false);
        return new SpecialListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SpecialList specialList=specialLists.get(position);
        holder.title.setText(specialList.getSpecialTitle());
        switch (specialList.getIsAnswer()) {
            case "0":
                //此专题从未回答完过
                switch (specialList.getIsOut()) {
                    case "0":
                        //第一次回答
                        holder.analysis.setVisibility(View.GONE);
                        holder.begin.setText("开始答题");
                        break;
                    case "1":
                        //中途退出
                        holder.analysis.setVisibility(View.GONE);
                        holder.begin.setText("继续答题");
                        break;
                }
                break;
            case "1":
                //此专题回答过，但未满分
                switch (specialList.getIsOut()) {
                    case "0":
                        //重新第一次回答
                        holder.analysis.setVisibility(View.VISIBLE);
                        holder.begin.setText("重新答题");
                        break;
                    case "1":
                        //重新回答时中途退出
                        holder.analysis.setVisibility(View.VISIBLE);
                        holder.begin.setText("继续答题");
                        break;
                }
                break;
            case "2":
                //此专题回答过，并且满分
                holder.analysis.setVisibility(View.VISIBLE);
                holder.begin.setVisibility(View.GONE);
                break;
        }
        holder.begin.setOnClickListener(view -> {
            Intent intent=new Intent(SpecialAnswerListActivity.specialAnswerListActivity, SpecialAnswerActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("specialTitle",specialList.getSpecialTitle());
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
            SpecialAnswerListActivity.specialAnswerListActivity.finish();
        });
        holder.analysis.setOnClickListener(View -> {
            Intent intent=new Intent(SpecialAnswerListActivity.specialAnswerListActivity, SpecialAnalysisActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("specialTitle1",specialList.getSpecialTitle());
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
            SpecialAnswerListActivity.specialAnswerListActivity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return specialLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button begin,analysis;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            begin = itemView.findViewById(R.id.begin);
            analysis = itemView.findViewById(R.id.analysis);
        }
    }
}
