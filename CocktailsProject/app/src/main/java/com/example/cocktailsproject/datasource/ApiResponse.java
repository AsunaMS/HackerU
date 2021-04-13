package com.example.cocktailsproject.datasource;

import com.example.cocktailsproject.models.Cocktail;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// אובייקט גייסון שמכיל רשימה של קוקטיילים
public class ApiResponse {

    // רשימה של קוקטיילים
    @SerializedName("drinks")
    private ArrayList<Cocktail> allCocktails;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "allCocktails=" + allCocktails +
                '}';
    }

    // required by GSON
    public ApiResponse() {
    }


    public void setAllCocktails(ArrayList<Cocktail> allCocktails) {
        this.allCocktails = allCocktails;
    }

    public ArrayList<Cocktail> getAllCocktails() {
        return allCocktails;
    }
}
