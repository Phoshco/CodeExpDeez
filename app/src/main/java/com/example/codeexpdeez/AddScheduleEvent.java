package com.example.codeexpdeez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddScheduleEvent extends AppCompatActivity {

    EditText title;
    EditText details;
    Button addEvent;
    Button date;
    Button time;
    TextView showdate;
    TextView showtime;
    String selectDate;
    String selectTime;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_event);

        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        addEvent = findViewById(R.id.addEvent);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        showdate = findViewById(R.id.showdate);
        showtime = findViewById(R.id.showtime);

        SessionManager session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddScheduleEvent.this, "Please enter event date", Toast.LENGTH_LONG).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddScheduleEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String mth = ""+month;
                        if (mth.length() == 1){
                            mth = "0"+mth;
                        }
                        String dayt = ""+day;
                        if (dayt.length() == 1){
                            dayt = "0"+dayt;
                        }
                        selectDate = year+"-"+mth+"-"+dayt;
                        String show = "Selected Date: "+selectDate;
                        showdate.setText(show);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddScheduleEvent.this, "Please enter event time", Toast.LENGTH_LONG).show();
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddScheduleEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String hour = ""+i;
                        String min = ""+i1;
                        selectTime = hour+":"+min;
                        String show = "Selected time: "+selectTime;
                        showtime.setText(show);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String today = sdf.format(new Date());
                String eventSel = selectDate+" "+selectTime;

                if (TextUtils.isEmpty(title.getText().toString())||TextUtils.isEmpty(details.getText().toString())||TextUtils.isEmpty(selectDate)||TextUtils.isEmpty(selectTime)) {
                    Toast.makeText(AddScheduleEvent.this, "Please enter title/details/date/time", Toast.LENGTH_SHORT).show();
                }
                else if(eventSel.compareTo(today)<=0){
                    Toast.makeText(AddScheduleEvent.this, "Please enter a suitable date/time", Toast.LENGTH_LONG).show();
                }
                else{
                    String description = "Scheduled by: "+user.get("Rank").toString()+" "+user.get("Name").toString()+"\n"+details.getText().toString();

                    ScheduleEvent event = new ScheduleEvent();
                    event.setTitle(title.getText().toString());
                    event.setDate(eventSel);
                    event.setUnit(user.get("Unit").toString());
                    event.setCoy(user.get("Coy").toString());
                    event.setDetails(description);

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