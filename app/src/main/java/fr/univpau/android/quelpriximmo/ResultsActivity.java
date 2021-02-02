package fr.univpau.android.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {
    public static String nombre_pieces_principales;
    public static String strUrl;
    public LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_results);

        nombre_pieces_principales = this.getIntent().getStringExtra("nombre_pieces_principales");
        strUrl = this.getIntent().getStringExtra("strUrl");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            chart = findViewById(R.id.chart1);
            chart.setViewPortOffsets(0, 0, 0, 0);
            chart.setBackgroundColor(Color.rgb(104, 241, 175));

            chart.setContentDescription("Mon titre");
            chart.setTouchEnabled(true);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(false);

            chart.setDrawGridBackground(false);
            chart.setMaxHighlightDistance(300);

            XAxis x = chart.getXAxis();
            x.setAxisMaximum(2020);
            x.setAxisMinimum(2014);
            x.setTextColor(Color.BLACK);
            x.setPosition(XAxis.XAxisPosition.BOTTOM);
            x.setLabelCount(6, true);
            x.setDrawGridLines(false);


            YAxis y = chart.getAxisLeft();
            y.setLabelCount(10, false);
            y.setTextColor(Color.BLACK);
            y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            y.setDrawGridLines(false);
            y.setAxisLineColor(Color.BLACK);
            y.setAxisMinimum(0);

            // chart.getAxisRight().setEnabled(false);

            chart.getLegend().setEnabled(false);

            chart.animateXY(2000, 2000);

            chart.invalidate();

            setData(6,20);
        }
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        List<MutationContent.MutationItem> items = MutationContent.ITEMS;
        for (MutationContent.MutationItem item : items) {
            String date = item.date_mutation;
            String str_annee = date.substring(0,4);
            String str_valeur = item.valeur_fonciere;

            int iAnnee = 0;
            double fValeur = 0.0;
            int iValeur = 0;
            if (!str_annee.equals("") && !str_valeur.equals("")) {
                iAnnee = Integer.parseInt(str_annee);
                fValeur = Double.parseDouble(str_valeur);
                iValeur = (int) Math.round(fValeur);
                values.add(new Entry(iAnnee, iValeur));
            }
        }

        /*
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i + 2014, val));
        }
        */

        LineDataSet set1;

        if (chart.getData() != null &&
            chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }
}