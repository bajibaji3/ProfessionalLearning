package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.SpecialList;

import java.util.List;

/*
 *用于SpecialAnswerListActivity
 */
public interface ISpecialCallback {
    void loadSpecialList(List<SpecialList> specialLists);
    void initSpecial();
    void onError();
    void onEmpty();
    void onLoading();
}
