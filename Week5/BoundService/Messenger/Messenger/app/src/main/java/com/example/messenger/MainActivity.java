package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Messenger mMessenger;
    private boolean isServiceConnected;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mMessenger = new Messenger(iBinder);
        isServiceConnected = true;
        sendMessagePlayMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMessenger=null;
            isServiceConnected=false;
        }
    };

    private void sendMessagePlayMusic() {
        Message message = Message.obtain(null,MusicService.MSG_PLAY_MUSIC,0,0);
        try {
            mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStartService = findViewById(R.id.btn_start_service);
        Button btnStopService = findViewById(R.id.btn_stop_service);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStartService();
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStopService();
            }
        });
    }

    private void onClickStopService() {
        if(isServiceConnected){
            unbindService(mServiceConnection);
            isServiceConnected=false;
        }
    }

    private void onClickStartService() {
        Intent intent = new Intent(this, MusicService.class);
     bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);

    }
}