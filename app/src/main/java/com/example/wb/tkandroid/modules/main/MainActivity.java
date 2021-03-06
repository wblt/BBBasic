package com.example.wb.tkandroid.modules.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wb.tkandroid.App;
import com.example.wb.tkandroid.R;
import com.example.wb.tkandroid.base.BaseActivity;
import com.example.wb.tkandroid.commons.utils.BBConfig;
import com.example.wb.tkandroid.modules.classes.fragment.ClassesFragment;
import com.example.wb.tkandroid.modules.home.fragment.HomeFragment;
import com.example.wb.tkandroid.modules.mine.fragment.MineFragment;

import java.io.File;


public class MainActivity extends BaseActivity {

    private Button[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(this,R.layout.activity_main);
        App.getInstance().addActivity(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        permission();
    }


    private void initView() {
        HomeFragment homeFragment = new HomeFragment();
        ClassesFragment classesFragment = new ClassesFragment();
        MineFragment mineFragment = new MineFragment();
        fragments = new Fragment[] {homeFragment,classesFragment,mineFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, classesFragment)
                .add(R.id.fragment_container, mineFragment)
                .hide(homeFragment)
                .hide(classesFragment)
                .hide(mineFragment)
                .show(homeFragment)
                .commit();
        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.btn_deliver);
        mTabs[1] = (Button) findViewById(R.id.btn_recoder);
        mTabs[2] = (Button) findViewById(R.id.btn_mine);
        mTabs[0].setSelected(true);
    }
    /**
     * on tab clicked
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_deliver:
                index = 0;
                break;
            case R.id.btn_recoder:
                index = 1;
                break;
            case R.id.btn_mine:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    // 获取权限
    private void permission() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        } else {
            initTools();
        }
    }

    private void initTools(){
        String out_file_path = BBConfig.YYW_FILE_PATH;
        File dir = new File(out_file_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    //双击后退按钮关闭应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("","KKKKKKKKKKKKKKKKKKKKKKK=" + keyCode);
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            moveTaskToBack(true);
        }else if(keyCode == KeyEvent.KEYCODE_HOME){
            // 不退出程序，进入后台
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted准许
                Toast.makeText(MainActivity.this,"已获得授权！",Toast.LENGTH_SHORT).show();
                initTools();
            } else {
                // Permission Denied拒绝
                Toast.makeText(MainActivity.this,"未获得授权！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
