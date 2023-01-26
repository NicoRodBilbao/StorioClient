package windowController;

import entities.Item;
import entities.Model;
import factories.ItemFactory;
import factories.ModelFactory;
import interfaces.Itemable;
import interfaces.Modelable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This window adds the functionality of Managing the entity Item and navigation
 * through various windows.
 *
 * @author Nicolás Rodríguez
 */
public class ItemManagementWindowController {

    // FXML Attributes
    @FXML
    private TableView tvTableItem;
    @FXML
    private TableColumn tcId, tcIssues, tcModel, tcDate, tcPack;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu mnGoBack, mnGoBack1, mnGoTo, mnHelp, mnDarkMode;
    @FXML
    private MenuItem miBooking, miReport, miPack, miModel, miUser;
    @FXML
    private Pane paneModel;
    @FXML
    private TextField tfIdItem;
    @FXML
    private TextArea taIssuesItem;
    @FXML
    private ComboBox cbModelItem, cbPackItem;
    @FXML
    private DatePicker dpCreateDateItem;
    @FXML
    private Label lblIdItem, lblModelItem, lblIssuesItem, lblCreateDateItem, lblPackItem;
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
        primaryStage.setTitle("Item Management");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
        if (KeyCode.ESCAPE == event.getCode()) {
            Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Storio?").showAndWait();
                if (action.get() == ButtonType.OK) 
                    primaryStage.close();
        }
    });

        tvTableItem.setOnMouseClicked(event -> this.handleOnMouseClick(event));
        refreshTable();
        primaryStage.show();
    }

    private void windowShowing(WindowEvent event) {
        tfIdItem.requestFocus();
        tfIdItem.setDisable(true);
        taIssuesItem.setDisable(true);
        cbModelItem.setDisable(true);
        cbPackItem.setDisable(true);
        dpCreateDateItem.setDisable(true);
        btnCreateItem.setDisable(false);
        btnModifyItem.setDisable(false);
        btnSearchItem.setDisable(false);
        btnDeleteItem.setDisable(false);
    }

    public void createItem(ActionEvent event) {
        LOGGER.info("Initializing creation.");
        if (!btnCreateItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to creation mode
            LOGGER.info("Create enabled state.");
            changeMode(1); // Change to creation mode
        } else { // Create and change to default mode
            LOGGER.info("Create disabled state.");
            if (!(taIssuesItem.getText().trim().equals("") || cbModelItem.getSelectionModel().getSelectedItem().toString().trim().equals("") || dpCreateDateItem.getValue() == null)) { // All fields have content
                LOGGER.info("Creating model.");
                List<Model> modelList = modelable.listAllModels().stream().filter(m -> m.getModel().equalsIgnoreCase(cbModelItem.getSelectionModel().getSelectedItem().toString())).collect(Collectors.toList());
                itemable.createItem(new Item(
                        0, 
                        (Model) cbModelItem.getSelectionModel().getSelectedItem(),
                        convertToDateViaInstant(dpCreateDateItem.getValue()),
                        taIssuesItem.getText(),
                        null,
                        null
                ));
            } else {// Some fields are empty
                new Alert(Alert.AlertType.ERROR, "Fill the all the fields before trying to create.", ButtonType.OK).showAndWait();
            }
            refreshTable(); // Change to creation mode
            changeMode(0); // Some fields are empty
        }
        LOGGER.info("Finishing creation.");
    }

    public void updateItem(ActionEvent event) {
        LOGGER.info("Initializing update.");
        if (!btnCreateItem.isDisabled() & !btnModifyItem.isDisabled()) {
            LOGGER.info("Update enabled state.");
            changeMode(2); // Change to update mode
        } else { // Update and change to default mode
            LOGGER.info("Update disabled state.");

            if (!tfIdItem.getText().trim().equals("")) { // There's a Item selected
                LOGGER.info("Updating Item.");
                itemable.updateItem(new Item(
                        Integer.parseInt(tfIdItem.getText()), 
                        (Model) cbModelItem.getSelectionModel().getSelectedItem(),
                        convertToDateViaInstant(dpCreateDateItem.getValue()),
                        taIssuesItem.getText(),
                        null,
                        null));
            } else { // There's not a Item selected
                new Alert(Alert.AlertType.ERROR, "Please select an item from the table before trying to update.", ButtonType.OK).showAndWait();
            }
            refreshTable(); // Refresh the table
            changeMode(0); // Change to default mode
        }
        LOGGER.info("Finishing update.");
    }

    public void findItem(ActionEvent event) {
        LOGGER.info("Initializing search.");
        if (!btnDeleteItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to search mode
            LOGGER.info("Search enabled state.");
            changeMode(3); // Change to search mode
        } else { // Find and change to default mode
            if (tfIdItem.getText().trim().isEmpty()) {  // Unfindable
                new Alert(Alert.AlertType.ERROR, "Fill the ID field before trying to search.", ButtonType.OK).showAndWait();
                refreshTable(); // Refresh table content
            } else { 
                LOGGER.log(Level.INFO, "Searching for Item {0}.", tfIdItem.getText());
                List<Item> listItem = itemable.findItemById(Integer.parseInt(tfIdItem.getText()));
                if (listItem.get(0) == null) { // Item wasn't found
                    new Alert(Alert.AlertType.ERROR, "Could not find the item.", ButtonType.OK).showAndWait();
                    refreshTable(); // Refresh table content
                } else { // Set the found item on the table
                    tvTableItem.setItems(FXCollections.observableArrayList(listItem));
                }
            }
            changeMode(0); // Change to default mode
        }
        LOGGER.info("Finishing search.");
    }

    public void deleteItem(ActionEvent event) {
        LOGGER.info("Initializing deletion.");
        if (!btnDeleteItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to deletion mode
            LOGGER.info("Delete enabled state.");
            changeMode(4);
        } else { // Delete and change to default mode
            LOGGER.log(Level.INFO, "Deleting Item {0}.", tfIdItem.getText());
            if (tfIdItem.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Fill the ID field before trying to Delete.", ButtonType.OK).showAndWait();
            } else {
                itemable.deleteItem(Integer.parseInt(tfIdItem.getText()));
            }
            changeMode(0);
            refreshTable();
        }
        LOGGER.info("Finishing deletion.");
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
                btnModifyItem.setDisable(true);
                btnSearchItem.setDisable(true);
                btnDeleteItem.setDisable(true);
                dpCreateDateItem.setDisable(false);
                taIssuesItem.setDisable(false);
                cbModelItem.setDisable(false);
                tfIdItem.setText("" + (modelable.countModel() + 1));
                LOGGER.log(Level.INFO, "Setting up Model for id {0}.", tfIdItem.getId());
                LOGGER.info("Refreshing table.");
                refreshTable();
                break;
            case 2: // Update mode
                LOGGER.info("Enabling and disabling fields.");
                btnCreateItem.setDisable(true);
                btnSearchItem.setDisable(true);
                btnDeleteItem.setDisable(true);
                taIssuesItem.setDisable(false);
                cbModelItem.setDisable(false);
                cbPackItem.setDisable(false);
                dpCreateDateItem.setDisable(false);
                LOGGER.info("Refreshing table.");
                refreshTable();
                break;
            case 3: // Search mode
                LOGGER.info("Enabling and disabling fields.");
                btnModifyItem.setDisable(true);
                btnDeleteItem.setDisable(true);
                btnCreateItem.setDisable(true);
                tfIdItem.setDisable(false);
                refreshTable();
                break;
            case 4: // Deletion mode
                LOGGER.info("Enabling and disabling fields.");
                btnModifyItem.setDisable(true);
                btnSearchItem.setDisable(true);
                btnCreateItem.setDisable(true);
                tfIdItem.setDisable(false);
                LOGGER.info("Refreshing table.");
                refreshTable();
                break;
        }
    }

    private void handleOnMouseClick(MouseEvent event) {
        TableView tv = (TableView) event.getSource();
        if (tv.getSelectionModel().getSelectedItem() != null) { // Checks if the table view is selected
            ObservableList selectedItems = tv.getSelectionModel().getSelectedItems();
            Item item = (Item) selectedItems.get(0);

            LOGGER.log(Level.INFO, "Selecting table row: {0}", item.getId());
            if (!(btnModifyItem.isDisabled() && btnSearchItem.isDisabled() && btnDeleteItem.isDisabled() && !btnCreateItem.isDisabled())) // If the creation mode is enabled, 
            {
                tfIdItem.setText(String.valueOf(item.getId()));
            }
            taIssuesItem.setText(item.getIssues());
            cbModelItem.getSelectionModel().select(item.getModel());
            dpCreateDateItem.setValue(convertToLocalDateViaInstant(item.getDateAdded()));
        }
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaInstant(LocalDate localDateToConvert) {
        return Date.from(localDateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
