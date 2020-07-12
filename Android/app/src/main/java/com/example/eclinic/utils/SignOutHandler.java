package com.example.eclinic.utils;

import android.content.Context;
import android.content.Intent;

import com.example.eclinic.activity.DoctorHomeActivity;
import com.example.eclinic.activity.GetStartedActivity;
import com.example.eclinic.data.GeneralData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class SignOutHandler {

    Context context;
    SharedPrefs prefs;

    public SignOutHandler(Context context) {
        this.context = context;

        prefs = new SharedPrefs(context);
        initialize();
    }

    void initialize(){
        //Signing out of Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        mGoogleSignInClient.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                GeneralData.reset();
                prefs.clearData();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(context, GetStartedActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            }
        });


    }
}
