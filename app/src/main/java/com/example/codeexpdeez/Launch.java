package com.example.codeexpdeez;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Launch extends AppCompatActivity {

    private Button register;
    private Button login;

    TextView textViewMsg;

    final String node = "current_msg";
    DatabaseReference mRootDatabaseRef;
    DatabaseReference mNodeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){

            String uid = FirebaseAuth.getInstance().getUid();
            DatabaseReference root = FirebaseDatabase.getInstance("https://expcode-2022-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(uid);

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    SessionManager session = new SessionManager(getApplicationContext());
                    String name = snapshot.child("info").child("name").getValue()+"";
                    String rank = snapshot.child("info").child("rank").getValue()+"";
                    String privilege = snapshot.child("info").child("privilege").getValue()+"";
                    String unit = snapshot.child("info").child("unit").getValue()+"";
                    String coy = snapshot.child("info").child("coy").getValue()+"";
                    String credit = snapshot.child("info").child("credit").getValue()+"";

                    session.createLoginSession(uid, name, rank, privilege, unit, coy, credit);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            Intent i = new Intent(Launch.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        textViewMsg = findViewById(R.id.textViewMesg);
        mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mNodeRef = mRootDatabaseRef.child(node);

        mNodeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String change = snapshot.getValue(String.class);
                textViewMsg.setText(change);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        mNodeRef.setValue(sdf.format(new Date()));


        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(Launch.this,Register.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Launch.this,Login.class));
            }
        });

    }
}