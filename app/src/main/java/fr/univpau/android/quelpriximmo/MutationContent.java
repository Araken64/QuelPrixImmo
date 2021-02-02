package fr.univpau.android.quelpriximmo;

import java.util.ArrayList;
import java.util.List;

public class MutationContent {
    public static final List<MutationContent.MutationItem> ITEMS = new ArrayList<>();

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
