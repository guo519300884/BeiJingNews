package com.atguigu.beijingnews.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 皇 上 on 2017/2/5.
 */

public class ContentFragment extends BaseFragment {

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    private TextView textView;
//    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_content, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void InitData() {
        super.InitData();
        rgMain.check(R.id.rb_news);
        //对3个页面进行初始化
        initPager();
        setAdapter();
        initListener();
    }

    private void initListener() {

    }

    private void setAdapter() {

    }

    private void initPager() {
//        basePagers = new ArrayList<>();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
