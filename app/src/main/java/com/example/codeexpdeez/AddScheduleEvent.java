package com.example.codeexpdeez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class AddScheduleEvent extends AppCompatActivity {

    EditText title;
    EditText details;
    Button addEvent;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_event);

        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        addEvent = findViewById(R.id.addEvent);

        SessionManager session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(title.getText().toString())||TextUtils.isEmpty(details.getText().toString())) {
                    Toast.makeText(AddScheduleEvent.this, "Please enter title/details", Toast.LENGTH_SHORT).show();
                }
                else{
                    ScheduleEvent event = new ScheduleEvent();
                    event.setTitle(title.getText().toString());
                    Timestamp date = new Timestamp(System.currentTimeMillis());
                    event.setDate(date.toString());
                    event.setUnit(user.get("Unit").toString());
                    event.setCoy(user.get("Coy").toString());
                    event.setDetails(details.getText().toString());

                    new ScheduleFirebase(user.get("Unit").toString(),user.get("Coy").toString()).updateSchedule(event, new ScheduleFirebase.DataStatus() {
                        @Override
                        public void DataIsLoadedEvent(List<ScheduleEvent> events, List<String> keys) {
                        }
                        @Override
                        public void DataInserted() {
                            Toast.makeText(AddScheduleEvent.this, "The event has been added successfully into schedule", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}