package com.example.wb.tkandroid.base;

import android.view.View;

/**
 * Created by wb on 2018/4/14.
 */

public interface OnItemLongClickListener<T> {
    public void onlongClick(T t, View view, int position);
}
