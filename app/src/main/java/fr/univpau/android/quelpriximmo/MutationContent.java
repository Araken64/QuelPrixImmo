package fr.univpau.android.quelpriximmo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MutationContent {
    public static final List<MutationContent.MutationItem> ITEMS = new ArrayList<>();
    public static final Map<String, MutationContent.MutationItem> ITEM_MAP = new HashMap<>();

    private static void addItem(MutationContent.MutationItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static final int COUNT = 5;

    public static void addSampleItems() {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createMutationItem(i));
        }
    }

    private static MutationItem createMutationItem(int position) {
        MutationItem mutationItem = new MutationItem();
        mutationItem.id = String.valueOf(position);
        mutationItem.valeur_fonciere = "165 000 €";
        mutationItem.nature_mutation = "Vente";
        mutationItem.suffixe_numero = "B";
        mutationItem.numero_voie = "3";
        mutationItem.type_voie = "RUE";
        mutationItem.voie = "DE L'EGLISE";
        mutationItem.date_mutation = "14/12/2015";
        return mutationItem;
    }

    public static class MutationItem {
        public String id = "";
        public String valeur_fonciere = "";
        public String nature_mutation = "";
        public String date_mutation = "";
        public String suffixe_numero = "";
        public String numero_voie = "";
        public String type_voie = "";
        public String voie = "";

        public String surface_relle_bati ="";
        public String type_local ="";
        public String nombre_pieces_principales ="";

        public String surface_terrain ="";
        public String nature_culture ="";

        public List<GoodContent.GoodItem> good_list = new ArrayList<>();

        public MutationItem() {
        }

        @Override
        public String toString() {
            return "MutationItem{" +
                "id='" + id + '\'' +
                ", valeur_fonciere='" + valeur_fonciere + '\'' +
                ", nature_mutation='" + nature_mutation + '\'' +
                ", date_mutation='" + date_mutation + '\'' +
                ", suffixe_numero='" + suffixe_numero + '\'' +
                ", numero_voie='" + numero_voie + '\'' +
                ", type_voie='" + type_voie + '\'' +
                ", voie='" + voie + '\'' +
                '}';
        }
    }
}
