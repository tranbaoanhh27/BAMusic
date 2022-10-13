package com.example.bamusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity: ";

    public static GoogleSignInAccount userAccount;

    private final AllSongsFragment allSongsFragment = new AllSongsFragment();
    private final FavouritesFragment favouritesFragment = new FavouritesFragment();
    private final SettingFragment settingFragment = new SettingFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        switchFragment(R.id.main_nav_fragmentView, allSongsFragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_menu_fav_songs: {
                    switchFragment(R.id.main_nav_fragmentView, favouritesFragment);
                    return true;
                }
                case R.id.bottom_nav_menu_setting: {
                    switchFragment(R.id.main_nav_fragmentView, settingFragment);
                    return true;
                }
                default: {
                    switchFragment(R.id.main_nav_fragmentView, allSongsFragment);
                    return true;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userAccount == null) {
            Log.d(TAG, "UserAccount is null");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                    MainActivity.this,
                    Collections.singleton(DriveScopes.DRIVE_FILE)
            );
            credential.setSelectedAccount(userAccount.getAccount());
            GoogleDriveHelper.googleDriveService = new Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    credential
            ).setApplicationName("BAMusic").build();
        }
    }

    private void switchFragment(int container_id, Fragment fragmentObj) {
        getSupportFragmentManager().beginTransaction()
                .replace(container_id, fragmentObj)
                .commit();
    }
}