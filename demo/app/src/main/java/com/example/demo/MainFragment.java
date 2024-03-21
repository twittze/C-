package com.example.demo;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button completeButton;
    private Button Btlisten;
    private  TextView completeStatusListen;
    private TextView completeStatusText;
    private boolean isCompleted = false;
    private boolean isCompleted1 = false;
    private String lastCheckInDate;
    private int checkInCount = 0;
    private int checkInCount1 = 0;
    private TextView countText;
    private TextView countText1;
    Button bt_jiluchengji;





    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View v =inflater.inflate(R.layout.fragment_main, container, false);

        completeButton = v.findViewById(R.id.complete_button);
        completeStatusText = v.findViewById(R.id.complete_status_text);
        Btlisten =v.findViewById(R.id.complete_button_listen);
        completeStatusListen =v.findViewById(R.id.complete_status_listen);
        countText =v.findViewById(R.id.dk_data);
        countText1 =v.findViewById(R.id.dk_listen);
        bt_jiluchengji =v.findViewById(R.id.bt_jiluchengji);


        bt_jiluchengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentscore= new Intent(getActivity().getApplicationContext(),ScoreActivity.class);
                startActivity(intentscore);
            }
        });





        String currentDate = getCurrentDate();
        String currentDate1 = getCurrentDate1();


        // 判断当前日期与上次打卡日期是否相同
        if (currentDate.equals(lastCheckInDate)) {
            // 如果相同，则表示今天已经打过卡了
            isCompleted = true;
            completeStatusText.setText("已完成打卡");


        } else {
            // 如果不相同，则表示可以进行打卡
            isCompleted = false;
            completeStatusText.setText("未完成打卡");
            countText.setText("打卡次数：" + checkInCount);
        }

        if (currentDate1.equals(lastCheckInDate)) {
            // 如果相同，则表示今天已经打过卡了
            isCompleted1 = true;
            completeStatusListen.setText("已完成打卡");
        } else {
            // 如果不相同，则表示可以进行打卡
            isCompleted1 = false;
            completeStatusListen.setText("未完成打卡");
            countText1.setText("打卡次数：" + checkInCount1);
        }






        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompleted) {
                    // 已经完成打卡
                    Toast.makeText(getContext(), "今天已经打过卡了", Toast.LENGTH_SHORT).show();
                } else {
                    // 还未完成打卡
                    isCompleted = true;
                    completeStatusText.setText("单词已完成打卡");
                    lastCheckInDate = currentDate; // 更新上次打卡日期
                    checkInCount++; // 更新打卡次数
                    countText.setText("打卡次数：" + checkInCount);
                    Toast.makeText(getContext(), "已打卡", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Btlisten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompleted1) {
                    // 已经完成打卡
                    Toast.makeText(getContext(), "今天已经打过卡了", Toast.LENGTH_SHORT).show();
                } else {
                    // 还未完成打卡
                    isCompleted1 = true;
                    completeStatusListen.setText("听力已完成打卡");
                    lastCheckInDate = currentDate1; // 更新上次打卡日期
                    checkInCount1++; // 更新打卡次数
                    countText1.setText("打卡次数：" + checkInCount1);
                    Toast.makeText(getContext(), "已打卡", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return v;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
    private String getCurrentDate1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }


}