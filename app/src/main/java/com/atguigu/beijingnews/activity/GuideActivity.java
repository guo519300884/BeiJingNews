package com.atguigu.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.utils.CacheUtils;
import com.atguigu.beijingnews.utils.DensityUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 皇 上 on 2017/2/5.
 */

public class GuideActivity extends AppCompatActivity {

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.btn_start_main)
    Button btnStartMain;
    @InjectView(R.id.ll_group_point)
    LinearLayout llGroupPoint;
    @InjectView(R.id.iv_red_point)
    ImageView ivRedPoint;

    private int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private int leftMagin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        initData();
    }

    /**
     * 间距 = 第1个点距离左边距离- 第0个点距离左边的距离
     * <p>
     * 红点移动的原理
     * 红点移动距离 ： 间距 = 手滑动的距离：屏幕宽 = 屏幕滑动的百分比
     * 红点移动距离 = 间距 * 屏幕滑动的百分比
     * <p>
     * 红点移动的坐标 = 起始坐标 + 红点移动距离
     */


    private void initData() {
        //设置适配器
        viewpager.setAdapter(new MyPagerAdapter());
        //监听页面的改变
        viewpager.addOnPageChangeListener(new MyOnPagerChangeListener());
//        根据页面数量添加点的数量
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this,10), DensityUtil.dip2px(this,10));
            if (i != 0) {
                //设置点的间距
                params.leftMargin = DensityUtil.dip2px(this,10);
            }
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.point_normal);
            //添加到线性布局
            llGroupPoint.addView(imageView);
        }
//        view 生命周期：测量--布局--绘制
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //间距 = 第1个点距离左边距离- 第0个点距离左边的距离
            leftMagin = llGroupPoint.getChildAt(1).getLeft() - llGroupPoint.getChildAt(0).getLeft();
            Log.e("TAG", "leftMagin==" + leftMagin);
        }
    }


    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(ids[position]);
            //背景添加到 viewPager
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当滑动了页面的时候回调
         *
         * @param position             当前滑动页面的位置
         * @param positionOffset       滑动的百分比
         * @param positionOffsetPixels 滑动的单位（像数）
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //红点移动距离 ： 间距 = 手滑动的距离：屏幕宽 = 屏幕滑动的百分比
            //红点移动距离 = 间距 * 屏幕滑动的百分比
            int maginLeft = (int) (leftMagin * positionOffset);
            //红点移动的坐标 = 起始坐标 + 红点移动距离
            maginLeft = position * leftMagin + (int) (leftMagin * positionOffset);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            params.leftMargin = maginLeft;
            ivRedPoint.setLayoutParams(params);
            Log.e("TAG", "maginLeft==" + maginLeft +
                    ",positionOffset==" + positionOffset +
                    ",positionOffsetPixels==" + positionOffsetPixels);
        }

        /**
         * 当某个页面被选中的时候回调
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            //判断是否最后一个页面
            if (position == ids.length - 1) {
                //是，显示按钮
                btnStartMain.setVisibility(View.VISIBLE);
            } else {
                //不是，隐藏按钮
                btnStartMain.setVisibility(View.GONE);
            }
        }

        /**
         * 当状态变化的时候回调
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @OnClick(R.id.btn_start_main)
    public void onClick() {
        //1.保存已进过引导页面，下次就不再进
        CacheUtils.putBoolean(this,"start_main",true);
        //2.直接进入主页面
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
