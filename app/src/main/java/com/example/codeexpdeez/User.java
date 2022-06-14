package com.example.codeexpdeez;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class User {

    private String name;
    private String rank;
    private String unit;
    private String coy;
    private String credit;
    private int privilege;
    private ArrayList<String> Officers = new ArrayList<>(Arrays.asList("GEN", "LG","MG", "BG", "COL", "SLTC", "LTC", "MAJ", "CPT", "LTA", "2LT"));
    private ArrayList<String> WOSpec = new ArrayList<>(Arrays.asList("CWO", "SWO", "MWO", "1WO", "2WO", "3WO", "MSG", "SSG", "1SG", "2SG", "3SG"));

    User(){
    }

    public void setName(String name){this.name = name;}
    public void setRank(String rank){this.rank = rank;setPrivilege();}
    public void setUnit(String unit){this.unit = unit;}
    public void setCoy(String coy){this.coy = coy;}
    public void setCredit(String credit){this.credit = credit;}

    public void setPrivilege(){
        if (Officers.contains(rank)){
            this.privilege = 2;
        }
        else if (WOSpec.contains(rank)){
            this.privilege = 1;
        }
        else this.privilege = 0;
    }

    public String getName(){return name;}
    public String getRank(){return rank;}
    public String getUnit(){return unit;}
    public String getCoy(){return coy;}
    public int getPrivilege(){return privilege;}

    public String getCredit() {
        return credit;
    }
}
