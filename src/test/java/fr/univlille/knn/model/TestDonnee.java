package fr.univlille.knn.model;

import fr.univlille.knn.model.Donnee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.HashMap;
import java.util.Map;

public class TestDonnee {
    public static final String SANS_CATEGORIE = "Sans catégorie";
    private String categorie;
    private Map<String, String> caracteristiques;
    private Donnee donnee;

    @BeforeEach
    public void setup() {
        /*
        * Paramétrage des attributs pour les test?
        */
        this.categorie = "categorie1";
        this.caracteristiques = new HashMap<>();
        this.caracteristiques.put("Rouge", "Couleur");
        this.caracteristiques.put("Bleu", "Couleur");
        this.caracteristiques.put("Vert", "Couleur");
        this.caracteristiques.put("Chien", "Animal");
        this.caracteristiques.put("Chat", "Animal");
        this.caracteristiques.put("Ornythorinque", "Animal");
        this.donnee = new Donnee(this.categorie, this.caracteristiques);
    }

    @Test
    public void testDonnee() {
        /*
        * Test du constructeur de Donnee.java, en spécifiant une catégorie.
        */
        this.donnee = new Donnee(this.categorie, caracteristiques);
        assertEquals("categorie1", this.donnee.getCategorie());
        assertEquals(caracteristiques, this.donnee.getCaracteristiques());


        /*
        * Test du constructeur de Donnee.java, sans spécifier de catégorie.
        */
        this.donnee = new Donnee(caracteristiques);
        assertEquals("Sans catégorie", this.donnee.getCategorie());
        assertEquals(caracteristiques, this.donnee.getCaracteristiques());
    }

    @Test
    public void testGetCategorie() {
        /*
        * Test de la méthode getCategorie().
        */
        assertEquals("categorie1", this.donnee.getCategorie());
        assertNotEquals("categorie2", this.donnee.getCategorie());
    }

    @Test
    public void testSetCategorie() {
        /*
        * Test de la méthode setCategorie().
        */
        assertEquals("categorie1", this.donnee.getCategorie());
        assertNotEquals("categorie2", this.donnee.getCategorie());
        this.donnee.setCategorie("categorie2");
        assertEquals("categorie2", this.donnee.getCategorie());
        assertNotEquals("categorie1", this.donnee.getCategorie());
    }

    @Test
    public void testEquals() {
        // Cas où ils sont littéralement les mêmes.
        assertTrue(this.donnee.equals(this.donnee));

        // Cas où le comparatif est null.
        assertFalse(this.donnee.equals(null));

        // Cas où le comparatif n'est pas de la même classe.
        assertFalse(this.donnee.equals(1));

        // Vont suivre les cas où c'est bien une instance de la classe Donnee.

        // Création d'une autre instance de la classe Donnee.
        String cat = "autre";
        Map<String,String> car = new HashMap<>();
        car.put("Rouge", "Couleur");
        car.put("Jaune", "Couleur");
        car.put("Vert", "Couleur");
        car.put("Chien", "Animal");
        car.put("Chaeazeat", "Animal");
        car.put("Ornythorinque", "Animal");
        Donnee comp;

        //Cas où les deux catégorie ne sont pas les mêmes.
        comp = new Donnee(cat, this.caracteristiques);
        assertFalse(this.donnee.equals(comp));

        // Cas où les deux Map de caractéristiques n'ont pas les mêmes clés.
        comp = new Donnee(this.categorie, car);
        assertFalse(this.donnee.equals(comp));

        // Cas où les les deux attributs sont différents.
        comp = new Donnee(cat, car);
        assertFalse(this.donnee.equals(comp));

        // Cas où les valeurs des Maps ne sont pas les mêmes.

        // Ici, on remet les mêmes clés, mais pas les mêmes valeurs.
        car = new HashMap<>();
        car.put("Rouge", "(╯°□°)╯︵ ┻━┻");
        car.put("Bleu", ":)");
        car.put("Vert", ":D");
        car.put("Chien", ":(");
        car.put("Chat", ":/");
        car.put("Ornythorinque", "┬─┬ノ( º _ ºノ)");

        comp = new Donnee(this.categorie, car);
        assertFalse(this.donnee.equals(comp));

        // Cas où les deux sont identiques.
        comp = new Donnee(this.categorie, this.caracteristiques);
        assertTrue(this.donnee.equals(comp));
    }
}
