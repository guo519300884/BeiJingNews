package com.atguigu.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.activity.MainActivity;
import com.atguigu.beijingnews.base.BaseFragment;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.pager.NewsCentenrPager;
import com.atguigu.beijingnews.utils.DensityUtil;

import java.util.List;

/**
 * Created by 皇 上 on 2017/2/5.
 */

public class LeftMenuFragment extends BaseFragment {

    private ListView listView;
    //左侧菜单对应的数据
    private List<NewsCenterBean.DataBean> datas;
    private int prePosition = 0;
    private LeftMenuFragmentAdapter adapter;

    @Override
    public View initView() {
        listView = new ListView(mContext);
        listView.setPadding(0, DensityUtil.dip2px(mContext, 40), 0, 0);
        listView.setBackgroundColor(Color.BLACK);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录位置和刷新适配器
                prePosition = position;
                adapter.notifyDataSetChanged();
                //2.点击后关闭左侧菜单
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();
                //3.切换到对应的菜单详情页面
                switchPager(prePosition);
            }
        });
        return listView;

    }
    //根据位置切换到不同的页面
    private void switchPager(int prePosition) {
        MainActivity mainActivity = (MainActivity) mContext;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsCentenrPager newsCentenrPager = contentFragment.getNewsCentenrPager();
        newsCentenrPager.switchPager(prePosition);
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    public void setData(List<NewsCenterBean.DataBean> dataBeanList) {
        this.datas = dataBeanList;
        //设置适配器
        adapter = new LeftMenuFragmentAdapter();
        listView.setAdapter(adapter);

        switchPager(prePosition);
    }

    private class LeftMenuFragmentAdapter extends BaseAdapter {
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
            TextView textView = (TextView) View.inflate(mContext, R.layout.item_leftmenu,null);
            //设置内容
            textView.setText(datas.get(position).getTitle());

            if(prePosition == position) {
                //设置高亮 红色
                textView.setEnabled(true);
            }else {
                //默认颜色 白色
                textView.setEnabled(false);
            }
            return textView;
        }
    }
}
