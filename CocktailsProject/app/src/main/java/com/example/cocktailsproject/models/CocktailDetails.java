package com.example.cocktailsproject.models;

import com.google.gson.annotations.SerializedName;

public class CocktailDetails {

    @SerializedName("strDrink")
    private String cocktailName;

    @SerializedName("strIngredient1")
    private String ingredientOne;

    @SerializedName("strIngredient2")
    private String ingredientTwo;

    @SerializedName("strIngredient3")
    private String ingredientThree;

    @SerializedName("strIngredient4")
    private String ingredientFour;

    @SerializedName("strCategory")
    private String cocktailCategory;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strDrinkThumb")
    private String cocktailImage;


    public CocktailDetails() {
    }

    @Override
    public String toString() {
        return "CocktailDetails{" +
                "cocktailName='" + cocktailName + '\'' +
                ", ingredientOne='" + ingredientOne + '\'' +
                ", ingredientTwo='" + ingredientTwo + '\'' +
                ", ingredientThree='" + ingredientThree + '\'' +
                ", ingredientFour='" + ingredientFour + '\'' +
                ", cocktailCategory='" + cocktailCategory + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }

    public String getCocktaiImage() {
        return cocktailImage;
    }

    public String getCocktailName() {
        return cocktailName;
    }

    public String getIngredientOne() {
        return ingredientOne;
    }

    public String getCocktailCategory() {
        return cocktailCategory;
    }

    public String getIngredientThree() {
        return ingredientThree;
    }

    public String getIngredientFour() {
        return ingredientFour;
    }

    public String getIngredientTwo() {
        return ingredientTwo;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setCocktailCategory(String cocktailCategory) {
        this.cocktailCategory = cocktailCategory;
    }

    public void setCocktaiImage(String cocktailImage) {
        this.cocktailImage = cocktailImage;
    }

    public void setCocktailName(String cocktailName) {
        this.cocktailName = cocktailName;
    }

    public void setIngredientFour(String ingredientFour) {
        this.ingredientFour = ingredientFour;
    }

    public void setIngredientOne(String ingredientOne) {
        this.ingredientOne = ingredientOne;
    }

    public void setIngredientThree(String ingredientThree) {
        this.ingredientThree = ingredientThree;
    }

    public void setIngredientTwo(String ingredientTwo) {
        this.ingredientTwo = ingredientTwo;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
