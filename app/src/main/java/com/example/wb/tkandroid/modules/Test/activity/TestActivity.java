package com.example.wb.tkandroid.modules.Test.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wb.tkandroid.R;
import com.example.wb.tkandroid.base.BaseActivity;
import com.example.wb.tkandroid.base.OnItemClickListener;
import com.example.wb.tkandroid.modules.Test.adapter.TestAdapter;
import com.example.wb.tkandroid.modules.Test.bean.TestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {

    @BindView(R.id.recyc_list)
    RecyclerView recyc_list;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    TestAdapter adapter;
    @BindView(R.id.sm_refreshLayout)
    SmartRefreshLayout sm_refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(this,R.layout.activity_test);
        appendTopBody(R.layout.activity_top_text);
        setTopLeftDefultListener();
        setTopBarTitle("测试");
        ButterKnife.bind(this);
        initview();

        // 测试数据
        List<TestBean> list = new ArrayList<>();
        for (int i= 0;i<10;i++) {
            TestBean bean = new TestBean();
            bean.setName(i+"你好");
            list.add(bean);
        }
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        if (adapter.getData().size()>0) {
            recyc_list.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
        } else {
            recyc_list.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
        }
    }

    private void initview() {
        adapter = new TestAdapter();
        recyc_list.setLayoutManager(new LinearLayoutManager(this));
        recyc_list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener<TestBean>() {
            @Override
            public void onClick(TestBean testBean, View view, int position) {
                // 点击相应方法

            }
        });
        sm_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
            }
        });

    }


}
