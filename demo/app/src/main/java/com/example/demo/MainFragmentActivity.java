package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainFragmentActivity extends AppCompatActivity {
    private ImageButton BtIndex,Bt2,Bt3,Bt4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        BtIndex=findViewById(R.id.fragment_b1);
        Bt2=findViewById(R.id.fragment_b2);
        Bt3=findViewById(R.id.fragment_b3);
        Bt4=findViewById(R.id.fragment_b4);
        BtIndex.setOnClickListener(new MyClickListener());
        Bt2.setOnClickListener(new MyClickListener());
        Bt3.setOnClickListener(new MyClickListener());
        Bt4.setOnClickListener(new MyClickListener());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_top,new MainFragment())
                .commit();
    }
    private class MyClickListener implements View.OnClickListener{
        public void onClick(View v){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            if (v.getId() == R.id.fragment_b1) {
                fragmentTransaction.add(R.id.fragment_top, new MainFragment());
            } else if (v.getId() == R.id.fragment_b2) {
                fragmentTransaction.add(R.id.fragment_top, new ListenFragment());
            } else if (v.getId() == R.id.fragment_b3) {
                fragmentTransaction.add(R.id.fragment_top, new IndexFragment());
            } else if (v.getId() == R.id.fragment_b4) {
                fragmentTransaction.add(R.id.fragment_top, new PlanFragment());
            }
            fragmentTransaction.commit();
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainFragmentActivity.this);
            builder.setTitle("版本信息")
                    .setMessage("开发者：lxk\n 日期：2023.12.21\n 浙江工业大学")
                    .setPositiveButton("确定", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}