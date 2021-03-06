package com.atguigu.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.baselibrary.CacheUtils;
import com.atguigu.baselibrary.Constants;
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
        Log.e("TAG", "新闻预加载");
        //显示菜单按钮
        ibMenu.setVisibility(View.VISIBLE);
        //标题
        tvTitle.setText("新闻");
        //内容
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("新闻");
        textView.setTextColor(Color.RED);

        //和父类的FragmentLayout结合一起
        flMain.addView(textView);

        String savaJson = CacheUtils.getString(mContext, Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(savaJson)) {
            processData(savaJson);
        }

        //联网请求
        getDataFromNet();
    }

    //联网请求数据
    public void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(mContext, Constants.NEWSCENTER_PAGER_URL, result);
                Log.e("TAG", "请求成功+++" + result);
                processData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败+++" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "onFinished==");
            }
        });
    }


    private void processData(String json) {
        //1.解析数据：手动解析（用系统的Api解析）和第三方解析json的框架（Gson,fastjson）
//        Gson gson = new Gson();
//        NewsCenterBean centerBean = new Gson().fromJson(json, NewsCenterBean.class);
//        dataBeanList = centerBean.getData();
//        Log.e("TAG", "新闻中心解析成功=" + dataBeanList.get(0).getChildren().get(0).getTitle());

        NewsCenterBean centerBean = paraseJson(json);
        dataBeanList = centerBean.getData();
        Log.e("TAG", "新闻中心解析成功=" + dataBeanList.get(0).getChildren().get(0).getTitle());


        //把新闻中心的数据传递给左侧菜单
        MainActivity mainActivity = (MainActivity) mContext;
        //得到左侧菜单
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        //2.绑定数据
        menuDetailBasePagers = new ArrayList<>();
//        menuDetailBasePagers.add(new NewsMenuDetailPager(mainActivity));//新闻
        menuDetailBasePagers.add(new NewsMenuDetailPager(mainActivity, dataBeanList.get(0)));//新闻详情页
        Log.e("QQ", dataBeanList.get(0).getUrl() + "dataBeanList.get(0).getUrl()");
        menuDetailBasePagers.add(new TopicMenuDetailPager(mainActivity, dataBeanList.get(0)));//专题
        menuDetailBasePagers.add(new PhotosMenuDetailPager(mainActivity, dataBeanList.get(2)));//图片
        Log.e("QQ", dataBeanList.get(2).getUrl() + "dataBeanList.get(2)");
        menuDetailBasePagers.add(new InteracMenuDetailPager(mainActivity));//互动
        menuDetailBasePagers.add(new VoteMenuDetailPager(mainActivity));//投票
        //调用LeftMenuFragment的setData
        leftMenuFragment.setData(dataBeanList);

    }

    /**
     * 使用系统api手动解析json
     *
     * @param json
     * @return
     */

    private NewsCenterBean paraseJson(String json) {

        NewsCenterBean centerBean = new NewsCenterBean();

        //Gson 解析
        return new Gson().fromJson(json, NewsCenterBean.class);

        //手动解析
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            int retcode = jsonObject.optInt("retcode");
//            centerBean.setRetcode(retcode);
//            JSONArray data = jsonObject.optJSONArray("data");
//
//            //数据集合
//            List<NewsCenterBean.DataBean> dataBeans = new ArrayList<>();
//            centerBean.setData(dataBeans);
//
//            for (int i = 0; i < data.length(); i++) {
//                JSONObject itemObject = (JSONObject) data.get(i);
//                if (itemObject != null) {
//
//                    //集合装入数据
//                    NewsCenterBean.DataBean itemBean = new NewsCenterBean.DataBean();
//                    dataBeans.add(itemBean);
//
//                    int id = itemObject.optInt("id");
//                    itemBean.setId(id);
//                    String title = itemObject.optString("title");
//                    itemBean.setTitle(title);
//                    int type = itemObject.optInt("type");
//                    itemBean.setType(type);
//                    String url = itemObject.optString("url");
//                    itemBean.setUrl(url);
//                    String url1 = itemObject.optString("url1");
//                    itemBean.setUrl1(url1);
//                    String excurl = itemObject.optString("excurl");
//                    itemBean.setExcurl(excurl);
//                    String dayurl = itemObject.optString("dayurl");
//                    itemBean.setDayurl(dayurl);
//                    String weekurl = itemObject.optString("weekurl");
//                    itemBean.setWeekurl(weekurl);
//
//                    JSONArray children = itemObject.optJSONArray("children");
//
//                    if (children != null && children.length() > 0) {
//                        //设置children的数据
//                        List<NewsCenterBean.DataBean.ChildrenBean> childrenBeans = new ArrayList<>();
//                        itemBean.setChildren(childrenBeans);
//                        for (int j = 0; j < children.length(); j++) {
//
//                            NewsCenterBean.DataBean.ChildrenBean childrenBean = new NewsCenterBean.DataBean.ChildrenBean();
//                            //添加到集合中
//                            childrenBeans.add(childrenBean);
//                            JSONObject childrenObje = (JSONObject) children.get(j);
//                            int idc = childrenObje.optInt("id");
//                            childrenBean.setId(idc);
//                            String titlec = childrenObje.optString("title");
//                            childrenBean.setTitle(titlec);
//                            int typec = childrenObje.optInt("type");
//                            childrenBean.setType(typec);
//                            String urlc = childrenObje.optString("url");
//                            childrenBean.setUrl(urlc);
//                        }
//                    }
//
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return centerBean;
    }

    /**
     * 根据位置切换到不同的页面
     *
     * @param prePosition
     */
    public void switchPager(int prePosition) {
        //设置标题
        tvTitle.setText(dataBeanList.get(prePosition).getTitle());

        MenuDetailBasePager menuDetailBasePager = menuDetailBasePagers.get(prePosition);
        //调用
        menuDetailBasePager.initData();
        //视图
        View rootview = menuDetailBasePager.rootView;

        flMain.removeAllViews();//移除之前所有的

        flMain.addView(rootview);

        //判断是不是组图详情页
        if (prePosition == 2) {
            //是组图详情页就显示按钮
            ibSwitch.setVisibility(View.VISIBLE);
            //切换按钮样式
            ibSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotosMenuDetailPager photosMenuDetailPager = (PhotosMenuDetailPager) menuDetailBasePagers.get(2);
                    photosMenuDetailPager.switchListGrid(ibSwitch);
                }
            });
            //不是组图详情页不显示按钮
        } else {
            ibSwitch.setVisibility(View.GONE);
        }
    }


}
