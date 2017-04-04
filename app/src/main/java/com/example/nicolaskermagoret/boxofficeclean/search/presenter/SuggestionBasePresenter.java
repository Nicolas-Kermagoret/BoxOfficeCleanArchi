package com.example.nicolaskermagoret.boxofficeclean.search.presenter;

import com.example.nicolaskermagoret.boxofficeclean.common.presenters.BasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.search.views.SearchBaseView;

public interface SuggestionBasePresenter extends BasePresenter<SearchBaseView> {

    public void getSuggestions(String query);

}
