package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.atguigu.baselibrary.Constants;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.adapter.PhotosMenuDetailPagerAdapter;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.bean.PohotosMenuBean;
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

public class PhotosMenuDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean dataBean;
//    @InjectView(R.id.listview)
//    ListView listview;
//    @InjectView(R.id.gridview)
//    GridView gridview;

    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String url;
    private List<PohotosMenuBean.DataBean.NewsBean> news;
    private PhotosMenuDetailPagerAdapter adapter;
    //是否显示listview  true:显示listview false：显示 gridview

    private boolean isShowListView = true;


    public PhotosMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
        Log.e("TAG", "PhotosMenuDetailPager PhotosMenuDetailPager()" + dataBean.getUrl());
    }


    @Override
    public View initView() {
        //图组详情页面的视图
        View view = View.inflate(mContext, R.layout.photos_menu_detail_pager, null);
        ButterKnife.inject(this, view);

        //设置下拉多少才有反应
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        //设置背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);
        //设置转圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mContext, "拉我干啥", Toast.LENGTH_SHORT).show();
                getDataFromNet();
            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + dataBean.getUrl();
        Log.e("TAG", "6666666666666666666666" + url);
        getDataFromNet();
    }


    public void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "PhotosMenuDetailPager onSuccess()联网成了" + result);
                processData(result);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "PhotosMenuDetailPager onError()失败了");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        PohotosMenuBean bean = new Gson().fromJson(result, PohotosMenuBean.class);
        news = bean.getData().getNews();
//        Log.e("TAG", "PhotosMenuDetailPager processData()" + news.get(0).getTitle());


        //设置RecyclerView的适配器
        adapter = new PhotosMenuDetailPagerAdapter(mContext, bean.getData().getNews(), recyclerview);
        recyclerview.setAdapter(adapter);

        //设置布局管理器
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));


//        if (news != null && news.size() > 0) {
//
//            //设置适配器
//            adapter = new PhotosMenuDetailPagerAdapter();
//            listview.setAdapter(adapter);
//
//        } else {
//            Toast.makeText(mContext, "没有数据了", Toast.LENGTH_SHORT).show();
//        }
    }

    public void switchListGrid(ImageButton ibSwitch) {

        if (isShowListView) {
            recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false));
            //按钮是list
            ibSwitch.setImageResource(R.drawable.icon_pic_list_type);
            isShowListView = false;
        } else {
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            //按钮是 grid
            ibSwitch.setImageResource(R.drawable.icon_pic_grid_type);
            isShowListView = true;
        }

    }


//    private class PhotosMenuDetailPagerAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return news.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                convertView = View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null);
//                viewHolder = new ViewHolder(convertView);
//                convertView.setTag(viewHolder);
//
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            PohotosMenuBean.DataBean.NewsBean newsBean = news.get(position);
//
//            Glide.with(mContext).load(Constants.BASE_URL + newsBean.getListimage()).into(viewHolder.ivIcon);
//            viewHolder.tvTitle.setText(newsBean.getTitle());
//
//
//            return convertView;
//        }
//    }
//
//    static class ViewHolder {
//        @InjectView(R.id.iv_icon)
//        ImageView ivIcon;
//        @InjectView(R.id.tv_title)
//        TextView tvTitle;
//
//        ViewHolder(View view) {
//            ButterKnife.inject(this, view);
//        }
//    }
}
