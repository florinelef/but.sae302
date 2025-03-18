package fr.univlille.utils.knn;

import fr.univlille.knn.model.Donnee;
import fr.univlille.utils.knn.DistanceEuclidienneNormalisee;
import fr.univlille.utils.knn.MethodeKnn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDistanceEuclidienneNormalisee {
    public static final String SANS_CATEGORIE = "Sans catégorie";
    private String categorie;
    private Map<String, String> caracteristiques1;
    private Map<String, String> caracteristiques2;
    private Map<String, String> caracteristiques3;
    private Donnee donnee1;
    private Donnee donnee2;
    private Donnee donnee3;
    private DistanceEuclidienneNormalisee distanceEuclidienneNormalisee;
    private MethodeKnn methodeKnn;

    @BeforeEach
    public void setup() {
        /*
         * Paramétrage des attributs pour les test.
         */
        this.categorie = "categorie1";
        this.caracteristiques1 = new HashMap<>();
        this.caracteristiques1.put("Couleur","Bleu");
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

        this.donnee1 = new Donnee(this.caracteristiques1);
        this.donnee2 = new Donnee(this.caracteristiques2);
        this.donnee3 = new Donnee(this.caracteristiques3);

        this.distanceEuclidienneNormalisee = new DistanceEuclidienneNormalisee();

        List<Donnee> li = new ArrayList<>();
        li.add(donnee1);
        li.add(donnee2);
        li.add(donnee3);

        this.methodeKnn = new MethodeKnn(li);
    }

    @Test
    public void testDistance() {
        double expectedResult = Math.sqrt(1 + Math.pow(((1.5-255)/254),2));
        assertEquals(expectedResult, distanceEuclidienneNormalisee.distance(donnee1, donnee2));
        expectedResult = Math.sqrt(1+Math.pow((1.5-1)/254,2)+1);
        assertEquals(expectedResult, distanceEuclidienneNormalisee.distance(donnee1, donnee3));
        expectedResult = 0;
        assertEquals(expectedResult, distanceEuclidienneNormalisee.distance(donnee1, donnee1));
    }
}
