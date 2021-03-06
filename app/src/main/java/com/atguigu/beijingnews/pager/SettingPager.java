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
public class SettingPager extends BasePager {

    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","设置预加载");
        //标题
        tvTitle.setText("设置");
        //内容
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("设置");
        textView.setTextColor(Color.RED);

        //和父类的FragmentLayout结合一起
        flMain.addView(textView);
    }
}
