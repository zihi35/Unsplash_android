package com.example.flowers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowers.model.imageModel;

import java.util.ArrayList;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.imagerViewHolder> {

    private Context context;
    private ArrayList<imageModel> list;

    public imageAdapter(Context context, ArrayList<imageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public imagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.image_item_layout, parent,false);
        return new imagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imagerViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getUrls().getRegular())
                .into(holder.imageview);

        holder.imageview.setOnClickListener(v ->{
            Intent intent = new Intent(context, FullImage.class);
            intent.putExtra("image",list.get(position).getUrls().getRegular());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class imagerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview;

        public imagerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
        }
    }
}
