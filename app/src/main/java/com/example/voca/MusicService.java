package com.example.voca;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class MusicService extends Service {

    MediaPlayer mp;
    String TAG = "서비스 테스트";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
