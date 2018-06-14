package com.example.mrokey.retrofit;

import com.example.mrokey.entity.APINowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitClient {
    final String API_KEY = "4de371dea47b9a5dcd86c1cf83c48d4e";
    final String BASE_URL = "https://api.themoviedb.org";
    final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
    final String POSTER_SCALE = "/w200";

    @GET("/3/movie/now_playing")
    Call<APINowPlaying> getNowPlaying(@Query("api_key") String values);
}
