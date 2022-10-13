package com.example.bamusic;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.api.services.drive.DriveScopes;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient loginClient;
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        // .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();

        loginClient = GoogleSignIn.getClient(this, googleSignInOptions);

        MaterialButton loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> logIn());
    }

    public void logIn() {
        Log.d(TAG, "login() invoked");
        Intent loginIntent = this.loginClient.getSignInIntent();
        logInActivityResultLauncher.launch(loginIntent);
    }

    ActivityResultLauncher<Intent> logInActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        MainActivity.userAccount = task.getResult(ApiException.class);
                        Log.d(TAG, "Now finish LoginActivity");
                        LoginActivity.this.finish();
                    } catch (ApiException e) {
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "GoogleSignIn failed " + Integer.toString(result.getResultCode()));
                    Toast.makeText(this, "Google Sign In canceled", Toast.LENGTH_SHORT).show();
                }
            }
    );
}