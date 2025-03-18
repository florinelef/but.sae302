package fr.univlille.knn;

import fr.univlille.knn.model.Gestion;
import fr.univlille.knn.view.AffichageChoisirDonnee;
import fr.univlille.knn.view.GestionAffichage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Gestion gestion = new Gestion();
        new AffichageChoisirDonnee(gestion);
    }
}
