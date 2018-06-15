package com.example.mrokey.api;

import com.example.mrokey.entity.APINowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APILink {
    final String API_KEY = "4de371dea47b9a5dcd86c1cf83c48d4e";
    final String BASE_URL = "https://api.themoviedb.org";
    final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
    final String POSTER_SIZE = "/w200";
    final String BACKDROP_SIZE = "/w400";
    final double VOTE_AVERAGE = 6.0;

    @GET("/3/movie/now_playing")
    Call<APINowPlaying> getNowPlaying(@Query("api_key") String values);
}
