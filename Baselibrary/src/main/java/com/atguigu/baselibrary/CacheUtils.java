package com.atguigu.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by 皇 上 on 2017/2/5.
 */


//缓存工具类
public class CacheUtils {


    /**
     * 保存参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取保存的参数
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 缓存的文本信息
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard 可用 就将文本缓存到sdcard中
            try {
                String fileName = MD5Encoder.encode(key);
                String dir = Environment.getExternalStorageDirectory() + "/beijingnews/text/";

                File file = new File(dir, fileName);

                File parent = file.getParentFile();

                if (!parent.exists()) {
                    parent.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }

                //保存到sdcard
                FileOutputStream filrOutputStream = new FileOutputStream(file);
                filrOutputStream.write(value.getBytes());
                filrOutputStream.flush();
                filrOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
            sp.edit().putString(key, value).commit();
        }
    }

    /**
     * 读取缓存的文本信息
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {

        String result = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard 可用 就将文本缓存到sdcard中

            try {
                String fileName = MD5Encoder.encode(key);
                String dir = Environment.getExternalStorageDirectory() + "/beijingnews/text/";

                File file = new File(dir, fileName);

                if (file.exists()) {
                    int len;
                    byte[] buffer = new byte[1024];
                    //文件输入流
                    FileInputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }

                    //转换为字符串
                    result = outputStream.toString();
                    inputStream.close();
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
            result = sp.getString(key, "");
        }
        return result;
    }
}