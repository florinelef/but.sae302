package fr.univlille.utils.knn;

import fr.univlille.knn.model.Donnee;
import fr.univlille.utils.knn.DistanceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDistanceUtils {
    public static final String SANS_CATEGORIE = "Sans catégorie";
    private String categorie;
    private Map<String, String> caracteristiques1;
    private Map<String, String> caracteristiques2;
    private Map<String, String> caracteristiques3;
    private Donnee donnee1;
    private Donnee donnee2;
    private Donnee donnee3;
    private List<Donnee> li;
    private DistanceUtils distanceUtils;

    @BeforeEach
    public void setup() {
        /*
         * Paramétrage des attributs pour les test.
         */
        this.categorie = "categorie1";
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

        this.donnee1 = new Donnee(this.caracteristiques1);
        this.donnee2 = new Donnee(this.caracteristiques2);
        this.donnee3 = new Donnee(this.caracteristiques3);

        this.li = new ArrayList<>();
        li.add(donnee1);
        li.add(donnee2);
        li.add(donnee3);

        this.distanceUtils = new DistanceUtils();
    }

    @Test
    public void testSubstract() {
        String a = "a";
        String b = "b";
        String un = "1";
        String deux = "999";

        assertEquals(1, this.distanceUtils.substract(a, b));
        assertEquals(0, this.distanceUtils.substract(a, a));
        assertEquals(-998, this.distanceUtils.substract(un, deux));
        assertEquals(0, this.distanceUtils.substract(un, un));
    }

    @Test
    public void testAmplitude() {
        assertEquals(1, this.distanceUtils.amplitude(this.li, "Couleur"));
        assertEquals(254, this.distanceUtils.amplitude(this.li, "Taux"));
        assertEquals(1, this.distanceUtils.amplitude(this.li, "Origine"));
        this.li.remove(donnee3);
        assertEquals(0, this.distanceUtils.amplitude(this.li, "Origine"));
        this.li.remove(donnee2);
        assertEquals(0, this.distanceUtils.amplitude(this.li, "Origine"));
    }

    @Test
    public void testIsParseable() {
        String no = "abc";
        String alsoNo = "five";
        String yes = "5";
        String alsoYes = "5.0";

        assertFalse(this.distanceUtils.isParseable(no));
        assertFalse(this.distanceUtils.isParseable(alsoNo));
        assertTrue(this.distanceUtils.isParseable(yes));
        assertTrue(this.distanceUtils.isParseable(alsoYes));
    }

    @Test
    public void testToDoubleList() {
        List<String> l = new ArrayList<>();
        for (Donnee d : this.li) {
            l.add(d.getCaracteristiques().get("Taux"));
        }
        List<Double> ref = new ArrayList<>();
        ref.add(1.5);
        ref.add(255.0);
        ref.add(1.0);

        assertEquals(ref, this.distanceUtils.toDoubleList(l));

        l = new ArrayList<>();
        for (Donnee d : this.li) {
            l.add(d.getCaracteristiques().get("Origine"));
        }

        assertNull(this.distanceUtils.toDoubleList(l));
    }
}