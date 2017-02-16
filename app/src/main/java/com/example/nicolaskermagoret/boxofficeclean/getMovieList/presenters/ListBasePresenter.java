package com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters;

import com.example.nicolaskermagoret.boxofficeclean.common.presenters.BasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.BaseListView;

public interface ListBasePresenter extends BasePresenter<BaseListView> {

    void refreshResponse(String search);

}
