package io.leo.futureflash;

import android.animation.ObjectAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class SomeUtils {

    public static void changeTextWithAnimate(String text, TextView tv){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0);
        alphaAnimation.setDuration(200);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv.setText(text);
                ObjectAnimator.ofFloat(tv,"alpha",0,1.0f).setDuration(200).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv.startAnimation(alphaAnimation);
    }
}
