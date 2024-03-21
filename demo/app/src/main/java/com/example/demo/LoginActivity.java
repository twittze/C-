package com.example.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText logUserName,logPW;
    private Button btReg,btSubmit;
    private int LogResultCode=3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logUserName = findViewById(R.id.log_username);
        logPW = findViewById(R.id.log_password);
        btReg = findViewById(R.id.log_reg);
        btSubmit = findViewById(R.id.log_submit);


        btReg.setOnClickListener((view -> {
            Intent intentReg = new Intent(LoginActivity.this,RegActivity.class);
            startActivity(intentReg);
        }));

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logUserName.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("WARNING!")
                            .setMessage("PLEASE WRITE USERNAME")
                            .setPositiveButton("0K", null)
                            .show();
                }
                else {
                    SharedPreferences userInor = getSharedPreferences("UserInfor",MODE_PRIVATE);
                    if (userInor.getString(logUserName.getText().toString(),"").equals(logPW.getText().toString())){
                        SharedPreferences.Editor editor = getSharedPreferences("UserInfor",MODE_PRIVATE).edit();
                        editor.putString("logUser",logUserName.getText().toString());
                        editor.apply();
                        Intent intent = new Intent();
                        intent.putExtra("username",logUserName.getText().toString());
                        setResult(LogResultCode,intent);
                        Intent intentSubmit = new Intent(LoginActivity.this, UserActivity.class);
                        startActivity(intentSubmit);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"USERNAME AND PASSWORD ERROR",Toast.LENGTH_SHORT).show();

                }
            }

        });
    }
}