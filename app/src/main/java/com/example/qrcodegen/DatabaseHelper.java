    package com.example.qrcodegen;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    import com.example.qrcodegen.model.AttendanceRecord;

    import java.security.MessageDigest;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;

    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "school_db.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE teachers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "login TEXT UNIQUE NOT NULL, " +
                    "password_hash TEXT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "subject TEXT)");

                db.execSQL("CREATE TABLE students (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "login TEXT UNIQUE NOT NULL, " +
                        "password_hash TEXT NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "class TEXT NOT NULL)");

            db.execSQL("CREATE TABLE attendance (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "teacher_id INTEGER NOT NULL, " +
                    "student_id INTEGER NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "FOREIGN KEY(teacher_id) REFERENCES teachers(id), " +
                    "FOREIGN KEY(student_id) REFERENCES students(id))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS teachers");
            db.execSQL("DROP TABLE IF EXISTS students");
            db.execSQL("DROP TABLE IF EXISTS attendance");
            onCreate(db);
        }

        public String hashPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public boolean registerTeacher(String login, String password, String name, String subject) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("login", login);
            values.put("password_hash", hashPassword(password));
            values.put("name", name);
            values.put("subject", subject);
            long result = db.insert("teachers", null, values);
            return result != -1;
        }

        public boolean registerStudent(String login, String password, String name, String studentClass) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("login", login);
            values.put("password_hash", hashPassword(password));
            values.put("name", name);
            values.put("class", studentClass);
            long result = db.insert("students", null, values);
            return result != -1;
        }

        public boolean checkTeacherParol(String login, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor cursor = db.query(
                    "teachers",
                    new String[]{"password_hash"},
                    "login = ?",
                    new String[]{login},
                    null, null, null)) {

                if (cursor.moveToFirst()) {
                    String storedHash = cursor.getString(0);
                    return hashPassword(password).equals(storedHash);
                }
                return false;
            }
        }

        public boolean checkStudentParol(String login, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor cursor = db.query(
                    "students",
                    new String[]{"password_hash"},
                    "login = ?",
                    new String[]{login},
                    null, null, null)) {

                if (cursor.moveToFirst()) {
                    String storedHash = cursor.getString(0);
                    return hashPassword(password).equals(storedHash);
                }
                return false;
            }
        }

        public boolean addAttendance(int teacherId, int studentId) {
            SQLiteDatabase db = this.getWritableDatabase();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            ContentValues values = new ContentValues();
            values.put("teacher_id", teacherId);
            values.put("student_id", studentId);
            values.put("date", date);
            long result = db.insert("attendance", null, values);
            return result != -1;
        }

        public String getStudentNameById(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(
                    "students",
                    new String[]{"name"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null
            );
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
            cursor.close();
            return "Неизвестный студент";
        }

        public String getTeacherNameById(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(
                    "teachers",
                    new String[]{"name"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null
            );
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                cursor.close();
                return name;
            }
            cursor.close();
            return "Неизвестный учитель";
        }

        public int getTeacherIdByLogin(String login) {
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor cursor = db.query(
                    "teachers",
                    new String[]{"id"},
                    "login = ?",
                    new String[]{login},
                    null, null, null)) {

                if (cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return -1; // Return -1 if teacher not found
            }
        }

        public int getStudentIdByLogin(String login) {
            SQLiteDatabase db = this.getReadableDatabase();
            try (Cursor cursor = db.query(
                    "students",
                    new String[]{"id"},
                    "login = ?",
                    new String[]{login},
                    null, null, null)) {

                if (cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
                return -1;
            }
        }

        public void createTestData() {
            registerTeacher("teacherPogova", "teacher1pass", "Елена Погова", "Математика");
            registerTeacher("teacherDevyatkina", "teacher2pass", "Ольга Девяткина", "Физика");

            registerStudent("studentPetrov", "student1pass", "Иван Петров", "10-А");
            registerStudent("studentSidorova", "student2pass", "Мария Сидорова", "10-А");
            registerStudent("studentIvanov", "student3pass", "Алексей Иванов", "10-А");
            registerStudent("studentSmirnova", "student4pass", "Елена Смирнова", "10-А");
            registerStudent("studentKuznetsov", "student5pass", "Дмитрий Кузнецов", "10-А");

            registerStudent("studentVasilieva", "student6pass", "Анна Васильева", "11-Б");
            registerStudent("studentPavlov", "student7pass", "Сергей Павлов", "11-Б");
            registerStudent("studentNikolaeva", "student8pass", "Ольга Николаева", "11-Б");
            registerStudent("studentFedorov", "student9pass", "Михаил Фёдоров", "11-Б");
            registerStudent("studentKozlova", "student10pass", "Наталья Козлова", "11-Б");
        }

        public List<AttendanceRecord> getTodaysAttendanceRecords(int teacherId) {
            List<AttendanceRecord> records = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            String query = "SELECT a.id, s.name AS student_name, s.class AS student_class, a.date " +
                    "FROM attendance a " +
                    "JOIN students s ON a.student_id = s.id " +
                    "WHERE a.teacher_id = ? AND date(a.date) = date(?) " +
                    "ORDER BY a.date DESC";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(teacherId), currentDate});

            if (cursor.moveToFirst()) {
                do {
                    AttendanceRecord record = new AttendanceRecord();
                    record.setId(cursor.getInt(0));
                    record.setStudentName(cursor.getString(1));
                    record.setStudentClass(cursor.getString(2)); // <- класс ученика
                    record.setDate(cursor.getString(3));
                    records.add(record);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return records;
        }
    }
