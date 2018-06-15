package com.example.mrokey.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrokey.api.APILink;
import com.example.mrokey.entity.APIMovie;
import com.example.mrokey.entity.APITrailer;
import com.example.mrokey.entity.APIYoutube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends YouTubeBaseActivity {

    APIMovie movie;
    @BindView(R.id.youtube_player) YouTubePlayerView youTubePlayerView;

    @BindView(R.id.tv_detail_title) TextView tv_detail_title;
    @BindView(R.id.rating_bar) RatingBar rating_bar;
    @BindView(R.id.tv_detail_release_date) TextView tv_detail_release_date;

    private static final String API_KEY_YOUTUBE = "AIzaSyDMIMloVYigVR8XyAZ5KQKAy7RXleGzNSE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        movie = (APIMovie) getIntent().getParcelableExtra("movie");

        setDetail();

        getAllTrailers();
    }

    private void setDetail() {
        tv_detail_title.setText(movie.getTitle());
        Double voteAverage = movie.getVoteAverage()/2.0;
        rating_bar.setRating(voteAverage.floatValue());
        rating_bar.setIsIndicator(true);
        tv_detail_release_date.setText("Release date: " + movie.getReleaseDate());
    }

    public void youtube(final String source) {
        youTubePlayerView.initialize(API_KEY_YOUTUBE,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(source); // .loadVideo: Play now - .cueVideo: only load

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    public void getAllTrailers() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(APILink.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        APILink retrofitClient = retrofit.create(APILink.class);
        Call<APITrailer> call = retrofitClient.getTrailers(movie.getId(), APILink.API_KEY);
        call.enqueue(new Callback<APITrailer>() {
            @Override
            public void onResponse(Call<APITrailer> call, Response<APITrailer> response) {
                if (response.body() != null) {
                    int size = response.body().getYoutube().size();
                    String source = response.body().getYoutube().get(new Random().nextInt(size)).getSource();
                    youtube(source);
                }

            }

            @Override
            public void onFailure(Call<APITrailer> call, Throwable t) {

            }
        });
    }
}
