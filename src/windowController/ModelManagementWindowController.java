package windowController;

import entities.Model;
import factories.ModelFactory;
import interfaces.Modelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private Label label;
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
    private MenuItem miUser, miGoBack;
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
    Modelable modelable = ModelFactory.getAccessModel();

    public void setStage(Parent root) {
        LOGGER.info("Initializing ModelManagementWindow.");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Model Management");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        miPack.setOnAction(event -> this.handleOnMouseClickNavPack(event)); // Adds an event handler that records every time the miPack element is clicked
        miUser.setOnAction(event -> this.handleOnMouseClickNavUser(event));// Adds an event handler that records every time the miUser element is clicked
        miBooking.setOnAction(event -> this.handleOnMouseClickNavBooking(event));// Adds an event handler that records every time the miBooking element is clicked
        miItem.setOnAction(event -> this.handleOnMouseClickNavItem(event));// Adds an event handler that records every time the miModel element is clicked
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> { // Adds an event handler that records every time the escape key is pressed
            if (KeyCode.ESCAPE == event.getCode()) {
                Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Storio?").showAndWait();
                if (action.get() == ButtonType.OK) {
                    primaryStage.close();
                }
            }
        });
        tvModel.setOnMouseClicked(event -> this.handleOnMouseClickTable(event));
        miItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                primaryStage.close();
                Stage stage = new Stage();
                // Carga el document FXML y obtiene un objeto Parent
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ItemManagementWindow.fxml"));
                // Crea una escena a partir del Parent
                Parent root = null;
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(ModelManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ItemManagementWindowController controller = (ItemManagementWindowController) loader.getController();
                // Establece la escena en el escensario (Stage) y la muestra
                controller.setStage(stage);
                controller.setStage(root);
            }
        });
        miGoBack.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                primaryStage.close();
                Stage stage = new Stage();
                // Carga el document FXML y obtiene un objeto Parent
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LogInWindow.fxml"));
                // Crea una escena a partir del Parent
                Parent root = null;
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                LogInWindowController controller = (LogInWindowController) loader.getController();
                // Establece la escena en el escensario (Stage) y la muestra
                controller.setStage(stage);
                controller.initStage(root);
            }
        });        
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
        if (!btnCreate.isDisabled() & !btnModify.isDisabled()) { // Change to creation mode
            LOGGER.info("Create enabled state.");
            changeMode(1); // Change to creation mode
        } else { // Create and change to default mode
            LOGGER.info("Create disabled state.");
            if (!(tfModel.getText().trim().equals("") || tfModel.getText().trim().equals("") || tfNote.getText().trim().equals(""))) { // All fields have content
                LOGGER.info("Creating model.");
                modelable.createModel(new Model(0, tfModel.getText(), tfDescription.getText(), tfNote.getText(), null));
            } else {// Some fields are empty
                new Alert(Alert.AlertType.ERROR, "Fill the all the fields before trying to create.", ButtonType.OK).showAndWait();
            }
            refreshTable(); // Change to creation mode
            changeMode(0); // Some fields are empty
        }
        LOGGER.info("Finishing creation.");
    }

    public void updateModel(ActionEvent event) {
        LOGGER.info("Initializing update.");
        if (!btnCreate.isDisabled() & !btnModify.isDisabled()) {
            LOGGER.info("Update enabled state.");
            changeMode(2); // Change to update mode
        } else { // Update and change to default mode
            LOGGER.info("Update disabled state.");

            if (!tfId.getText().trim().equals("")) { // There's a Model selected
                LOGGER.info("Updating model.");
                modelable.updateModel(new Model(Integer.parseInt(tfId.getText()), tfModel.getText(), tfDescription.getText(), tfNote.getText(), null));
            } else { // There's not a Model selected
                new Alert(Alert.AlertType.ERROR, "Please select a model from the table before trying to update.", ButtonType.OK).showAndWait();
            }

            refreshTable(); // Refresh the table
            changeMode(0); // Change to default mode
        }
        LOGGER.info("Finishing update.");
    }

    public void findModel(ActionEvent event) {
        LOGGER.info("Initializing search.");
        if (!btnDelete.isDisabled() & !btnModify.isDisabled()) { // Change to search mode
            LOGGER.info("Search enabled state.");
            changeMode(3); // Change to search mode
        } else { // Find and change to default mode
            if (tfId.getText().trim().isEmpty()) {  // Unfindable
                new Alert(Alert.AlertType.ERROR, "Fill the ID field before trying to search.", ButtonType.OK).showAndWait();
                refreshTable(); // Refresh table content
            } else {
                LOGGER.log(Level.INFO, "Searching for Model {0}.", tfId.getText());
                List<Model> listModel = modelable.findModelById(Integer.parseInt(tfId.getText()));
                if (listModel.get(0) == null) { // Model wasn't found
                    new Alert(Alert.AlertType.ERROR, "Could not find the Model.", ButtonType.OK).showAndWait();
                    refreshTable(); // Refresh table content
                } else { // Set the found item on the table
                    tvModel.setItems(FXCollections.observableArrayList(listModel));
                }
            }
            changeMode(0); // Change to default mode
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

    private void handleOnMouseClickTable(MouseEvent event) {
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

    private void handleOnMouseClickMenu(MouseEvent event) {
        primaryStage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ItemManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ModelManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemManagementWindowController controller = (ItemManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.setStage(root);
    }
    
    /**
     * This method closes the current window and opens ModelManagementWindow.
     *
     * @param event The window event
     */
    private void handleOnMouseClickNavItem(ActionEvent event) {
        primaryStage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ItemManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemManagementWindowController controller = (ItemManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.setStage(root);
    }

    /**
     * This method closes the current window and opens PackManagementWindow.
     *
     * @param event The window event
     */
    private void handleOnMouseClickNavPack(ActionEvent event) {
        primaryStage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/PackManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PackManagementWindowController controller = (PackManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.initStage(root);
    }

    /**
     * This method closes the current window and opens UserManagementWindow.
     *
     * @param event The window event
     */
    private void handleOnMouseClickNavUser(ActionEvent event) {
        primaryStage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserManagementWindowController controller = (UserManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.initStage(root);
    }

    /**
     * This method closes the current window and opens BookingManagementWindow.
     *
     * @param event The window event
     */
    private void handleOnMouseClickNavBooking(ActionEvent event) {
        primaryStage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/BookingManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookingManagementWindowController controller = (BookingManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.initStage(root);
    }
}
