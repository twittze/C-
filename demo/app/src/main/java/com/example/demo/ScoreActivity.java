package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class ScoreActivity extends AppCompatActivity {

    EditText et_score;
    Button bt_jilu,bt_chaxun,bt_qingchu,bt_jisuan,bt_qingall;
    private MyDBOpenHelper dbOpenHelper;
    TextView tv_pj,tv_infor;
    private String userName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        et_score=findViewById(R.id.jiluchengji);
        bt_chaxun=findViewById(R.id.bt_chaxun);
        bt_jilu =findViewById(R.id.bt_jilu);

        tv_infor=findViewById(R.id.tv_infor);
        bt_qingchu=findViewById(R.id.bt_clear);
        bt_jisuan=findViewById(R.id.pingjun);
        bt_qingall=findViewById(R.id.bt_clearAll);


        SharedPreferences userinfro =getSharedPreferences("UserInfor",MODE_PRIVATE);
        userName = userinfro.getString("logUser","");
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),userName,null,1);


        bt_jisuan.setOnClickListener((view -> {
            int average = dbOpenHelper.calculateAverage();
            bt_jisuan.setText("平均值：" + average);

        }));

        bt_jilu.setOnClickListener((view -> {
            if (et_score.getText().toString().equals("")){
                tv_infor.setText("创建失败");
            }else{
            String state ="待办";
            SQLiteDatabase dbWriter =dbOpenHelper.getWritableDatabase();
            ContentValues contentValues =new ContentValues();
            contentValues.put("title",et_score.getText().toString());
            contentValues.put("state",state);

            if (dbWriter.insert("scorelist",null,contentValues)<0){
                tv_infor.setText("查询失败");
            }else {
                SQLiteDatabase dbReader = dbOpenHelper.getReadableDatabase();
                Cursor cursor = dbReader.query("scorelist",null,null,null,null,null,"_id desc","0,1");
                cursor.moveToFirst();
                Integer title = Integer.valueOf(cursor.getString(cursor.getColumnIndex("title")));
                tv_infor.setText("已加入一个成绩:\n"+title);

            }}
        }));
        bt_chaxun.setOnClickListener((view -> {
            Intent intent =new Intent(ScoreActivity.this, ScoreListActivity.class);
            intent.putExtra("DBname",userName);
            startActivity(intent);
        }));

        bt_qingchu.setOnClickListener((view -> {
            et_score.setText("");
        }));
        bt_qingall.setOnClickListener((view -> {
            dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),userName,null,1);
            dbOpenHelper.deleteAllData();
        }));


    }






}