package com.example.photobrowser;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class PhotoDetailActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        imageView = findViewById(R.id.imageViewDetail);
        Photo photo = getIntent().getParcelableExtra("photo");

        if (photo != null) {
            Glide.with(this).load(photo.getUrl()).into(imageView);
        }
    }
}
