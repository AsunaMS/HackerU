package com.example.cocktailsproject.datasource;


import retrofit2.Call;
import retrofit2.http.GET;
// כתובת ה API
// https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic

// נפרק את הכתובת לגורמים
// URL: www.thecocktaildb.com
// ENDPOINTS : /api/json/v1/1/filter.php
// QUERY : ?a=Alcoholic

// נפרק את הכתובת לגורמים שוב

public interface ApiService {
    @GET("api/json/v1/1/filter.php?a=Alcoholic")
    Call<ApiResponse> getAPIResponse();
}
