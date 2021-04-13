package com.example.entitiesexample.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.entitiesexample.models.Cocktail;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CocktailDataSource {
    private static String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita";


    public static void getMargaritas(MutableLiveData<List<Cocktail>> cocktailsLiveData) {

        List<Cocktail> cocktails = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.err.println(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("drinks");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject cocktailJsonObject = jsonArray.getJSONObject(i);
                        String strDrink = cocktailJsonObject.getString("strDrink");
                        String strCategory = cocktailJsonObject.getString("strDrink");
                        Cocktail cocktail = new Cocktail(strDrink, strCategory);
                        cocktails.add(cocktail);
                    }
                    cocktailsLiveData.postValue(cocktails);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
