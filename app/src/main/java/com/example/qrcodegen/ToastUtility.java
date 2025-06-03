package com.example.qrcodegen;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToastUtility {
    public static void showToast(AppCompatActivity activity, String message) {
        Toast toast = new Toast(activity);
        View customView = activity.getLayoutInflater().inflate(R.layout.custom_toast_purple, null);
        TextView textView = customView.findViewById(R.id.toast_message);
        textView.setText(message);
        toast.setView(customView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -0);
        toast.show();
    }
}
