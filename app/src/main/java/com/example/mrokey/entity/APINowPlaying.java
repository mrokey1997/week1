package com.example.mrokey.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APINowPlaying {
    @SerializedName("results")
    @Expose
    private List<APIMovie> movies = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("dates")
    @Expose
    private APIDate dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    public List<APIMovie> getMovies() {
        return movies;
    }

    public void setResults(List<APIMovie> results) {
        this.movies = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public APIDate getDates() {
        return dates;
    }

    public void setDates(APIDate dates) {
        this.dates = dates;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
