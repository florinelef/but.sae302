package fr.univlille.knn.view;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import fr.univlille.knn.model.Gestion;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AffichageChoisirDonnee extends Stage {
    private String cheminFichier;
    private String nomColonne;
    private Button buttonConfirmer;

    public AffichageChoisirDonnee(Gestion gestion){
        this.setTitle("Choisir données");

        Label labelImportDonnees = new Label("Choisissez le jeu de données à importer : ");
        Label labelChoixCategorie = new Label("Choisissez la catégorie à classer : ");

        Button buttonImportDonnee = new Button("Choisir lot de données");

        ComboBox<String> choixCategorie = new ComboBox<>();
        choixCategorie.setDisable(true);
        buttonImportDonnee.setOnAction(
        e -> {
            FileChooser dataFileChooser = new FileChooser();
            dataFileChooser.setTitle("Votre fichier");
            dataFileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/res"));

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            File dataFile = dataFileChooser.showOpenDialog(stage);

            cheminFichier =dataFile.getPath();

            try(CSVReader reader = new CSVReader(new FileReader(cheminFichier))){
                for (String categorie :
                        reader.readNext()) {
                    choixCategorie.getItems().add(categorie);
                }

            } catch (IOException | CsvValidationException exception) {
                new ExceptionPopUp(exception.getMessage());
            }
            choixCategorie.setDisable(false);
            buttonImportDonnee.setDisable(true);
            buttonImportDonnee.setText(cheminFichier);
        });

        choixCategorie.setOnAction(e->{
            buttonConfirmer.setDisable(false);
        });

        buttonConfirmer = new Button("Confirmer");
        buttonConfirmer.setDisable(true);
        buttonConfirmer.setOnAction(e->{
            this.nomColonne = choixCategorie.getValue();
            GestionAffichage view = new GestionAffichage(gestion, this.cheminFichier, this.nomColonne);
            this.close();
        });

        HBox hboxImportDonnees = new HBox(labelImportDonnees, buttonImportDonnee);
        HBox hboxChoisirCategorie = new HBox(labelChoixCategorie, choixCategorie);

        GridPane root = new GridPane();
        root.getChildren().addAll(hboxImportDonnees, hboxChoisirCategorie, buttonConfirmer);
        GridPane.setRowIndex(hboxImportDonnees, 1);
        GridPane.setRowIndex(hboxChoisirCategorie, 2);
        GridPane.setRowIndex(buttonConfirmer, 3);
        GridPane.setHalignment(hboxImportDonnees, HPos.CENTER);
        GridPane.setHalignment(hboxChoisirCategorie, HPos.CENTER);
        GridPane.setHalignment(buttonConfirmer, HPos.CENTER);
        root.setPadding(new Insets(30));
        root.setVgap(30.0);

        root.setStyle("-fx-background-color: #EFCFD4;");
        this.setScene(new Scene(root));
        this.setResizable(false);
        this.show();
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public String getNomColonne() {
        return nomColonne;
    }
}
