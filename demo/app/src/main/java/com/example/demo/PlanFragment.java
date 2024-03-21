package com.example.demo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.DoubleSummaryStatistics;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btTianjia;
    private Button btNext;
    private Button btBefor;
    private TextView planDanCi;
    private SQLiteDatabase database;
    private int currentIndex = 0;
    private int totalItemCount = 0;
    private MyDBOpenHelper dbOpenHelper;

    private String userName;
    private SharedPreferences userInfor;







    public PlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        planDanCi = v.findViewById(R.id.danci);
        btNext = v.findViewById(R.id.nbt_next);
        btBefor = v.findViewById(R.id.nbt_befor);
        btTianjia = v.findViewById(R.id.tianjia);


        database = SQLiteDatabase.openDatabase("/data/data/com.example.demo/databases/lxk", null, SQLiteDatabase.OPEN_READWRITE);


        loadData();

        userInfor= getActivity().getSharedPreferences("UserInfor", Context.MODE_PRIVATE);
        userName = userInfor.getString("logUser","");
        dbOpenHelper = new MyDBOpenHelper(getActivity(), userName, null, 1);

        int count = dbOpenHelper.calculateTotal();
        totalItemCount=count;





        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= totalItemCount) {
                    currentIndex = 0;
                }
                loadData();
            }
        });



        btBefor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = totalItemCount - 1;
                }
                loadData();
            }
        });


        btTianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTianjia = new Intent(getActivity().getApplicationContext(), PlanActivity.class);
                startActivity(intentTianjia);

            }
        });


        return v;


    }
    private void loadData() {
        String[] columns = {"title", "content"};
        Cursor cursor = database.query("agenda", columns, null, null, null, null, "date,time");

            if (cursor != null && cursor.moveToPosition(currentIndex)) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            planDanCi.setText("单词："+title + " - "+"意思：" + content);
        } else {
            planDanCi.setText("No data");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}










