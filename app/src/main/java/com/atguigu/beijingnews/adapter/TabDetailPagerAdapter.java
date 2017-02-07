package com.atguigu.beijingnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.baselibrary.CacheUtils;
import com.atguigu.baselibrary.Constants;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.bean.TabDetailPagerBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.atguigu.beijingnews.detailpager.TabDetailPager.ID_ARRAY;

/**
 * Created by 皇 上 on 2017/2/7.
 */

public class TabDetailPagerAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<TabDetailPagerBean.DataEntity.NewsEntity> datas;

    public TabDetailPagerAdapter(Context mContext, List<TabDetailPagerBean.DataEntity.NewsEntity> news) {
        this.mContext = mContext;
        this.datas = news;
    }

    @Override
    public int getCount() {
        return datas.size();
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
            convertView = View.inflate(mContext, R.layout.item_tab_detailpager, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置的获取对应的数据
        TabDetailPagerBean.DataEntity.NewsEntity newsEntity = datas.get(position);

        viewHolder.tvTitle.setText(newsEntity.getTitle());
        viewHolder.tvTime.setText(newsEntity.getPubdate());
        //加载图片
        Glide.with(mContext).load(Constants.BASE_URL + newsEntity.getListimage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.news_pic_default)
                .error(R.drawable.news_pic_default)
                .into(viewHolder.ivIcon);

        //重新取出保存的id
        String idArray = CacheUtils.getString(mContext, ID_ARRAY);
        //对比是否与保存的id相同
        if(idArray.contains(newsEntity.getId()+"")) {
            //点击过设置为灰色
            viewHolder.tvTitle.setTextColor(Color.GRAY);
        }else {
            //未点击的设置为黑色
            viewHolder.tvTitle.setTextColor(Color.BLACK);
        }


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
