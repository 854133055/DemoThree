package com.mlmOK;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.mlmOK.demoThree.RecycleViewDemo.RecycleViewActivity;
import com.mlmOK.demothree.R;
import com.mlmOK.hotWheel.customView.arcView.ArcActivity;
import com.mlmOK.demoThree.bitmapDraw.BitmapActivity;
import com.mlmOK.demoThree.bottomWindow.BottonDialogActivity;
import com.mlmOK.hotWheel.customView.fluctuateView.FluctuateActivity;


import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Fresco.initialize(this);
        Button btnRecycle = findViewById(R.id.btn_recyclerview);
        btnRecycle.setOnClickListener(this);
        Button btnBitmap = findViewById(R.id.btn_bitmap);
        btnBitmap.setOnClickListener(this);
        Button btnRecycleTest = findViewById(R.id.recycle_test);
        btnRecycleTest.setOnClickListener(this);
        Button btnFluctuate = findViewById(R.id.btn_fluctuate);
        btnFluctuate.setOnClickListener(this);
        Button btnArc = findViewById(R.id.btn_arc);
        btnArc.setOnClickListener(this);
        Button btnBottomDialog = findViewById(R.id.btn_bottom_dialog);
        btnBottomDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickLog(v);
        switch (v.getId()) {
            case R.id.btn_recyclerview:
                startActivity(new RecycleViewActivity());
                break;
            case R.id.btn_bitmap:
                startActivity(new BitmapActivity());
                break;
            case R.id.btn_fluctuate:
                startActivity(new FluctuateActivity());
                break;
            case R.id.btn_arc:
                startActivity(new ArcActivity());
                break;
            case R.id.btn_bottom_dialog:
                startActivity(new BottonDialogActivity());
                break;
        }
    }

    private void startActivity(Activity goalActivity){
        Intent intent = new Intent(this,goalActivity.getClass());
        startActivity(intent);
    }

    private void clickLog(View view) {
        JSONObject object = new JSONObject();
        object.put("className", this.getLocalClassName());
        object.put("viewId", view.getId());

        Ext ext = new Ext();
        ext.keyword = String.valueOf(view.getId());
        ext.title = "点击日志";
        object.put("ext", ext);
//        Log.i("ClickLog", JSON.toJSONString(object, new SerializerFeature[]{SerializerFeature.WriteTabAsSpecial}));
    }

    public class Ext implements Serializable{
        public String title;
        public String keyword;
    }
}
