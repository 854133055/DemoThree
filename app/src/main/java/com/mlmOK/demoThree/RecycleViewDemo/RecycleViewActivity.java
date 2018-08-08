package com.mlmOK.demoThree.RecycleViewDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.mlmOK.demothree.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private MRecycleAdapter recycleAdapter;

    private List<Integer> imageList;
    private int[] imageArray = new int[]{R.drawable.picutre2, R.drawable.picture1, R.drawable.picutre2, R.drawable.picture1, R.drawable.picutre2, R.drawable.picture1, R.drawable.picture1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        MRecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));
        recycleAdapter = new MRecycleAdapter(this, initTagList());
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.mOnClick(recycleAdapter);
    }

    private List<Integer> initTagList() {
        imageList = new ArrayList<>();
        for (int i : imageArray) {
            imageList.add(i);
        }
        return imageList;
    }


}
