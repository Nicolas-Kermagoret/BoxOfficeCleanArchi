package com.example.nicolaskermagoret.boxofficeclean.common.services;

import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionResultEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    @GET("3/discover/movie?sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Call<SearchResultEntity> getPopularMovie(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/search/movie?")
    Call<SearchResultEntity> searchMovie(@Query("api_key") String apiKey, @Query("query") String film, @Query("language") String language);

    @GET("3/movie/{id}?")
    Call<MovieEntityFull> getMovie(@Path("id") String id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/discover/movie?sort_by=vote_average.desc&include_adult=false&include_video=false&page=1&vote_count.gte=500")
    Call<SearchResultEntity> getBestRatedMovie(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/genre/{genre}/movies?include_adult=false&sort_by=created_at.asc")
    Call<SearchResultEntity> getGenreMovie(@Path("genre") String genre, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/search/keyword?page=1")
    Call<SuggestionResultEntity> getSuggestion(@Query("api_key") String apiKey, @Query("query") String query);

}
