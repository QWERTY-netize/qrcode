package com.example.qrcodegen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodegen.DatabaseHelper;
import com.example.qrcodegen.R;
import com.example.qrcodegen.adapter.AttendanceAdapter;
import com.example.qrcodegen.model.AttendanceRecord;

import java.util.List;

public class TableActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private DatabaseHelper dbHelper;
    private int teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        dbHelper = new DatabaseHelper(this);
        teacherId = getIntent().getIntExtra("teacherId", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnBack;
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(TableActivity.this, MainTeacher.class));
            finish();
        });

        loadAttendanceData();
    }

    private void loadAttendanceData() {
        List<AttendanceRecord> records = dbHelper.getTodaysAttendanceRecords(teacherId);
        adapter = new AttendanceAdapter(records);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}