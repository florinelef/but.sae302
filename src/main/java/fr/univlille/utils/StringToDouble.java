package fr.univlille.utils;

import java.util.Map;
import java.util.HashMap;

public class StringToDouble {

    private static final Map<String, Map<String, Double>> table = new HashMap<>();

    private static void newEntryTable(String caracteristique, String value){
        double nbValues;
        table.putIfAbsent(caracteristique, null);
        if(table.get(caracteristique) == null){
            table.put(caracteristique, new HashMap<>());
        }
        nbValues = (table.get(caracteristique).size() + 1);
        table.get(caracteristique).putIfAbsent(value, nbValues);

    }

    public static double toDouble(String caracteristique, String value){
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            newEntryTable(caracteristique, value);
            return table.get(caracteristique).get(value);
        }
    }

    public static boolean contains(String o){
        return table.containsKey(o);
    }

    public static Map<String, Double> getTable(String caracteristique) {
        return table.get(caracteristique);
    }
}
