package com.example.flowers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        ImageView imageview = findViewById(R.id.myZoomageView);

        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imageview);
    }
}