package com.dakiiii.hungerwarrior.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dakiiii.hungerwarrior.MainActivity;
import com.dakiiii.hungerwarrior.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FirebaseUiLoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 122;
    private FirebaseAuth eFirebaseAuth;
    public static Intent INTENT_MAIN_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui_login);
        eFirebaseAuth = FirebaseAuth.getInstance();
        INTENT_MAIN_ACTIVITY = new Intent(this, MainActivity.class);
//        Check if user is already signed in


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = eFirebaseAuth.getCurrentUser();
        if (user != null) {
//            Toast.makeText(this, "user is already logged in", Toast.LENGTH_SHORT).show();
            startActivity(INTENT_MAIN_ACTIVITY);
            Log.i("Sign in", "User already logged in");
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(INTENT_MAIN_ACTIVITY);
                Toast.makeText(this, user.getDisplayName() + " is it", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, FirebaseUiLoginActivity.class));
            }
        }
    }
}