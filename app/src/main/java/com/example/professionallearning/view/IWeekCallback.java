package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.WeekList;

import java.util.List;

/*
*用于WeekAnswerListActivity
 */
public interface IWeekCallback {
    void weekListLoaded(List<WeekList> weekLists);
    void initUserWeek();
    void onError();
    void onLoading();
    void onEmpty();
}
