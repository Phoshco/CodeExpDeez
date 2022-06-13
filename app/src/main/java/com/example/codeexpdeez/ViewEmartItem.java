package com.example.codeexpdeez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ViewEmartItem extends AppCompatActivity {
    private Spinner colourDropdown, sizeDropdown;
    private Button btnSumbit;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emart_item);

        colourDropdown = findViewById(R.id.spinnerCOlour);
        sizeDropdown = findViewById(R.id.spinnerSize);

        String[] citems = new String[]{"Red", "Black", "Blue"};
        ArrayAdapter<String> cadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, citems);
        colourDropdown.setAdapter(cadapter);

        String[] sitems = new String[]{"Red", "Black", "Blue"};
        ArrayAdapter<String> sadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sitems);
        sizeDropdown.setAdapter(sadapter);

//        itemName = // get name or id of the selecte item?

        btnSumbit = findViewById(R.id.dropdownSubmit);
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colour = String.valueOf(colourDropdown.getSelectedItem());
                String size = String.valueOf(sizeDropdown.getSelectedItem());


            }
        });


    }
}