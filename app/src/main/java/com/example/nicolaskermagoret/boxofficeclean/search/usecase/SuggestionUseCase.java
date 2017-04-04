package com.example.nicolaskermagoret.boxofficeclean.search.usecase;

import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionResultEntity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SuggestionUseCase implements SuggestionBaseUseCase {

    private RestApi restApi;
    private SuggestionBaseUseCase.UseCaseListener listener;

    public SuggestionUseCase(RestApi restApi){
        this.restApi = restApi;
    }

    @Override
    public void getSuggestions(String query) {
        this.restApi.getSuggestionList(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SuggestionResultEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SuggestionResultEntity result) {
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
        this.listener=listener;
    }
}
