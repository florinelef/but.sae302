package fr.univlille.utils.knn;

import java.util.Map;

import fr.univlille.knn.model.Donnee;

public class DistanceManhattan implements Distance {
    @Override
    public double distance(Donnee d1, Donnee d2) {

        Map<String, String> mapD1 = d1.getCaracteristiques(), mapD2 = d2.getCaracteristiques();
        double manhattanDistanceSum = 0;

        for (String entry : mapD1.keySet()) {
            double dist = DistanceUtils.substract(mapD1.get(entry), mapD2.get(entry));
            manhattanDistanceSum += Math.abs(dist);
        }

        return manhattanDistanceSum;
    }
}
