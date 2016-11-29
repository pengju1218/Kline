package com.csdn.kline;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.csdn.kline.listener.CoupleChartGestureListener;
import com.csdn.kline.util.ConstantTest;
import com.csdn.kline.util.DataParse;
import com.csdn.kline.view.MaloBarChart;
import com.csdn.kline.view.MaloCombinedChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/26.
 */

public class Test extends Activity {


    private com.csdn.kline.view.MaloCombinedChart combinedchart;
    private com.csdn.kline.view.MaloBarChart barchart;
    private DataParse mData;

    private void getOffLineData() {
           /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);

        mData.getKLineDatas();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t);
        this.barchart = (MaloBarChart) findViewById(R.id.barchart);
        this.combinedchart = (MaloCombinedChart) findViewById(R.id.combinedchart);

        getOffLineData();
       // barchart.setData(mData);
        //mbinedchart.setBarChart(barchart);
        combinedchart.setAllData(mData,barchart);

       // setOffset();



        CoupleChartGestureListener seltect = new CoupleChartGestureListener(combinedchart, new Chart[]{barchart});

        CoupleChartGestureListener seltect2 = new CoupleChartGestureListener(barchart, new Chart[]{combinedchart});

        // 将K线控的滑动事件传递给交易量控件
        combinedchart.setOnChartGestureListener(seltect);
        // 将交易量控件的滑动事件传递给K线控件
        barchart.setOnChartGestureListener(seltect2);









/*
        Intent intent = getIntent();

        String okse = intent.getStringExtra("ok");

        int oks = intent.getIntExtra("intel", 0);

        Toast.makeText(Test.this, okse + "-----" + oks, Toast.LENGTH_SHORT).show();*/

    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = combinedchart.getViewPortHandler().offsetLeft();
        float barLeft = barchart.getViewPortHandler().offsetLeft();
        float lineRight = combinedchart.getViewPortHandler().offsetRight();
        float barRight = barchart.getViewPortHandler().offsetRight();
        float barBottom = barchart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            combinedchart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            combinedchart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barchart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
        handler.sendEmptyMessageDelayed(0, 300);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            barchart.setAutoScaleMinMaxEnabled(true);
            combinedchart.setAutoScaleMinMaxEnabled(true);

            combinedchart.notifyDataSetChanged();
            barchart.notifyDataSetChanged();

            combinedchart.invalidate();
            barchart.invalidate();

        }
    };

}
