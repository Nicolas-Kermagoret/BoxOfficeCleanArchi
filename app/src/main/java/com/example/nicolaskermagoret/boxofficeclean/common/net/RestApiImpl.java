package com.example.nicolaskermagoret.boxofficeclean.common.net;

import com.example.nicolaskermagoret.boxofficeclean.common.services.WebService;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nicolaskermagoret on 06/02/2017.
 */

public class RestApiImpl implements RestApi {

    public static final String BASE_URL = "https://api.themoviedb.org/";

    private RestApi restApi;
    private WebService webService;

    public RestApiImpl() {
        initApi();
    }

    private void initApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.webService = retrofit.create(WebService.class);
    }


    @Override
    public Observable<SearchResultEntity> getMoviesList(final String language, final String search) {
        return Observable.create(new ObservableOnSubscribe<SearchResultEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResultEntity> e) throws Exception {

                String searchSplit[] = search.split(" ");
                Call<SearchResultEntity> webCall = null;
                if (searchSplit.length == 1) {
                    if (searchSplit[0].equals("popular")) {
                        webCall = webService.getPopularMovie(language);
                    } else if (searchSplit[0].equals("rated")) {
                        webCall = webService.getBestRatedMovie(language);
                    }
                } else {
                    if (searchSplit[0].equals("genre")) {
                        webCall = webService.getGenreMovie(searchSplit[1], language);
                    } else if (searchSplit[0].equals("search")) {
                        String search = "";
                        int size = searchSplit.length;
                        for (int i = 1; i < size; i++) {
                            search += searchSplit[i] + " ";
                        }
                        webCall = webService.searchMovie(search, language);
                    }
                }
                try {
                    final Response<SearchResultEntity> webResponse = webCall.execute();
                    final SearchResultEntity response = webResponse.body();

                    e.onNext(response);
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    @Override
    public Observable<MovieEntityFull> getMovieDetails(final String id, final String language) {
        return Observable.create(new ObservableOnSubscribe<MovieEntityFull>() {
            @Override
            public void subscribe(ObservableEmitter<MovieEntityFull> e) throws Exception {


                Call<MovieEntityFull> webCall = webService.getMovie(language, id);
                try {
                    final Response<MovieEntityFull> webResponse = webCall.execute();
                    final MovieEntityFull response = webResponse.body();

                    e.onNext(response);
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }


}
