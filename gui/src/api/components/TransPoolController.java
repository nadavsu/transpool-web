package api.components;

import api.Engine;
import api.components.data.bar.DataBarController;
import api.components.trips.bar.Clearable;
import api.components.menu.bar.MenuBarController;
import api.components.trips.bar.match.MatchTripController;
import api.components.trips.bar.offer.TransPoolTripOfferController;
import api.components.trips.bar.request.TransPoolTripRequestController;
import data.transpool.TransPoolData;
import data.transpool.trip.PossibleMatch;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
    The main controller of the application.
 */
public class TransPoolController {

    private Stage primaryStage;
    private Engine engine;

    @FXML private MatchTripController matchTripComponentController;
    @FXML private MenuBarController menuBarComponentController;
    @FXML private TransPoolTripOfferController tripOfferComponentController;
    @FXML private TransPoolTripRequestController tripRequestComponentController;
    @FXML private DataBarController dataBarComponentController;

    @FXML private MenuBar menuBarComponent;
    @FXML private AnchorPane matchTripComponent;
    @FXML private AnchorPane tripOfferComponent;
    @FXML private AnchorPane tripRequestComponent;
    @FXML private VBox dataBarComponent;

    private BooleanProperty fileLoaded;
    private StringProperty currentTaskProgress;

    public TransPoolController() {
        fileLoaded = new SimpleBooleanProperty(false);
        currentTaskProgress = new SimpleStringProperty("");
    }

    @FXML
    public void initialize() {
        if (matchTripComponentController != null
                && menuBarComponentController != null
                && tripOfferComponentController != null
                && tripRequestComponentController != null) {
            matchTripComponentController.setTransPoolController(this);
            tripOfferComponentController.setTranspoolController(this);
            tripRequestComponentController.setTransPoolController(this);
            menuBarComponentController.setTransPoolController(this);
            dataBarComponentController.setTransPoolController(this);
        }

        fileLoaded.bindBidirectional(matchTripComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripOfferComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripRequestComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(menuBarComponentController.fileLoadedProperty());
        currentTaskProgress.bindBidirectional(dataBarComponentController.currentTaskProgressProperty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        fileLoaded.bind(this.engine.fileLoadedProperty());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Engine getEngine() {
        return engine;
    }


    //---------------------------------------------------------------------------------------------//

    public boolean isFileLoaded() {
        return fileLoaded.get();
    }

    public void setFileLoaded(boolean value) {
        fileLoaded.set(value);
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }

    //---------------------------------------------------------------------------------------------//

    public void loadFile() {
        menuBarComponentController.loadFile();
    }


    public void setColorScheme(String colorSchemeFileLocation) {
        menuBarComponentController.setColorScheme(colorSchemeFileLocation);
    }

    public void quit() {
        menuBarComponentController.quit();
    }

    //---------------------------------------------------------------------------------------------//
    public void addNewTripOffer() {
        tripOfferComponentController.addNewTripOffer();
    }


    //---------------------------------------------------------------------------------------------//

    public void addNewTripRequest() {
        tripRequestComponentController.addNewTripRequest();
    }

    //---------------------------------------------------------------------------------------------//

    public void searchForMatches() {
        matchTripComponentController.searchForMatches();
    }

    public void clearForm(Clearable form) {
        form.clear();
    }

    public void bindTaskToUI(Task currentRunningTask) {
        dataBarComponentController.bindTaskToUI(currentRunningTask);
    }

    public void bindUIToData(TransPoolData data) {
        dataBarComponentController.bindUIToData(data);
        matchTripComponentController.bindUIToData(data);
        tripOfferComponentController.bindDataToUI(data);
    }

    public void createNewMatch() {
        matchTripComponentController.createNewMatch();
    }

/*    public void addTripRequestCard(Node card) {
        dataBarComponentController.addTripRequestCard(card);
    }*/
}