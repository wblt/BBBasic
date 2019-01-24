package com.example.wb.tkandroid.modules.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.wb.tkandroid.R;
import com.example.wb.tkandroid.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.top_left)
    ImageButton top_left;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        appendMainBody(this,R.layout.fragment_home);
        appendTopBody(R.layout.activity_top_icon);
        setTopBarTitle("首页");
        unbinder = ButterKnife.bind(this,view);
        initview(view);
        return view;
    }

    private void initview(View view) {
        top_left.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }

}
