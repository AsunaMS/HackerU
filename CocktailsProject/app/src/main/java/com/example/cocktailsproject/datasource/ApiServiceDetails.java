package com.example.cocktailsproject.datasource;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
// כתובת ה API
// https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic

// נפרק את הכתובת לגורמים
// URL: www.thecocktaildb.com
// ENDPOINTS : /api/json/v1/1/filter.php
// QUERY : ?a=Alcoholic
public interface ApiServiceDetails {
    // אנחנו נבחר את הQuery כנקרא למתודה הזאת
    @GET("api/json/v1/1/lookup.php")
    Call<ApiResponseDetails> getAPIResponse(@Query("i") String id);
}
