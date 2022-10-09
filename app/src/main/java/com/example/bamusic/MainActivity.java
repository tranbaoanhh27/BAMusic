package com.example.bamusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static GoogleSignInAccount userAccount;
    private final AllSongsFragment allSongsFragment = new AllSongsFragment();
    private final FavouritesFragment favouritesFragment = new FavouritesFragment();
    private final SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        switchFragment(R.id.main_nav_fragmentView, allSongsFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
                        switchFragment(R.id.main_nav_fragmentView, allSongsFragment);
                        return true;
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userAccount == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
        }
    }

    private void switchFragment(int container_id, Fragment fragmentObj) {
        getSupportFragmentManager().beginTransaction()
                .replace(container_id, fragmentObj)
                .commit();
    }
}