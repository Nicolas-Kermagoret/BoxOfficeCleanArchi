package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.usecase;

import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;

public interface GetMovieDetailBaseUseCase {

    interface UseCaseListener {
        void setResponse(MovieEntityFull result);

        void error(String message);

        void endTask();
    }

    void refreshResponse(String language, String search);

    void clean();

    void setUseCaseListener(UseCaseListener listener);

}
