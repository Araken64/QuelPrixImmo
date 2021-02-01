package fr.univpau.android.quelpriximmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodContent {
    public static final List<GoodContent.GoodItem> ITEMS = new ArrayList<>();
    public static final Map<String, GoodContent.GoodItem> ITEM_MAP = new HashMap<>();

    private static void addItem(GoodContent.GoodItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createGoodItem(i));
        }
    }

    private static GoodItem createGoodItem(int position) {
        String surface_relle_bati = "128 m²";
        String surface_terrain = "2010 m²";
        String type_local = "Maison";
        String nb_pieces_principales = "4p";

        return new GoodItem(String.valueOf(position), surface_relle_bati, surface_terrain, type_local, nb_pieces_principales);
    }

    public static class GoodItem {
        public String id;
        public String surface_relle_bati;
        public String surface_terrain;
        public String type_local;
        public String nb_pieces_principales;

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

