package com.example.bamusic;

import android.content.Context;
import android.widget.Toast;

public class Utilities {

    public static void showToast(Context context, String message, int time) {
        Toast.makeText(context, message, time).show();
    }
}
