package fr.univlille.knn.model;

import fr.univlille.knn.view.ExceptionPopUp;
import fr.univlille.utils.knn.MethodeKnn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestGestionApresModif {
    public static final String SANS_CATEGORIE = "Sans catégorie";
    private String categorie1;
    private String categorie2;
    private String categorie3;
    private String sans_categorie;
    private Map<String, String> caracteristiques1;
    private Map<String, String> caracteristiques2;
    private Map<String, String> caracteristiques3;
    private Map<String, String> caracteristiques4;
    private Map<String, String> caracteristiques5;
    private Map<String, String> caracteristiques6;
    private Donnee donnee1;
    private Donnee donnee2;
    private Donnee donnee3;
    private Donnee donnee4;
    private Donnee donnee5;
    private Donnee donnee6;
    private List<Donnee> li;
    private List<String> cat;
    private List<String> car;
    private Gestion gestion;

    @BeforeEach
    public void setup() {
        /*
         * Paramétrage des attributs pour les test.
         */
        this.categorie1 = "Setosa";
        this.categorie2 = "Versicolor";
        this.categorie3 = "Virginica";
        this.sans_categorie = "Sans catégorie";
        this.caracteristiques1 = new HashMap<>();
        this.caracteristiques1.put("petal.length", "1.4");
        this.caracteristiques1.put("petal.width", ".2");
        this.caracteristiques1.put("sepal.length", "4.9");
        this.caracteristiques1.put("sepal.width", "3");

        this.caracteristiques2 = new HashMap<>();
        this.caracteristiques2.put("petal.length", "1.3");
        this.caracteristiques2.put("petal.width", ".2");
        this.caracteristiques2.put("sepal.length", "4.7");
        this.caracteristiques2.put("sepal.width", "3.2");

        this.caracteristiques3 = new HashMap<>();
        this.caracteristiques3.put("petal.length", "4.5");
        this.caracteristiques3.put("petal.width", "1.5");
        this.caracteristiques3.put("sepal.length", "6.4");
        this.caracteristiques3.put("sepal.width", "3.2");

        this.caracteristiques4 = new HashMap<>();
        this.caracteristiques4.put("petal.length", "4.9");
        this.caracteristiques4.put("petal.width", "1.5");
        this.caracteristiques4.put("sepal.length", "6.9");
        this.caracteristiques4.put("sepal.width", "3.1");

        this.caracteristiques5 = new HashMap<>();
        this.caracteristiques5.put("petal.length", "5.3");
        this.caracteristiques5.put("petal.width", "2.3");
        this.caracteristiques5.put("sepal.length", "6.4");
        this.caracteristiques5.put("sepal.width", "3.2");

        this.caracteristiques6 = new HashMap<>();
        this.caracteristiques6.put("petal.length", "5.5");
        this.caracteristiques6.put("petal.width", "1.8");
        this.caracteristiques6.put("sepal.length", "6.5");
        this.caracteristiques6.put("sepal.width", "3");

        this.donnee1 = new Donnee(this.caracteristiques1);
        this.donnee1.setCategorie(categorie1);
        this.donnee2 = new Donnee(this.caracteristiques2);
        this.donnee2.setCategorie(categorie1);
        this.donnee3 = new Donnee(this.caracteristiques3);
        this.donnee3.setCategorie(categorie2);
        this.donnee4 = new Donnee(this.caracteristiques4);
        this.donnee4.setCategorie(categorie2);
        this.donnee5 = new Donnee(this.caracteristiques5);
        this.donnee5.setCategorie(categorie3);
        this.donnee6 = new Donnee(this.caracteristiques6);
        this.donnee6.setCategorie(categorie3);

        this.li = new ArrayList<>();
        li.add(donnee1);
        li.add(donnee2);
        li.add(donnee3);
        li.add(donnee4);
        li.add(donnee5);
        li.add(donnee6);

        this.cat = new ArrayList<>();
        cat.add(categorie1);
        cat.add(categorie2);
        cat.add(categorie3);
        cat.add(sans_categorie);

        this.car = new ArrayList<>();
        car.add("petal.length");
        car.add("petal.width");
        car.add("sepal.length");
        car.add("sepal.width");

        this.gestion = new Gestion();

        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        this.gestion.chargerDonnees(helper.substring(0, helper.length()-1) + "/res/Test.csv", "variety");
    }
    @Test
    public void testChargerDonnees() {
        assertEquals(this.cat, Gestion.categories);
        assertEquals(this.car, Gestion.caracteristiques);
        assertEquals(this.li, this.gestion.getDonnees());
        this.gestion = new Gestion();
    }

    @Test
    public void testSetK() throws NumberFormatException {
        this.gestion.setK(1);
        assertEquals(1, this.gestion.getClasseur().getK());
        assertThrows(NumberFormatException.class, () -> this.gestion.setK(-1));
    }

    @Test
    public void testGetMaxK() {
        assertEquals((int)(6/5), this.gestion.getMaxK());
    }

    @Test
    public void testGetK() {
        assertEquals(5, this.gestion.getK());
    }
}
