package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListenPlayActivity extends AppCompatActivity {


    private TextView tvListenName;
    private CircleImageView ivListenPic;
    private ImageButton ibPlay,ibPause;
    private String[] listenNameList={"Listening comprehension1","Listening comprehension2","Listening comprehension3","Listening comprehension4","Listening comprehension5","Listening comprehension6","Listening comprehension7","Listening comprehension8","Listening comprehension9","Listening comprehension10","Listening comprehension11"};
    private int[] listenPicId={R.drawable.tingl4,R.drawable.tingl2,R.drawable.tingl3,R.drawable.tingl4,R.drawable.tingl4,R.drawable.tingl4,R.drawable.tingl4,R.drawable.tingl4,R.drawable.tingl4,R.drawable.tingl4,R.drawable.tingl4};
    private int[] listenFileId={R.raw.listen1,R.raw.listen2,R.raw.listen3,R.raw.tingl4,R.raw.tingl5,R.raw.tingl6,R.raw.tingl7,R.raw.tingl8,R.raw.tingl9,R.raw.tingl10,R.raw.tingl11};
    private int id=0;
    private Intent intentActivity,intentService;

    Animation animation;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_play);

        tvListenName=findViewById(R.id.listenPlay_listenName);
        CircleImageView ivListenPic = findViewById(R.id.listenPlay_pic);
        ibPlay =findViewById(R.id.listenPlay_play);
        ibPause =findViewById(R.id.listenPlay_pause);

        animation = AnimationUtils.loadAnimation(this,R.anim.rotation);






        intentActivity = getIntent();
        String listenName = intentActivity.getStringExtra("listenName");
        tvListenName.setText(listenName);

        for (int i=0;i<listenNameList.length;i++){
            if (listenNameList[i].equals(listenName)){
                id=i;
                break;
            }
        }
        ivListenPic.setImageResource(listenPicId[id]);

        intentService =new Intent(ListenPlayActivity.this, ListenService.class);
        intentService.putExtra("ListenName",listenNameList[id]);
        intentService.putExtra("ListenFile",listenFileId[id]);
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentService.putExtra("Action","play");
                startService(intentService);
                ivListenPic.startAnimation(animation);
                ibPlay.setVisibility(View.INVISIBLE);
                ibPause.setVisibility(View.VISIBLE);
            }
        });
        ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentService.putExtra("Action","pause");
                startService(intentService);
                ivListenPic.clearAnimation();
                ibPause.setVisibility(View.INVISIBLE);
                ibPlay.setVisibility(View.VISIBLE);
            }
        });

    }





    protected void onDestroy(){
        super.onDestroy();
        stopService(intentService);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.regmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override//菜单选择项对应操作
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Drawable drawable;
        Resources res=getResources();
        if (item.getItemId() == R.id.style_red) {
            drawable = res.getDrawable(R.color.colorStRed);
            getWindow().setBackgroundDrawable(drawable);
            return true;
        } else if (item.getItemId() == R.id.style_green) {
            drawable = res.getDrawable(R.color.colorStGreen);
            getWindow().setBackgroundDrawable(drawable);
            return true;
        } else if (item.getItemId() == R.id.style_blue) {
            drawable = res.getDrawable(R.color.colorStBlue);
            getWindow().setBackgroundDrawable(drawable);
            return true;
        } else if (item.getItemId() == R.id.app_version) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListenPlayActivity.this);
            builder.setTitle("版本信息")
                    .setMessage("开发者：lxk\n 日期：2023.12.21\n 浙江工业大学")
                    .setPositiveButton("确定", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // 更新进度条的方法

}