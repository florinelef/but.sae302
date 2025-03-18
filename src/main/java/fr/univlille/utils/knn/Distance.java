package fr.univlille.utils.knn;

import fr.univlille.knn.model.Donnee;

public interface Distance {
    public double distance(Donnee d1, Donnee d2);
}
