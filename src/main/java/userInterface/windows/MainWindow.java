package userInterface.windows;

import algorithms.Graph;
import algorithms.dijkstra.DijkstraAlgorithms;
import algorithms.dijkstra.WeightTypes.TimeWeightCalculator;
import algorithms.pointsFinders.ClosestPointFinderTree;
import algorithms.pointsFinders.QuadTree;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mapObjects.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    public TextField startLat;
    public TextField startLon;
    public TextField finishLat;
    public TextField finishLon;
    public ChoiceBox<String> weightTypes;
    public Button getRoute;
    public VBox routeBox;
    public Button speedConfigFileSelect;
    public Button blockedPointFileConfigSelect;

    private OsmParser parser;
    private MapData mapData;
    private Graph graph;
    private QuadTree quadTree;
    private DijkstraAlgorithms algorithm;

    public MainWindow(){
        try {
            //Фаил карты
            URL urlToMap = MainWindow.class.getResource("/toParse.osm");
            parser = new OsmParser(Paths.get(urlToMap.toURI()));
            mapData = parser.extract();
            quadTree = new ClosestPointFinderTree(
                    55.63213647883612, 52.201261423294824,
                    55.81003367465946, 52.605008981888574,
                    6);
            mapData.getTowerNodes()
                    .forEach(quadTree::add);
            graph = new Graph(mapData.getNodes(), mapData.getEdges());
            algorithm = new DijkstraAlgorithms(graph);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weightTypes.getItems().addAll("Расстояние", "Время");

        getRoute.setOnAction(action -> calculateRoute());
    }

    public void calculateRoute(){
        GeographicPoint start = new GeographicPoint(
                Double.parseDouble(startLat.getText()),
                Double.parseDouble(startLon.getText()));
        GeographicPoint finish = new GeographicPoint(
                Double.parseDouble(finishLat.getText()),
                Double.parseDouble(finishLon.getText()));

        Route route = algorithm.calculatePath(
                new TimeWeightCalculator(),
                (Node) quadTree.find(start),
                (Node) quadTree.find(finish));
    }
}
