package com.example.qrcodegen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainTeacher extends AppCompatActivity {

    Button scanButton, leaveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_teacher);


        scanButton = findViewById(R.id.scan_button);
        leaveBtn = findViewById(R.id.leave);

        scanButton.setOnClickListener(v ->
                startActivity(new Intent(MainTeacher.this, ScanActivity.class)));

        leaveBtn.setOnClickListener(v ->
                startActivity(new Intent(MainTeacher.this, LoginActivity.class)));
    }
}