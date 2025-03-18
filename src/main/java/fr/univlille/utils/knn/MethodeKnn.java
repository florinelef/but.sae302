package fr.univlille.utils.knn;

import java.util.*;

import fr.univlille.knn.model.Classeur;
import fr.univlille.knn.model.Donnee;

public class MethodeKnn {

    private static List<Donnee> dataList;
    private static Map<String, Double> amplitudeMap;

    public MethodeKnn(List<Donnee> data){
        dataList = data;
        Map<String, String> sampleCaracMap = dataList.get(0).getCaracteristiques();
        amplitudeMap = new HashMap<>();
        for (String carac : sampleCaracMap.keySet()) {
            amplitudeMap.put(carac, DistanceUtils.amplitude(dataList, carac));
        }
    }

    public static Map<String, Double> getAmplitudeMap() {
        return amplitudeMap;
    }

    public static List<Donnee> getDataList() {
        return dataList;
    }

    public List<Donnee> knn(int k, Donnee d0, Distance dist, List<Donnee> dataList){
        if(k > dataList.size()) throw new RuntimeException("k est trop grand");
        Map<Donnee, Double> distanceMap = new HashMap<>();

        for(Donnee donnee : dataList){
            if(!donnee.getCategorie().equals(Donnee.SANS_CATEGORIE)) distanceMap.put(donnee, dist.distance(donnee, d0));
        }
        Map<Donnee, Double> sortedDistanceMap = MapUtils.sortByValue(distanceMap);
        List<Donnee> knn = new ArrayList<>(sortedDistanceMap.keySet());
        return knn.subList(0, k);
    }
}
