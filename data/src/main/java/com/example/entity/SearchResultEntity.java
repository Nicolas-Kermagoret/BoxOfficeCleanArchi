package com.example.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nicolaskermagoret on 06/02/2017.
 */

public class SearchResultEntity {

    @SerializedName("results")
    @Expose
    private List<MovieEntity> search = null;
    @SerializedName("total_results")
    @Expose
    private String totalResults;
    @SerializedName("total_pages")
    @Expose
    private String response;

    public List<MovieEntity> getSearch() {
        return search;
    }

    public void setSearch(List<MovieEntity> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
