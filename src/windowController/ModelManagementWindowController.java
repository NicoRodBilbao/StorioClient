package windowController;

import entities.Model;
import factories.ModelFactory;
import interfaces.Modelable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *  This window adds the functionality of Managing the entity Model and navigation
 *   through various windows.
 *
 * @author Nicolás Rodríguez
 */
public class ModelManagementWindowController {
    // FXML Attributes
    @FXML
    private TableView tvModel;
    @FXML 
    private TableColumn tcId;
    @FXML 
    private TableColumn tcModel;
    @FXML 
    private TableColumn tcDescription;
    @FXML 
    private TableColumn tcNote;
    @FXML 
    private TableColumn tcItemsId;
    @FXML 
    private MenuBar menuBar;
    @FXML 
    private Menu mnGoBack;
    @FXML 
    private Menu mnGoTo;
    @FXML 
    private MenuItem mi;
    @FXML 
    private MenuItem miBooking;
    @FXML 
    private MenuItem miReport;
    @FXML 
    private MenuItem miPack;
    @FXML 
    private MenuItem miItem;
    @FXML 
    private MenuItem miUser;
    @FXML 
    private Menu mnDarkMode;
    @FXML
    private Menu mnHelp;
    @FXML
    private Pane paneModel;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfModel;
    @FXML
    private TextField tfNote;
    @FXML
    private Label lblId;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblModel;
    @FXML
    private Label lblNote;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnSearch;
    /**
     * Logger for tracking the windows functionality.
     */
    protected static final Logger LOGGER = Logger.getLogger(ModelManagementWindowController.class.getName());
    
    Stage primaryStage;
    Modelable modelable = ModelFactory.getAccessUser();
    
    public void setStage(Parent root) {
        LOGGER.info("Initializing ModelManagementWindow.");
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Signin");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        LOGGER.info("Retrieving model data.");
        List<Model> listModel = modelable.listAllModels();
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tcNote.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        tvModel.setItems(FXCollections.observableArrayList(listModel));
        primaryStage.show();
    }
    
    private void windowShowing(WindowEvent event) {
        tfId.requestFocus();
        tfId.setDisable(true);
        tfDescription.setDisable(true);
        tfModel.setDisable(true);
        tfNote.setDisable(true);
        btnCreate.setDisable(false);
        btnModify.setDisable(false);
        btnSearch.setDisable(false);
        btnDelete.setDisable(false);

    }
    
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
}
