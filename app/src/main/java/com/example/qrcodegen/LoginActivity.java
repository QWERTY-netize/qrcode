package com.example.qrcodegen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.editTextText);
        etPassword = findViewById(R.id.editTextText2);
        btnLogin = findViewById(R.id.button);
        dbHelper = new DatabaseHelper(this);
        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            showToast("Заполните все поля");
            return;
        }

        if (dbHelper.checkTeacherParol(login, password)) {
            startMainActivity("teacher");
        } else if (dbHelper.checkStudentParol(login, password)) {
            startMainActivity("student");
        } else {
            showToast("Неверный логин или пароль");
        }
    }

    private void startMainActivity(String userType) {
        Intent intent;
        if ("teacher".equals(userType)) {
            intent = new Intent(this, MainTeacher.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
    private void showToast(String message) {
        ToastUtility.showToast(this, message);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}