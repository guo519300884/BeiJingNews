package com.atguigu.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnews.activity.MainActivity;
import com.atguigu.beijingnews.base.BasePager;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.detailpager.InteracMenuDetailPager;
import com.atguigu.beijingnews.detailpager.NewsMenuDetailPager;
import com.atguigu.beijingnews.detailpager.PhotosMenuDetailPager;
import com.atguigu.beijingnews.detailpager.TopicMenuDetailPager;
import com.atguigu.beijingnews.detailpager.VoteMenuDetailPager;
import com.atguigu.beijingnews.fragment.LeftMenuFragment;
import com.atguigu.beijingnews.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 皇 上 on 2017/2/5.
 */
public class NewsCentenrPager extends BasePager {
    private List<NewsCenterBean.DataBean> dataBeanList;
    private ArrayList<MenuDetailBasePager> menuDetailBasePagers;

    public NewsCentenrPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","新闻预加载");
        //显示菜单按钮
        ib_menu.setVisibility(View.VISIBLE);
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

        //联网请求
        getDataFromNet();
    }

    //联网请求数据
    public void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params,new Callback.CommonCallback<String>(){

            @Override
            public void onSuccess(String result) {
                Log.e("TAG","请求成功+++"+result);
                processData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","请求失败+++"+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished==");
            }
        });
    }

    private void processData(String json){
        //1.解析数据：手动解析（用系统的Api解析）和第三方解析json的框架（Gson,fastjson）
//        Gson gson = new Gson();
        NewsCenterBean centerBean = new Gson().fromJson(json, NewsCenterBean.class);
        dataBeanList = centerBean.getData();
        Log.e("TAG","新闻中心解析成功="+dataBeanList.get(0).getChildren().get(0).getTitle());
        //把新闻中心的数据传递给左侧菜单
        MainActivity mainActivity = (MainActivity) mContext;
        //得到左侧菜单
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        //2.绑定数据
        menuDetailBasePagers = new ArrayList<>();
        menuDetailBasePagers.add(new NewsMenuDetailPager(mainActivity));//新闻
        menuDetailBasePagers.add(new TopicMenuDetailPager(mainActivity));//专题
        menuDetailBasePagers.add(new PhotosMenuDetailPager(mainActivity));//图片
        menuDetailBasePagers.add(new InteracMenuDetailPager(mainActivity));//互动
        menuDetailBasePagers.add(new VoteMenuDetailPager(mainActivity));//投票
        //调用LeftMenuFragment的setData
        leftMenuFragment.setData(dataBeanList);

    }

    /**
     * 根据位置切换到不同的页面
     * @param prePosition
     */
    public void switchPager(int prePosition) {
        //设置标题
        tv_title.setText(dataBeanList.get(prePosition).getTitle());

        MenuDetailBasePager menuDetailBasePager = menuDetailBasePagers.get(prePosition);
        menuDetailBasePager.initData();
        //视图
        View rootview = menuDetailBasePager.rootView;
        fl_main.removeAllViews();
        fl_main.addView(rootview);

    }
}
