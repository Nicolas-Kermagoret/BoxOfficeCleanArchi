package com.example.nicolaskermagoret.boxofficeclean.search.usecase;

import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionResultEntity;

public interface SuggestionBaseUseCase {

    interface UseCaseListener {
        void setResponse(SuggestionResultEntity result);

        void error(String message);

        void endTask();
    }

    void getSuggestions(String query);

    void clean();

    void setUseCaseListener(SuggestionBaseUseCase.UseCaseListener listener);
}
