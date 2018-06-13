package com.example.mrokey.remote;

import com.example.mrokey.model.NowPlaying;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {
    @GET("/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
    Call<NowPlaying> getMovie();
}
