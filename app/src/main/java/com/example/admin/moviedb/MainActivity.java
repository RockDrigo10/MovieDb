package com.example.admin.moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

import com.example.admin.moviedb.Model.Movies;
import com.example.admin.moviedb.Model.Result;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "92fc5accd27d55d6fe91d8e7f06dfbb7";
    private static final String TAG = "MainActivity";
    RecyclerView rvMovies;
    AutoCompleteTextView tvSearch;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    List<Result> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Movies Db");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        tvSearch = (AutoCompleteTextView) findViewById(R.id.tvSearch);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setItemAnimator(itemAnimator);
        getMovies("A");

    }
    public void getMovies(String search) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.themoviedb.org")
                .addPathSegment("3")
                .addPathSegment("search")
                .addPathSegment("movie")
                .addQueryParameter("api_key", KEY)
                .addQueryParameter("query",search)
                .build();
        Log.d(TAG, "getGeocodeAddress: " + url.toString());
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Movies movie = gson.fromJson(response.body().string(), Movies.class);
                result = movie.getResults();
                Log.d(TAG, "onResponse: " +response.body());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MovieAdapter movieAdapter = new MovieAdapter(result);
                        rvMovies.setAdapter(movieAdapter);
                        movieAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    public void searchMovie(View view) {
        getMovies(tvSearch.getText().toString());
    }
}
