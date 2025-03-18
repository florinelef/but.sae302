package fr.univlille.utils.knn;

import fr.univlille.knn.model.Donnee;
import fr.univlille.utils.knn.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestMethodeKnn {
    public static final String SANS_CATEGORIE = "Sans catégorie";
    private String categorie1;
    private String categorie2;
    private Map<String, String> caracteristiques1;
    private Map<String, String> caracteristiques2;
    private Map<String, String> caracteristiques3;
    private Map<String, String> caracteristiques4;
    private Map<String, String> caracteristiques5;
    private Donnee donnee1;
    private Donnee donnee2;
    private Donnee donnee3;
    private Donnee donnee4;
    private Donnee donnee5;
    private List<Donnee> li;
    private MethodeKnn methodeKnn;

    @BeforeEach
    public void setup() {
        /*
         * Paramétrage des attributs pour les test.
         */
        this.categorie1 = "categorie1";
        this.categorie2 = "categorie2";
        this.caracteristiques1 = new HashMap<>();
        this.caracteristiques1.put("Couleur", "Bleu");
        this.caracteristiques1.put("Taux", "1.5");
        this.caracteristiques1.put("Origine", "RV");

        this.caracteristiques2 = new HashMap<>();
        this.caracteristiques2.put("Couleur", "Violet");
        this.caracteristiques2.put("Taux", "255");
        this.caracteristiques2.put("Origine", "RV");

        this.caracteristiques3 = new HashMap<>();
        this.caracteristiques3.put("Couleur", "Rose");
        this.caracteristiques3.put("Taux", "1");
        this.caracteristiques3.put("Origine", "DP");

        this.caracteristiques4 = new HashMap<>();
        this.caracteristiques4.put("Couleur", "Jaune");
        this.caracteristiques4.put("Taux", "2");
        this.caracteristiques4.put("Origine", "RV");

        this.caracteristiques5 = new HashMap<>();
        this.caracteristiques5.put("Couleur", "Vert");
        this.caracteristiques5.put("Taux", "3.5");
        this.caracteristiques5.put("Origine", "DP");

        this.donnee1 = new Donnee(this.caracteristiques1);
        this.donnee1.setCategorie(categorie1);
        this.donnee2 = new Donnee(this.caracteristiques2);
        this.donnee2.setCategorie(categorie2);
        this.donnee3 = new Donnee(this.caracteristiques3);
        this.donnee3.setCategorie(categorie1);
        this.donnee4 = new Donnee(this.caracteristiques4);
        this.donnee4.setCategorie(categorie2);
        this.donnee5 = new Donnee(this.caracteristiques5);
        this.donnee5.setCategorie(categorie1);

        this.li = new ArrayList<>();
        li.add(donnee1);
        li.add(donnee2);
        li.add(donnee3);

        this.methodeKnn = new MethodeKnn(li);
    }

    @Test
    public void testConstructeur() {
        this.methodeKnn = new MethodeKnn(li);

        Map<String,Double> referencielAmplitude = new HashMap<>();
        referencielAmplitude.put("Couleur", 1.0);
        referencielAmplitude.put("Taux", 254.0);
        referencielAmplitude.put("Origine", 1.0);

        assertEquals(MethodeKnn.getDataList(), this.li);
        assertEquals(MethodeKnn.getAmplitudeMap(), referencielAmplitude);
    }

    @Test
    public void testGetAmplitudeMap() {
        Map<String,Double> referencielAmplitude = new HashMap<>();
        referencielAmplitude.put("Couleur", 1.0);
        referencielAmplitude.put("Taux", 254.0);
        referencielAmplitude.put("Origine", 1.0);

        assertTrue(this.methodeKnn.getAmplitudeMap() instanceof Map<String,Double>);
        assertEquals(this.methodeKnn.getAmplitudeMap(), referencielAmplitude);
    }
    @Test
    public void testGetDataList() {
        assertTrue(this.methodeKnn.getDataList() instanceof List<Donnee>);
        assertEquals(this.li, this.methodeKnn.getDataList());
    }

    @Test
    public void testKnnAvecKTropGrand() throws RuntimeException {
        Distance d = new DistanceEuclidienne();
        assertThrows(RuntimeException.class, () -> this.methodeKnn.knn(500, this.donnee1, d, this.li));
    }

    @Test
    public void testKnnDistanceEuclidienne() throws RuntimeException {
        Distance d = new DistanceEuclidienne();
        Map<Donnee,Double> distanceMap = new HashMap<>();

        // Donnée Comparative
        Map<String,String> comparatifCaracteristique = new HashMap<>();
        comparatifCaracteristique.put("Couleur", "BleuFonce");
        comparatifCaracteristique.put("Taux", "0.1");
        comparatifCaracteristique.put("Origine", "SL");
        Donnee comparatif = new Donnee(comparatifCaracteristique);

        //Résultat des distances euclidienne avec la donnée comparative
        double res1 = Math.sqrt(1 + Math.pow(0.1 - 1.5, 2) + 1);
        double res2 = Math.sqrt(1 + Math.pow(0.1 - 255, 2) + 1);
        double res3 = Math.sqrt(1 + Math.pow(0.1 - 1, 2) + 1);

        distanceMap.put(this.donnee1, res1);
        distanceMap.put(this.donnee2, res2);
        distanceMap.put(this.donnee3, res3);

        distanceMap = MapUtils.sortByValue(distanceMap);

        List<Donnee> result = new ArrayList<>(distanceMap.keySet());

        assertEquals(result, methodeKnn.knn(3, comparatif, d, this.li));
    }

    @Test
    public void testKnnDistanceEuclidienneNormalisee() throws RuntimeException {
        Distance d = new DistanceEuclidienneNormalisee();
        Map<Donnee,Double> distanceMap = new HashMap<>();

        // Donnée Comparative
        Map<String,String> comparatifCaracteristique = new HashMap<>();
        comparatifCaracteristique.put("Couleur", "BleuFonce");
        comparatifCaracteristique.put("Taux", "0.1");
        comparatifCaracteristique.put("Origine", "SL");
        Donnee comparatif = new Donnee(comparatifCaracteristique);

        //Résultat des distances euclidienne avec la donnée comparative

        double res1 = Math.sqrt(1 + Math.pow(((0.1-1.5)/254),2));
        double res2 = Math.sqrt(1 + Math.pow(((0.1-255)/254),2));
        double res3 = Math.sqrt(1 + Math.pow(((0.1-1)/254),2));

        distanceMap.put(this.donnee1, res1);
        distanceMap.put(this.donnee2, res2);
        distanceMap.put(this.donnee3, res3);

        distanceMap = MapUtils.sortByValue(distanceMap);

        List<Donnee> result = new ArrayList<>(distanceMap.keySet());

        assertEquals(result, methodeKnn.knn(3, comparatif, d, this.li));
    }

    @Test
    public void testKnnDistanceManhattan() throws RuntimeException {
        Distance d = new DistanceManhattan();
        Map<Donnee,Double> distanceMap = new HashMap<>();

        // Donnée Comparative
        Map<String,String> comparatifCaracteristique = new HashMap<>();
        comparatifCaracteristique.put("Couleur", "BleuFonce");
        comparatifCaracteristique.put("Taux", "0.1");
        comparatifCaracteristique.put("Origine", "SL");
        Donnee comparatif = new Donnee(comparatifCaracteristique);

        //Résultat des distances euclidienne avec la donnée comparative

        double res1 = 1 + Math.abs(0.1 - 1.5) + 1;
        double res2 = 1 + Math.abs(0.1 - 255) + 1;
        double res3 = 1 + Math.abs(0.1 - 1) + 1;

        distanceMap.put(this.donnee1, res1);
        distanceMap.put(this.donnee2, res2);
        distanceMap.put(this.donnee3, res3);

        distanceMap = MapUtils.sortByValue(distanceMap);

        List<Donnee> result = new ArrayList<>(distanceMap.keySet());

        assertEquals(result, methodeKnn.knn(3, comparatif, d, this.li));
    }

    @Test
    public void testKnnDistanceManhattanNormalisee() throws RuntimeException {
        Distance d = new DistanceManhattanNormalisee();
        Map<Donnee,Double> distanceMap = new HashMap<>();

        // Donnée Comparative
        Map<String,String> comparatifCaracteristique = new HashMap<>();
        comparatifCaracteristique.put("Couleur", "BleuFonce");
        comparatifCaracteristique.put("Taux", "0.1");
        comparatifCaracteristique.put("Origine", "SL");
        Donnee comparatif = new Donnee(comparatifCaracteristique);

        //Résultat des distances euclidienne avec la donnée comparative

        double res1 = 1 + Math.abs(0.1 - 1.5)/254 + 1;
        double res2 = 1 + Math.abs(0.1 - 255)/254 + 1;
        double res3 = 1 + Math.abs(0.1 - 1)/254 + 1;

        distanceMap.put(this.donnee1, res1);
        distanceMap.put(this.donnee2, res2);
        distanceMap.put(this.donnee3, res3);

        distanceMap = MapUtils.sortByValue(distanceMap);

        List<Donnee> result = new ArrayList<>(distanceMap.keySet());

        assertEquals(result, methodeKnn.knn(3, comparatif, d, this.li));
    }
}