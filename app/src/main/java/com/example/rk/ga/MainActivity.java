package com.example.rk.ga;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.rk.ga.ga.Population;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    Button button;
    LineChart mChart;
    Population population;
    LineData data;
    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    /**
     * 显示精度
     */
    public double accuracy=0.001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        population = new Population(0);
        button = (Button) findViewById(R.id.but);
        button.setText("第 ："+population.generations+"  代");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                population.heredity();
                if (dataSets.size() < 2) dataSets.add(getss());
                else dataSets.set(1, getss());
                mChart.setData(data);
                mChart.invalidate();
                button.setText("第 ：" + population.generations + "  代");
            }
        });
        //**************************************************************/
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        setData((int) (1/accuracy));
    }

    /**
     * 函数图像
     * @param count
     */
    public void setData(int count) {
        /**
         * 生成曲线
         */
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        double x1 = -1;
        for (int i = 0; i < count; i++) {
            x1 += 0.003;
            double y = (x1 * Math.sin(10 * Math.PI * x1) + 2.0);
            //Log.i("x  "+x1," y  "+y);
            float val = (float) (y * 1000) + 1000;

            yVals.add(new Entry(val, i));
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        /**
         * 曲线样式设置
         */
        // set the line to be drawn like this "- - - - - -"
        //set1.enableDashedLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1.2f);
        set1.setCircleSize(2f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setDrawCubic(true);
        /**
         * 绑定曲线到图 表>
         */
        dataSets.add(set1); // add the datasets
        data = new LineData(xVals, dataSets);
        mChart.setData(data);
    }

    /**
     * 解集坐标
     * @return
     */
    public LineDataSet getss() {
        ArrayList<Entry> individuals = new ArrayList<Entry>();
        double x = -1;
        for (int i = 0; i < 1/accuracy; i++) {
            x += 0.003;
            double y = (x * Math.sin(10 * Math.PI * x) + 2.0);
            float val = (float) (y * 1000) + 1000;
            if (population.individual != null) for (Float f : population.individual) {
                if (Math.abs(f - x) < 0.0015) {
                    individuals.add(new Entry(val, i));
                    Log.i("f-x",""+Math.abs(f - x));
                }
            }
        }
        LineDataSet set = new LineDataSet(individuals, "DataSet 2");
        set.setDrawCircles(true);
        set.setColor(Color.parseColor("#eeeeee"));
        set.setCircleColor(Color.RED);
        set.setCircleSize(2f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        return set;
    }
}
