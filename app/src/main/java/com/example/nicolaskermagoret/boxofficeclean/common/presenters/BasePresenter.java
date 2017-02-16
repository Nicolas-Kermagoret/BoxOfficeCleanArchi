package com.example.nicolaskermagoret.boxofficeclean.common.presenters;

public interface BasePresenter<T> {

    void onViewAttached(T view);

    void onViewDetached();

    void onDestroy();

}
