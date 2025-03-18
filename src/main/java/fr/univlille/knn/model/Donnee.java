package fr.univlille.knn.model;

import fr.univlille.utils.knn.MethodeKnn;

import java.util.*;

public class Donnee {
    public static final String SANS_CATEGORIE = "Sans catégorie";
    private String categorie;
    private Map<String, String> caracteristiques;


    public Donnee(String categorie, Map<String, String> caracteristiques){
        this.categorie = categorie;
        this.caracteristiques = caracteristiques;
    }

    public Donnee(Map<String, String> caracteristiques){
        this(Donnee.SANS_CATEGORIE, caracteristiques);
    }

    public Map<String, String> getCaracteristiques(){
        return this.caracteristiques;
    }

    public String getCategorie(){
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Donnee other = (Donnee) o;

        if (!this.categorie.equals(other.categorie)) {
            return false;
        }

        List<String> keys = new ArrayList<>(caracteristiques.keySet());
        List<String> oKeys = new ArrayList<>(other.getCaracteristiques().keySet());
        Collections.sort(keys);
        Collections.sort(oKeys);
        for (int i = 0; i < keys.size(); i++) {
            if (!keys.get(i).equals(oKeys.get(i))) {
                // Vérifie que les deux possèdent les mêmes caractéristiques.
                return false;
            } else if (!this.caracteristiques.get(keys.get(i)).equals(other.getCaracteristiques().get(keys.get(i)))) {
                // Vérifie que les valeurs sont identiques des deux côtés.
                return false;
            }
        }

        return true;
    }

}
