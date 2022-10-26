package com.example.bamusic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utilities {

    public static final String[] necessaryPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.GET_ACCOUNTS
    };

    public static void showToast(Context context, String message, int time) {
        Toast.makeText(context, message, time).show();
    }

    public static void checkAndRequestPermissions(Context context, Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[] {permission}, 100);
            }
        }
    }

    public static String millisecondToMinute(int ms) {
        int secs = ms / 1000;
        int minutes = secs / 60;
        secs %= 60;

        String strMinutes = "", strSeconds = "";
        if (minutes < 10) strMinutes = "0";
        if (secs < 10) strSeconds = "0";
        strMinutes += String.valueOf(minutes);
        strSeconds += String.valueOf(secs);
        return strMinutes + ":" + strSeconds;
    }
}
