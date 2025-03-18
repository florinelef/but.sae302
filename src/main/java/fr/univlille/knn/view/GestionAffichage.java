package fr.univlille.knn.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import fr.univlille.knn.model.Gestion;
import fr.univlille.utils.StringToDouble;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*** Classe qui gère la fenêtre principal de l'application
 * @author <a href=mailto:maxime.gosselin.etu@univ-lille.fr>Maxime GOSSELIN</a>
 * @author <a href=mailto:florine.lefebvre.etu@univ-lille.fr>Florine LEFEBVRE</a>
 * @author <a href=mailto:nathanael.collumeau.etu@univ-lille.fr>Nathanaël COLLUMEAU</a>
 * @author <a href=mailto:baptiste.royer.etu@univ-lille.fr>Baptiste ROYER</a>
 * @version Jalon 2
 */
public class GestionAffichage extends Stage{
    /** Le modèle qui gère le fonctionnement interne de l'application */
    private final Gestion modele;
    /** Le graphique des points */

    private GrapheDonnee grapheActuel;
    /** La caracteristique représenté par l'axe X */
    private String xAxis;
    /** La caracteristique représenté par l'axe Y */
    private String yAxis;
    /** Le bouton pour changer l'axe X */
    private final MenuButton menuButtonChoixAxeX;
    /** Le bouton pour changer l'axe Y */
    private final MenuButton menuButtonChoixAxeY;
    /** Le bouton pour ajouter un point*/
    private final Button buttonAjoutPoint;
    /** Le bouton pour classer les points*/
    private Button buttonClasserPoint;
    /** Le bouton pour importer le jeu de données*/
    private final ScrollPane spCorrespondanceAxeX;
    private final ScrollPane spCorrespondanceAxeY;
    /** L'entier qui correspond à la tab*/
    private static int tabNumber = 1;

