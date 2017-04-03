package com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase;

import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nicolaskermagoret on 07/02/2017.
 */

public class GetMovieList implements GetMovieListBaseUseCase {

    private RestApi restApi;
    private GetMovieListBaseUseCase.UseCaseListener listener;

    private boolean isEmpty = true;

    public GetMovieList(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public void refreshResponse(String language, String search) {

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
                        isEmpty = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.error(e.getMessage());
                            listener.endTask(isEmpty);
                        }
                        isEmpty = true;
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.endTask(isEmpty);
                        }
                        isEmpty = true;
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
