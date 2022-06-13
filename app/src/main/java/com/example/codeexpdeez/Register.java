package com.example.codeexpdeez;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner rank;
    private EditText name;
    private Spinner unit;
    private Spinner coy;
    private Button register;

    private FirebaseAuth auth;

    String[] units = new String[]{"-- Choose Unit --","CDO", "1SIR", "2SIR", "3SIR", "5SIR", "6SIR", "8SIR", "9SIR", "1GDS", "3GDS", "40SAR", "41SAR", "42SAR", "48SAR", "20SA", "21SA", "23SA", "24SA", "30SCE", "35SCE", "36SCE", "38SCE", "39SCE", "1SIG", "6SIG", "9SIG", "MPEU"};
    String[] coys = new String[]{"-- Choose Company --", "Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet", "Kilo", "Lima", "Mike", "November", "Oscar", "Papa", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey", "X-ray", "Yankee", "Zulu"};
    String[] ranks = new String[]{"-- Choose Rank --", "GEN", "LG","MG", "BG", "COL", "SLTC", "LTC", "MAJ", "CPT", "LTA", "2LT", "CWO", "SWO", "MWO", "1WO", "2WO", "3WO", "MSG", "SSG", "1SG", "2SG", "3SG", "CFC", "CPL", "LCP", "PFC", "PTE", "REC"};

    String unitSel;
    String coySel;
    String rankSel;

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


        //For dropdown menu for Unit
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, units);
        unit.setAdapter(unitAdapter);
        unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitSel = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //For dropdown menu for Company
        ArrayAdapter<String> coyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, coys);
        coy.setAdapter(coyAdapter);
        coy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                coySel = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //For dropdown menu for Rank
        ArrayAdapter<String> rankAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ranks);
        rank.setAdapter(rankAdapter);
        rank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rankSel = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_name = name.getText().toString();
                if (TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)||TextUtils.isEmpty(txt_name)){
                    Toast.makeText(Register.this,"Please enter email/password/name",Toast.LENGTH_SHORT).show();
                } else if (txt_password.length()<6){
                    Toast.makeText(Register.this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                } else if (rankSel.equals("-- Choose Rank --")){
                    Toast.makeText(Register.this,"Please choose rank",Toast.LENGTH_SHORT).show();
                } else if (unitSel.equals("-- Choose Unit --")){
                    Toast.makeText(Register.this,"Please choose Unit",Toast.LENGTH_SHORT).show();
                } else if (coySel.equals("-- Choose Company --")){
                    Toast.makeText(Register.this,"Please choose Company",Toast.LENGTH_SHORT).show();
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
                    user.setRank(rankSel);
                    user.setUnit(unitSel);
                    user.setCoy(coySel);
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
                String rank = snapshot.child("info").child("rank").getValue()+"";
                String privilege = snapshot.child("info").child("privilege").getValue()+"";
                String unit = snapshot.child("info").child("unit").getValue()+"";
                String coy = snapshot.child("info").child("coy").getValue()+"";
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