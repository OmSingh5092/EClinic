package com.example.eclinic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageDownloader {

    ImageView imageView;
    String path;
    Context context;

    FirebaseStorage storage;

    public ImageDownloader(ImageView imageView, String path, Context context) {
        this.imageView = imageView;
        this.path = path;
        this.context = context;

        storage = FirebaseStorage.getInstance();

        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void start() throws IOException {
        if(path == null){
            Toast.makeText(context, "Image not found", Toast.LENGTH_SHORT).show();
            return;
        }
        final File tempFile = File.createTempFile("image","jpg");
        storage.getReference().child(path)
                .getFile(tempFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap temp = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                        imageView.setImageBitmap(temp);
                    }
                });
    }
}
