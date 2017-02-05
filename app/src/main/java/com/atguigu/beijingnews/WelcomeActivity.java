package com.atguigu.beijingnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.atguigu.beijingnews.activity.GuideActivity;

public class WelcomeActivity extends AppCompatActivity {

    private RelativeLayout activity_welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        activity_welcome = (RelativeLayout)findViewById(R.id.activity_welcome);


        //透明度
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(2000);
        aa.setFillAfter(true);

        //旋转
        RotateAnimation ra = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);

        //缩放
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);
        sa.setFillAfter(true);
        //复合动画
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(aa);
        set.addAnimation(ra);
        set.addAnimation(sa);
        //播放动画
        activity_welcome.startAnimation(set);

        //设置监听
        set.setAnimationListener(new MyAnimationListener());


    }

    private class MyAnimationListener implements Animation.AnimationListener {
        //播放开始时
        @Override
        public void onAnimationStart(Animation animation) {

        }

        //播放结束时
        @Override
        public void onAnimationEnd(Animation animation) {

//            Toast.makeText(WelcomeActivity.this, "555555", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(WelcomeActivity.this,GuideActivity.class);
            startActivity(intent);
            finish();

        }

        //重复播放时
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
