package fr.univlille.knn.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import fr.univlille.utils.knn.DistanceEuclidienneNormalisee;
import fr.univlille.knn.view.ExceptionPopUp;
import fr.univlille.utils.Observable;
import fr.univlille.utils.StringToDouble;
import fr.univlille.utils.knn.MethodeKnn;

public class Gestion extends Observable {
    public static List<String> categories = new ArrayList<>();
    public static List<String> caracteristiques = new ArrayList<>();
    private List<Donnee> donnees;
    private Classeur classeur;

    public Gestion(){
        this.donnees = new ArrayList<>();
    }

    public List<Donnee> getDonnees(){
        return this.donnees;
    }

    public Classeur getClasseur() {
        return this.classeur;
    }

    public void setK(int k) throws NumberFormatException{
        this.classeur.setK(k);
    }

    public int getK(){
        return this.classeur.getK();
    }

    public int getMaxK(){
        return this.classeur.getMaxK();
    }

    public int getOptimalK(){
        return this.classeur.robustness();
    }

    //ajout d'un point en connaissance de sa catégorie (pour le chargement des données)
    private void ajouterDonnee(String c, Map<String, String> caracteristiques){
        Donnee d = new Donnee(c, caracteristiques);
        this.donnees.add(d);
    }

    //ajout d'un point sans connaissance de sa catégorie (avec les caractéristiques données par l'utilisateur)
    public void ajouterDonnee(Map<String, String> caracteristiques){
        Donnee d = new Donnee(Donnee.SANS_CATEGORIE, caracteristiques);
        this.donnees.add(d);
        notifyObservers(d);
    }

    public void mettreAJourDonnee(){
        for (Donnee d : this.donnees) {
            notifyObservers(d);
        }
    }

    public void classerDonnees(){
        for(Donnee d : this.donnees){
            if(d.getCategorie().equals(Donnee.SANS_CATEGORIE)){
                classeur.classerDonnee(d);
                notifyObservers(d);
            }
        }
    }

    public void chargerDonnees(String cheminFichier, String colonneCategorie) {
        Gestion.caracteristiques = new ArrayList<>();
        Gestion.categories = new ArrayList<>();
        this.donnees = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(cheminFichier))){
            List<String[]> csv = reader.readAll();
            String[] firstLine = csv.get(0);
            List<String> nomCaracteristiques = new ArrayList<>(Arrays.asList(firstLine));

            for(int i=1; i<csv.size(); i++){
                Map<String, String> caracteristiques = new TreeMap<>();
                String[] ligne = csv.get(i);
                String categorie = "";
                for(int j=0; j<ligne.length; j++){
                    if(!nomCaracteristiques.get(j).equals(colonneCategorie)){
                        caracteristiques.put(nomCaracteristiques.get(j), ligne[j]);
                        StringToDouble.toDouble(nomCaracteristiques.get(j), ligne[j]);
                    } else {
                        if(!Gestion.categories.contains(ligne[j])){
                            Gestion.categories.add(ligne[j]);
                        }
                        categorie = ligne[j];
                    }
                }
                this.ajouterDonnee(categorie, caracteristiques);
            }
            Gestion.categories.add(Donnee.SANS_CATEGORIE);
            Gestion.caracteristiques.addAll(this.donnees.get(0).getCaracteristiques().keySet());
            classeur = new Classeur(this.donnees);
        } catch (IOException | CsvException e) {
            System.err.println(e.getClass().getName() + "\n" + e.getMessage());
            new ExceptionPopUp("Impossible de charger le fichier (format attendu : .csv)");
        }
    }

    @Override
    public String toString() {
        return this.donnees.toString();
    }
}