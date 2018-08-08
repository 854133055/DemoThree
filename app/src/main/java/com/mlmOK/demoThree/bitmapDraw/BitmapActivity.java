package com.mlmOK.demoThree.bitmapDraw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mlmOK.demothree.R;


public class BitmapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        final CircleWaveView circleWaveView = findViewById(R.id.circleview);
        circleWaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleWaveView.isStarting()) {
                    circleWaveView.stop();
                } else {
                    circleWaveView.start();
                }
            }
        });

    }
}
