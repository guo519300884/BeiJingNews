package com.atguigu.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BasePager;

/**
 * Created by 皇 上 on 2017/2/5.
 */
public class NewsCentenrPager extends BasePager {
    public NewsCentenrPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //标题
        tv_title.setText("新闻");
        //内容
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("新闻");
        textView.setTextColor(Color.RED);

        //和父类的FragmentLayout结合一起
        fl_main.addView(textView);
    }
}
