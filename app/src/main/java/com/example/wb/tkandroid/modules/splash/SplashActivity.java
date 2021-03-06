package com.example.wb.tkandroid.modules.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wb.tkandroid.R;
import com.example.wb.tkandroid.base.BaseActivity;
import com.example.wb.tkandroid.modules.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
        finish();
    }
}
