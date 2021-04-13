package com.example.cocktailsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import com.example.cocktailsproject.datasource.APIManager;
import com.example.cocktailsproject.models.Cocktail;
import com.example.cocktailsproject.ui.main.MainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}