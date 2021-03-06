package com.example.nicolaskermagoret.boxofficeclean.common.net;

import android.content.Context;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.common.services.WebService;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionResultEntity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nicolaskermagoret on 06/02/2017.
 */

public class RestApiImpl implements RestApi {

    public static final String BASE_URL = "https://api.themoviedb.org/";
    private static final int CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private static final int TIME_OUT_SECOND = 10;

    private WebService webService;
    private boolean connected;
    private Context context;

    private String api_key = "";

    public RestApiImpl(File cacheDir, Context context) {
        this.context = context;
        this.api_key = context.getString(R.string.api_key);
        initApi(cacheDir);
    }

    private void initApi(File cacheDir) {
        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        final Request original = chain.request();
                        final Request.Builder builder = original.newBuilder();

//                        if (!connected) {
//                        builder.cacheControl(CacheControl.FORCE_CACHE);
//                        }

                        return chain.proceed(builder.build());
                    }
                })
                .connectTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .cache(new Cache(cacheDir, CACHE_MAX_SIZE))
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        this.webService = retrofit.create(WebService.class);
    }


    @Override
    public Observable<SearchResultEntity> getMoviesList(final String language, final String search) {
        return Observable.create(new ObservableOnSubscribe<SearchResultEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResultEntity> subscriber) throws Exception {

                String searchSplit[] = search.split(" ");
                Call<SearchResultEntity> webCall = null;
                if (searchSplit.length == 1) {
                    if (searchSplit[0].equals(context.getString(R.string.category_popular))) {
                        webCall = webService.getPopularMovie(api_key, language);
                    } else if (searchSplit[0].equals(context.getString(R.string.category_rated))) {
                        webCall = webService.getBestRatedMovie(api_key, language);
                    }
                } else {
                    if (searchSplit[0].equals(context.getString(R.string.category_genre))) {
                        webCall = webService.getGenreMovie(searchSplit[1], api_key, language);
                    } else if (searchSplit[0].equals(context.getString(R.string.category_search))) {
                        String search = "";
                        int size = searchSplit.length;
                        for (int i = 1; i < size; i++) {
                            search += searchSplit[i] + " ";
                        }
                        webCall = webService.searchMovie(api_key, search, language);
                    }
                }
                try {
                    final Response<SearchResultEntity> webResponse = webCall.execute();
                    final SearchResultEntity response = webResponse.body();

                    subscriber.onNext(response);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onComplete();
            }
        });
    }

    @Override
    public Observable<MovieEntityFull> getMovieDetails(final String id, final String language) {
        return Observable.create(new ObservableOnSubscribe<MovieEntityFull>() {
            @Override
            public void subscribe(ObservableEmitter<MovieEntityFull> e) throws Exception {


                Call<MovieEntityFull> webCall = webService.getMovie(id, api_key, language);
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

    @Override
    public Observable<SuggestionResultEntity> getSuggestionList(final String query) {
        return Observable.create(new ObservableOnSubscribe<SuggestionResultEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SuggestionResultEntity> subscriber) throws Exception {


                Call<SuggestionResultEntity> webCall = webService.getSuggestion(api_key, query);
                try {
                    final Response<SuggestionResultEntity> webResponse = webCall.execute();
                    final SuggestionResultEntity response = webResponse.body();

                    subscriber.onNext(response);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onComplete();
            }
        });
    }

    @Override
    public void setConnectivity(boolean connected) {
        this.connected = connected;
    }

}
