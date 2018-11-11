package com.example.wb.tkandroid.modules.Test.activity;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wb.tkandroid.R;
import com.example.wb.tkandroid.base.BaseActivity;
import com.example.wb.tkandroid.base.OnItemClickListener;
import com.example.wb.tkandroid.commons.utils.BBConfig;
import com.example.wb.tkandroid.commons.utils.ToastUtils;
import com.example.wb.tkandroid.modules.Test.adapter.TestAdapter;
import com.example.wb.tkandroid.modules.Test.bean.RowsBean;
import com.example.wb.tkandroid.modules.Test.bean.ShopBean;
import com.example.wb.tkandroid.modules.Test.bean.TestBean;
import com.example.wb.tkandroid.net.CommonCallbackImp;
import com.example.wb.tkandroid.net.FlowAPI;
import com.example.wb.tkandroid.net.MXUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.tsz.afinal.FinalDb;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

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
    private List<RowsBean> rowsArray;
    private List<ShopBean> shopsArray;
    private int rowindex;
    private int shopindex;
    private  FinalDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(this,R.layout.activity_test);
        appendTopBody(R.layout.activity_top_text);
        setTopLeftDefultListener();
        setTopBarTitle("测试");
        ButterKnife.bind(this);
        db = FinalDb.create(this, BBConfig.YYW_FILE_PATH, "yy.db");
        String url = "https://niu.souche.com/filter_screening?parameter=area&parent_code=02272&national=0";
        RequestParams requestParams= FlowAPI.getRequestParams(url);
        MXUtils.httpGet(requestParams,new CommonCallbackImp("USER - 添加支付信息",requestParams,this){
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                JSONObject jsonObject = JSONObject.parseObject(data);
                JSONObject da = JSONObject.parseObject(jsonObject.getString("data"));
                JSONArray rowsBeanList = JSONArray.parseArray(da.getString("select_list"));
                JSONObject firs = rowsBeanList.getJSONObject(0);
                rowsArray = JSONArray.parseArray(firs.getString("rows"),RowsBean.class);
                rowindex = 0;
                handlerShop();
            }
        });
    }

    private void handlerShop() {
        if (rowindex >=rowsArray.size()) {
            return;
        }
        RowsBean bean = rowsArray.get(rowindex);
        shop(bean.getCode());
    }

    private void shop(String idd) {
        int size = 0;
        if (idd.equals("02273")) {
            size = 5000;
        } else {
            size = 1024;
        }
        String url = "https://niu.souche.com/yellowpage/shops?token=02_yxuR_eL4M981dB2&id="+idd+"-0&page=1&size="+size;
        RequestParams requestParams= FlowAPI.getRequestParams(url);
        MXUtils.httpGet(requestParams,new CommonCallbackImp("USER - 添加支付信息",requestParams,this){
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                JSONObject jsonObject = JSONObject.parseObject(data);
                JSONObject da = JSONObject.parseObject(jsonObject.getString("data"));
                shopsArray = JSONArray.parseArray(da.getString("list"),ShopBean.class);
                shopindex = 0;
                rowindex = rowindex +1;
                handlerSearch();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                // 重新请求
                handlerSearch();
            }
        });

    }

    private void handlerSearch() {
        if (shopindex == shopsArray.size()) {
            // 返回上一级
            handlerShop();
        } else {
            ShopBean bean = shopsArray.get(shopindex);
            serach(bean.getShop_name());
        }
    }

    private void serach(final String keywords) {
        String url = "https://niu.souche.com/yellowpage?token=02_yxuR_eL4M981dB2&keyword="+keywords+"&parameter=&page=1&size=1024&first=1";
        RequestParams requestParams= FlowAPI.getRequestParams(url);
        MXUtils.httpGet(requestParams,new CommonCallbackImp("USER - 添加支付信息",requestParams,this){
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                JSONObject jsonObject = JSONObject.parseObject(data);
                JSONObject da = JSONObject.parseObject(jsonObject.getString("data"));
                List<ShopBean> teempArr = JSONArray.parseArray(da.getString("list"),ShopBean.class);
                if (teempArr != null && teempArr.size()>0) {
                    for (ShopBean bean:teempArr) {
                        if (keywords.equals(bean.getShop_name())) {
                            LogUtil.i("x_log:"+"店家"+bean.getShop_name()+"，电话："+bean.getUser_mobile());
                            ShopBean shopBean = new ShopBean();
                            shopBean.setShop_name(bean.getShop_name());
                            shopBean.setUser_mobile(bean.getUser_mobile());
                            shopBean.setUser_name(bean.getUser_name());
                            db.save(shopBean);
                            break;
                        }
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shopindex = shopindex + 1;
                        handlerSearch();
                    }
                }, 3000);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handlerSearch();
                    }
                }, 3000);
            }
        });

    }


}
