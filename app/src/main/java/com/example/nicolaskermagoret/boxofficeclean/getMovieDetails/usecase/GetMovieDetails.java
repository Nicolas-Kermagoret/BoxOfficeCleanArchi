package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.usecase;

import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetMovieDetails implements GetMovieDetailBaseUseCase {

    private RestApi restApi;
    private GetMovieDetailBaseUseCase.UseCaseListener listener;

    public GetMovieDetails(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public void refreshResponse(String language, String id) {

        this.restApi.getMovieDetails(id, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntityFull>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieEntityFull result) {
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
