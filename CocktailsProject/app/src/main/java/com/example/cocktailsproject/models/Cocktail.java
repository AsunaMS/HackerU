package com.example.cocktailsproject.models;

import com.google.gson.annotations.SerializedName;

public class Cocktail {


    // Java, cocktailName = strDrink
    @SerializedName("strDrink")
    private String cocktailName;

    // Java, cocktailImage = strDrinkThumb
    @SerializedName("strDrinkThumb")
    private String cocktailImage;

    @SerializedName("idDrink")
    private int id;

    public Cocktail() {  }

    public String getCocktailName() {
        return cocktailName;
    }

    public void setCocktailName(String cocktailName) {
        this.cocktailName = cocktailName;
    }

    public String getCocktailImage() {
        return cocktailImage;
    }

    public void setCocktailImage(String cocktailImage) {
        this.cocktailImage = cocktailImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "cocktailName='" + cocktailName + '\'' +
                ", cocktailImage='" + cocktailImage + '\'' +
                ", id=" + id +
                '}';
    }
}
