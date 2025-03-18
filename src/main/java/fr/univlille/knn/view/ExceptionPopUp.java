package fr.univlille.knn.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExceptionPopUp extends Stage {
    public ExceptionPopUp(String message){
        this.setTitle("Erreur");
        Label label = new Label(message);
        Button close = new Button("OK");
        close.setOnAction(e -> {
            this.close();
        });
        VBox root = new VBox(label, close);
        VBox.setMargin(label, new Insets(20.0));
        VBox.setMargin(close, new Insets(20.0));
        this.setScene(new Scene(root));
        this.show();
    }
}
