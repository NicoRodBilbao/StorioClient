package windowController;

import entities.Model;
import factories.ModelFactory;
import interfaces.Modelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This window adds the functionality of Managing the entity Model and
 * navigation through various windows.
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
    private Button btnCreate, btnDelete, btnModify, btnSearch;
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
        primaryStage.setTitle("Model Management");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        tvModel.setOnMouseClicked(event -> this.handleOnMouseClick(event));
        refreshTable();
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

    public void createModel(ActionEvent event) {
        LOGGER.info("Initializing creation.");
        if (!btnCreate.isDisabled() & !btnModify.isDisabled()) {
            LOGGER.info("Create enabled state.");
            changeMode(1);
        } else {
            LOGGER.info("Create disabled state.");
            LOGGER.info("Creating model.");
            modelable.createModel(new Model(0, tfModel.getText(), tfDescription.getText(), tfNote.getText(), null));
            
            refreshTable();
            changeMode(0);
        }
        LOGGER.info("Finishing creation.");
    }

    public void updateModel(ActionEvent event) {
        LOGGER.info("Initializing update.");
        if (!btnCreate.isDisabled() & !btnModify.isDisabled()) {
            LOGGER.info("Update enabled state.");
            changeMode(2);
        } else {
            LOGGER.info("Update disabled state.");

            LOGGER.info("Updating model.");
            modelable.updateModel(new Model(Integer.parseInt(tfId.getText()), tfModel.getText(), tfDescription.getText(), tfNote.getText(), null));
            
            refreshTable();
            changeMode(0);
        }
        LOGGER.info("Finishing update.");
    }

    public void findModel(ActionEvent event) {
        LOGGER.info("Initializing search.");
        if (!btnDelete.isDisabled() & !btnModify.isDisabled()) { // Change to search mode
            LOGGER.info("Search enabled state.");
            changeMode(3);
        } else { // Find and change to default mode
            if (tfId.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Fill the ID field before trying to search.", ButtonType.OK).showAndWait();
                refreshTable();
            } else {
                LOGGER.log(Level.INFO, "Searching for Model {0}.", tfId.getText());
                List<Model> listModel = modelable.findModelById(Integer.parseInt(tfId.getText()));
                if (listModel.get(0) == null) {
                    new Alert(Alert.AlertType.ERROR, "Could not find the Model.", ButtonType.OK).showAndWait();
                    refreshTable();
                } else {
                    tvModel.setItems(FXCollections.observableArrayList(listModel));
                }
                changeMode(0); // Change to default mode
            }

        }
        LOGGER.info("Finishing search.");
    }

    public void deleteModel(ActionEvent event) {
        LOGGER.info("Initializing deletion.");
        if (!btnDelete.isDisabled() & !btnModify.isDisabled()) { // Change to deletion mode
            LOGGER.info("Delete enabled state.");
            changeMode(4);
        } else { // Delete and change to default mode
            LOGGER.log(Level.INFO, "Deleting Model {0}.", tfId.getText());
            if (tfId.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Fill the ID field before trying to Delete.", ButtonType.OK).showAndWait();
            } else {
                modelable.deleteModel(Integer.parseInt(tfId.getText()));
            }
            changeMode(0);
            refreshTable();
        }
        LOGGER.info("Finishing deletion.");
    }

    private void refreshTable() {
        LOGGER.info("Retrieving model data.");
        List<Model> listModel = modelable.listAllModels();
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tcNote.setCellValueFactory(new PropertyValueFactory<>("notes"));
        tcItemsId.setCellValueFactory(new PropertyValueFactory<>("items"));

        tvModel.setItems(FXCollections.observableArrayList(listModel));
    }

    private void emptyFields() {
        LOGGER.info("Emptying fields.");
        tfId.setText("");
        tfDescription.setText("");
        tfModel.setText("");
        tfNote.setText("");
    }

    private void enableButtons() {
        LOGGER.info("Enabling buttons.");
        btnCreate.setDisable(false);
        btnModify.setDisable(false);
        btnSearch.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void disableTextFields() {
        LOGGER.info("Disabling text fields.");
        tfId.setDisable(true);
        tfDescription.setDisable(true);
        tfModel.setDisable(true);
        tfNote.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * This method changes the mode the window is on:
     * <ul>
     * <li><b>0: </b>Default mode, all buttons enabled, all textFields
     * disabled.</li>
     * <li><b>1: </b>Creation mode, create button enabled, all textField but
     * tfId enabled</li>
     * <li><b>2: </b>Update mode, update button enabled, all textField but tfId
     * enabled</li>
     * <li><b>3: </b>Search mode, search button enabled, all textField but tfId
     * disabled</li>
     * <li><b>4: </b>Deletion mode, delete button enabled, all textField but
     * tfId disabled</li>
     * </ul>
     *
     * @param selectedMode The mode selected for the window.
     */
    private void changeMode(Integer selectedMode) {
        switch (selectedMode) {
            case 0: // Regular mode
                LOGGER.info("Enabling and disabling fields.");
                disableTextFields();
                enableButtons();
                emptyFields();
                LOGGER.info("Refreshing table.");
                
                break;
            case 1: // Creation mode
                LOGGER.info("Enabling and disabling fields.");
                btnModify.setDisable(true);
                btnSearch.setDisable(true);
                btnDelete.setDisable(true);
                tfDescription.setDisable(false);
                tfModel.setDisable(false);
                tfNote.setDisable(false);
                tfId.setText("" + (modelable.countModel() + 1));
                LOGGER.log(Level.INFO, "Setting up Model for id {0}.", tfId.getId());
                LOGGER.info("Refreshing table.");
                refreshTable();
                break;
            case 2: // Update mode
                LOGGER.info("Enabling and disabling fields.");
                btnCreate.setDisable(true);
                btnSearch.setDisable(true);
                btnDelete.setDisable(true);
                tfDescription.setDisable(false);
                tfModel.setDisable(false);
                tfNote.setDisable(false);
                LOGGER.info("Refreshing table.");
                refreshTable();
                break;
            case 3: // Search mode
                LOGGER.info("Enabling and disabling fields.");
                btnModify.setDisable(true);
                btnDelete.setDisable(true);
                btnCreate.setDisable(true);
                tfId.setDisable(false);
                refreshTable();
                break;
            case 4: // Deletion mode
                LOGGER.info("Enabling and disabling fields.");
                btnModify.setDisable(true);
                btnSearch.setDisable(true);
                btnCreate.setDisable(true);
                tfId.setDisable(false);
                LOGGER.info("Refreshing table.");
                refreshTable();
                break;
        }
    }

    private void handleOnMouseClick(MouseEvent event) {
        TableView tv = (TableView) event.getSource();
        if (tv.getSelectionModel().getSelectedItem() != null) { // Checks if the table view is selected
            ObservableList selectedItems = tv.getSelectionModel().getSelectedItems();
            Model model = (Model) selectedItems.get(0);

            LOGGER.log(Level.INFO, "Selecting table row: {0}", model.getId());
            if (!(btnModify.isDisabled() && btnSearch.isDisabled() && btnDelete.isDisabled() && !btnCreate.isDisabled())) // If the creation mode is enabled, 
            {
                tfId.setText(String.valueOf(model.getId()));
            }
            tfDescription.setText(model.getDescription());
            tfModel.setText(model.getModel());
            tfNote.setText(model.getNotes());
        }
    }
}
