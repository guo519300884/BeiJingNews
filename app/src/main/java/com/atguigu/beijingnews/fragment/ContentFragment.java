package com.atguigu.beijingnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.activity.MainActivity;
import com.atguigu.beijingnews.base.BaseFragment;
import com.atguigu.beijingnews.base.BasePager;
import com.atguigu.beijingnews.pager.HomePager;
import com.atguigu.beijingnews.pager.NewsCentenrPager;
import com.atguigu.beijingnews.pager.SettingPager;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

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
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void InitData() {
        super.InitData();
        //对3个页面进行初始化
        initPager();
        setAdapter();


        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //默认设置为不可滑动
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                switch (checkedId) {
                    case R.id.rb_home:
//                        viewpager.setCurrentItem(0);
                        viewpager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
//                        viewpager.setCurrentItem(1);
                        //Viewpager 切换到不同页面的方法
                        viewpager.setCurrentItem(1, false);
                        //点击选中新闻页修改为可滑动
                        mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_setting:
//                        viewpager.setCurrentItem(2);
                        viewpager.setCurrentItem(2, false);
                        break;
                }
            }
        });
        //打开默认页面
        rgMain.check(R.id.rb_news);

        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        basePagers.get(1).initData();
    }

    //获取到新闻中心
    public NewsCentenrPager getNewsCentenrPager() {
        return (NewsCentenrPager) basePagers.get(1);
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            BasePager basePager = basePagers.get(position);//HomePager、NewsCenterPager,SetttingPager
            //调用initData
            basePagers.get(position).initData();//孩子的视图和父类的FrameLayout结合
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    //viewPager的适配器
    private void setAdapter() {
        viewpager.setAdapter(new MyPagerAdapter());
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePager basePager = basePagers.get(position);
            //代表这三个不同页面的实例
            View rootview = basePager.rootView;

//            basePager.initData();

            container.addView(rootview);

            return rootview;
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

    private void initPager() {
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(mContext));//主页面
        basePagers.add(new NewsCentenrPager(mContext));//新闻
        basePagers.add(new SettingPager(mContext));//设置

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
