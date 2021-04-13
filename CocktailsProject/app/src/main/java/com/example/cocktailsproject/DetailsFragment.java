package com.example.cocktailsproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocktailsproject.models.CocktailDetails;
import com.example.cocktailsproject.ui.main.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    private MainViewModel mViewModel;
    TextView cocktailNameTv;
    TextView cocktailInstructionsTv;
    ImageView cocktailIv;
    Button back;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cocktailNameTv = view.findViewById(R.id.cocktailName_Details);
        cocktailInstructionsTv = view.findViewById(R.id.cocktailInstructions_Details);
        cocktailIv = view.findViewById(R.id.cocktailImage_Details);
        back = view.findViewById(R.id.backtoMain);
        back.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.toMainFragment);
        });
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        CocktailDetails cocktailDetailsObject = mViewModel.getDetails();
        if (cocktailDetailsObject != null) {
            String imageURL = cocktailDetailsObject.getCocktaiImage();
            String cocktailName = cocktailDetailsObject.getCocktailName();
            String instructions = cocktailDetailsObject.getInstructions();
            cocktailNameTv.setText(cocktailName);
            cocktailInstructionsTv.setText(instructions);
            Picasso.with(getContext()).load(imageURL).into(cocktailIv);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}