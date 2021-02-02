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
        android.util.Log.d("elian", "onFragmentStart");
        AsyncTask<URL, Void, JSONObject> task = null;
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
                            MutationContent.MutationItem item = MutationContent.addJSON(o);

                        } else if (((JSONObject) o.get("properties")).get("nombre_pieces_principales").toString().equals(ResultsActivity.nombre_pieces_principales)) {
                            MutationContent.MutationItem item = MutationContent.addJSON(o);
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
    }
}