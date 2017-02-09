package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.baselibrary.Constants;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.bean.PohotosMenuBean;
import com.bumptech.glide.Glide;
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
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.gridview)
    GridView gridview;
    private String url;
    private List<PohotosMenuBean.DataBean.NewsBean> news;
    private PhotosMenuDetailPagerAdapter adapter;

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
                ProcessData(result);
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

    private void ProcessData(String result) {
        PohotosMenuBean bean = new Gson().fromJson(result, PohotosMenuBean.class);
        news = bean.getData().getNews();
//        Log.e("TAG", "PhotosMenuDetailPager ProcessData()" + news.get(0).getTitle());

        if (news != null && news.size() > 0) {

            //设置适配器
            adapter = new PhotosMenuDetailPagerAdapter();
            listview.setAdapter(adapter);

        } else {
            Toast.makeText(mContext, "没有数据了", Toast.LENGTH_SHORT).show();
        }
    }

    private class PhotosMenuDetailPagerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            PohotosMenuBean.DataBean.NewsBean newsBean = news.get(position);

            Glide.with(mContext).load(Constants.BASE_URL + newsBean.getListimage()).into(viewHolder.ivIcon);
            viewHolder.tvTitle.setText(newsBean.getTitle());


            return convertView;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
