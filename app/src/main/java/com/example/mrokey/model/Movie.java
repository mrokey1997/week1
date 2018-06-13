package com.example.mrokey.model;

public class Movie {
    private int     vote_count;
    private int     id;
    private boolean video;
    private double   vote_average;
    private String  title;
    private String  poster_path;
    private String  original_title;
    private String  backdrop_path;
    private String  overview;
    private String  release_date;

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Movie() {
//        this.vote_count = 0;
//        this.id = 0;
//        this.video = false;
//        this.vote_average = 0.0;
//        this.title = "";
//        this.poster_path = "";
//        this.original_title = "";
//        this.backdrop_path = "";
//        this.overview = "";
//        this.release_date = "";
    }

    // Test RecyclerView
    public Movie(int id, String title, String overview) {
        this.id = id;
        this.title = title;
        this.overview = overview;
    }

    public Movie(int vote_count, int id, boolean video, double vote_average, String title, String poster_path, String original_title, String backdrop_path, String overview, String release_date) {
        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }
}
