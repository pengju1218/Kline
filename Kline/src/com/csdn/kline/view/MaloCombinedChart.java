package com.csdn.kline.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.csdn.kline.R;
import com.csdn.kline.util.DataParse;
import com.csdn.kline.util.KLineBean;
import com.csdn.kline.util.MyUtils;
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
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class MaloCombinedChart extends CombinedChart implements OnChartValueSelectedListener, View.OnLongClickListener {
    private XAxis xAxisK;
    private YAxis axisLeftK, axisRightK;
    float sum = 0;
    private ArrayList<KLineBean> kLineDatas;

    public MaloCombinedChart(Context context) {
        super(context);
        initChart();
    }

    public MaloCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChart();
    }

    public MaloCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initChart();
    }


    private Handler handler = new Handler() {

    };


    public void initChart() {


        this.setAutoScaleMinMaxEnabled(true);
        this.setDrawBorders(true);
        // this.setBorderWidth(1);
        this.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        //this.setDescription("");
        this.setDragEnabled(true);
        this.setScaleYEnabled(false);

        this.setDragDecelerationEnabled(true);
        this.setDragDecelerationFrictionCoef(0.2f);


        setOnLongClickListener(this);

    }


    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        KLineBean kLineBean = kLineDatas.get(entry.getXIndex());
        float[] floats = new float[4];

        floats[0] = kLineBean.open;
        floats[1] = kLineBean.close;
        floats[2] = kLineBean.high;
        floats[3] = kLineBean.low;
        CToast.makeText(getContext(), floats, 5000).show();
    }


    @Override
    public void onNothingSelected() {

    }

    public void setData(DataParse mData) {


//bar x y轴
        xAxisK = this.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(false);
        xAxisK.setDrawAxisLine(false);
        // xAxisK.setSpaceBetweenLabels(12);//轴刻度间的宽度，默认值是4
        xAxisK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setGridColor(getResources().getColor(R.color.minute_grayLine));


        axisLeftK = this.getAxisLeft();
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawLabels(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftK.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftK.setLabelCount(7, false);


        axisRightK = this.getAxisRight();
        axisRightK.setDrawLabels(false);
        axisRightK.setDrawGridLines(true);
        axisRightK.setDrawAxisLine(false);
        axisRightK.setGridColor(getResources().getColor(R.color.minute_grayLine));

      /*  // get the legend (only possible after setting data)
        Legend l = this.getLegend();  // 设置比例图标示
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);  //显示位置
        l.setForm(Legend.LegendForm.SQUARE);// 样式
        l.setFormSize(6f);// 字号
        l.setTextColor(Color.WHITE);// 颜色
        //l.setTypeface(mTf);// 字体

        List<String> labels=new ArrayList<>();
        labels.add("红涨");
        labels.add("绿跌");
        List<Integer> colors=new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        l.setExtra(colors,labels);//设置标注的颜色及内容，设置的效果如下图

        l.setEnabled(false);//决定显不显示标签


*/
        kLineDatas = mData.getKLineDatas();
        // axisLeftBar.setAxisMaxValue(mData.getVolmax());
        String unit = MyUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        //axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        // axisRightBar.setAxisMaxValue(mData.getVolmax());
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


        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, " K线");
     /*   candleDataSet.setDrawHorizontalHighlightIndicator(false);
        candleDataSet.setHighlightEnabled(true);
        candleDataSet.setHighLightColor(Color.WHITE);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);
        candleDataSet.setColor(Color.RED);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);


*/


        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setShadowColor(Color.DKGRAY);//影线颜色
        candleDataSet.setShadowColorSameAsCandle(true);//影线颜色与实体一致
        candleDataSet.setShadowWidth(0.7f);//影线
        candleDataSet.setDecreasingColor(Color.RED);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);//红涨，实体
        candleDataSet.setIncreasingColor(Color.GREEN);
        candleDataSet.setIncreasingPaintStyle(Paint.Style.STROKE);//绿跌，空心
        candleDataSet.setNeutralColor(Color.RED);//当天价格不涨不跌（一字线）颜色
        candleDataSet.setHighlightLineWidth(1f);//选中蜡烛时的线宽
        candleDataSet.setDrawValues(false);//在图表中的元素上面是否显示数值


        CandleData candleData = new CandleData(xVals, candleDataSet);


        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setMaLine(5, xVals, line5Entries));
        sets.add(setMaLine(10, xVals, line10Entries));
        sets.add(setMaLine(30, xVals, line30Entries));

        CombinedData combinedData = new CombinedData(xVals);
        LineData lineData = new LineData(xVals, sets);
        combinedData.setData(candleData);
        combinedData.setData(lineData);
        this.setData(combinedData);
        this.moveViewToX(mData.getKLineDatas().size() - 1);
        final ViewPortHandler viewPortHandlerCombin = this.getViewPortHandler();
        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        final float xscaleCombin = 3;
        matrixCombin.postScale(xscaleCombin, 1f);


        this.moveViewToX(mData.getKLineDatas().size() - 1);
        //setOffset();

/****************************************************************************************
 此处解决方法来源于CombinedChartDemo，k线图y轴显示问题，图表滑动后才能对齐的bug，希望有人给出解决方法
 (注：此bug现已修复，感谢和chenguang79一起研究)
 ****************************************************************************************/

       // handler.sendEmptyMessageDelayed(0, 300);

    }

    private float getSum(Integer a, Integer b, DataParse mData) {

        for (int i = a; i <= b; i++) {
            sum += mData.getKLineDatas().get(i).close;
        }
        return sum;
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(Color.WHITE);
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(Color.GREEN);
        } else if (ma == 10) {
            lineDataSetMa.setColor(Color.GRAY);
        } else {
            lineDataSetMa.setColor(Color.YELLOW);
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

    private BarChart barChart;

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
        //setOffset();
    }


    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = this.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = this.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float barBottom = barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            this.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            this.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barChart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
    }

    @Override
    public boolean onLongClick(View v) {

        this.setOnChartValueSelectedListener(this);

        return true;
    }
}
