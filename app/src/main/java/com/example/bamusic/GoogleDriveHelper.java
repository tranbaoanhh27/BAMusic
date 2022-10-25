package com.example.bamusic;

import static com.example.bamusic.MainActivity.userAccount;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Collections;

public class GoogleDriveHelper {

    private static final String TAG = "GoogleDriveHelper";

    private Drive googleDriveService;

    GoogleDriveHelper(Context context) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                context,
                Collections.singleton(DriveScopes.DRIVE_FILE)
        );
        if (userAccount != null) {
            credential.setSelectedAccount(userAccount.getAccount());
            googleDriveService = new Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    credential
            ).setApplicationName("BAMusic").build();
        } else {
            Log.d(TAG, "userAccount is null");
        }

    }
}
