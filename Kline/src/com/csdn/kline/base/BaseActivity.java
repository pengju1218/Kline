package com.csdn.kline.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/23.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener {
    private static final int ACTIVITY_START = 0;
    private static final int ACTIVITY_RESUME = 1;
    private static final int ACTIVITY_STOP = 2;
    private static final int ACTIVITY_PAUSE = 3;
    private static final int ACTIVITY_DESTROY = 4;

    public int activityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getName(), "---------onCreat ");
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏锁定
        initView();
    }


    public abstract void initView();


    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * 事件的点击
     *
     * @param v
     */
    public abstract void widgetClick(View v);

    @Override
    protected void onStart() {
        super.onStart();
        activityState = ACTIVITY_START;
        Log.i(this.getClass().getName(), "---------onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        Log.i(this.getClass().getName(), "---------onResume ");
    }

    @Override
    protected void onStop() {
        super.onResume();
        activityState = ACTIVITY_STOP;
        Log.i(this.getClass().getName(), "---------onStop ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
        Log.i(this.getClass().getName(), "---------onPause ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getName(), "---------onRestart ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        Log.i(this.getClass().getName(), "---------onDestroy ");
        // AppManager.getAppManager().finishActivity(this);
    }


    /**
     * 启动下一个activiy不用传参数
     *
     * @param activity
     */
    protected void startIntentActivity(Class activity) {
        Intent intent = new Intent(BaseActivity.this, activity);
        startActivity(intent);
    }


    /**
     * 启动下一个activiy要传参数
     *
     * @param activity 要跳转的activity
     * @param map      参数
     */
    protected void startParamesActiviyt(Class activity, Map<String, Object> map) {
        Intent intent = new Intent(BaseActivity.this, activity);
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                setValueToIntent(intent, entry.getKey(), entry.getValue());
            }
        }
        startActivity(intent);
    }

    /**
     * 183         * </br><b>title : </b>       将值设置到Intent里
     * 184         * </br><b>description :</b>将值设置到Intent里
     * 185         * </br><b>time :</b>     2012-7-8 下午3:31:17
     * 186         * @param intent            Inent对象
     * 187         * @param key               Key
     * 188         * @param val               Value
     * 189
     */
    public static void setValueToIntent(Intent intent, String key, Object val) {
        if (val instanceof Boolean)
            intent.putExtra(key, (Boolean) val);
        else if (val instanceof Boolean[])
            intent.putExtra(key, (Boolean[]) val);
        else if (val instanceof String)
            intent.putExtra(key, (String) val);
        else if (val instanceof String[])
            intent.putExtra(key, (String[]) val);
        else if (val instanceof Integer)
            intent.putExtra(key, (Integer) val);
        else if (val instanceof Integer[])
            intent.putExtra(key, (Integer[]) val);
        else if (val instanceof Long)
            intent.putExtra(key, (Long) val);
        else if (val instanceof Long[])
            intent.putExtra(key, (Long[]) val);
        else if (val instanceof Double)
            intent.putExtra(key, (Double) val);
        else if (val instanceof Double[])
            intent.putExtra(key, (Double[]) val);
        else if (val instanceof Float)
            intent.putExtra(key, (Float) val);
        else if (val instanceof Float[])
            intent.putExtra(key, (Float[]) val);
    }

    /**
     * </br><b>title : </b>       使UI适配输入法
     * </br><b>description :</b>使UI适配输入法
     * </br><b>time :</b>     2012-7-17 下午10:21:26
     *
     * @param activity
     */
    public void adjustSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * dp转px
     *
     * @param dpVal
     * @return
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseActivity.this.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseActivity.this.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal
     * @return
     */
    public float px2dp(float pxVal) {
        final float scale = BaseActivity.this.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public float px2sp(float pxVal) {
        return (pxVal / BaseActivity.this.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取应用程序名称
     */
    public String getAppName() {
        try {
            PackageManager packageManager = BaseActivity.this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseActivity.this.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return BaseActivity.this.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public String getVersionName() {
        try {
            PackageManager packageManager = BaseActivity.this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseActivity.this.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
