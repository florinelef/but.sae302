package fr.univlille.knn.view;

import fr.univlille.knn.model.Gestion;
import fr.univlille.knn.model.Donnee;
import fr.univlille.utils.Observable;
import fr.univlille.utils.Observer;
import fr.univlille.utils.StringToDouble;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class GrapheDonnee extends ScatterChart<Number, Number> implements Observer {
    private String xAxis;
    private String yAxis;

    public GrapheDonnee(Axis<Number> axis, Axis<Number> axis1, Gestion modele, String xAxis, String yAxis) {
        super(axis, axis1);
        modele.attach(this);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.creerSerie();
        this.configurerGraphe();
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {
        Donnee donnee = (Donnee) data;
        String strValueX = donnee.getCaracteristiques().get(this.xAxis); //récupère la valeur associé à la caractéristque X
        String strValueY = donnee.getCaracteristiques().get(this.yAxis); //récupère la valeur associé à la caractéristque Y
        double valueX;
        double valueY;
        try{
            valueX = Double.parseDouble(strValueX);
        } catch (Exception e){
            valueX = StringToDouble.toDouble(this.xAxis, strValueX);
        }
        try{
            valueY = Double.parseDouble(strValueY);
        } catch (Exception e){
            valueY = StringToDouble.toDouble(this.yAxis, strValueY);
        }
        String categorie = donnee.getCategorie(); //récupère la categorie du donnee
        Series<Number, Number> serie = this.getData().get(Gestion.categories.indexOf(categorie)); //récupère la série associé à la bonne categorie
        Data<Double, Double> point = new XYChart.Data<>(valueX, valueY);
        serie.getData().add(new XYChart.Data<>(valueX, valueY)); //ajoute le donnee a la série
        this.updateLegend();
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public void creerSerie(){
        Series<Number, Number> serie;
        for (String categorie : Gestion.categories) {
            serie = new Series<>();
            serie.setName(categorie);
            this.getData().add(serie);
        }
    }

    public void configurerGraphe(){
        this.getXAxis().setLabel(xAxis);
        this.getXAxis().setAutoRanging(true);
        this.getYAxis().setLabel(yAxis);
        this.getYAxis().setAutoRanging(true);

        this.setLegendSide(Side.BOTTOM);
        this.setPrefSize(900, 500);
        this.setMaxSize(900, 500);
    }
}
