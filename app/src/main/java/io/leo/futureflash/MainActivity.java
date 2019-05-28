package io.leo.futureflash;

import android.animation.ObjectAnimator;
import android.content.ClipboardManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mNoticeText;
    private RelativeLayout mBackground;
    private ImageButton mAbout;
    private FlashlightUtils flashlightUtils;
    private BackgroundGradualChangeThread backgroundGradualChangeThread;

    private Random random;

    private static final String[] openTips = {"使用另一只手电筒照射我我就会打开！","您想打开手电筒吗？用另一只手电筒照我！"
            ,"您现在一定需要一个手电筒吧，不用担心，我就是。不过如果想打开我，还需要另一只手电筒！","我就是黑暗中的一道微光，却想要给你灿烂的光芒。(如果你能先给我点光芒)"};

    private static final String[] opendTips = {"Good job!就是这样，我打开了！","恭喜你点亮了我，就算我燃尽了，我也会化为风雨，一直在黑暗中保护你！",
            "Awesome!!我想为你照亮世界！","妈咪妈咪哄！开！"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFullScreen();
        bindView();
        random = new Random();

        if (!LightSensorUtil.getInstance().hasLightSensor())
            showDontHaveSensorMessage();

        flashlightUtils = new FlashlightUtils();

        if (!flashlightUtils.hasFlashlight(this))
            showDontHaveFlashLightMessage();

        LightSensorUtil.getInstance().register((isBright) -> {
            if (isBright) {
                SomeUtils.changeTextWithAnimate(randomOpendTips(),mNoticeText);
                flashlightUtils.lightsOn(this);
            } else {
                SomeUtils.changeTextWithAnimate(randomOpenTips(),mNoticeText);
                flashlightUtils.lightsOff();
            }
        });

        SomeUtils.changeTextWithAnimate(randomOpenTips(),mNoticeText);
    }

    private void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private String randomOpenTips(){
        return openTips[random.nextInt(openTips.length)];
    }

    private String randomOpendTips(){
        return opendTips[random.nextInt(opendTips.length)];
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LightSensorUtil.getInstance().destorySensor();
        backgroundGradualChangeThread.close();
    }
    private void showDontHaveFlashLightMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sorry");
        builder.setMessage("您的设备不支持闪光灯，所以无法使用本软件");
        builder.setCancelable(false);
        builder.setNegativeButton("知道了",(iface,which)-> finish());
        builder.show();
    }
    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("关于");
        builder.setMessage("开发者：Leo LILPIG\n" +
                "这不是一个沙雕设计");
        builder.setCancelable(false);
        builder.setNegativeButton("知道了",(iface,which)->iface.dismiss());
        builder.setPositiveButton("开源repo",(iface,which)->{
            iface.dismiss();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboardManager.setText("");
        });
        builder.show();
    }
    private void showDontHaveSensorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sorry");
        builder.setMessage("您的设备不支持光学传感器，所以无法使用本软件");
        builder.setCancelable(false);
        builder.setNegativeButton("知道了",(iface,which)-> finish() );
        builder.show();
    }

    private void bindView() {
        mNoticeText = findViewById(R.id.am_notice_text);
        mBackground = findViewById(R.id.am_background);
        mAbout = findViewById(R.id.am_about);
        mAbout.setOnClickListener(v -> showAboutDialog());
        backgroundGradualChangeThread = new BackgroundGradualChangeThread(mBackground,100);
        backgroundGradualChangeThread.start();
    }


}
