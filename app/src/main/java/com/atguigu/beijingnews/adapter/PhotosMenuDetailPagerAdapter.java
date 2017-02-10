package com.atguigu.beijingnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.baselibrary.BitmapCacheUtils;
import com.atguigu.baselibrary.Constants;
import com.atguigu.baselibrary.NetCacheUtils;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.activity.PicassoSampleActivity;
import com.atguigu.beijingnews.bean.PohotosMenuBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by 皇 上 on 2017/2/9.
 */

public class PhotosMenuDetailPagerAdapter extends RecyclerView.Adapter<PhotosMenuDetailPagerAdapter.ViewHolder> {

    private final RecyclerView recyclerview;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //若成功
                case NetCacheUtils.SECUSS:
                    //获取位置
                    int position = msg.arg1;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (recyclerview.isShown()) {
                        ImageView ivIcon = (ImageView) recyclerview.findViewWithTag(position);
                        if (ivIcon != null && bitmap != null) {
                            Log.e("TAG", "PhotosMenuDetailPagerAdapter handleMessage()==图片请求成功" + position);
                            ivIcon.setImageBitmap(bitmap);
                        }
                    }
                    break;
                case NetCacheUtils.FAIL:
                    position = msg.arg1;
                    Log.e("TAG", "PhotosMenuDetailPagerAdapter handleMessage()==图片请求失败了" + position);
                    break;
            }
        }
    };

    private final Context mContext;
    private final List<PohotosMenuBean.DataBean.NewsBean> datas;
    private BitmapCacheUtils bitmapCacheUtils;

    public PhotosMenuDetailPagerAdapter(Context mContext, List<PohotosMenuBean.DataBean.NewsBean> news, RecyclerView recyclerview) {
        this.mContext = mContext;
        this.datas = news;
        this.recyclerview = recyclerview;
        bitmapCacheUtils = new BitmapCacheUtils(handler);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //根据位置的获取对应的数据
        PohotosMenuBean.DataBean.NewsBean newsEntity = datas.get(position);

        holder.tvTitle.setText(newsEntity.getTitle());
        //加载图片
//        Glide.with(mContext).load(Constants.BASE_URL + newsEntity.getListimage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.news_pic_default)
//                .error(R.drawable.news_pic_default)
//                .into(holder.ivIcon);

        //三级缓存图片
        //设置标识
        holder.ivIcon.setTag(position);
        Bitmap bitmap = bitmapCacheUtils.getBitmapFromNet(Constants.BASE_URL + newsEntity.getListimage(), position);
        //内存或本地
        if (bitmap != null) {
            holder.ivIcon.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, PicassoSampleActivity.class)
                            .putExtra("url", Constants.BASE_URL + datas.get(getLayoutPosition()).getListimage()));
                }
            });
        }
    }
}
