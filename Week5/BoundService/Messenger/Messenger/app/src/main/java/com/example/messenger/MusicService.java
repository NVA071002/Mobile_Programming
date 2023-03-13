package com.example.messenger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MusicService extends Service {
    @Nullable
    private MediaPlayer mMediaPlayer;
    public static final int MSG_PLAY_MUSIC = 1;
    private Messenger mMessenger;

    public class MyHandler extends Handler {
        private Context applicationContext;

        public MyHandler(Context context) {
            this.applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_PLAY_MUSIC:
                    startMusic();
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

    @Override
    public void onCreate() {
        Log.e("MusicService","onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MusicService","onBind");

        mMessenger = new Messenger(new MyHandler(this));

        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MusicService","onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MusicService","onDestroy");
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }

    private void startMusic() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.file_music);
        }
        mMediaPlayer.start();

    }
}
