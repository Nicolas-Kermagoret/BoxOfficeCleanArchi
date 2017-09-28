package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels;

import android.content.Context;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.common.viewmodels.BaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailsViewModel implements MovieDetailBaseViewModel, BaseViewModel {

    MovieEntityFull movie;
    Context context;

    public MovieDetailsViewModel(MovieEntityFull movie, Context context) {
        this.movie = movie;
        this.context = context;
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
            return context.getString(R.string.not_available);
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
        return context.getString(R.string.picture_path) + movie.getBackdropPath();
    }

    @Override
    public String getRunTime() {
        if (movie.getRuntime() == 0) {
            return context.getString(R.string.not_available);
        } else {
            return movie.getRuntime().toString() + context.getString(R.string.minutes);
        }
    }

    @Override
    public String getCountry() {
        if (movie.getProductionCountries().size() == 0) {
            return context.getString(R.string.not_available);
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
            return context.getString(R.string.not_available);
        } else {
            return movie.getOverview();
        }
    }

    @Override
    public String getGenres() {
        if (movie.getGenres().size() == 0) {
            return context.getString(R.string.not_available);
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
            return context.getString(R.string.not_available);
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
