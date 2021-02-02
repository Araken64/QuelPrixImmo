package fr.univpau.android.quelpriximmo;

import java.util.Comparator;

public class MutationComparator implements Comparator<MutationContent.MutationItem> {
    @Override
    public int compare(MutationContent.MutationItem mutationItem, MutationContent.MutationItem t1) {
        String str_date1 = mutationItem.date_mutation.substring(0,4);
        String str_date2 = t1.date_mutation.substring(0,4);
        return Integer.valueOf(str_date1).compareTo(Integer.valueOf(str_date2));
    }
}
