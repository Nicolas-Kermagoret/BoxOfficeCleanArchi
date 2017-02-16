package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels;

import com.example.nicolaskermagoret.boxofficeclean.common.viewmodels.BaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailsViewModel implements MovieDetailBaseViewModel, BaseViewModel {

    MovieEntityFull movie;

    public MovieDetailsViewModel(MovieEntityFull movie) {
        this.movie = movie;
    }

    @Override
    public MovieEntityFull getMovieDetails() {
        return this.movie;
    }

    @Override
    public String getTitle() {
        return movie.getTitle();
    }

    @Override
    public String getReleaseDate() {
        String year = movie.getYear();
        if (year.equals("")) {
            year = "1789-07-14";
        }

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dt.parse(year);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

        return df.format(date);
    }

    @Override
    public String getBackDropPath() {
        return "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();
    }

    @Override
    public String getRunTime() {
        if (movie.getRuntime() == null) {
            return "N/A";
        } else {
            return movie.getRuntime().toString() + "min";
        }
    }

    @Override
    public String getCountry() {
        if (movie.getProductionCountries().size() == 0) {
            return "N/A";
        } else {
            return movie.getProductionCountries().get(0).getName();
        }
    }

    @Override
    public float getRating() {
        return Float.parseFloat(movie.getVoteAverage().toString()) / 2;
    }

    @Override
    public String getOverview() {
        if (movie.getOverview() == null) {
            return "N/A";
        } else {
            return movie.getOverview();
        }
    }

    @Override
    public String getGenres() {
        if (movie.getGenres().size() == 0) {
            return "N/A";
        } else {
            String genreList = "";
            for (MovieEntityFull.Genre genre : movie.getGenres()) {
                genreList += genre.getName() + ", ";
            }

            return genreList.substring(0, genreList.length() - 2);
        }
    }

    @Override
    public String getBudget() {
        if (movie.getBudget() == 0) {
            return "N/A";
        } else {
            return movie.getBudget().toString() + "$";
        }
    }

    @Override
    public String getOriginalLanguage() {
        Locale loc = new Locale(movie.getOriginalLanguage());
        return loc.getDisplayLanguage(loc);
    }

    @Override
    public String getId() {
        return movie.getId();
    }
}
