package com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters;

import android.content.Context;


import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase.UseCase;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseBaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.ListFragment;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.BaseView;
import com.laimiux.rxnetwork.RxNetwork;


import java.util.Locale;

import rx.Subscription;

public class ListPresenter implements Presenter, UseCase.UseCaseListener {

    private Context context;
    private UseCase useCase;
    private boolean connected;

    private BaseView view;

    private Subscription connectivityChangeSubscription;

    public ListPresenter(Context context, UseCase useCase){
        this.context = context;
        this.connected = RxNetwork.getConnectivityStatus(this.context);
        this.useCase = useCase;
        this.useCase.setUseCaseListener(this);
    }

    @Override
    public void onViewAttached(ListFragment view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        this.context = null;
    }

    @Override
    public void setResponse(SearchResultEntity result) {
        this.view.loading(false);
        final ResponseBaseViewModel responseViewModel = new ResponseViewModel(result);
        this.view.setResponse(responseViewModel);
    }

    @Override
    public void error(String message) {
        if (this.view != null) {
            this.view.loading(false);
        }
    }

    @Override
    public void endTask() {

    }

    @Override
    public void refreshResponse(String search) {
        this.view.loading(true);
        this.useCase.refreshResponse(Locale.getDefault().getLanguage(), search);
    }

//    private void subscribeConnectivityChange() {
//        connectivityChangeSubscription = RxNetwork.stream(this.context)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean connected) {
//                        if (connected) {
//                            ListPresenter.this.refreshResponse();
//                        } else {
//                            if (baseView != null) {
//                                baseView.onNoNetwork();
//                            }
//                        }
//
//                        ListPresenter.this.connected = connected;
//                    }
//                });
//    }
//
//    private void unSubscribeConnectivityChange() {
//        if (this.connectivityChangeSubscription != null) {
//            this.connectivityChangeSubscription.unsubscribe();
//            this.connectivityChangeSubscription = null;
//        }
//    }
}
