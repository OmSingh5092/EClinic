package com.example.eclinic.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.apiControllers.DoctorProfileController;
import com.example.eclinic.apiControllers.PatientProfileController;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.DoctorUpdateResponseModel;
import com.example.eclinic.apiModel.Patient;
import com.example.eclinic.apiModel.PatientUpdateResponseModel;
import com.example.eclinic.databinding.LayoutDoctorFormBinding;
import com.example.eclinic.databinding.LayoutPatientFormBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorFormHandler {

    private FormHandler handler;
    private LayoutDoctorFormBinding binding;
    private Context context;
    SharedPrefs prefs;
    Activity activity;
    Doctor doctor;

    Bitmap profileImage;
    FirebaseStorage storage;
    FirebaseAuth auth;

    String name,phone,registration,year,council,profilePath,category,about;

    public DoctorFormHandler(FormHandler handler, LayoutDoctorFormBinding binding) {
        this.handler = handler;
        this.binding = binding;
        context = (Context)handler;
        activity = (Activity)handler;
        prefs = new SharedPrefs(context);
        auth = FirebaseAuth.getInstance();
        doctor = new Doctor();

        storage = FirebaseStorage.getInstance();

    }

    public void handleOpenImage(Uri uri){
        File file = null; // 2
        try {
            file = getImageFile();
            Uri destinationUri = Uri.fromFile(file);  // 3
            openCropActivity(uri, destinationUri);  // 4

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCroppedImage(Uri imageUri){
        File file = new File(imageUri.getPath());
        try {
            InputStream inputStream = new FileInputStream(file);
            profileImage = BitmapFactory.decodeStream(inputStream);
            binding.image.setImageBitmap(profileImage);
            uploadImage(imageUri);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    void uploadImage(Uri uri){
        final Snackbar snackbar = Snackbar.make(binding.getRoot(),"Uploading Image...",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        storage.getReference()
                .child("doctor")
                .child("profile")
                .child(auth.getUid()).child("image.jpg").putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        snackbar.dismiss();
                        Toast.makeText(context, "Upload Successful", Toast.LENGTH_SHORT).show();
                        snackbar.dismiss();
                        profilePath = "doctor/profile/"+auth.getUid()+"/image.jpg";
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                snackbar.dismiss();
            }
        });
    }

    private void openCropActivity(Uri sourceUri, Uri destinationUri) {
        UCrop.of(sourceUri, destinationUri)
                .withMaxResultSize(300, 300)
                .withAspectRatio(5f, 5f)
                .start(activity);
    }

    private File getImageFile() throws IOException {
        String currentPhotoPath = "";
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                ), "Camera"
        );
        File file = File.createTempFile(
                imageFileName, ".jpg", storageDir
        );
        currentPhotoPath = "file:" + file.getAbsolutePath();
        return file;
    }



    public void initialize(){
        binding.category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                initCategoryPopOver();
            }
        });
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pictureIntent.setType("image/*");  // 1
                pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);
                activity.startActivityForResult(Intent.createChooser(pictureIntent,"Select Picture"), 100);  // 4
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

    }

    private  void onSubmit(){
        initText();
        if(!isFilled()){
            return;
        }

        doctor.setDoctorName(name);
        doctor.setRegistrationNumber(registration);
        doctor.setStateMedicalCouncil(council);
        doctor.setYearOfRegistration(year);
        doctor.setPhotoPath(profilePath);
        doctor.setPhoneNumber(phone);
        doctor.setCategory(category);
        doctor.setAbout(about);

        DoctorProfileController.updateDoctor(doctor, prefs.getToken(), new Callback<DoctorUpdateResponseModel>() {
            @Override
            public void onResponse(Call<DoctorUpdateResponseModel> call, Response<DoctorUpdateResponseModel> response) {
                if(response.isSuccessful()){
                    handler.onFormSubmitted();
                }
            }

            @Override
            public void onFailure(Call<DoctorUpdateResponseModel> call, Throwable t) {

            }
        });


    }



    private void initText(){
        name = binding.name.getText().toString();
        phone = binding.phone.getText().toString();
        registration = binding.registrationNumber.getText().toString();
        council = binding.stateMedicalCouncil.getText().toString();
        year = binding.yearOfRegistration.getText().toString();
        about = binding.about.getText().toString();
    }

    boolean isFilled(){
        if(name == null){
            Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(phone == null){
            Toast.makeText(context, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        }else if(registration == null){
            Toast.makeText(context, "Please enter a gender", Toast.LENGTH_SHORT).show();
            return false;
        }else if(council == null){
            Toast.makeText(context, "Please enter a weight", Toast.LENGTH_SHORT).show();
            return false;
        }else if(year == null){
            Toast.makeText(context, "Please enter an year", Toast.LENGTH_SHORT).show();
            return false;
        }else if(about == null){
            Toast.makeText(context, "Please type something about you", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void initCategoryPopOver(){
        PopupMenu menu = new PopupMenu(context, binding.category);
        int id = 0;
        String[] categoryList = context.getResources().getStringArray(R.array.doctor_category);
        for(String string: categoryList){
            menu.getMenu().add(100,id,id,string);
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                category = categoryList[menuItem.getItemId()];
                binding.category.setText(category);
                return false;
            }
        });

        menu.show();
    }
}
