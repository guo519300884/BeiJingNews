package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.baselibrary.Constants;
import com.atguigu.baselibrary.DensityUtil;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.adapter.TabDetailPagerAdapter;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.bean.TabDetailPagerBean;
import com.atguigu.beijingnews.view.HorizontalScrollViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 皇 上 on 2017/2/6.
 */
public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;

    @InjectView(R.id.listview)
    ListView listview;

    HorizontalScrollViewPager viewpager;
    TextView tvTitle;
    LinearLayout llGroupPoint;

    private String url;

    //列表数据
    private List<TabDetailPagerBean.DataEntity.NewsEntity> news;
    private TabDetailPagerAdapter adapter;
    //顶部轮播图片新闻的数据
    private List<TabDetailPagerBean.DataEntity.TopnewsBean> topnews;
    private int prePosition;

    public TabDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        //图组详情页面的视图
        View view = View.inflate(mContext, R.layout.tab_detail_pager, null);
        ButterKnife.inject(this, view);

        View headerView = View.inflate(mContext, R.layout.header_view, null);
        viewpager = (HorizontalScrollViewPager) headerView.findViewById(R.id.viewpager);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        llGroupPoint = (LinearLayout) headerView.findViewById(R.id.ll_group_point);

        listview.addHeaderView(headerView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenBean.getUrl();
        //设置数据
        getDataFromNet();
    }

    public void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求数据成功==TabDetailPager==" + childrenBean.getTitle());
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求数据失败==TabDetailPager==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {

        TabDetailPagerBean pagerBean = new Gson().fromJson(json, TabDetailPagerBean.class);
        news = pagerBean.getData().getNews();

        //设置适配器
        adapter = new TabDetailPagerAdapter(mContext, news);
        listview.setAdapter(adapter);

        //设置顶部的轮播图片新闻

        //设置 ViewPager的适配器
        topnews = pagerBean.getData().getTopnews();
        viewpager.setAdapter(new MyPagerAdapter());

        //设置监听 ViewPager的页面变化
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置标题
        tvTitle.setText(topnews.get(prePosition).getTitle());

        //删除指示点缓存
        llGroupPoint.removeAllViews();

        //添加红指示点
        for (int i = 0;i < topnews.size();i++){
            //添加到线性布局
            ImageView point = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2,ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i != 0) {
                //设置距离左边的间距
                params.leftMargin = DensityUtil.dip2px(mContext,7);
                point.setEnabled(false);
            }else {
                point.setEnabled(true);
            }
            point.setLayoutParams(params);
            //设置指示点的背景选择器
            point.setBackgroundResource(R.drawable.point_selector);

            llGroupPoint.addView(point);
        }

    }


    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //非当前页的指示点保持默认
            llGroupPoint.getChildAt(prePosition).setEnabled(false);
            //当前页的指示点设置高亮
            llGroupPoint.getChildAt(position).setEnabled(true);
            prePosition = position;
        }

        @Override
        public void onPageSelected(int position) {
            tvTitle.setText(topnews.get(position).getTitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return topnews.size();
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
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置默认的图片和联网请求
            //加载轮播图片
            Glide.with(mContext).load(Constants.BASE_URL + topnews.get(position).getTopimage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //设置默认图片
                    .placeholder(R.drawable.news_pic_default)
                    //请求失败图
                    .error(R.drawable.news_pic_default)
                    .into(imageView);
            //添加到 ViewPager中
            container.addView(imageView);

            return imageView;
        }

    }

}
