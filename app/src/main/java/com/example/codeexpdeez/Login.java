package com.example.codeexpdeez;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_email,txt_password);
            }
            //code to retrieve the instance associated with this user
        });
    }

    // logs the user in the application if the particulars are in par with the one in Firebase
    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Logged In!",Toast.LENGTH_SHORT).show();

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

                    Intent i = new Intent(Login.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}