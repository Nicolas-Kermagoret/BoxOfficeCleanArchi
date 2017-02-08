package com.example.net;

import com.example.entity.MovieEntity;
import com.example.entity.SearchResultEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nicolaskermagoret on 06/02/2017.
 */

public interface RestApi {

    @GET("3/search/movie?api_key=41ee868c27200b33a3ac13ba6796bd08")
    Call<SearchResultEntity> searchMovie(@Query("query") String film, @Query("language") String language);

    @GET("3/movie/{id}?api_key=41ee868c27200b33a3ac13ba6796bd08")
    Call<MovieEntity> getMovie(@Path("id") String id, @Query("language") String language);

    @GET("3/discover/movie?api_key=41ee868c27200b33a3ac13ba6796bd08&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Call<SearchResultEntity> getPopularMovie(@Query("language") String language);

    @GET("3/discover/movie?api_key=41ee868c27200b33a3ac13ba6796bd08&sort_by=vote_average.desc&include_adult=false&include_video=false&page=1&vote_count.gte=500")
    Call<SearchResultEntity> getBestRatedMovie(@Query("language") String language);

    @GET("3/genre/{genre}/movies?api_key=41ee868c27200b33a3ac13ba6796bd08&include_adult=false&sort_by=created_at.asc")
    Call<SearchResultEntity> getGenreMovie(@Path("genre") String genre, @Query("language") String language);

}
