package com.example.qrcodegen.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcodegen.DatabaseHelper;
import com.example.qrcodegen.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {

    Button scanButton, leaveBtn;
    ImageView qrImage;
    TextView profileHeader;

    DatabaseHelper dbHelper;
    int studentId;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scan_button);
        leaveBtn = findViewById(R.id.leave);
        qrImage = findViewById(R.id.qr_image);
        profileHeader = findViewById(R.id.profile_header);
        dbHelper = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("studentId", -1);
        name = dbHelper.getStudentNameById(studentId);

        profileHeader.setText("Профиль ученика: " + name);

        scanButton.setOnClickListener(v -> {
            generateQR(String.valueOf(studentId));
        });

        leaveBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

    }

    private void generateQR(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            int size = 512;
            var bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            qrImage.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}

