package windowController;

import entities.Item;
import entities.Model;
import factories.ItemFactory;
import factories.ModelFactory;
import interfaces.Itemable;
import interfaces.Modelable;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This window adds the functionality of Managing the entity Item and
 * navigation through various windows.
 *
 * @author Nicolás Rodríguez
 */
public class ItemManagementWindowController {

    // FXML Attributes
    @FXML
    private TableView tvTableItem;
    @FXML
    private TableColumn tcId,tcIssues,tcModel,tcDate,tcPack;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu mnGoBack,mnGoBack1,mnGoTo,mnHelp,mnDarkMode;
    @FXML
    private MenuItem miBooking,miReport,miPack,miModel,miUser;
    @FXML
    private Pane paneModel;
    @FXML
    private TextField tfIdItem;
    @FXML
    private TextArea taIssuesItem;
    @FXML
    private ComboBox cbModelItem,cbPackItem;
    @FXML
    private DatePicker dpCreateDateItem;
    @FXML
    private Label lblIdItem,lblModelItem,lblIssuesItem,lblCreateDateItem,lblPackItem;
    @FXML
    private Button btnCreateItem, btnDeleteItem, btnModifyItem, btnSearchItem;
    /**
     * Logger for tracking the windows functionality.
     */
    protected static final Logger LOGGER = Logger.getLogger(ItemManagementWindowController.class.getName());

    Stage primaryStage;
    Itemable itemable = ItemFactory.getAccessItem();
    Modelable modelable = ModelFactory.getAccessModel();
    //Packable packable = PackFactory.getAccessPack();

    
    public void setStage(Parent root) {
        LOGGER.info("Initializing ItemManagementWindow.");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Model Management");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        
        //tvTableItem.setOnMouseClicked(event -> this.handleOnMouseClick(event));
        refreshTable();
        primaryStage.show();
    }

    private void windowShowing(WindowEvent event) {
        tfIdItem.requestFocus();
        tfIdItem.setDisable(true);
        /*taIssuesItem.setDisable(true);
        cbModelItem.setDisable(true);
        cbPackItem.setDisable(true);
        dpCreateDateItem.setDisable(true);*/
        btnCreateItem.setDisable(false);
        btnModifyItem.setDisable(false);
        btnSearchItem.setDisable(false);
        btnDeleteItem.setDisable(false);
    }

    public void createItem(ActionEvent event) {
    /*    LOGGER.info("Initializing creation.");
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
        LOGGER.info("Finishing creation.");*/
    }

    public void updateItem(ActionEvent event) {
        /*LOGGER.info("Initializing update.");
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
        LOGGER.info("Finishing update.");*/
    }

    public void findItem(ActionEvent event) {
        /*LOGGER.info("Initializing search.");
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
        LOGGER.info("Finishing search.");*/
    }

    public void deleteItem(ActionEvent event) {
        /*LOGGER.info("Initializing deletion.");
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
        LOGGER.info("Finishing deletion.");*/
    }

    private void refreshTable() {
        LOGGER.info("Retrieving model data.");
        List<Item> listItem = itemable.listAllItems();
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        tcIssues.setCellValueFactory(new PropertyValueFactory<>("issues"));
        tcPack.setCellValueFactory(new PropertyValueFactory<>("pack"));

        tvTableItem.setItems(FXCollections.observableArrayList(listItem));
        
        List<Model> listModel = modelable.listAllModels();
        cbModelItem.setItems(FXCollections.observableArrayList(listModel));
        //List<Pack> listPack = packable.listAllPacks();
        //cbPackItem.setItems(FXCollections.observableArrayList(listPack));
    }

    private void emptyFields() {
        LOGGER.info("Emptying fields.");
        tfIdItem.setText("");
        taIssuesItem.setText("");
        dpCreateDateItem.setValue(null);
        cbPackItem.setValue(null);
        cbModelItem.getSelectionModel().select(0);
    }

    private void enableButtons() {
        LOGGER.info("Enabling buttons.");
        btnCreateItem.setDisable(false);
        btnModifyItem.setDisable(false);
        btnSearchItem.setDisable(false);
        btnDeleteItem.setDisable(false);
    }

    private void disableTextFields() {
        LOGGER.info("Disabling text fields.");
        tfIdItem.setDisable(true);
        taIssuesItem.setDisable(true);
        dpCreateDateItem.setDisable(true);
        cbModelItem.setDisable(true);
        cbPackItem.setDisable(true);
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
    /*private void changeMode(Integer selectedMode) {
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
    }*/

    /*private void handleOnMouseClick(MouseEvent event) {
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
    }*/
}
