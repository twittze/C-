package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class PlanActivity extends AppCompatActivity {


     EditText et_title,et_content,et_date,et_time;
    Button bt_clear,bt_insert,bt_query,bt_ca;
    private MyDBOpenHelper dbOpenHelper;
    TextView tv_Infor;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        et_title=findViewById(R.id.et_agendaTitle);
        et_content=findViewById(R.id.et_agendaContent);
        et_date=findViewById(R.id.et_agendaDate);
        et_time=findViewById(R.id.et_agendaTime);
        bt_clear=findViewById(R.id.bt_clean);
        bt_insert=findViewById(R.id.bt_insert);
        bt_query=findViewById(R.id.bt_query);
        tv_Infor=findViewById(R.id.tv_infor);
        bt_ca=findViewById(R.id.bt_ca);

        SharedPreferences userinfro =getSharedPreferences("UserInfor",MODE_PRIVATE);
        userName = userinfro.getString("logUser","");
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),userName,null,1);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(PlanActivity.this, (v, year, mouth , dayOfMouth)->{
                    et_date.setText(new StringBuilder().append(year).append("-").append(mouth+1).append("-").append(dayOfMouth));
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(PlanActivity.this,(v, hourOfDay, minute)->{
                    et_time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
                },0,0,true);
                timePickerDialog.show();
            }
        });
        bt_insert.setOnClickListener((view -> {
            if (et_title.getText().toString().equals("") || et_date.getText().toString().equals("") || et_time.getText().toString().equals("")||et_content.getText().toString().equals("")){
                tv_Infor.setText("无法创建");

            }else {
            String state ="daiban";
            SQLiteDatabase dbWriter =dbOpenHelper.getWritableDatabase();
            ContentValues contentValues =new ContentValues();
            contentValues.put("title",et_title.getText().toString());
            contentValues.put("date",et_date.getText().toString());
            contentValues.put("time",et_time.getText().toString());
            contentValues.put("content",et_content.getText().toString());
            contentValues.put("state",state);

            if (dbWriter.insert("agenda",null,contentValues)<0){
                tv_Infor.setText("查询失败");
            }else {
                SQLiteDatabase dbReader = dbOpenHelper.getReadableDatabase();
                Cursor cursor = dbReader.query("agenda",null,null,null,null,null,"_id desc","0,1");
                cursor.moveToFirst();
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                tv_Infor.setText("已加入一个新单词:\n"+date+""+time+""+title);
            }
            }
        }));
        bt_query.setOnClickListener((view -> {
            Intent intent =new Intent(PlanActivity.this, PlanListActivity.class);
            intent.putExtra("DBname",userName);
            startActivity(intent);
        }));
        bt_clear.setOnClickListener((view -> {
            et_title.setText("");
            et_content.setText("");
            et_date.setText("");
            et_time.setText("");
        }));
        bt_ca.setOnClickListener((view -> {
            dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),userName,null,1);
            dbOpenHelper.deleteData();

        }));
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
            AlertDialog.Builder builder = new AlertDialog.Builder(PlanActivity.this);
            builder.setTitle("版本信息")
                    .setMessage("开发者：lxk\n 日期：2023.12.21\n 浙江工业大学")
                    .setPositiveButton("确定", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}