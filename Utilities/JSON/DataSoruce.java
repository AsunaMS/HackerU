package com.example.jsonpractice.models;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDataSoruce {
    private static String address = "https://api.androidhive.info/json/movies.json";


    public void addMovieData(MutableLiveData<ArrayList<Movie>> callback) {
        Call call = IO.getJsonCall(address);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("[DEBUG] Respose Called!");
                String jsonBody = response.body().string();
                ArrayList<Movie> movies = new ArrayList<>();
                try {
                    JSONArray rootArray = new JSONArray(jsonBody);
                    for (int i = 0; i < rootArray.length(); i++) {
                        // here we get each 'result' from the JSONArray "results"
                        JSONObject movieJsonObject = rootArray.getJSONObject(i);
                        // Extracting JAVA objects from JSON object "movieJsonObject" & Creating Movie instance for each result & add to List "movies"
                        movies.add(new Movie(movieJsonObject.getString("title"),
                                movieJsonObject.getJSONArray("genre"),
                                movieJsonObject.getString("image")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.postValue(movies);

            }
        });


    }

}
