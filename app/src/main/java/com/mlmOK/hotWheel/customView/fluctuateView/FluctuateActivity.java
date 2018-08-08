package com.mlmOK.hotWheel.customView.fluctuateView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mlmOK.demothree.R;

public class FluctuateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluctuate);
        final FluctuateView fluctuateView = findViewById(R.id.fluctuate);
        fluctuateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fluctuateView.start();
            }
        });
    }
}
