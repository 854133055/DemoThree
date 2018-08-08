package com.mlmOK.demoThree.bottomWindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.mlmOK.hotWheel.utils.UIUtils;
import com.mlmOK.demothree.R;
import com.mlmOK.hotWheel.recycleView.MRecycleView;

import java.util.ArrayList;


public class BottonDialogActivity extends AppCompatActivity implements View.OnClickListener, RecycleAdapter.OnItemClickListener {

    private PopupWindow windows;
    private View parentView;
    private RecycleAdapter baseAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bottom_sheet);

        Button btnShow = findViewById(R.id.atom_sv_bottom_main_show);
        Button btnHiden = findViewById(R.id.atom_sv_bottom_main_hiden);
        Button btnChecked = findViewById(R.id.atom_sv_bottom_main_showchecked);
        btnHiden.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnChecked.setOnClickListener(this);



        parentView = LayoutInflater.from(this).inflate(R.layout.fragment_bottom_window, null);
        MRecycleView recycleView = parentView.findViewById(R.id.atom_sv_bottom_listview);
        baseAdapter1 = new RecycleAdapter(this, new ArrayList<String>(), false);
        recycleView.setAdapter(baseAdapter1);
        recycleView.initDivider(this);
        baseAdapter1.setOnItemClickListener(this);
        windows = new PopupWindow(parentView, ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(this, 250));
        windows.setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.atom_sv_bottom_main_show:
                windows.showAtLocation(parentView, Gravity.BOTTOM,0,0);
                break;
            case R.id.atom_sv_bottom_main_hiden:
                windows.dismiss();
                break;
            case R.id.atom_sv_bottom_main_showchecked:
                for (Integer integer : baseAdapter1.getCheckedItemArray()) {
                    Log.d("atom_sv", "点击位置" + integer);
                }
        }
    }

    @Override
    public void onRecycleViewItemClick() {

    }
}

