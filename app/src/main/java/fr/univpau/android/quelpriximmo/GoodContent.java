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
        String surface = "128 m²";
        String type_local = "Maison";
        String nb_pieces_principales = "4p";

        return new GoodItem(String.valueOf(position), surface, type_local, nb_pieces_principales);
    }

    public static class GoodItem {
        public String id;
        public String surface;
        public String type_local;
        public String nb_pieces_principales;

        public GoodItem(String id, String surface, String type_local, String nb_pieces_principales) {
            this.id = id;
            this.surface = surface;
            this.type_local = type_local;
            this.nb_pieces_principales = nb_pieces_principales;
        }

        public GoodItem() {}

        @Override
        public String toString() {
            return "GoodItem{" +
                "surface='" + surface + '\'' +
                ", type_local='" + type_local + '\'' +
                ", nb_pieces_principales='" + nb_pieces_principales + '\'' +
                '}';
        }
    }
}

