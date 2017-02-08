package com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;

public class ResponseViewModel implements ResponseBaseViewModel {

    private SearchResultEntity result;

    public ResponseViewModel(SearchResultEntity result){
        this.result = result;
    }

    @Override
    public SearchResultEntity getMovieList() {
        return this.result;
    }
}
