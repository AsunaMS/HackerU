package com.example.cocktailsproject.datasource;

import com.example.cocktailsproject.models.CocktailDetails;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// אובייקט גייסון שמכיל רשימה של קוקטיילים
public class ApiResponseDetails {

    // רשימה של קוקטיילים
    @SerializedName("drinks")
    private ArrayList<CocktailDetails> allCocktails;

    @Override
    public String toString() {
        return "ApiResponseDetails{" +
                "allCocktails=" + allCocktails +
                '}';
    }

    // required by GSON
    public ApiResponseDetails() {
    }


    public void setAllCocktails(ArrayList<CocktailDetails> allCocktails) {
        this.allCocktails = allCocktails;
    }

    public ArrayList<CocktailDetails> getAllCocktails() {
        return allCocktails;
    }
}
