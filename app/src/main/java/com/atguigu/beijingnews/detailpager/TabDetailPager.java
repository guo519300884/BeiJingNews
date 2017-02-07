package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.baselibrary.CacheUtils;
import com.atguigu.baselibrary.Constants;
import com.atguigu.baselibrary.DensityUtil;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.activity.NewsDetailActivity;
import com.atguigu.beijingnews.adapter.TabDetailPagerAdapter;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.bean.TabDetailPagerBean;
import com.atguigu.beijingnews.view.HorizontalScrollViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

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

    public static final String ID_ARRAY = "id_array";
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;


    ListView listview;

    HorizontalScrollViewPager viewpager;
    TextView tvTitle;
    LinearLayout llGroupPoint;

    @InjectView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;

    private String url;

    //列表数据
    private List<TabDetailPagerBean.DataEntity.NewsEntity> news;
    private TabDetailPagerAdapter adapter;
    //顶部轮播图片新闻的数据
    private List<TabDetailPagerBean.DataEntity.TopnewsBean> topnews;
    private int prePosition;
    //更多的网路请求路径
    private String moreUrl;
    //是否加载更多数据
    private boolean isLoadMore = false;

    public TabDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        //图组详情页面的视图
        View view = View.inflate(mContext, R.layout.tab_detail_pager, null);
        ButterKnife.inject(this, view);//初始化PullToRefreshListView

        View headerView = View.inflate(mContext, R.layout.header_view, null);
        viewpager = (HorizontalScrollViewPager) headerView.findViewById(R.id.viewpager);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        llGroupPoint = (LinearLayout) headerView.findViewById(R.id.ll_group_point);

        listview = pullRefreshList.getRefreshableView();

        listview.addHeaderView(headerView);

        //上滑&下拉的声音
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(mContext);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        pullRefreshList.setOnPullEventListener(soundListener);

        //设置下拉&上滑
        pullRefreshList.setOnRefreshListener(new MyOnRefreshListener());

        //设置listVIEW里的item的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获取到 Bean的对象
                TabDetailPagerBean.DataEntity.NewsEntity newsEntity = news.get(position-2);
                String title = newsEntity.getTitle();
                int ids = newsEntity.getId();

                String idArray = CacheUtils.getString(mContext, ID_ARRAY);
                //判断获取的id是否已经存在 不存在情况下才会保存

                if(!idArray.contains(ids+"")) {
                    //保存点击的item的id
                    CacheUtils.putString(mContext,ID_ARRAY,idArray+ids+"");
                    //刷新适配器
                    adapter.notifyDataSetChanged();
                }

                //跳转到新闻的浏览页面
                Intent intent = new Intent(mContext,NewsDetailActivity.class);
                intent.putExtra("url",Constants.BASE_URL+ newsEntity.getUrl());
                mContext.startActivity(intent);

            }
        });



        return view;
    }


    class MyOnRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {


        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//            Toast.makeText(mContext, "刷新", Toast.LENGTH_SHORT).show();
            isLoadMore = false;
            getDataFromNet();

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//            Toast.makeText(mContext, "加载", Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(moreUrl)) {
                isLoadMore = true;
                getMoreDataFromNet();
            } else {
                Toast.makeText(mContext, "别拉了，没东西了（没有更多数据了）", Toast.LENGTH_SHORT).show();
                pullRefreshList.onRefreshComplete();
            }
        }
    }

    //请求更多数据
    public void getMoreDataFromNet() {
        RequestParams params = new RequestParams(moreUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求多多多数据成功==TabDetailPager==" + childrenBean.getTitle());
                processData(result);
                //将下拉&上滑隐藏
                pullRefreshList.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求多多多数据失败==TabDetailPager==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
                //将下拉&上滑隐藏
                pullRefreshList.onRefreshComplete();
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

        String more = pagerBean.getData().getMore();
        if (TextUtils.isEmpty(more)) {
            moreUrl = "";
        } else {
            moreUrl = Constants.BASE_URL + more;
        }
        if (!isLoadMore) {
            //原有的数据

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
            for (int i = 0; i < topnews.size(); i++) {
                //添加到线性布局
                ImageView point = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i != 0) {
                    //设置距离左边的间距
                    params.leftMargin = DensityUtil.dip2px(mContext, 7);
                    point.setEnabled(false);
                } else {
                    point.setEnabled(true);
                }
                point.setLayoutParams(params);
                //设置指示点的背景选择器
                point.setBackgroundResource(R.drawable.point_selector);

                llGroupPoint.addView(point);
            }
        } else {
            //新加载的更多数据
            isLoadMore = false;
//            List<TabDetailPagerBean.DataEntity.NewsEntity> moreNews = pagerBean.getData().getNews();
//            news.addAll(moreNews);
            news.addAll(pagerBean.getData().getNews());

            adapter.notifyDataSetChanged();
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
