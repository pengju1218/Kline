package com.csdn.kline.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.csdn.kline.R;


/**
 * 自定义时长的Toast
 *
 * @author DexYang
 */
public class CToast {


    private static  KeyTextView tv1=null, tv2, tv3, tv4;

    public static   CToast makeText(Context context, float[] text, int duration) {
        CToast result = new CToast(context);

        LinearLayout mLayout = new LinearLayout(context);


        tv1=new KeyTextView(context);
        tv1.setName("开盘");


        tv2 = new KeyTextView(context);
        tv2.setName("收盘");

        tv3 = new KeyTextView(context);
        tv3.setName("最高");

        tv4 = new KeyTextView(context);
        tv4.setName("最低");

        tv1.setValue(text[0]+"");
        tv2.setValue(text[1]+"");
        tv3.setValue(text[2]+"");
        tv4.setValue(text[3]+"");
        //  mLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mLayout.setBackgroundResource(R.drawable.toast_bg);

        //int w = context.getResources().getDisplayMetrics().widthPixels / 2;
        //nt h = context.getResources().getDisplayMetrics().widthPixels / 10;

        mLayout.setOrientation(LinearLayout.VERTICAL);


        mLayout.addView(tv1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mLayout.addView(tv2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mLayout.addView(tv3, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mLayout.addView(tv4, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mLayout.setPadding(20,10,20,10);


        result.mNextView = mLayout;
        result.mDuration = duration;

        return result;
    }

    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_LONG = 3500;

    private final Handler mHandler = new Handler();
    private int mDuration = LENGTH_SHORT;
    private int mGravity = Gravity.CENTER;
    private int mX, mY;
    private float mHorizontalMargin;
    private float mVerticalMargin;
    private View mView;
    private View mNextView;

    private WindowManager mWM;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();


    public CToast(Context context) {
        init(context);
    }

    /**
     * Set the view to show.
     *
     * @see #getView
     */
    public void setView(View view) {
        mNextView = view;
    }

    /**
     * Return the view.
     *
     * @see #setView
     */
    public View getView() {
        return mNextView;
    }

    /**
     * Set how long to show the view for.
     *
     * @see #LENGTH_SHORT
     * @see #LENGTH_LONG
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * Return the duration.
     *
     * @see #setDuration
     */
    public int getDuration() {
        return mDuration;
    }

    /**
     * Set the margins of the view.
     *
     * @param horizontalMargin The horizontal margin, in percentage of the
     *                         container width, between the container's edges and the
     *                         notification
     * @param verticalMargin   The vertical margin, in percentage of the
     *                         container height, between the container's edges and the
     *                         notification
     */
    public void setMargin(float horizontalMargin, float verticalMargin) {
        mHorizontalMargin = horizontalMargin;
        mVerticalMargin = verticalMargin;
    }

    /**
     * Return the horizontal margin.
     */
    public float getHorizontalMargin() {
        return mHorizontalMargin;
    }

    /**
     * Return the vertical margin.
     */
    public float getVerticalMargin() {
        return mVerticalMargin;
    }

    /**
     * Set the location at which the notification should appear on the screen.
     *
     * @see Gravity
     * @see #getGravity
     */
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mGravity = gravity;
        mX = xOffset;
        mY = yOffset;
    }

    /**
     * Get the location at which the notification should appear on the screen.
     *
     * @see Gravity
     * @see #getGravity
     */
    public int getGravity() {
        return mGravity;
    }

    /**
     * Return the X offset in pixels to apply to the gravity's location.
     */
    public int getXOffset() {
        return mX;
    }

    /**
     * Return the Y offset in pixels to apply to the gravity's location.
     */
    public int getYOffset() {
        return mY;
    }

    /**
     * schedule handleShow into the right thread
     */
    public void show() {
        mHandler.post(mShow);

        if (mDuration > 0) {
            mHandler.postDelayed(mHide, mDuration);
        }
    }

    /**
     * schedule handleHide into the right thread
     */
    public void hide() {
        mHandler.post(mHide);
    }

    private final Runnable mShow = new Runnable() {
        public void run() {
            handleShow();
        }
    };

    private final Runnable mHide = new Runnable() {
        public void run() {
            handleHide();
        }
    };

    private void init(Context context) {
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = android.R.style.Animation_Toast;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");

        mWM = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
    }


    private void handleShow() {

        if (mView != mNextView) {
            // remove the old view if necessary
            handleHide();
            mView = mNextView;
//            mWM = WindowManagerImpl.getDefault();
            final int gravity = mGravity;
            mParams.gravity = gravity;
            if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }
            mParams.x = mX;
            mParams.y = mY;
            mParams.verticalMargin = mVerticalMargin;
            mParams.horizontalMargin = mHorizontalMargin;
            if (mView.getParent() != null) {
                mWM.removeView(mView);
            }
            mWM.addView(mView, mParams);
        }
    }

    private void handleHide() {
        if (mView != null) {
            if (mView.getParent() != null) {
                mWM.removeView(mView);
            }
            mView = null;
        }
    }


}