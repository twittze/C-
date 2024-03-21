package com.example.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlanListActivity extends AppCompatActivity {


    ListView listView;
    MyDBOpenHelper dbOpenHelper;
    SQLiteDatabase dbReader,dbWriter;
    SimpleCursorAdapter adapter;
    String[] args;
    String DBname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        listView = findViewById(R.id.list_content);

        Intent intent =getIntent();
        DBname=intent.getStringExtra("DBname");

        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),DBname,null,1);
        dbReader = dbOpenHelper.getReadableDatabase();
        dbWriter = dbOpenHelper.getWritableDatabase();
        showALL();

        listView.setOnItemClickListener((parent,view,position,id)->{
            TextView itemID = view.findViewById(R.id.list_id);
            args = new String[]{itemID.getText().toString()};

            Cursor cursor=dbReader.query("agenda",null,"_id=?",args,null,null,null);
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            AlertDialog.Builder builder = new AlertDialog.Builder(PlanListActivity.this);
            builder.setTitle("daily title:"+title);
            builder.setMessage("content:"+content);

            builder.setPositiveButton("完成",(dialog,which)->{
                ContentValues contentValues = new ContentValues();
                contentValues.put("state","finish");
                dbWriter.update("agenda",contentValues,"_id=?",args);
                showALL();
            });
            builder.setNegativeButton("删除",(dialog,which)->{
                ContentValues contentValues = new ContentValues();
                contentValues.put("state","删除");
                dbWriter.delete("agenda","_id=?",args);
                showALL();
            });
            builder.show();
        });


    }
    public void showALL(){
        Cursor cursor = dbReader.query("agenda",null,null,null,null,null,"date,time");
        cursor.moveToFirst();
        String[] from = {"_id","date","time","title","state"};
        int[] to = {R.id.list_id,R.id.list_data,R.id.list_time,R.id.list_title,R.id.list_state};

        adapter = new SimpleCursorAdapter(PlanListActivity.this,R.layout.listitem,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();
        dbReader.close();
        dbWriter.close();
    }
}