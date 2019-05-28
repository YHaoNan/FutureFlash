package io.leo.futureflash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {
    private TextView mTitle;
    private TextView mText;
    private ImageButton mNextPage;
    private RelativeLayout mBackground;

    private BackgroundGradualChangeThread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FutureLightApplication.getInstance().isFirstLaunch()){
            toMainActivity();
        }
        setContentView(R.layout.activity_guide);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bindView();
        step1();
    }

    private void step1() {
        SomeUtils.changeTextWithAnimate("我来自未来",mTitle);
        SomeUtils.changeTextWithAnimate("我是一个来自未来的手电筒，采用巨硬风格设计。\n我会默默地陪着你，你不需要我的时候，我不会主动出现，当你某一天突然身处黑暗，我会奋不顾身地站出来保护你。就算把我燃尽。\n也许我，只是一道微光，却想要给你灿烂的光芒～",mText);
        mNextPage.setOnClickListener(v -> {
            step2();
        });
    }

    private void step2() {
        SomeUtils.changeTextWithAnimate("如何使用我",mTitle);
        SomeUtils.changeTextWithAnimate("我的操作非常简单，我全身没有任何一个开关，只能通过屏幕写一些文字和你交流。\n当你想打开我，首先你要先用其他的手电筒对着我照射。这个过程称为能量转换。\n当我感受到其他手电筒的能量时，我就会发亮。而当你把其他手电筒拿开时，我就会熄灭。\n也就是说要点亮我，你至少还需要拿一个手电筒。",mText);
        mNextPage.setOnClickListener(v -> {
            step3();
        });
    }

    private void step3() {
        SomeUtils.changeTextWithAnimate("我依赖的",mTitle);
        SomeUtils.changeTextWithAnimate("我需要您的手机有光学传感器和闪光灯，一会儿我会检查下有没有。\n还有就是，我依赖你～\n好了，差不多了，点击右下角开始使用吧。",mText);
        mNextPage.setOnClickListener(v -> {
            FutureLightApplication.getInstance().setFirstLaunchFalse();
            toMainActivity();
        });
    }

    private void toMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.close();
    }

    private void bindView() {
        mTitle = findViewById(R.id.ag_title);
        mText = findViewById(R.id.ag_text);
        mNextPage = findViewById(R.id.ag_next);
        mBackground = findViewById(R.id.ag_bg);
        thread = new BackgroundGradualChangeThread(mBackground,100);
        thread.start();
    }


}
