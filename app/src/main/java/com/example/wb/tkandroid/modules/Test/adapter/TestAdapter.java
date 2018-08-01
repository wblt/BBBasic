package com.example.wb.tkandroid.modules.Test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.wb.tkandroid.R;
import com.example.wb.tkandroid.base.BaseRecyclerViewAdapter;
import com.example.wb.tkandroid.base.BaseRecyclerViewHolder;
import com.example.wb.tkandroid.databinding.ItemTestBinding;
import com.example.wb.tkandroid.modules.Test.bean.TestBean;

/**
 * Created by wb on 2018/8/1.
 */

public class TestAdapter extends BaseRecyclerViewAdapter<TestBean> {


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_test);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<TestBean,ItemTestBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(TestBean object, int position) {
            // 这个直接复制就可以了
            binding.executePendingBindings();
            binding.tvName.setText(position+"你好");
        }
    }
}
