package com.atguigu.baselibrary;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * Created by 皇 上 on 2017/2/10.
 */

public class BitmapCacheUtils {

    //网络缓存工具类
    private NetCacheUtils netCacheUtils;

    public BitmapCacheUtils(Handler handler) {
        netCacheUtils = new NetCacheUtils(handler);
    }

    /**
     * 三级缓存设计步骤：
     *   * 从内存中取图片
     *   * 从本地文件中取图片
     *        向内存中保持一份
     *   * 请求网络图片，获取图片，显示到控件上
     *      * 向内存存一份
     *      * 向本地文件中存一份
     *
     * @param url
     * @param position
     * @return
     */
    public Bitmap getBitmapFromNet(String url, int position) {

        //1.内存取图

        //2.本地取图

        //3.网络取图

        netCacheUtils.getBitmapFromNet(url, position);
        return null;
    }
}
