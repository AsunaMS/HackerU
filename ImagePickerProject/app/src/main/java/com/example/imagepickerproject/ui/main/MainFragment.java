package com.example.imagepickerproject.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.imagepickerproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ImageView mImageView;
    private Button mButtonChoose;
    private ProgressBar progressBar;
    private Button mLoadButton;
    // Some constant for our Picker Intent
    public static int IMAGE_PICKER_REQUEST = 1;

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
        mImageView = view.findViewById(R.id.imageView);
        mButtonChoose = view.findViewById(R.id.btnPick);
        progressBar = view.findViewById(R.id.progress_bar);
        mLoadButton = view.findViewById(R.id.btnLoad);
        mButtonChoose.setOnClickListener(v -> {
            openImagePicker();
        });
        mLoadButton.setOnClickListener(v -> loadImageFromStorage("Image"));
    }

    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, IMAGE_PICKER_REQUEST);
    }

    // Method to save an Image to our Firebase Storage
    public void saveImageToStorage(Uri uri, String imageName) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads").child(imageName);

        storageReference.putFile(uri).
                addOnSuccessListener(taskSnapshot -> Toast.makeText(getContext(), "Success!", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show())
                .addOnProgressListener(snapshot -> {
                    // Update our progress Bar
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }).addOnCompleteListener(task -> {
            // Reset progress bar upon Completion
            TimerTask progressResetTask = new TimerTask() {
                @Override
                public void run() {
                    progressBar.setProgress(0);
                }
            };
            Timer timer = new Timer();
            timer.schedule(progressResetTask, 1000);
        });
    }

    // Method to load an image from our Firebase storage uploads/Image
    public void loadImageFromStorage(String ImageName) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads/").child(ImageName);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            Toast.makeText(getContext(), "Image Loaded Sucessfuly!", Toast.LENGTH_LONG).show();
            Picasso.with(getContext()).load(uri).into(mImageView);
        });

    }

    // This method executes when we finish picking an image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            saveImageToStorage(uri, "Image");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}