package io.leo.futureflash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * 用光学传感器监听外部光线的类
 */
public class LightSensorUtil {
    private SensorManager mSensorManager;
    private Sensor mLightSensor;

    private Context mContext;
    private SensorStateChangedListener mStateChangeListener;

    private OnBrightnessChangedListener mListener;
    private boolean mIsBright = false;
    class SensorStateChangedListener implements SensorEventListener{

        @SuppressLint("LongLogTag")
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT){
                boolean isBright = event.values[0] > 2000.0f? true : false ;
                Log.i("SensorStateChangedListener", String.valueOf(event.values[0]));
                if (mIsBright != isBright){
                    mIsBright = isBright;
                    if (mListener!=null)
                        mListener.onChanged(mIsBright);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private static LightSensorUtil instance = new LightSensorUtil();

    private LightSensorUtil(){}


    public void register(OnBrightnessChangedListener listener){
        this.mListener = listener;
        mSensorManager.registerListener((mStateChangeListener = new SensorStateChangedListener()),mLightSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * 该方法应在Application中被调用
     * @param context
     */
    public void init(Context context){
        if (mSensorManager!=null)return;
        this.mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    public void destorySensor(){
        if (mSensorManager!=null&&mStateChangeListener!=null){
            mSensorManager.unregisterListener(mStateChangeListener);
        }
    }

    public boolean hasLightSensor(){
        return mLightSensor!=null;
    }

    public static LightSensorUtil getInstance() {
        return instance;
    }
}
