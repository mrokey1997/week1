package com.example.mrokey.movie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mrokey.adapter.ItemClickListener;
import com.example.mrokey.entity.APIMovie;
import com.example.mrokey.adapter.MovieAdapter;
import com.example.mrokey.entity.APINowPlaying;
import com.example.mrokey.api.APILink;
import com.example.mrokey.features.SomeFeatures;

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

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    MovieAdapter movieAdapter;

    AlertDialog.Builder builder;

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
            public void onResponse(@NonNull Call<APINowPlaying> call, @NonNull Response<APINowPlaying> response) {
                if (response.body() != null) {
                    movieAdapter.setData(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(@NonNull Call<APINowPlaying> call, @NonNull Throwable t) {
                showAlertDialogNoInternetConnection();
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

        /*
          Handle what happened when click on item of recycler view
         */
        movieAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(APIMovie movie) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });

        this.recyclerView.setAdapter(movieAdapter);

        /*
          set up swipe refresh layout
         */
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

    /**
     * show a dialog if no internet connection
     */
    public void showAlertDialogNoInternetConnection() {
        if (!SomeFeatures.isInternetConnection(MainActivity.this))
            alertDialogNoInternetConnection();
    }

    /**
     * a dialog: no internet connection
     */
    public void alertDialogNoInternetConnection() {
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.connection_failed));
        builder.setMessage(getString(R.string.no_internet));
        builder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
                getAllMovies();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


