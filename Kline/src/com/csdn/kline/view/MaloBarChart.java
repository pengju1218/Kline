package com.csdn.kline.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.csdn.kline.R;
import com.csdn.kline.util.DataParse;
import com.csdn.kline.util.KLineBean;
import com.csdn.kline.util.MyUtils;
import com.csdn.kline.util.VolFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/28.
 */

public class MaloBarChart extends BarChart implements OnChartValueSelectedListener {
    XAxis xAxisBar, xAxisK;
    YAxis axisLeftBar, axisLeftK;
    YAxis axisRightBar, axisRightK;
    BarDataSet barDataSet;
    float sum = 0;

    private CombinedChart combinedChart;

    public void setCombinedChart(CombinedChart combinedChart) {
        this.combinedChart = combinedChart;
      //  setOffset();
    }

    public MaloBarChart(Context context) {
        super(context);
        initChart();
    }

    public MaloBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChart();
    }

    public MaloBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initChart();
    }

    private void initChart() {
        setDescription("");
        setBackgroundColor(Color.BLACK);
        setDrawGridBackground(false);
        setDrawBarShadow(false);

        // 取消Y轴缩放动画
        setScaleYEnabled(false);

        // 自动缩放调整
        setAutoScaleMinMaxEnabled(true);

        /*
        * Y轴
        * ******************************************************************************/
        YAxis left = getAxisLeft();
        // 左侧Y轴坐标
        left.setDrawLabels(true);
        // 左侧Y轴
        left.setDrawAxisLine(true);
        // 横向线
        left.setDrawGridLines(true);
        // 纵轴数据显示在图形内部

        left.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不显示右侧Y轴
        getAxisRight().setEnabled(false);

        /*
        * X轴
        * ******************************************************************************/
        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 格式化X轴时间
        // xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());


        setDrawBarShadow(false);
        setDrawValueAboveBar(true);

        setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        setPinchZoom(false);

        setDrawGridBackground(false);

    }

/*
    public void init() {


        setDescription("");
        setBackgroundColor(Color.WHITE);
        setDrawGridBackground(false);
        setDrawBarShadow(false);

        // 取消Y轴缩放动画
        setScaleYEnabled(false);

        // 自动缩放调整
        setAutoScaleMinMaxEnabled(true);





        this.setDrawBorders(true);
/       this.setBorderWidth(1f);
        this.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        this.setDescription("");
        this.setDragEnabled(true);
        this.setScaleYEnabled(false);
*//*


        //Legend barChartLegend = getLegend();
        // barChartLegend.setEnabled(false);



        this.setDragDecelerationEnabled(true);

        this.setDragDecelerationFrictionCoef(0.2f);
        this.setOnChartValueSelectedListener(this);
    }

*/

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
       if(combinedChart!=null){
           combinedChart.setOnChartValueSelectedListener(this);
       }
    }

    @Override
    public void onNothingSelected() {

    }


    public void setData(DataParse mData) {

        this.setDrawBorders(true);
        this.setBorderWidth(1f);
        this.setBorderColor(getResources().getColor(R.color.minute_grayLine));
//bar x y轴
        xAxisBar = getXAxis();
        xAxisBar.setDrawLabels(true);
        xAxisBar.setDrawGridLines(false);
        xAxisBar.setDrawAxisLine(false);
        xAxisBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setGridColor(getResources().getColor(R.color.minute_grayLine));


        axisLeftBar = getAxisLeft();
        axisLeftBar.setAxisMinValue(0);
        axisLeftBar.setDrawGridLines(false);
        axisLeftBar.setDrawAxisLine(false);
        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftBar.setDrawLabels(true);
        axisLeftBar.setSpaceTop(0);
        axisLeftBar.setShowOnlyMinMax(true);


        axisRightBar = getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);
        axisRightBar.setDrawAxisLine(false);


        axisLeftBar.setAxisMaxValue(mData.getVolmax());
        String unit = MyUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        axisRightBar.setAxisMaxValue(mData.getVolmax());
        Log.e("@@@", mData.getVolmax() + "da");

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<CandleEntry> candleEntries = new ArrayList<>();
        ArrayList<Entry> line5Entries = new ArrayList<>();
        ArrayList<Entry> line10Entries = new ArrayList<>();
        ArrayList<Entry> line30Entries = new ArrayList<>();
        for (int i = 0, j = 0; i < mData.getKLineDatas().size(); i++, j++) {
            xVals.add(mData.getKLineDatas().get(i).date + "");
            barEntries.add(new BarEntry(mData.getKLineDatas().get(i).vol, i));
            candleEntries.add(new CandleEntry(i, mData.getKLineDatas().get(i).high, mData.getKLineDatas().get(i).low, mData.getKLineDatas().get(i).open, mData.getKLineDatas().get(i).close));
            if (i >= 4) {
                sum = 0;
                line5Entries.add(new Entry(getSum(i - 4, i, mData) / 5, i));
            }
            if (i >= 9) {
                sum = 0;
                line10Entries.add(new Entry(getSum(i - 9, i, mData) / 10, i));
            }
            if (i >= 29) {
                sum = 0;
                line30Entries.add(new Entry(getSum(i - 29, i, mData) / 30, i));
            }

        }
        barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(Color.WHITE);
        barDataSet.setDrawValues(false);
        barDataSet.setColor(Color.RED);
        BarData barData = new BarData(xVals, barDataSet);
        this.setData(barData);
        final ViewPortHandler viewPortHandlerBar = this.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 3;
        touchmatrix.postScale(xscale, 1f);


        final ViewPortHandler viewPortHandlerCombin = this.getViewPortHandler();
        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        final float xscaleCombin = 3;
        matrixCombin.postScale(xscaleCombin, 1f);


        this.moveViewToX(mData.getKLineDatas().size() - 1);

       // setOffset();
/****************************************************************************************
 此处解决方法来源于CombinedChartDemo，k线图y轴显示问题，图表滑动后才能对齐的bug，希望有人给出解决方法
 (注：此bug现已修复，感谢和chenguang79一起研究)
 ****************************************************************************************/


    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    private float getSum(Integer a, Integer b, DataParse mData) {

        for (int i = a; i <= b; i++) {
            sum += mData.getKLineDatas().get(i).close;
        }
        return sum;
    }





    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = combinedChart.getViewPortHandler().offsetLeft();
        float barLeft = this.getViewPortHandler().offsetLeft();
        float lineRight = combinedChart.getViewPortHandler().offsetRight();
        float barRight = this.getViewPortHandler().offsetRight();
        float barBottom = this.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            combinedChart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            combinedChart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        this.setViewPortOffsets(transLeft, 15, transRight, barBottom);
    }

}
