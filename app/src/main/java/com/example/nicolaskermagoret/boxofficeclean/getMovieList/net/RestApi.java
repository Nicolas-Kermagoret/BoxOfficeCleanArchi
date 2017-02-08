package com.example.nicolaskermagoret.boxofficeclean.getMovieList.net;



import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;

import io.reactivex.Observable;

/**
 * Created by nicolaskermagoret on 06/02/2017.
 */

public interface RestApi {

    Observable<SearchResultEntity> getMoviesList(String language, String search);

}
