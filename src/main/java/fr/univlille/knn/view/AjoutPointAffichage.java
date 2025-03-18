package fr.univlille.knn.view;

import fr.univlille.knn.model.Gestion;
import fr.univlille.utils.StringToDouble;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class AjoutPointAffichage extends Stage{

    public AjoutPointAffichage(Gestion modele){
        this.setTitle("Ajouter un point");

        Label titre = new Label("Ajouter un point");
        titre.setPrefSize(1280, 100);
        titre.setTextFill(Color.PURPLE);
        titre.setStyle(
                "-fx-font-size: 40;" +
                "-fx-alignment:CENTER;");
        HBox hb;
        VBox cadre = new VBox();
        for(String c : Gestion.caracteristiques){
            hb = new HBox(new Label(c), new TextField());
            hb.setSpacing(20.0);
            cadre.getChildren().add(hb);
        }
        cadre.setPadding(new Insets(50));

        Button ajouter = new Button("Ajouter le point");
        ajouter.setOnAction(event -> {
            Map<String, String> values = new HashMap<>();
            String erreurMessage = "";
            try {
                int i=0;
                for (String caracteristique : Gestion.caracteristiques) {
                    erreurMessage = caracteristique;
                    HBox hb1 = (HBox) (cadre.getChildren().get(i));
                    TextField tf = (TextField) (hb1.getChildren().get(1));
                    if(!StringToDouble.contains(caracteristique)){
                        Double.parseDouble(tf.getText());
                    }
                    values.put(caracteristique, tf.getText());
                    i++;
                }
                ajouterPoint(modele, values);
            } catch (Exception e) {
                new ExceptionPopUp("Erreur lors de la saisie de la caractéristique " + erreurMessage);
            }
            this.close();
        });
        ajouter.setStyle("-fx-font-size: 30;");

        GridPane root = new GridPane();
        root.getChildren().add(titre);
        root.getChildren().add(cadre);
        root.getChildren().add(ajouter);
        GridPane.setRowIndex(titre, 1);
        GridPane.setRowIndex(cadre, 2);
        GridPane.setRowIndex(ajouter, 3);
        GridPane.setHalignment(ajouter, HPos.CENTER);

        this.setScene(new Scene(root, 700, 600));
        this.show();
    }

    private void ajouterPoint(Gestion modele, Map<String, String> map){
        modele.ajouterDonnee(map);
    }
}
