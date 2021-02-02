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
import java.util.Collections;
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
            chart.setBackgroundColor(Color.rgb(255, 255, 255));

            chart.getDescription().setEnabled(false);
            chart.setTouchEnabled(true);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(true);

            chart.setDrawGridBackground(false);
            chart.setMaxHighlightDistance(300);

            XAxis x = chart.getXAxis();
            x.setAxisMaximum(2020f);
            x.setAxisMinimum(2013f);
            x.setTextSize(10f);
            x.setTextColor(Color.BLACK);
            x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            x.setLabelCount(8, true);
            x.setDrawGridLines(true);
            x.setCenterAxisLabels(false);


            YAxis y = chart.getAxisLeft();
            y.setTextSize(10f);
            //y.setLabelCount(true);
            y.setTextColor(Color.BLACK);
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            y.setDrawGridLines(false);
            y.setAxisLineColor(Color.BLACK);
            y.setAxisMinimum(0);
            y.setAxisMaximum(maxValue());
            y.setCenterAxisLabels(false);

            // chart.getAxisRight().setEnabled(false);

            chart.getLegend().setEnabled(false);

            chart.animateXY(2000, 2000);

            chart.invalidate();

            setData();
        }
    }

    private static int maxValue () {
        int max = 0;
        double fValeur = 0;
        int iValeur = 0;
        for (MutationContent.MutationItem item : MutationContent.ITEMS) {
            String str_valeur = item.valeur_fonciere;
            if (!str_valeur.isEmpty()) {
                fValeur = Double.parseDouble(str_valeur);
                iValeur = (int) Math.round(fValeur);
                if (iValeur > max)
                    max = iValeur;
            }
        }
        return max+100000;
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        List<MutationContent.MutationItem> items = MutationContent.ITEMS;
        int annee_prec = 0;
        float pas = 0f;
        Collections.sort(items, new MutationComparator());
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
                if (annee_prec == iAnnee) {
                    values.add(new Entry(iAnnee + pas, iValeur));
                } else
                    values.add(new Entry(iAnnee, iValeur));
                annee_prec = iAnnee;


            }
        }

        LineDataSet set1;

        if (chart.getData() != null &&
            chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.STEPPED);
            set1.setCubicIntensity(0.1f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.rgb(255, 233, 0));
            set1.setHighLightColor(Color.rgb(255, 160, 0));
            set1.setColor(Color.RED);
            set1.setFillColor(Color.rgb(255, 160, 0));
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