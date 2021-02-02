package fr.univpau.android.quelpriximmo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a list of Items.
 */
public class MutationFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MutationFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MutationFragment newInstance(int columnCount) {
        MutationFragment fragment = new MutationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mutation_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MutationRecyclerViewAdapter(MutationContent.ITEMS));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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
                    culture.type_local = mutation.nature_culture;
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
}