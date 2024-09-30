package com.example.photobrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private List<Photo> photoList = new ArrayList<>();
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch photos from Firebase Storage
        fetchPhotosFromFirebase();

        if (savedInstanceState != null) {
            selectedPosition = savedInstanceState.getInt("selectedPosition", -1);
            recyclerView.scrollToPosition(selectedPosition);
        }
    }

    private void fetchPhotosFromFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("photos");
        listRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Add the photo to the list with its URL
                    photoList.add(new Photo(item.getName(), uri.toString()));
                    photoAdapter.notifyDataSetChanged();  // Notify the adapter
                }).addOnFailureListener(e -> {
                    Toast.makeText(PhotoListActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(PhotoListActivity.this, "Failed to load photos", Toast.LENGTH_SHORT).show();
        });

        photoAdapter = new PhotoAdapter(photoList, (photo, position) -> {
            selectedPosition = position;
            Intent intent = new Intent(PhotoListActivity.this, PhotoDetailActivity.class);
            intent.putExtra("photo", photo);
            startActivityForResult(intent, 100);
        });
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedPosition", selectedPosition);
    }
}
