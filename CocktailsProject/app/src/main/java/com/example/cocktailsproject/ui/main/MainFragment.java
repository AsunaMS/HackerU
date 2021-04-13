package com.example.cocktailsproject.ui.main;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cocktailsproject.models.CocktailDetailsAction;
import com.example.cocktailsproject.CocktailsRecyclerViewAdapter;
import com.example.cocktailsproject.R;
import com.example.cocktailsproject.models.DetailsCallBack;
import com.example.cocktailsproject.models.CocktailDetails;


// נממש 2 interfaces , אחד ללחיצה על אייטם ברסייקלר ואחד למתי שקיבלנו מידע מהAPI
public class MainFragment extends Fragment implements CocktailDetailsAction, DetailsCallBack {

    private MainViewModel mViewModel;
    // our recycler view instance
    private RecyclerView rvCocktails;
    // our adapter instance
    private CocktailsRecyclerViewAdapter rvAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCocktails = view.findViewById(R.id.rvCocktails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCocktails.setLayoutManager(layoutManager);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mViewModel.getAllCocktails().observe(getViewLifecycleOwner(), cocktails -> {
            rvAdapter = new CocktailsRecyclerViewAdapter(getContext(), cocktails, MainFragment.this);
            rvCocktails.setAdapter(rvAdapter);
        });
    }


    // מתודה הנקראת מתוך הרסייקלר שלנו בעת לחיצה על פריט
    @Override
    public void cocktailClicked(String id) {
        // נבקש מהוויו מודל שלנו להתחיל את הוצאת המידע מהAPI
        mViewModel.getCocktailDetails(id, this);

        // אחרי שלב זה, אם נקבל חזרה מידע מהשרת, תרוץ המתודה detailsFetched
        // ראו: APIManager.getCocktailDetails
    }


    // במקום להשתמש ב LiveData יצרנו קולבאק משלנו (DetailsCallBack)
    // מתודה הנקראת כשהוויו מודל שלנו מסיים להביא את המידע על הקוקטייל מ API
    @Override
    public void detailsFetched(CocktailDetails cocktailDetails) {
        mViewModel.setDetails(cocktailDetails);
        NavHostFragment.findNavController(MainFragment.this).navigate(R.id.toDetailsFragment);
    }
}