package com.example.mrokey.movie;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mrokey.model.Movie;
import com.example.mrokey.model.MovieAdapter;
import com.example.mrokey.model.NowPlaying;
import com.example.mrokey.remote.APIUtils;
import com.example.mrokey.remote.SOService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> list_film;
    RecyclerView recyclerView;
    ImageView imageView;

    SOService soService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrofit

        soService = APIUtils.getSOService();

        loadMovie();

        //initialization
//        list_film = new ArrayList<>();
//
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);


    }

    public void loadMovie() {
        soService.getMovie().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    Log.d("MainActivity..", "posts loaded from API");
                }
                else {
                    int statusCode = response.code();
                    Log.d("MainActivity..", Integer.toString(statusCode));
                }
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.d("MainActivity..", "error loading from API");
            }
        });
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

    //call api here
    public class ReadJSON extends AsyncTask<Void, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Downloading JSON...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://api.themoviedb.org/3/movie/now_playing";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed");
//            client.setTimeout(5000);
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject aFilm = jsonArray.getJSONObject(i);
                            String title = aFilm.getString("title");
                            String overview = aFilm.getString("overview");
                            list_film.add(new Movie(R.drawable.a, title, overview));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Read JSON failed.", Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MovieAdapter movieAdapter = new MovieAdapter(list_film, getApplicationContext());
            recyclerView.setAdapter(movieAdapter);
        }
    }

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

//    public void readJSON(String url, String key, String value) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put(key, value);
//        client.get(url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//
//                    Toast.makeText(MainActivity.this, response.getJSONArray("results").toString(), Toast.LENGTH_LONG).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Toast.makeText(MainActivity.this, "Read JSON failed.", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}


