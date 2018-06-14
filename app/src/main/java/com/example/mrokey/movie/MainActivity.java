package com.example.mrokey.movie;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mrokey.model.Movie;
import com.example.mrokey.model.MovieAdapter;
import com.example.mrokey.model.NowPlaying;
import com.example.mrokey.remote.RetrofitClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Debugggg";

    ArrayList<Movie> arrayList_movies;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // Retrofit
        getAllMovies();

        Log.d(TAG, "in onCreate: " + Integer.toString(arrayList_movies.size()));
    }

    // Get movies from API
    void getAllMovies() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);
        Call<NowPlaying> call = retrofitClient.getMovies();
        call.enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                if (response.isSuccessful()) {
                    NowPlaying res = response.body();
                    List<Movie> list_movies = res.getMovies();
                    for (int i=0; i<list_movies.size(); i++) {
                        Movie aFilm = list_movies.get(i);
                        arrayList_movies.add(new Movie(aFilm));
                        Log.d(TAG, arrayList_movies.get(i).getTitle());
                    }
                    Log.d(TAG, "onResponse - in for loop - size of arrayList_movies: " + Integer.toString(arrayList_movies.size()));
                }
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });

    }

    //initialize for RecyclerView
    void init() {
        arrayList_movies = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MovieAdapter movieAdapter = new MovieAdapter(arrayList_movies, MainActivity.this);
        recyclerView.setAdapter(movieAdapter);
    }


//    public class ReadJSON extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(MainActivity.this, "Downloading JSON...", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            String result = ReadContentFromUrl("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed");
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//                for (int i=0; i<jsonArray.length(); i++) {
//                    JSONObject aFilm = jsonArray.getJSONObject(i);
//                    String title = aFilm.getString("title");
//                    String overview = aFilm.getString("overview");
//                    list_film.add(new Movie(R.drawable.a, title, overview));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            MovieAdapter movieAdapter = new MovieAdapter(list_film, getApplicationContext());
//            recyclerView.setAdapter(movieAdapter);
//        }
//    }
//    private String ReadContentFromUrl(String theUrl){
//        StringBuilder content = new StringBuilder();
//        try    {
//            // create a url object
//            URL url = new URL(theUrl);
//            // create a urlconnection object
//            URLConnection urlConnection = url.openConnection();
//
//            // wrap the urlconnection in a bufferedreader
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//
//            // read from the urlconnection via the bufferedreader
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line + "\n");
//            }
//            bufferedReader.close();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }
}


