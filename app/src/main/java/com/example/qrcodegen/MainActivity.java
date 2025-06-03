package com.example.qrcodegen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button scanButton, leaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scan_button);
        leaveBtn = findViewById(R.id.leave);

        scanButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GenerateActivity.class)));

        leaveBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }
}