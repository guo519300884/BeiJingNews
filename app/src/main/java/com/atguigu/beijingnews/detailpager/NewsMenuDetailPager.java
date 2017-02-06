package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 皇 上 on 2017/2/6.
 */

public class NewsMenuDetailPager extends MenuDetailBasePager {

    //新闻详情页数据
    private final List<NewsCenterBean.DataBean.ChildrenBean> childrenData;

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.indicator)
    TabPageIndicator indicator;
    //页签页面集合
    private ArrayList<TabDetailPagers> tabDetailPagers;

    public NewsMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);
        this.childrenData = dataBean.getChildren();
    }

    @Override
    public View initView() {
        //新闻详情页面的视图
        View view = View.inflate(mContext, R.layout.news_menu_detail_pager, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //准备数据
        tabDetailPagers = new ArrayList<>();
        //根据数据的数量创建相应数量的TabDetailPager,并且将数据传入到对象中
        for (int i = 0; i < childrenData.size(); i++) {
            tabDetailPagers.add(new TabDetailPagers(mContext, childrenData.get(i)));
        }
        //设置适配器
        viewpager.setAdapter(new MyPagerAdapter());

        //设置适配器后，监听页面的变化 用TabPagerIndicator
        indicator.setViewPager(viewpager);

    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return childrenData.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPagers tabDetailPager = tabDetailPagers.get(position);
            tabDetailPager.initData();//重点记忆
            View rootView = tabDetailPager.rootView;
            container.addView(rootView);
            return rootView;
        }
    }
}
