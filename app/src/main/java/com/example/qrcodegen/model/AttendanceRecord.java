package com.example.qrcodegen.model;

public class AttendanceRecord {
    private int id;
    private String studentName;
    private String date;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
