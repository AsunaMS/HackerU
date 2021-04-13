package com.example.cocktailsproject.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cocktailsproject.datasource.APIManager;
import com.example.cocktailsproject.models.DetailsCallBack;
import com.example.cocktailsproject.models.Cocktail;
import com.example.cocktailsproject.models.CocktailDetails;

import java.util.ArrayList;

// נרצה ליצור ViewModel שיחזיק לנו את המידע גם בשינוי קונפיגורציה! מאוד חשוב!
// בלי קשר, גם מועיל לסדר הפרויקט ולחלוקה נכונה של אחריות!

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Cocktail>> allCocktails = new MutableLiveData<>();
    private CocktailDetails details;
    private APIManager manager;

    public MainViewModel() {
        // Create new mutable live data
        // Create a new manager object
        manager = new APIManager();
        // Populate our live data
        manager.getCocktails(allCocktails);
    }

    // נריץ מתודה שמקבלת interface וקוראת למתודה בעת קבלת המידע
    public void getCocktailDetails(String id, DetailsCallBack callBack) {
        manager.getCocktailDetails(id, callBack);
    }

    public void setDetails(CocktailDetails details) {
        this.details = details;
    }

    public CocktailDetails getDetails() {
        return details;
    }

    public MutableLiveData<ArrayList<Cocktail>> getAllCocktails() {
        return allCocktails;
    }
}