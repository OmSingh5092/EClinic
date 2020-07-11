package com.example.eclinic.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eclinic.MainActivity;
import com.example.eclinic.R;
import com.example.eclinic.apiModel.TokenResponseModel;
import com.example.eclinic.databinding.ActivityPatientRegisterBinding;
import com.example.eclinic.handlers.FormHandler;
import com.example.eclinic.handlers.PatientFormHandler;
import com.example.eclinic.retrofit.RetrofitClient;
import com.example.eclinic.utils.PermissionHandler;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PatientRegisterActivity extends AppCompatActivity implements FormHandler {

    ActivityPatientRegisterBinding binding;
    int PICK_IMAGE =100;
    PatientFormHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new PermissionHandler(this).askStoragePermissions();

        //Initializing form handling
        handler = new PatientFormHandler(this,binding.patientForm);
        handler.initialize();
        //Setting up toolbar
        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            handler.handleOpenImage(data.getData());
        }
        else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            handler.handleCroppedImage(UCrop.getOutput(data));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onFormSubmitted() {
        Intent i = new Intent(this,PatientHomeActivity.class);
        startActivity(i);
    }
}