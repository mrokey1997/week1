package com.example.mrokey.remote;

public class APIUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
