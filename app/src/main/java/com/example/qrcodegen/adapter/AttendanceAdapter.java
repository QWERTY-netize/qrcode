package com.example.qrcodegen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodegen.R;
import com.example.qrcodegen.model.AttendanceRecord;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private List<AttendanceRecord> records;

    public AttendanceAdapter(List<AttendanceRecord> records) {
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AttendanceRecord record = records.get(position);
        holder.tvStudent.setText(record.getStudentName());
        holder.tvDate.setText(record.getDate());
    }

    @Override
    public int getItemCount() { return records.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStudent, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStudent = itemView.findViewById(R.id.tvStudent);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
