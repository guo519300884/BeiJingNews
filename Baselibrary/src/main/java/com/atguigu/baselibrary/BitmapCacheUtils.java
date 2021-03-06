package com.atguigu.baselibrary;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * Created by 皇 上 on 2017/2/10.
 */

public class BitmapCacheUtils {

    //网络缓存工具类
    private NetCacheUtils netCacheUtils;
    //本地缓存工具类
    private LocalCacheUtils localCacheUtils;
    //内存缓存工具类
    private MemoryCacheUtils memoryCacheUtils;

    public BitmapCacheUtils(Handler handler) {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
        netCacheUtils = new NetCacheUtils(handler, localCacheUtils, memoryCacheUtils);
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
        if (memoryCacheUtils != null) {
            Bitmap bitmap = memoryCacheUtils.getBitmapFromUrl(url);
            if (bitmap != null) {
                Log.e("TAG", "BitmapCacheUtils getBitmapFromNet()==内存缓存的图片成了" + position);
                return bitmap;
            }
        }

        //2.本地取图
        if (memoryCacheUtils != null) {
            Bitmap bitmap = localCacheUtils.getBitmapFromUrl(url);
            if (bitmap != null) {
                Log.e("TAG", "BitmapCacheUtils getBitmapFromNet()==本地缓存的图片成了" + position);
                return bitmap;
            }
        }

        //3.网络取图
        netCacheUtils.getBitmapFromNet(url, position);
        return null;
    }
}
