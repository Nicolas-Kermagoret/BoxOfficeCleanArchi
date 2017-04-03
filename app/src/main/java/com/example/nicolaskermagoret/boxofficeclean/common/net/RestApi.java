package com.example.nicolaskermagoret.boxofficeclean.common.net;


import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;

import io.reactivex.Observable;

/**
 * Created by nicolaskermagoret on 06/02/2017.
 */

public interface RestApi {

    Observable<SearchResultEntity> getMoviesList(String language, String search);

    Observable<MovieEntityFull> getMovieDetails(String id, String language);

    void setConnectivity(boolean connected);

}
