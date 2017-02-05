package com.atguigu.beijingnews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.fragment.ContentFragment;
import com.atguigu.beijingnews.fragment.LeftMenuFragment;
import com.atguigu.beijingnews.utils.DensityUtil;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by 皇 上 on 2017/2/5.
 */
public class MainActivity extends SlidingFragmentActivity {

    public static final String LEFTMENU_TAG = "leftmenu_tag";
    public static final String CONTENT_TAG = "content_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.设置主页面
        setContentView(R.layout.activity_main);
        //2.设置左侧菜单
        setBehindContentView(R.layout.leftmenu);
        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.leftmenu);
        //4.设置模式：左+主；左+主+右；主+右
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置滑动模式；全屏；边缘；不可滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //6.设置主页所占屏幕宽度用dp
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this, 250));
        initFragment();

    }

    private void initFragment() {
        //1.获取事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //2.替换主页和左侧滑动菜单
        ft.replace(R.id.fl_leftmenu,new LeftMenuFragment(), LEFTMENU_TAG);
        ft.replace(R.id.fl_content,new ContentFragment(), CONTENT_TAG);
        //3.提交
        ft.commit();
        //getSupportFragmentManager().beginTransaction().
        // replace(R.id.fl_leftmenu,new LeftMunuFragment(),
        // LEFTMENU_TAG).replace(R.id.fl_content,
        // new ContentFragment(),  CONENT_TAG).commit();
    }
}

