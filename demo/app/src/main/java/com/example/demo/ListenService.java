package com.example.demo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ListenService extends Service {

    private MediaPlayer mp;
    Animation animation;

    public ListenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String listenName = intent.getStringExtra("ListenName");
        int listenFileId = intent.getIntExtra("ListenFile", 0);
        String action =intent.getStringExtra("Action");
        animation = AnimationUtils.loadAnimation(this,R.anim.rotation);

        switch(action){
            case"play":
                if (mp==null){
                    mp= MediaPlayer.create(this,listenFileId);
                }
                mp.start();




                break;
            case "pause":
                if (mp!=null){
                    mp.pause();
                }
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy(){
        super.onDestroy();
        mp.stop();
        mp.release();
        mp=null;
    }
}