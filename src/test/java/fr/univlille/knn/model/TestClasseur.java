package fr.univlille.knn.model;

import fr.univlille.utils.knn.Distance;
import fr.univlille.utils.knn.DistanceEuclidienne;
import fr.univlille.utils.knn.MethodeKnn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestClasseur {
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
    private Classeur classeur;

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
        li.add(donnee4);
        li.add(donnee5);

        this.methodeKnn = new MethodeKnn(li);

        this.classeur = new Classeur(li);
    }

    @Test
    public void testConstructeur() {
        this.classeur = new Classeur(this.li);

        assertTrue(this.classeur instanceof Classeur);
        assertNotNull(this.classeur);
    }

    @Test
    public void testSetK_GetK() throws NumberFormatException{
        this.classeur.setK(1);
        assertEquals(1, this.classeur.getK());
        assertThrows(NumberFormatException.class, () -> this.classeur.setK(-1));
    }

    @Test
    public void testGetMethodeKnn() {
        assertNotNull(this.classeur.getMethodeKnn());
        assertTrue(this.classeur.getMethodeKnn() instanceof MethodeKnn);
    }

    @Test
    public void testMostCommonCategory() {
        assertEquals("categorie1", Classeur.mostCommonCategory(this.li));
    }

    @Test
    public void testClasserDonnee() {
        Map car = new HashMap<>();
        car.put("Couleur", "Vert");
        car.put("Taux", "254");
        car.put("Origine", "RV");
        Donnee donneeTest = new Donnee(car);

        this.classeur.classerDonnee(donneeTest);

        assertEquals(categorie1, donneeTest.getCategorie());
    }

    // Les méthodes sur la robustesse et la cross validation consomment énormément de temps.
    // C'est donc pour ça que nous n'avons pas inclut les tests de ces méthodes.
}