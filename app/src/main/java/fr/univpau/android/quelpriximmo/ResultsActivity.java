package fr.univpau.android.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;


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

        AsyncTask<URL, Void, JSONObject> task = null;
        List<MutationContent.MutationItem> tmpMutationList = new ArrayList<>();
        MutationContent.ITEMS.clear();
        try {
            URL url = new URL(ResultsActivity.strUrl); // MalformedURLException

            task = new AsyncDataTask().execute(url);
            JSONObject datas = task.get(); // ExecutionException + InterruptedException
            JSONArray array_json_objects1 = (JSONArray) datas.get("features"); // JSONException
            for (int i = 0; i < array_json_objects1.length(); i++) {
                JSONObject o = array_json_objects1.getJSONObject(i);
                if (((JSONObject) o.get("properties")).has("type_local")) {
                    String type_local = ((JSONObject) o.get("properties")).get("type_local").toString();
                    if (type_local.equals(getResources().getString(R.string.spinner_choice_maison)) || type_local.equals(getResources().getString(R.string.spinner_choice_appartement))) {
                        if (ResultsActivity.nombre_pieces_principales.equals("")) {
                            tmpMutationList.add(mapJSON(o));
                        } else if (((JSONObject) o.get("properties")).get("nombre_pieces_principales").toString().equals(ResultsActivity.nombre_pieces_principales)) {
                            tmpMutationList.add(mapJSON(o));
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(final MutationContent.MutationItem mutation : tmpMutationList) {
            if (!anyMatchInList(mutation, MutationContent.ITEMS)) { // if not already in list
                MutationContent.MutationItem mt = new MutationContent.MutationItem();
                mt.valeur_fonciere = mutation.valeur_fonciere;
                mt.date_mutation = mutation.date_mutation;
                mt.nature_mutation = mutation.nature_mutation;
                mt.type_voie = mutation.type_voie;
                mt.suffixe_numero = mutation.suffixe_numero;
                mt.numero_voie = mutation.numero_voie;
                mt.voie = mutation.voie;
                mt.id = mutation.id;

                GoodContent.GoodItem good = new GoodContent.GoodItem();
                good.nb_pieces_principales = mutation.nombre_pieces_principales;
                good.surface = mutation.surface_relle_bati;
                good.type_local = mutation.type_local;
                mt.good_list.add(good);
                if(!mutation.surface_terrain.isEmpty()) {
                    GoodContent.GoodItem culture = new GoodContent.GoodItem();
                    culture.surface = mutation.surface_terrain;
                    culture.type_local = "Nature du sol: " + mutation.nature_culture;
                    mt.good_list.add(culture);
                }
                MutationContent.ITEMS.add(mt);
            } else { // if already in the list
                MutationContent.MutationItem mt = getMatchInList(mutation, MutationContent.ITEMS);
                assert mt != null;
                GoodContent.GoodItem good = new GoodContent.GoodItem();
                good.nb_pieces_principales = mutation.nombre_pieces_principales;
                good.surface = mutation.surface_relle_bati;
                good.type_local = mutation.type_local;
                mt.good_list.add(good);
            }
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            chart = findViewById(R.id.chart1);
            chart.setViewPortOffsets(0, 0, 0, 0);
            chart.setBackgroundColor(Color.rgb(255, 255, 255));

            chart.getDescription().setEnabled(false);
            chart.setTouchEnabled(false);

            // enable scaling and dragging
            chart.setDragEnabled(false);
            chart.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(false);

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

    private boolean areSameMutation(MutationContent.MutationItem m1, MutationContent.MutationItem m2) {
        return m1.date_mutation.equals(m2.date_mutation) && m1.nature_mutation.equals(m2.nature_mutation)
            && m1.valeur_fonciere.equals(m2.valeur_fonciere) && m1.numero_voie.equals(m2.numero_voie)
            && m1.suffixe_numero.equals(m2.suffixe_numero) && m1.voie.equals(m2.voie);
    }

    private boolean anyMatchInList(MutationContent.MutationItem mutation, List<MutationContent.MutationItem> list) {
        boolean anyMatch = false;
        int index = 0;
        while(index < list.size() && !anyMatch) {
            anyMatch = areSameMutation(list.get(index), mutation);
            index += 1;
        }
        return anyMatch;
    }

    private MutationContent.MutationItem getMatchInList(MutationContent.MutationItem mutation, List<MutationContent.MutationItem> list) {
        int index = 0;
        while(index < list.size()) {
            if(areSameMutation(list.get(index), mutation))
                return list.get(index);
            index += 1;
        }
        return null;
    }

    private MutationContent.MutationItem mapJSON(JSONObject json) throws JSONException {
        MutationContent.MutationItem mutationItem = new MutationContent.MutationItem();
        JSONObject properties = json.optJSONObject("properties");
        if(properties != null) {
            mutationItem.valeur_fonciere = properties.optString("valeur_fonciere", "");
            mutationItem.nature_mutation = properties.optString("nature_mutation", "");
            mutationItem.suffixe_numero = properties.optString("suffixe_numero", "");
            mutationItem.numero_voie = properties.optString("numero_voie", "");
            mutationItem.type_voie = properties.optString("type_voie", "");
            mutationItem.voie = properties.optString("voie", "");
            mutationItem.date_mutation = properties.optString("date_mutation", "");

            mutationItem.type_local = properties.optString("type_local", "");
            mutationItem.surface_relle_bati = properties.optString("surface_relle_bati", "");
            mutationItem.nombre_pieces_principales = properties.optString("nombre_pieces_principales", "");
            mutationItem.surface_terrain = properties.optString("surface_terrain");
            mutationItem.nature_culture = properties.optString("nature_culture", "");
        }
        return mutationItem;
    }
}