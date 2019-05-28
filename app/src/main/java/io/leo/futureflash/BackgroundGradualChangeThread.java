package io.leo.futureflash;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class BackgroundGradualChangeThread extends Thread{
    private Random random;
    private boolean isOpen = true;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            view.setBackgroundColor(Color.parseColor((String) msg.obj));
            Log.i("BackgroundGradualChangeThread", String.valueOf(msg.obj));
        }
    };

    private final int speed;
    private View view;
    private int rgb[] = {0,0,0};

    boolean isBUp = true,isGUp=true,isRUp=true;

    public BackgroundGradualChangeThread(View view,int speed){
        this.view = view;
        this.speed = speed;
        this.random = new Random();
    }


    public void close(){
        isOpen = false;
    }
    @Override
    public void run() {
        super.run();
        while (true&&isOpen){
            try {
                Thread.sleep(speed);
                if (rgb[2]<128&&isBUp){
                    rgb[2]++;
                }else if (rgb[2]==128)
                    isBUp=false;
                if (rgb[2]>0&&!isBUp){
                    rgb[2]--;
                }else if (rgb[2]==0)
                    isBUp=true;

                if (rgb[2]%2==0){
                    if (random.nextBoolean()){
                        if (rgb[1]<60&&isGUp)
                            rgb[1]++;
                        else if (rgb[1]==60)
                            isGUp=false;
                        if (rgb[1]>0&&!isGUp)
                            rgb[1]--;
                        else if (rgb[1]==0)
                            isGUp=true;
                    }else{
                        if (rgb[0]<20&&isRUp)
                            rgb[0]++;
                        else if (rgb[0]==20)
                            isRUp=false;
                        if (rgb[0]>0&&!isRUp)
                            rgb[0]--;
                        else if (rgb[0]==0)
                            isRUp=true;
                    }
                }
                Message message = handler.obtainMessage();
                String r = Integer.toHexString(rgb[0]);
                String g = Integer.toHexString(rgb[1]);
                String b = Integer.toHexString(rgb[2]);

                if (r.length()==1) r="0"+r;
                if (g.length()==1) g="0"+g;
                if (b.length()==1) b="0"+b;

                message.obj = "#"+r+g+b;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
