package com.example.nicolaskermagoret.boxofficeclean.search.presenter;

import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.search.usecase.SuggestionBaseUseCase;
import com.example.nicolaskermagoret.boxofficeclean.search.viewModel.SuggestionViewModel;
import com.example.nicolaskermagoret.boxofficeclean.search.viewModel.SuggestionsBaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.search.views.SearchBaseView;

public class SuggestionPresenter implements SuggestionBasePresenter, SuggestionBaseUseCase.UseCaseListener {

    private SuggestionBaseUseCase useCase;

    private SearchBaseView view;

    public SuggestionPresenter(SuggestionBaseUseCase useCase) {
        this.useCase = useCase;
        this.useCase.setUseCaseListener(this);
    }

    @Override
    public void onViewAttached(SearchBaseView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void getSuggestions(String query) {
        this.useCase.getSuggestions(query);
    }

    @Override
    public void setResponse(SuggestionResultEntity result) {
        final SuggestionsBaseViewModel viewModel = new SuggestionViewModel(result);
        this.view.setSuggestions(viewModel);
    }

    @Override
    public void error(String message) {

    }

    @Override
    public void endTask() {

    }
}
