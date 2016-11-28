package com.csdn.kline.trans;

import android.content.Context;
import android.graphics.Bitmap;

import com.csdn.kline.util.BitmapUtils;
import com.csdn.kline.util.JMUiUtils;
import com.squareup.picasso.Transformation;

public class CircleTransform implements Transformation {


    private Context context;

    public CircleTransform(Context context) {

        this.context = context;
    }

    /**
     * @param source :还未处理的矩形的Bitmap对象
     * @return ：返回的是处理后的圆形Bitmap对象
     */
    @Override
    public Bitmap transform(Bitmap source) {
        //1.压缩处理
        Bitmap zoomBitmp = BitmapUtils.zoom(source, JMUiUtils.dp2px(context,100), JMUiUtils.dp2px(context,100));
        //2.圆形处理
        Bitmap bitmap = BitmapUtils.circleBitmap(zoomBitmp);
        //必须要回收source，否则会报错
        source.recycle();
        return bitmap;//返回圆形的Bitmap对象
    }

    /**
     * 该方法没有什么实际意义，但是要保证其返回的值不能为null！
     *
     * @return
     */
    @Override
    public String key() {
        return "";
    }
}