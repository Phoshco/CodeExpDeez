package com.example.codeexpdeez;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText rank;
    private EditText name;
    private EditText unit;
    private EditText coy;
    private Button register;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        rank = findViewById(R.id.rank);
        name = findViewById(R.id.name);
        unit = findViewById(R.id.unit);
        coy = findViewById(R.id.coy);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Register.this,"Please enter email/password",Toast.LENGTH_SHORT).show();
                } else if (txt_password.length()<6){
                    Toast.makeText(Register.this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_email,txt_password);
                }
            }
        }
        );

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"Registered!",Toast.LENGTH_SHORT).show();
                    User user = new User();
                    user.setName(name.getText().toString());
                    user.setRank(rank.getText().toString());
                    user.setUnit(unit.getText().toString());
                    user.setCoy(coy.getText().toString());
                    /*startActivity(new Intent(Register.this, MainActivity.class));
                    finish();*/
                    loginUser(email, password, user);
                } else{
                    Toast.makeText(Register.this,"Register failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String password, User user) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(Login.this, "Logged In!",Toast.LENGTH_SHORT).show();
                    adduserdata(user);
                }
                else{
                    Toast.makeText(Register.this,"Login Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void adduserdata(User user){
        String username = FirebaseAuth.getInstance().getUid();
        DatabaseReference root = FirebaseDatabase.getInstance("https://expcode-2022-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(username);

        root.child("info").setValue(user);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SessionManager session = new SessionManager(getApplicationContext());
                String name = snapshot.child("info").child("name").getValue()+"";
                String rank = snapshot.child("info").child("name").getValue()+"";
                String privilege = snapshot.child("info").child("name").getValue()+"";
                String unit = snapshot.child("info").child("name").getValue()+"";
                String coy = snapshot.child("info").child("name").getValue()+"";
                session.createLoginSession(username, name, rank, privilege, unit, coy);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Intent i = new Intent(Register.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}