package com.bca.weatherinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectLocationActivity extends AppCompatActivity {

    Button getMyLocationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        getMyLocationBtn = findViewById(R.id.getMyLocationBtn);

        getMyLocationBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SelectLocationActivity.this, LoadCurrentLocationActivity.class);
            startActivity(intent);
        });
    }
}