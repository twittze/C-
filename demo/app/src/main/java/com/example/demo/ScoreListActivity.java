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

public class ScoreListActivity extends AppCompatActivity {
    ListView listView;
    MyDBOpenHelper dbOpenHelper;
    SQLiteDatabase dbReader,dbWriter;
    SimpleCursorAdapter adapter;
    String[] args;
    String DBname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);


        listView = findViewById(R.id.list_score);

        Intent intent =getIntent();
        DBname=intent.getStringExtra("DBname");

        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),DBname,null,1);
        dbReader = dbOpenHelper.getReadableDatabase();
        dbWriter = dbOpenHelper.getWritableDatabase();
        showALL();

        listView.setOnItemClickListener((parent,view,position,id)->{
            TextView itemID = view.findViewById(R.id.list_cishu);
            args = new String[]{itemID.getText().toString()};

            Cursor cursor=dbReader.query("scorelist",null,"_id=?",args,null,null,null);
            cursor.moveToFirst();
            Integer title = Integer.valueOf(cursor.getString(cursor.getColumnIndex("title")));

            AlertDialog.Builder builder = new AlertDialog.Builder(ScoreListActivity.this);
            builder.setTitle("分数:"+title);


            builder.setPositiveButton("完成",(dialog,which)->{
                ContentValues contentValues = new ContentValues();
                contentValues.put("state","完成");
                dbWriter.update("scorelist",contentValues,"_id=?",args);
                showALL();
            });
            builder.setNegativeButton("删除",(dialog,which)->{
                ContentValues contentValues = new ContentValues();
                contentValues.put("state","删除");
                dbWriter.delete("scorelist","_id=?",args);
                showALL();
            });
            builder.show();
        });


    }
    public void showALL(){
        Cursor cursor = dbReader.query("scorelist",null,null,null,null,null,null);
        cursor.moveToFirst();
        String[] from = {"_id","title","state"};
        int[] to = {R.id.list_cishu,R.id.list_chengji,R.id.list_state2};

        adapter = new SimpleCursorAdapter(ScoreListActivity.this,R.layout.listitem2,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();
        dbReader.close();
        dbWriter.close();
    }
    }
