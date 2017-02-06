package com.atguigu.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BasePager;

/**
 * Created by 皇 上 on 2017/2/5.
 */
public class HomePager extends BasePager {
    public HomePager(Context context) {
        super(context);
    }

    //重写initData方法

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","主页预加载");
        //设置标题
        tv_title.setText("主页");
        //内容

        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("主页面");
        textView.setTextColor(Color.RED);


        //和父类的FragmentLayout结合一起
        fl_main.addView(textView);
    }
}
