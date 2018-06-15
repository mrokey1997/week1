package com.example.mrokey.movie;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mrokey.entity.APIMovie;
import com.example.mrokey.adapter.MovieAdapter;
import com.example.mrokey.entity.APINowPlaying;
import com.example.mrokey.api.APILink;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Debugggg";

    List<APIMovie> list_movies;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (list_movies == null) {
            init();
            getAllMovies();
        } else {
            movieAdapter.setData(list_movies);
        }

    }

    /**
     * Get movie data from API
     */
    void getAllMovies() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(APILink.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        APILink retrofitClient = retrofit.create(APILink.class);
        Call<APINowPlaying> call = retrofitClient.getNowPlaying(APILink.API_KEY);
        call.enqueue(new Callback<APINowPlaying>() {
            @Override
            public void onResponse(Call<APINowPlaying> call, Response<APINowPlaying> response) {
                if (response.body() != null) {
                    movieAdapter.setData(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(Call<APINowPlaying> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Initialize for RecyclerView
     */
    void init() {
        list_movies = new ArrayList<>();

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(MainActivity.this);
        movieAdapter.setData(list_movies);

        this.recyclerView.setAdapter(movieAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieAdapter.clearData();
                getAllMovies();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

}


