package com.example.qrcodegen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainTeacher extends AppCompatActivity {

    private Button scanButton, leaveBtn;
    private TextView profileHeader;
    private DatabaseHelper dbHelper;
    private int teacherId;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);

        scanButton = findViewById(R.id.scan_button);
        leaveBtn = findViewById(R.id.leave);
        profileHeader = findViewById(R.id.profile_header);
        dbHelper = new DatabaseHelper(this);

        teacherId = getIntent().getIntExtra("teacherId", -1);
        name = dbHelper.getTeacherNameById(teacherId);

        profileHeader.setText("Профиль преподавателя: " + name);

        scanButton.setOnClickListener(v ->
                startActivity(new Intent(MainTeacher.this, ScanActivity.class)));

        leaveBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainTeacher.this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}