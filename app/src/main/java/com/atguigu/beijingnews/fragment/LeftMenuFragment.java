package com.atguigu.beijingnews.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BaseFragment;

/**
 * Created by 皇 上 on 2017/2/5.
 */

public class LeftMenuFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void InitData() {
        super.InitData();
        textView.setText("左141351616165");
    }

}
