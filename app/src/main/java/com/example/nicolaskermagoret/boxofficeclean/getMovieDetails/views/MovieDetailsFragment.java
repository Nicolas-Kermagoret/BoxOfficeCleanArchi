package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.views;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApiImpl;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.presenters.DetailsBasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.presenters.MovieDetailsPresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.usecase.GetMovieDetailBaseUseCase;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.usecase.GetMovieDetails;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels.MovieDetailBaseViewModel;
import com.squareup.picasso.Picasso;


public class MovieDetailsFragment extends Fragment implements BaseDetailsView {

    private View view;
    private DetailsBasePresenter presenter;
    private String id;
    private MovieDetailBaseViewModel viewModel;

    private TextView releaseDate;
    private ImageView poster;
    private RatingBar rating;
    private TextView runtime;
    private TextView country;
    private TextView overview;
    private TextView genre;
    private TextView budget;
    private TextView language;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public static Fragment newInstance() {
        return new MovieDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        this.id = args.getString("id");
        final RestApi restApi = new RestApiImpl(getContext().getCacheDir());
        final GetMovieDetailBaseUseCase useCase = new GetMovieDetails(restApi);

        this.presenter = new MovieDetailsPresenter(getContext(), useCase);
        this.presenter.onViewAttached(this);
        this.presenter.refreshResponse(this.id);

        this.view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        this.bindView();

        return view;
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void setResponse(MovieDetailBaseViewModel viewModel) {
        this.viewModel = viewModel;
        this.fillView();
    }

    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    public void onNoNetwork() {

    }

    private void bindView() {
        releaseDate = (TextView) view.findViewById(R.id.release_detail);
        poster = (ImageView) getActivity().findViewById(R.id.poster_detail);
        rating = (RatingBar) view.findViewById(R.id.rating_detail);
        runtime = (TextView) view.findViewById(R.id.runtime_detail);
        country = (TextView) view.findViewById(R.id.country_detail);
        overview = (TextView) view.findViewById(R.id.overview_detail);
        genre = (TextView) view.findViewById(R.id.genre_detail);
        budget = (TextView) view.findViewById(R.id.budget_detail);
        language = (TextView) view.findViewById(R.id.language_detail);
        collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar);
    }

    private void fillView() {

        collapsingToolbarLayout.setTitle(viewModel.getTitle());
        releaseDate.setText(viewModel.getReleaseDate());
        Picasso.with(getContext())
                .load(viewModel.getBackDropPath())
                .placeholder(R.drawable.cinema)
                .into(poster);
        runtime.setText(viewModel.getRunTime());
        country.setText(viewModel.getCountry());
        rating.setRating(viewModel.getRating());
        overview.setText(viewModel.getOverview());
        genre.setText(viewModel.getGenres());
        budget.setText(viewModel.getBudget());
        language.setText(viewModel.getOriginalLanguage());

    }

}
