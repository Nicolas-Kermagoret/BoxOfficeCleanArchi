package com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;

/**
 * Created by nicolaskermagoret on 07/02/2017.
 */

public interface UseCase {

    interface UseCaseListener {
        void setResponse(SearchResultEntity result);

        void error(String message);

        void endTask();
    }

    void refreshResponse(String language, String search);

    void clean();

    void setUseCaseListener(UseCaseListener listener);
}
