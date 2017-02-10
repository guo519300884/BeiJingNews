package com.atguigu.baselibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 皇 上 on 2017/2/10.
 */
public class NetCacheUtils {

    //图片请求成功
    public static final int SECUSS = 1;
    //图片请求失败
    public static final int FAIL = 2;
    private final Handler handler;
    private ExecutorService service;

    public NetCacheUtils(Handler handler) {
        this.handler = handler;
        service = Executors.newFixedThreadPool(10);
    }

    public void getBitmapFromNet(String url, int position) {

//        new Thread(new MyRunnable(url,position)).start();
        service.execute(new MyRunnable(url, position));
    }

    // 请求图片
    class MyRunnable implements Runnable {
        private final String url;
        private final int position;

        public MyRunnable(String url, int position) {
            this.url = url;
            this.position = position;
        }

        @Override
        public void run() {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(5000);
                connection.connect();
                int code = connection.getResponseCode();
                
                if(code == 200) {
                    //请求图片成功
                    InputStream is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    //成功后保存一份到本地

                    //保存一份在内存中

                   //将图片显示在控件上
                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    msg.what = SECUSS;
                    msg.arg1 = position;
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //请求图片上失败
                Message msg = Message.obtain();
                msg.what = FAIL;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        }
    }
}
