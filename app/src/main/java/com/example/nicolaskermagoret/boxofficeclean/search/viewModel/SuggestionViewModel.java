package com.example.nicolaskermagoret.boxofficeclean.search.viewModel;

import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionResultEntity;

public class SuggestionViewModel implements SuggestionsBaseViewModel {

    private SuggestionResultEntity result;

    public SuggestionViewModel(SuggestionResultEntity result) {
        this.result = result;
    }

    @Override
    public SuggestionResultEntity getSuggestionList() {
        return this.result;
    }
}
