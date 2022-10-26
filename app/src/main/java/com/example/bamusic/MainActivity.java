package com.example.bamusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener {

    private static final String TAG = "MainActivity: ";

    public static GoogleSignInAccount userAccount;

    private final AllMusicsFragment allMusicsFragment = new AllMusicsFragment();
    private final FavouritesFragment favouritesFragment = new FavouritesFragment();
    private final SettingFragment settingFragment = new SettingFragment();

    private static GoogleDriveHelper googleDriveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilities.checkAndRequestPermissions(this, this, Utilities.necessaryPermissions);
        setContentView(R.layout.activity_main);
        Music.updateLocalMusics();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userAccount == null) {
            Log.d(TAG, "UserAccount is null");
            startLoginActivity();
        } else {
            Log.d(TAG, "Google userAccount detected");
            String message = "Welcome to BAMusic!\n" + userAccount.getEmail();
            Utilities.showToast(this, message, Toast.LENGTH_LONG);
            googleDriveHelper = new GoogleDriveHelper(this);
        }
    }

    private void startLoginActivity() {
        Log.d(TAG, "Start LoginActivity");
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void switchFragment(int container_id, Fragment fragmentObj) {
        getSupportFragmentManager().beginTransaction()
                .replace(container_id, fragmentObj)
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                switchFragment(R.id.main_nav_fragmentView, allMusicsFragment);
                return true;
            }
        }
    }
}