    /**
     * Le constructeur de la classe, qui gère donc affiche l'application
     * @param modele Le modele
     */
    public GestionAffichage(Gestion modele, String cheminFichier, String nomCategorie){

        this.modele = modele;
        importerDonnees(cheminFichier, nomCategorie);

        this.setTitle("K-NN");

        Label titre = creationLabelTitre(); // Création du titre


        TabPane tabs = new TabPane(); // Création du tab avec les graphes
        Tab premiereTab = new Tab("Graphe " + tabNumber, this.creationGraphes()); // Création de la première Tab
        premiereTab.setClosable(false);
        tabs.getTabs().add(premiereTab);

        Tab tabPlus = new Tab("+");
        tabPlus.setClosable(false);
        tabs.getTabs().add(tabPlus);

        grapheActuel = (GrapheDonnee) tabs.getSelectionModel().getSelectedItem().getContent();
        tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab != null){
                if(newTab.getText().equals("+")) { //test si le tab selectionne est le tab "+"
                    tabNumber++;
                    GrapheDonnee nouveauGraphe = this.creationGraphes();
                    this.modele.mettreAJourDonnee();
                    Tab nouveauTab = new Tab("Graphe " + tabNumber, nouveauGraphe);
                    tabs.getTabs().add(tabs.getTabs().size()-1, nouveauTab);
                    nouveauTab.setOnClosed(e-> modele.detach(nouveauGraphe));
                    SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
                    selectionModel.select(tabs.getTabs().size() - 2);
                }

            }
            grapheActuel = (GrapheDonnee) tabs.getSelectionModel().getSelectedItem().getContent();
        });



        menuButtonChoixAxeX = new MenuButton("Axe X");
        setButtonChoixAxe(menuButtonChoixAxeX);
        spCorrespondanceAxeX = createScrollPane();

        VBox vBoxChoixAxeX = new VBox(new Label("Axe X:"), menuButtonChoixAxeX, spCorrespondanceAxeX);


        menuButtonChoixAxeY = new MenuButton("Axe Y");
        setButtonChoixAxe(menuButtonChoixAxeY);
        spCorrespondanceAxeY = createScrollPane();

        VBox vBoxChoixAxeY = new VBox(new Label("Axe Y:"), menuButtonChoixAxeY, spCorrespondanceAxeY);


        HBox hBoxAxes = new HBox(vBoxChoixAxeX, vBoxChoixAxeY);
        hBoxAxes.setPrefSize(380, 500);
        hBoxAxes.setStyle("-fx-background-color: #EFCFD4;");


        // La HBOX du milieu de la page (avec le tabs et le choix des axes)
        HBox hBoxMilieuPage = new HBox(tabs, hBoxAxes);

        buttonAjoutPoint = new Button("Ajouter Point");
        buttonAjoutPoint.setOnAction(evt -> {
            new AjoutPointAffichage(modele);
            buttonClasserPoint.setDisable(false);
        });

        Label labelK = new Label("Valeur de K :");

        TextField tfK = new TextField("" + modele.getK());
        Slider sliderK = new Slider(1, modele.getMaxK(), modele.getK());

        tfK.setOnAction(e->{
            try {
                int k = Integer.parseInt(tfK.getText());
                this.modele.setK(k);
                sliderK.setValue(k);
            } catch (NumberFormatException exception){
                new ExceptionPopUp("K doit être un entier non nul inférieur à " + modele.getMaxK());
            }
        });

        sliderK.valueProperty().addListener((observableValue, oldValue, newValue) ->
                tfK.setText("" + newValue.intValue()));

        Button calculK = new Button("Calcul K Optimal");
        calculK.setOnAction(e->{
            int k = modele.getOptimalK();
            modele.setK(k);
            tfK.setText("" + k);
            sliderK.setValue(k);
        });

        VBox vBoxMenuK = new VBox(labelK, tfK, sliderK, calculK);


        buttonClasserPoint = new Button("Classer Point");
        buttonClasserPoint.setDisable(true);
        buttonClasserPoint.setOnAction(e -> {
            modele.classerDonnees();
            mettreAJourGraphe();
            buttonClasserPoint.setDisable(true);
            buttonAjoutPoint.setDisable(false);
        });


        HBox hBoxBasDePage = new HBox(buttonAjoutPoint, vBoxMenuK, buttonClasserPoint);
        hBoxBasDePage.setPrefSize(1280, 120);
        hBoxBasDePage.setStyle("-fx-background-color: #E4A0B7;");
        buttonAjoutPoint.setStyle("-fx-font-size: 30;");
        buttonClasserPoint.setStyle("-fx-font-size: 30;");
        hBoxBasDePage.setPadding(new Insets(45));
        hBoxBasDePage.setSpacing(200.0);


        VBox vBox1 = new VBox(titre, hBoxMilieuPage, hBoxBasDePage);
        BorderPane root = new BorderPane(vBox1);

        this.setScene(new Scene(root, 1280, 720));
        this.setResizable(false);
        this.show();
    }

    /**
     * importe les données dans le modèle à partir du chemin
     */
    private void importerDonnees(String path, String nomCategorie){
        // Le chargement des données dans le modèle
        modele.chargerDonnees(path, nomCategorie);
        // Choix des axes par défaut
        this.xAxis = Gestion.caracteristiques.get(1);
        this.yAxis = Gestion.caracteristiques.get(2);
        modele.mettreAJourDonnee();
    }

    /**
     * Met à jour le graphe actuel (supprime les données et les remplace)
     */
    private void mettreAJourGraphe(){
        supprimerDonnee();
        this.modele.mettreAJourDonnee();
    }

    /**
     * Supprime les donnee du graphe actuel pour mettre à jour le graphe
     */
    private void supprimerDonnee(){
        for (Series<Number, Number> serie :
                this.grapheActuel.getData()) {
            serie.getData().clear();
        }
    }

    /**
     * Configure le MenuButton: Insère les menus items dans le MenuButton et configure sa taille et position
     * @param menuButton Le MenuButton a configuré
     */
    private void setButtonChoixAxe(MenuButton menuButton){
        for (String caracteristique: Gestion.caracteristiques) {
            MenuItem menuItem = creationMenuItem(menuButton, caracteristique);
            menuButton.getItems().add(menuItem);
        }
        menuButton.setPrefSize(190, 20);
        menuButton.setAlignment(Pos.CENTER);
    }

    /**
     * Création et configuration (setOnAction) d'un Menu Item pour les MenuButton des choix des Axes
     * @param menuButton Le MenuButton dans lequel est le MenuItem
     * @param caracteristique Caractéristique que représente le MenuItem
     * @return Le MenuItem configuré
     */
    private MenuItem creationMenuItem(MenuButton menuButton, String caracteristique){
        MenuItem menuItem = new MenuItem(caracteristique);
        menuItem.setOnAction(actionEvent -> {
            String axe = ((MenuItem) actionEvent.getSource()).getText();
            setAxis(axe, menuButton);
        });
        return menuItem;
    }

    /**
     * Change l'axe (X ou Y selon le MenuButton) en fonction de la Caractéristique et met à jour le graphe
     * @param caracteristique La Caractéristique à mettre dans l'axe
     * @param menuButton Le MenuButton parent (Soit X, soit Y)
     */
    private void setAxis(String caracteristique, MenuButton menuButton){
        if(menuButton == menuButtonChoixAxeX){
            setXAxis(caracteristique);
            menuButtonChoixAxeX.setText(caracteristique);
        } else {
            setYAxis(caracteristique);
            menuButtonChoixAxeY.setText(caracteristique);
        }
    }

    /**
     * Change l'axe X et met a jour le graphe
     */
    private void setXAxis(String caracteristique){
        this.xAxis = caracteristique;
        this.grapheActuel.getXAxis().setLabel(caracteristique);
        this.grapheActuel.setxAxis(caracteristique);
        this.mettreAJourGraphe();
        this.setTableCorrespondance(caracteristique, spCorrespondanceAxeX);
    }

    /**
     * Change l'axe Y et met a jour le graphe
     */
    private void setYAxis(String caracteristique){
        this.yAxis = caracteristique;
        this.grapheActuel.getYAxis().setLabel(caracteristique);
        this.grapheActuel.setyAxis(caracteristique);
        this.mettreAJourGraphe();
        this.setTableCorrespondance(caracteristique, spCorrespondanceAxeY);
    }

    private void setTableCorrespondance(String caracteristique, ScrollPane sp){
        if(StringToDouble.contains(caracteristique)){
            sp.setVisible(true);
            VBox vBox = ((VBox) sp.getContent());
            vBox.getChildren().clear();

            Map<String, Double> tableCorrespondance = StringToDouble.getTable(caracteristique);
            ArrayList<Label> textes = new ArrayList<>();

            for (String key:
                    tableCorrespondance.keySet()) {
                Label texte = new Label(tableCorrespondance.get(key).toString() + "  ->  " + key);
                textes.add(texte);
            }

            textes.sort(Comparator.comparingDouble(label -> {
                String s = label.getText();
                return Double.parseDouble(s.substring(0, s.indexOf(" -> ")));
            }));
            vBox.getChildren().addAll(textes);
        } else {
            sp.setVisible(false);
        }
    }

    /**
     * Crée le Label du titre de l'application
     * @return label
     */
    private Label creationLabelTitre(){
        Label titre = new Label("K-NN");
        titre.setPrefSize(1280, 100);
        titre.setTextFill(Color.WHITE);
        titre.setStyle("-fx-background-color: #E4A0B7;" +
                "-fx-font-size: 40;" +
                "-fx-alignment:CENTER;");
        return titre;
    }

    /**
     * Créer et configure les graphes
     * @return La liste des n graphes
     */
    private GrapheDonnee creationGraphes(){
        GrapheDonnee graphe = new GrapheDonnee(new NumberAxis(), new NumberAxis(), this.modele, this.xAxis, this.yAxis);
        this.modele.mettreAJourDonnee();
        return graphe;
    }

    private ScrollPane createScrollPane(){
        ScrollPane sp = new ScrollPane(new VBox());
        sp.setVisible(false);
        sp.setPrefSize(190, 600);
        return sp;
    }
}
