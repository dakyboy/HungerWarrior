package com.dakiiii.hungerwarrior.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dakiiii.hungerwarrior.R;

public class OrderTrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track);
        Toast.makeText(this, "Order sent", Toast.LENGTH_SHORT).show();
    }
}