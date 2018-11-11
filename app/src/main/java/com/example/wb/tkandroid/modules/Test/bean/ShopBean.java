package com.example.wb.tkandroid.modules.Test.bean;

import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by wb on 2018/11/10.
 */

@Table(name = "user")
public class ShopBean {
    private int id;
    private String user_mobile;
    private String user_name;
    private String shop_name;

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

