package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {
    private EditText regUesrName, regPW, regPWconfirm,regGrade,regSpeciality;
    private Button regSubmit, regCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        regUesrName = findViewById(R.id.reg_et_username);
        regPW = findViewById(R.id.reg_et_pw);

        regPWconfirm = findViewById(R.id.reg_et_pwconfirm);
        regSubmit = findViewById(R.id.reg_submit);
        regCancle = findViewById(R.id.reg_cancle);

        regSubmit.setOnClickListener(new View.OnClickListener() {

            NotificationManager manager;
            NotificationChannel channel;
            String channelID ="RegResult";
            int REG_NOTIFICATION_ID=1000;
            @Override
            public void onClick(View view) {
                if (regUesrName.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "PLEASE WRITE YOUR USERNAME", Toast.LENGTH_SHORT).show();
                else if (regPW.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "PLEASE WRITE YOUR PASSWORD", Toast.LENGTH_SHORT).show();
                else if (!regPW.getText().toString().equals(regPWconfirm.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "AGAIN ERROR", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editorCompat = getSharedPreferences("UserInfor", MODE_PRIVATE).edit();
                    editorCompat.putString(regUesrName.getText().toString(), regPW.getText().toString());

                    manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        channel=new NotificationChannel(channelID,"注册信息",NotificationManager.IMPORTANCE_DEFAULT);
                        manager.createNotificationChannel(channel);
                    }
                    Notification notification=new NotificationCompat.Builder(getApplicationContext(),channelID)
                            .setSmallIcon(R.drawable.zjutback)
                            .setContentTitle("恭喜你：注册成功！")
                            .setShowWhen(true)
                            .setAutoCancel(true)
                            .build();
                    manager.notify(REG_NOTIFICATION_ID,notification);

                    editorCompat.apply();
                    finish();
                }
            }
        });
        regCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
            builder.setTitle("版本信息")
                    .setMessage("开发者：lxk\n 日期：2023.12.21\n 浙江工业大学")
                    .setPositiveButton("确定", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
