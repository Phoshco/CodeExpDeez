package com.example.codeexpdeez;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddAnnouncementObject extends AppCompatActivity {
    EditText title;
    EditText message;
    Button addAnnouncement;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);

        title = findViewById(R.id.announcement_title);
        message = findViewById(R.id.announcement_message);
        addAnnouncement = findViewById(R.id.addAnnouncement);

        SessionManager session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();


        addAnnouncement.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String date = LocalDate.now().toString();
                String time = LocalTime.now().toString();
                LocalDateTime dateTime= LocalDateTime.now();
                AnnouncementObject announcement = new AnnouncementObject();
                announcement.setTitle(title.getText().toString());
                announcement.setDate(date);
                announcement.setTime(time);
                announcement.setUnit(user.get("Unit").toString());
                announcement.setCoy(user.get("Coy").toString());
                announcement.setMessage(message.getText().toString());
                new AnnouncementFirebase(user.get("Unit").toString(),user.get("Coy").toString()).updateAnnouncement(announcement, new AnnouncementFirebase.DataStatus() {
                    @Override
                    public void DataIsLoadedAnnouncements(List<AnnouncementObject> announcement, List<String> keys) {
                    }
                    @Override
                    public void DataInserted() {
                        Toast.makeText(AddAnnouncementObject.this, "The Announcement has been added successfully,", Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });
    }
}
