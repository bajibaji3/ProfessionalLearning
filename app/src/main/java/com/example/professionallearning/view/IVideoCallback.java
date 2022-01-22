package com.example.professionallearning.view;

import com.example.professionallearning.model.bean.Article;
import com.example.professionallearning.model.bean.VideoUri;

import java.util.List;

public interface IVideoCallback {
    void loadVideo(List<VideoUri> videoUris);
    void onError();
    void onLoading();
}
