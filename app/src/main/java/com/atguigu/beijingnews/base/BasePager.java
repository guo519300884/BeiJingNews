package com.atguigu.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.atguigu.beijingnews.R.id.fl_main;
import static com.atguigu.beijingnews.R.id.ib_menu;
import static com.atguigu.beijingnews.R.id.tv_title;

/**
 * Created by 皇 上 on 2017/2/5.
 */

public class BasePager {

    public final Context mContext;
//    public ImageButton ib_menu;
//    public TextView tv_title;
//    public FrameLayout fl_main;

    @InjectView(tv_title)
    public TextView tvTitle;
    @InjectView(ib_menu)
    public ImageButton ibMenu;
    @InjectView(fl_main)
    public FrameLayout flMain;
    @InjectView(R.id.ib_switch)
    public ImageButton ibSwitch;

    //代表各个页面的实例化
    public View rootView;

    public BasePager(Context context) {
        this.mContext = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.basepager, null);
        ButterKnife.inject(this, view);
//        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
//        tv_title = (TextView) view.findViewById(R.id.tv_title);
//        fl_main = (FrameLayout) view.findViewById(R.id.fl_main);

        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();//开<->关
            }
        });

        return view;
    }

//    @OnClick(R.id.ib_menu)
//    public void onClick(){
//        MainActivity mainActivity = (MainActivity) mContext;
//        mainActivity.getSlidingMenu().toggle();//开<->关
//    }

    /**
     * 1.在子类重写initData方法，实现子类的视图，
     * 并且视图在该方法中和基类的Fragmelayout布局结合在一起
     * <p>
     * 2.绑定数据或者请求数据再绑定数据
     */
    public void initData() {

    }

}
