package com.example.wb.tkandroid.net;

import org.xutils.http.RequestParams;

/**
 * Created by wb on 2018/4/19.
 */

public class FlowAPI {
    // 服务器返回成功标志
    public static final String SUCCEED="1000";
    public static RequestParams getRequestParams(String uri){
        RequestParams requestParams=new RequestParams(uri);
        requestParams.setConnectTimeout(150000);
        return requestParams;
    }

    // 服务器地址
    public static String SERVER_IP = "";

    // USER - 注册短信验证码
    public static String register_code = SERVER_IP + "";
}
