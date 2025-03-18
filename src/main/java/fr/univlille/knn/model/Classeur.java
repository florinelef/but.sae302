package fr.univlille.knn.model;

import fr.univlille.utils.knn.Distance;
import fr.univlille.utils.knn.DistanceEuclidienneNormalisee;
import fr.univlille.utils.knn.DistanceEuclidienneNormalisee;
import fr.univlille.utils.knn.MethodeKnn;

import java.util.*;

public class Classeur {
    private static final int FOLD = 5;
    private final MethodeKnn methodeKnn;
    private int k;
    private final Distance dist;

    public Classeur(List<Donnee> dataList) {
        this.methodeKnn = new MethodeKnn(dataList);
        this.dist = new DistanceEuclidienneNormalisee();
        this.k = 5;
        System.out.println("Valeur de k " + k);
    }

    public void setK(int k) throws NumberFormatException{
        if (k <= 0 || k>getMaxK()){
            throw new NumberFormatException();
        } else {
            this.k = k;
        }
    }

    public int getK() {
        return k;
    }

    public int getMaxK(){
        return MethodeKnn.getDataList().size() / FOLD;
    }

    public void classerDonnee(Donnee d0) {
        List<Donnee> kNNList = methodeKnn.knn(k, d0, dist, MethodeKnn.getDataList());
        String category = mostCommonCategory(kNNList);
        System.out.println(category);
        d0.setCategorie(category);
    }

    public static String mostCommonCategory(List<Donnee> kNNlist) {
        Map<String, Integer> map = new HashMap<>();

        for (Donnee donnee : kNNlist) {
            Integer count = map.get(donnee.getCategorie());
            map.put(donnee.getCategorie(), count == null ? 1 : count + 1);
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public MethodeKnn getMethodeKnn() {
        return methodeKnn;
    }

    //calcule la meilleure valeur de k et la renvoie
    public int robustness(){
        Map<Integer, Double> scores = new HashMap<>();
        List<Donnee> dataList = MethodeKnn.getDataList();
        int k = 1;
        while(k < dataList.size()/FOLD){
            scores.put(k, robustnessScore(dataList, k));
            k+=2;
        }
        int bestK;
        bestK = Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
        return bestK;
    }

    //retourne le score de robustesse pour un k passé en paramètre
    private Double robustnessScore(List<Donnee> dataList, int k){
        Collections.shuffle(dataList);
        double score = 0.0;
        for(int i=0; i<dataList.size(); i += FOLD){
            List<Donnee> dataPackageTrain = new ArrayList<>();
            List<Donnee> dataPackageLearn = new ArrayList<>();
            if(i+FOLD > dataList.size()){
                dataPackageTrain.addAll(dataList.subList(i, dataList.size()));
                dataPackageLearn.addAll(dataList.subList(0, i));
            } else {
                dataPackageTrain.addAll(dataList.subList(i, i+FOLD));
                dataPackageLearn.addAll(dataList.subList(0, i));
                dataPackageLearn.addAll(dataList.subList(i+FOLD, dataList.size()));
            }
            score += crossValidation(dataPackageTrain, dataPackageLearn, k);
        }
        return score/(dataList.size()/FOLD);
    }

    private Double crossValidation(List<Donnee> dpTrain, List<Donnee>dpLearn,  int k){
        Donnee dataTest;
        double correct = 0;
        MethodeKnn knn = this.getMethodeKnn();
        String categorieTest;
        for (Donnee donnee : dpTrain) {
            dataTest = donnee;
            categorieTest = Classeur.mostCommonCategory(knn.knn(k, dataTest, dist, dpLearn));
            if (categorieTest.equals(dataTest.getCategorie())) {
                correct++;
            }
        }
        return correct / dpTrain.size();
    }
}
