package com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels;

import com.example.nicolaskermagoret.boxofficeclean.common.viewmodels.BaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;

public class ResponseViewModel implements ResponseBaseViewModel, BaseViewModel {

    private SearchResultEntity result;

    public ResponseViewModel(SearchResultEntity result) {
        this.result = result;
    }

    public ResponseViewModel() {
        
    }

    @Override
    public SearchResultEntity getMovieList() {
        return this.result;
    }

    @Override
    public String getId() {
        return null;
    }
}
