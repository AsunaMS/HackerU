package com.example.jsonpractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonpractice.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.util.ArrayList;

public class NamesRecyclerViewAdapter extends RecyclerView.Adapter<NamesRecyclerViewAdapter.ViewHolder> {

    //Properties
    // Information , Context
    private ArrayList<String> names;
    //private ArrayList<Integer> lines;
    private ArrayList<JSONArray> genres;
    private ArrayList<String> images;
    private Context context;

    public NamesRecyclerViewAdapter(ArrayList<String> names/*, ArrayList<Integer> lines*/, ArrayList<JSONArray> genres, ArrayList<String> images, Context context) {
        this.names = names;
       // this.lines = lines;
        this.genres = genres;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // We need: View v =  How to inflate XMl file.
        // Create new ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.name_item, parent, false);
        return new ViewHolder(v);
    }

    public static void loadImagefromURL(ViewHolder holder, String imageURL) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.tvImage.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                holder.tvImage.setImageResource(R.drawable.loading);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
    //    Integer line = lines.get(position);
        String image = images.get(position);
        JSONArray genre = genres.get(position);
        Picasso.get().load(image).into(holder.tvImage);
        holder.tvGenres.setText(genre.toString());
       // holder.line.setImageResource(line);
        holder.tvName.setText(name);
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    // מחלקה פנימית שיורשת מViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvGenres;
        ImageView tvImage;
       // ImageView line;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  line = itemView.findViewById(R.id.line);
            tvImage = itemView.findViewById(R.id.tvImage);
            tvGenres = itemView.findViewById(R.id.tvGenres);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
