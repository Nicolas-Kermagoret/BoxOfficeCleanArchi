package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels;

import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;

public interface MovieDetailBaseViewModel {

    MovieEntityFull getMovieDetails();

    String getTitle();

    String getReleaseDate();

    String getBackDropPath();

    String getRunTime();

    String getCountry();

    float getRating();

    String getOverview();

    String getGenres();

    String getBudget();

    String getOriginalLanguage();
}
