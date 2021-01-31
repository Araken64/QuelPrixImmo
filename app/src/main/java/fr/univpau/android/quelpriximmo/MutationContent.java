package fr.univpau.android.quelpriximmo;

import fr.univpau.android.quelpriximmo.dummy.DummyContent;

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

    public static class MutationItem {
        public String id = "";
        public String valeur_fonciere = "";
        public String nature_mutation = "";
        public String date_mutation = "";
        public String suffixe_numero = "";
        public String numero_voie = "";
        public String type_voie = "";
        public String voie = "";

        public MutationItem(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
