package io.leo.futureflash;

import android.app.Application;
import android.content.SharedPreferences;

public class FutureLightApplication extends Application {
    private static FutureLightApplication _singleTon;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        _singleTon = this;
        sharedPreferences = getSharedPreferences("FUTURELIGHT",MODE_PRIVATE);
        LightSensorUtil.getInstance().init(getApplicationContext());
    }

    public boolean isFirstLaunch(){
        return sharedPreferences.getBoolean("first",true);
    }

    public void setFirstLaunchFalse(){
        sharedPreferences.edit().putBoolean("first",false).commit();
    }

    public static FutureLightApplication getInstance(){
        return _singleTon;
    }

}
