package com.example.qrcodegen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcodegen.DatabaseHelper;
import com.example.qrcodegen.R;
import com.example.qrcodegen.ToastUtility;

public class MainTeacher extends AppCompatActivity {
    private static final int SCAN_REQUEST_CODE = 123;
    private Button scanButton, leaveBtn, tableBtn;
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

        scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainTeacher.this, ScanActivity.class);
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        });

        leaveBtn = findViewById(R.id.leave);
        leaveBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainTeacher.this, LoginActivity.class));
            finish();
        });

        tableBtn = findViewById(R.id.table);
        tableBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainTeacher.this, TableActivity.class);
            intent.putExtra("teacherId", teacherId);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            String scannedData = data.getStringExtra("SCAN_RESULT");
            try {
                int studentId = Integer.parseInt(scannedData);
                boolean success = dbHelper.addAttendance(teacherId, studentId);

                if (success) {
                    ToastUtility.showToast(this, "Посещение отмечено");
                } else {
                    ToastUtility.showToast(this, "Ошибка при отметке посещения");
                }
            } catch (NumberFormatException e) {
                ToastUtility.showToast(this, "Неверный QR-код: " + scannedData);
            }
        }
    }
}