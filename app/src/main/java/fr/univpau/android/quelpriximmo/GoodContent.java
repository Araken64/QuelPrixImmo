package fr.univpau.android.quelpriximmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodContent {
    public static final List<GoodContent.GoodItem> ITEMS = new ArrayList<GoodContent.GoodItem>();
    public static final Map<String, GoodContent.GoodItem> ITEM_MAP = new HashMap<String, GoodContent.GoodItem>();

    private static void addGood(GoodContent.GoodItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class GoodItem {
        public String id = "";
        public String surface_relle_bati = "";
        public String surface_terrain = "";
        public String type_local = "";
        public String nb_pieces_principales = "";

        public GoodItem(String id, String surface_relle_bati, String surface_terrain, String type_local, String nb_pieces_principales) {
            this.id = id;
            this.surface_relle_bati = surface_relle_bati;
            this.surface_terrain = surface_terrain;
            this.type_local = type_local;
            this.nb_pieces_principales = nb_pieces_principales;
        }

        @Override
        public String toString() {
            return "GoodItem{" +
                "id='" + id + '\'' +
                ", surface_relle_bati='" + surface_relle_bati + '\'' +
                ", surface_terrain='" + surface_terrain + '\'' +
                ", type_local='" + type_local + '\'' +
                ", nb_pieces_principales='" + nb_pieces_principales + '\'' +
                '}';
        }
    }
}

