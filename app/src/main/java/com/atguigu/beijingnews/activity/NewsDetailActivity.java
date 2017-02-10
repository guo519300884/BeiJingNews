package com.atguigu.beijingnews.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.beijingnews.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends AppCompatActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_menu)
    ImageButton ibMenu;
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.ib_textsize)
    ImageButton ibTextsize;
    @InjectView(R.id.ib_share)
    ImageButton ibShare;
    @InjectView(R.id.webview)
    WebView webview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.activity_news_detail)
    LinearLayout activityNewsDetail;

    //加载新闻页的url
    private String url;
    //设置字体大小 默认正常大小
    private int currentTextSize = 2;
    //临时设置字体大小
    private int tempTextSize = 2;

    private WebSettings setting;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.inject(this);
        url = getIntent().getStringExtra("url");

        tvTitle.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        //webView 的使用
        webview.loadUrl(url);
//      webview.loadUrl("http://www.baidu.com");

        webSettings = webview.getSettings();

        //支持JavaScript 脚本语言
        webSettings.setJavaScriptEnabled(true);

        //添加缩放按钮 页面支持
        webSettings.setBuiltInZoomControls(true);

        //支持双击变换大小 页面支持
        webSettings.setUseWideViewPort(true);

        //设置监听
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.ib_back, R.id.ib_textsize, R.id.ib_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_textsize:
                showChangeTextSizeDialog();
                break;
            case R.id.ib_share:
                Toast.makeText(NewsDetailActivity.this, "分享呀", Toast.LENGTH_SHORT).show();
                showShare();
                break;
        }
    }

    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择字体大小");
        String items[] = {"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        builder.setSingleChoiceItems(items, currentTextSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempTextSize = which;
//              currentSelectTextSize = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentTextSize = tempTextSize;
                changeTextSize(currentTextSize);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    //设置字体大小
    protected void changeTextSize(int currentTextSize) {
        switch (currentTextSize) {
            case 0://超大
                webSettings.setTextZoom(200);
//                webSettings.setTextSize(WebSettings.TextSize.LARGEST);
                break;
            case 1://大
                webSettings.setTextZoom(150);
//                webSettings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2://正常
                webSettings.setTextZoom(100);
//                webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3://小
                webSettings.setTextZoom(75);
//                webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4://超小
                webSettings.setTextZoom(50);
//                webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
            default:
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
