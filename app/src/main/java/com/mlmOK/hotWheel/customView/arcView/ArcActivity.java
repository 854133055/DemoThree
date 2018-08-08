package com.mlmOK.hotWheel.customView.arcView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mlmOK.demothree.R;

public class ArcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i("atom_arc", "1");
        setContentView(R.layout.activity_arc);
//        Log.i("atom_arc", "2");
        Log.i("atom_arc", "onCreate");

        if (savedInstanceState != null) {
            String schemeFrom = savedInstanceState.getString("schemeFrom");
            Log.i("atom_arc", "onCreate:value=" + schemeFrom);
        }

        EditText editText = findViewById(R.id.atom_sv_edit);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.d("atom_arc", "键盘事件消费：物理返回键");
                }

                return false;
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("atom_arc", "键盘事件消费1:键盘右下角完成时后收起");
                return false;
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getTvHeight(TextView tv, String source, int width){
        StaticLayout staticLayout = new StaticLayout(source, tv.getPaint(), width,
                Layout.Alignment.ALIGN_NORMAL, tv.getLineSpacingMultiplier(), 0f, false);
        return staticLayout.getHeight();
    }

    /**
     * 官方注释：
     * This hook is called whenever the content view of the screen changes
     * (due to a call to Window.setContentView or Window.addContentView).
     * 即：view的内容有变化时会回调该方法
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.i("atom_arc", "onContentChanged");
    }

    /**
     * 官方注释：
     * Called when the current Window of the activity gains or loses focus.
     * This is the best indicator of whether this activity is visible to the user.
     * The default implementation clears the key tracking state, so should always
     * be called.
     * 补充：失去焦点时在onPause和onStop方法后执行，获得焦点时在onResume之后执行
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i("atom_arc", "onWindowFocusChanged");
    }

    /**
     * 官方注释：
     * Called by the system when the device configuration changes
     * while your activity is running
     * 一些系统事件会触发该方法的回调，比如屏幕方向的改变，打开/隐藏键盘（均未验证）
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("atom_arc", "onConfigurationChanged");
    }

    /**
     * 官方注释：
     * Called when activity start-up is complete (after onStart() and
     * onRestoreInstanceState(Bundle) have been called). Applications
     * will generally not implement this method; it is intended for
     * system classes to do final initialization after application code has run
     * 可以理解为onCreate方法彻底执行完毕的回调，执行于onStart方法后
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("atom_arc", "onPostCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("atom_arc", "onStart");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("atom_arc", "onPostResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("atom_arc", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("atom_arc", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("atom_arc", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("atom_arc", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("schemeFrom", "A");
        Log.i("atom_arc", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String schemeFrom = savedInstanceState.getString("schemeFrom");
        Log.i("atom_arc", "onRestoreInstanceState:value="+schemeFrom);
    }
}
