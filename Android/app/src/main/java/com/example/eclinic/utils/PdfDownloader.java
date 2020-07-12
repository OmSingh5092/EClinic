package com.example.eclinic.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

import androidx.core.content.FileProvider;

public class PdfDownloader {
    Context context;
    String path;

    public PdfDownloader(Context context, String path) {
        this.context = context;
        this.path = path;
        initialize();
    }
    private void initialize() {
        File root = new File(Environment.getExternalStorageDirectory(),"EClinic");

        if(!root.exists()){
            root.mkdir();
        }

        File pdfFile = new File(root,"prescription.pdf");

        if(path == null){
            Toast.makeText(context, "No file found", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference().child(path)
                .getFile(pdfFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        openPdf(pdfFile);
                    }
                });
    }

    private void openPdf(File pdfFile){
        Uri contentUri = FileProvider.getUriForFile(context,"com.example.eclinic.provider",pdfFile);
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(contentUri,"application/pdf");
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(i);
    }
}
