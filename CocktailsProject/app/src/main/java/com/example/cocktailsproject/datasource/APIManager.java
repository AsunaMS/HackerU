package com.example.cocktailsproject.datasource;

import androidx.lifecycle.MutableLiveData;

import com.example.cocktailsproject.models.Cocktail;
import com.example.cocktailsproject.models.CocktailDetails;
import com.example.cocktailsproject.models.DetailsCallBack;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    // Our base URL
    private static String URL = "https://www.thecocktaildb.com/";

    // Retrofit object to handle our api
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

    // Retrofit populates our service
    private ApiService service = retrofit.create(ApiService.class);

    // Retrofit populates our details service
    private ApiServiceDetails detailsService = retrofit.create(ApiServiceDetails.class);


    // מתודה הנקראת מהViewModel על ידי לחיצה על פריט ברסייקלר
    public void getCocktailDetails(String id, DetailsCallBack callBack) {
        Call<ApiResponseDetails> responseDetailsCall = detailsService.getAPIResponse(id);
        responseDetailsCall.enqueue(new Callback<ApiResponseDetails>() {
            @Override
            public void onResponse(Call<ApiResponseDetails> call, Response<ApiResponseDetails> response) {
                ApiResponseDetails apiResponse = response.body();
                if (apiResponse == null) {
                    System.out.println("Retrofit failed to fetch data");
                    return;
                }
                // פה קיבלנו את המידע שביקשנו מAPI
                CocktailDetails details = apiResponse.getAllCocktails().get(0);

                //(MainViewModel) נקרא למתודה שלנו שתרוץ כעת שקיבלנו את המידע
                callBack.detailsFetched(details);
            }

            @Override
            public void onFailure(Call<ApiResponseDetails> call, Throwable t) {
                System.out.println("[Debug] " + t.getMessage());
            }
        });

    }

    public void getCocktails(MutableLiveData<ArrayList<Cocktail>> cocktailsMutable) {
        // call the service for response
        Call<ApiResponse> response = service.getAPIResponse();

        // Retrofit downloads data on secondary thread!
        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse == null) {
                    System.out.println("Could not load cocktails");
                    return;
                }
                ArrayList<Cocktail> cocktails = apiResponse.getAllCocktails();
                System.err.println("[Debug] " + cocktails.size());
                cocktailsMutable.postValue(cocktails);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                System.out.print(t.getMessage());
            }
        });

    }

}
