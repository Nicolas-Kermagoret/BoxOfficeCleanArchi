package com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.net.RestApiImpl;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nicolaskermagoret on 07/02/2017.
 */

public class GetMovieList implements UseCase {

    private RestApi restApi;
    private UseCase.UseCaseListener listener;

    public GetMovieList(RestApi restApi){
        this.restApi = restApi;
    }

    @Override
    public void refreshResponse(String language, String search){

        this.restApi.getMoviesList(language, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResultEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchResultEntity result) {
                        if (listener != null) {
                            listener.setResponse(result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.endTask();
                        }
                    }
                });
    }

    @Override
    public void clean() {

    }

    @Override
    public void setUseCaseListener(UseCaseListener listener) {
        this.listener = listener;

    }

}
