package fr.univlille.utils.knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univlille.knn.model.Donnee;

public class DistanceUtils {
    public static double substract(String s1, String s2) {
        boolean isNumeric = isParseable(s1);
        double d1 = 0, d2 = 0;

        if (isNumeric) {
            d1 = Double.parseDouble(s1);
            d2 = Double.parseDouble(s2);
        }
        else {
            return s1.equals(s2) ? 0 : 1;
        }

        return d1 - d2;
    }

    public static double amplitude(List<Donnee> donnees, String carac) {
        List<String> values = new ArrayList<>();
        String temp;

        for (Donnee d : donnees) {
            temp = d.getCaracteristiques().get(carac);
            if (temp != null) {
                values.add(temp);
            }
        }

        if (isParseable(values.get(0))) {
            List<Double> doubleValues = toDoubleList(values);

            return Collections.max(doubleValues) - Collections.min(doubleValues);
        }

        else {
            if (values.size() == 1) {
                return 0;
            } else {
                String ref = values.get(0);
                for(String s : values) {
                    if(!s.equals(ref)) {
                        return 1;
                    }
                }
            }
            return 0;
        }
    }

    public static boolean isParseable(String s1) {
        try {
            Double.parseDouble(s1);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static List<Double> toDoubleList(List<String> values) {
        try {
            List<Double> output = new ArrayList<>();
            for (String s : values) {
                output.add(Double.parseDouble(s));
            }
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}
