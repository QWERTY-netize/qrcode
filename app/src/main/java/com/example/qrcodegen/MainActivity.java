package com.example.qrcodegen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button scanButton, generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scan_button);
        generateButton = findViewById(R.id.generate_button);

        scanButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ScanActivity.class)));

        generateButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GenerateActivity.class)));
    }
}