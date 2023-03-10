package windowController;

import entities.Item;
import entities.Model;
import entities.Pack;
import factories.ItemFactory;
import factories.ModelFactory;
import factories.PackFactory;
import interfaces.Itemable;
import interfaces.Modelable;
import interfaces.Packable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * This window adds the functionality of Managing the entity Item and navigation
 * through various windows. It contains any elements and methods with code
 * initialization on the file ItemManagementWindow.fxml
 *
 * @author Nicol??s Rodr??guez
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
    private Menu mnGoBack, mnGoTo, mnHelp, mnDarkMode;
    @FXML
    private MenuItem miBooking, miPack, miModel, miUser, miGoBack, miHelp, miDarkMode, miUsersManual;
    @FXML
    private Pane paneItem;
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
    private final Itemable itemable = ItemFactory.getAccessItem();
    private final Modelable modelable = ModelFactory.getAccessModel();
    private final Packable packable = PackFactory.getAccessPack();

    /**
     * Initializes the stage for the window for ItemManagementWindow.
     *
     * @param root The Parent object representing the root nodes.
     */
    public void setStage(Parent root) {
        LOGGER.info("Initializing ItemManagementWindow.");

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("windowController/images/logo.png"));
        LOGGER.info("Setting scene.");
        primaryStage.setScene(scene); // Set the stage's propperties
        primaryStage.setTitle("Item Management");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        LOGGER.info("Adding event handlers.");
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> { // Adds an event handler that records every time the escape key is pressed
            if (KeyCode.ESCAPE == event.getCode()) {
                Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Storio?").showAndWait();
                if (action.get() == ButtonType.OK) {
                    primaryStage.close();
                }
            }
        });

        tvTableItem.setOnMouseClicked(event -> this.handleOnMouseClick(event)); // Adds an event handler that records every time the TableView element is clicked
        miPack.setOnAction(event -> this.handleOnMouseClickNavPack(event)); // Adds an event handler that records every time the miPack element is clicked
        miUser.setOnAction(event -> this.handleOnMouseClickNavUser(event));// Adds an event handler that records every time the miUser element is clicked
        miBooking.setOnAction(event -> this.handleOnMouseClickNavBooking(event));// Adds an event handler that records every time the miBooking element is clicked
        miModel.setOnAction(event -> this.handleOnMouseClickNavModel(event));// Adds an event handler that records every time the miModel element is clicked
        miGoBack.setOnAction(event -> this.handleOnMouseClickLogOut(event));// Adds an event handler that records every time the miGoBack element is clicked
        miHelp.setOnAction(event -> this.handleOnMouseClickHelp(event));// Adds an event handler that records every time the miHelp element is clicked
        miUsersManual.setOnAction(event -> this.handleOnMouseClickUserMan(event));// Adds an event handler that records every time the miUsersManual element is clicked
        miDarkMode.setOnAction(event -> this.handleOnMouseClickDarkMode(event));// Adds an event handler that records every time the miDarkMode element is clicked
        refreshTable(); // Refresh the table's data
        LOGGER.info("Showing window.");
        primaryStage.show(); // Show the window
    }

                // TODO Javadoc
    private void windowShowing(WindowEvent event) {
        tfIdItem.requestFocus();
        disableTextFields(); // Disables all fields
        enableButtons(); // Enables all buttons
    }

    /**
     * This method listens for an event to click the Button btnCreateItem, it
     * makes it switch between two modes:
     * <ul>
     * <li><b>Create enabled: </b>This mode enables all fields with the
     * exception of tfIdItem and prepares to create an Item.</li>
     * <li><b>Create disabled: </b>This mode disables all fields and creates an
     * Item if possible. The tables is always refreshed after that</li>
     * </ul>
     *
     * @param event The window event
     */
    @FXML
    private void createItem(ActionEvent event) {
        LOGGER.info("Initializing creation.");
        if (!btnCreateItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to creation mode
            LOGGER.info("Create enabled state.");
            changeMode(1); // Change to creation mode
        } else { // Create and change to default mode
            LOGGER.info("Create disabled state.");
            if (!(taIssuesItem.getText().trim().equals("") || cbModelItem.getSelectionModel().getSelectedItem().toString().trim().equals("") || dpCreateDateItem.getValue() == null)) { // All fields have content
                LOGGER.info("Creating item.");
                itemable.createItem(new Item( // We create a new item with the data of the fields for it to be inserted
                        0,
                        (Model) cbModelItem.getSelectionModel().getSelectedItem(),
                        convertToDateViaInstant(dpCreateDateItem.getValue()),
                        taIssuesItem.getText(),
                        null,
                        (Pack) cbPackItem.getSelectionModel().getSelectedItem()
                ));
            } else {// Some fields are empty
                new Alert(Alert.AlertType.ERROR, "Fill the all the fields before trying to create.", ButtonType.OK).showAndWait();
            }
            refreshTable(); // Change to creation mode
            changeMode(0); // Some fields are empty
        }
        LOGGER.info("Finishing creation.");
    }

    /**
     * This method listens for an event to click the Button btnModifyItem, it
     * makes it switch between two modes:
     * <ul>
     * <li><b>Modify enabled: </b>This mode enables all fields with the
     * exception of tfIdItem and prepares to update an Item. An item must be
     * selected from the TableView first.</li>
     * <li><b>Modify disabled: </b>This mode disables all fields and updates an
     * Item if possible. The tables is always refreshed after that.</li>
     * </ul>
     *
     * @param event The window event
     */
    @FXML
    private void updateItem(ActionEvent event) {
        LOGGER.info("Initializing update.");
        if (!btnCreateItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to update mode
            LOGGER.info("Update enabled state.");
            changeMode(2); // Change to update mode
        } else { // Update and change to default mode
            LOGGER.info("Update disabled state.");

            if (!tfIdItem.getText().trim().equals("") && !(dpCreateDateItem.getValue() == null)) { // There's a Item selected
                LOGGER.info("Updating Item.");
                itemable.updateItem(new Item(
                        Integer.parseInt(tfIdItem.getText()),
                        (Model) cbModelItem.getSelectionModel().getSelectedItem(),
                        convertToDateViaInstant(dpCreateDateItem.getValue()),
                        taIssuesItem.getText(),
                        null,
                        (Pack) cbPackItem.getSelectionModel().getSelectedItem()
                ));
            } else { // There's not an Item selected
                new Alert(Alert.AlertType.ERROR, "Please select an item from the table before trying to update.", ButtonType.OK).showAndWait();
            }
            refreshTable(); // Refresh the table
            changeMode(0); // Change to default mode
        }
        LOGGER.info("Finishing update.");
    }

    /**
     * This method listens for an event to click the Button btnSearchItem, it
     * makes it switch between two modes:
     * <ul>
     * <li><b>Search enabled: </b>This mode enables all fields with the
     * exception of tfIdItem and cbPackItem and prepares to search for an Item.
     * Either tfIdItem or cbPackItem must be filled, but not both.</li>
     * <li><b>Search disabled: </b>This mode disables all fields and searches an
     * Item if possible. The tables is always refreshed after that if the
     * validation fails.</li>
     * </ul>
     *
     * @param event The window event
     */
    @FXML
    public void findItem(ActionEvent event) {
        LOGGER.info("Initializing search.");
        if (!btnDeleteItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to search mode
            LOGGER.info("Search enabled state.");
            changeMode(3); // Change to search mode
        } else { // Find and change to default mode
            LOGGER.info(cbModelItem.getSelectionModel().getSelectedIndex() + "");

            if (!tfIdItem.getText().trim().isEmpty()) { // Find by id
                LOGGER.log(Level.INFO, "Searching for Item {0}.", tfIdItem.getText());
                List<Item> listItem = itemable.findItemById(Integer.parseInt(tfIdItem.getText()));
                if (listItem.get(0) == null) { // Item wasn't found
                    new Alert(Alert.AlertType.ERROR, "Could not find the item.", ButtonType.OK).showAndWait();
                    refreshTable(); // Refresh table content
                } else { // Set the found item on the table
                    tvTableItem.setItems(FXCollections.observableArrayList(listItem));
                }

            } else if (!(cbModelItem.getSelectionModel().getSelectedIndex() == -1)) { // Find by Model
                LOGGER.log(Level.INFO, "Searching for Item model {0}.", ((Model) cbModelItem.getSelectionModel().getSelectedItem()).getModel());
                List<Item> listItem = itemable.findItemByModel(((Model) cbModelItem.getSelectionModel().getSelectedItem()).getId() + "");
                if (listItem.get(0) == null) { // Item wasn't found
                    new Alert(Alert.AlertType.ERROR, "Could not find the item.", ButtonType.OK).showAndWait();
                    refreshTable(); // Refresh table content
                } else { // Set the found item on the table
                    tvTableItem.setItems(FXCollections.observableArrayList(listItem));
                }

            } else if (!(cbPackItem.getSelectionModel().getSelectedIndex() == -1)) { // Find by Pack
                LOGGER.log(Level.INFO, "Searching for Item model {0}.", ((Pack) cbPackItem.getSelectionModel().getSelectedItem()).getId());
                List<Item> listItem = itemable.findItemByPack(((Pack) cbPackItem.getSelectionModel().getSelectedItem()).getId() + "");
                if (listItem.get(0) == null) { // Item wasn't found
                    new Alert(Alert.AlertType.ERROR, "Could not find the item.", ButtonType.OK).showAndWait();
                    refreshTable(); // Refresh table content
                } else { // Set the found item on the table
                    tvTableItem.setItems(FXCollections.observableArrayList(listItem));
                }

            } else { // Unfindable
                new Alert(Alert.AlertType.ERROR, "Fill the ID, Model or Pack field before trying to search.", ButtonType.OK).showAndWait();
                refreshTable(); // Refresh table content               
            }
            changeMode(0); // Change to default mode
        }
        LOGGER.info("Finishing search.");
    }

    /**
     * This method listens for an event to click the Button btnDeleteItem, it
     * makes it switch between two modes:
     * <ul>
     * <li><b>Delete enabled: </b>This mode enables tfIdItem and prepares to
     * delete an Item. An item must be selected from the TableView first or its
     * respective id typed.</li>
     * <li><b>Delete disabled: </b>This mode disables all fields and deletes an
     * Item if possible. The tables is always refreshed after that.</li>
     * </ul>
     *
     * @param event The window event
     */
    @FXML
    public void deleteItem(ActionEvent event
    ) {
        LOGGER.info("Initializing deletion.");
        if (!btnDeleteItem.isDisabled() & !btnModifyItem.isDisabled()) { // Change to deletion mode
            LOGGER.info("Delete enabled state.");
            changeMode(4);
        } else { // Delete and change to default mode
            LOGGER.log(Level.INFO, "Deleting Item {0}.", tfIdItem.getText());
            if (tfIdItem.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Fill the ID field before trying to Delete.", ButtonType.OK).showAndWait();
            } else {// Asks for confirmation before deleting
                Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the item " + tfIdItem.getText() + "?").showAndWait();
                if (action.get() == ButtonType.OK) {// Deletes
                    itemable.deleteItem(Integer.parseInt(tfIdItem.getText()));
                }
            }
            changeMode(0);
            refreshTable();
        }
        LOGGER.info("Finishing deletion.");
    }

    /**
     * This method retrieves the information from the interface for it to be
     * displayed on the TableView and the ComboBoxes. It always shows all the
     * Items recieved. For more specific searches <code>findItem</code>.
     *
     * @see ItemManagementWindowController#findItem(javafx.event.ActionEvent)
     */
    private void refreshTable() {
        LOGGER.info("Retrieving model data.");
        try {
            List<Item> listItem = itemable.listAllItems(); // Looks for all the items
            // Sets the CellValueFactory for all TableColumns
            tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));

            tcDate.setCellValueFactory(
                    new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> item) {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.setValue(formatDate((item.getValue()).getDateAdded()).toString());
                    return property;
                }
            });

            tcIssues.setCellValueFactory(new PropertyValueFactory<>("issues"));
            tcPack.setCellValueFactory(new PropertyValueFactory<>("pack"));

            tvTableItem.setItems(FXCollections.observableArrayList(listItem)); // Sets the Items on the TableView

            List<Model> listModel = modelable.listAllModels();
            cbModelItem.setItems(FXCollections.observableArrayList(listModel)); // Sets all Models on the ComboBox
            List<Pack> listPack = packable.getAllPacks();
            cbPackItem.setItems(FXCollections.observableArrayList(listPack)); // Sets all Packs on the ComboBox
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "The database was inaccessible. Closing Storio.").showAndWait();
            primaryStage.close();
        }
    }

    /**
     * This methods empties all fields. A regular idea on the window
     * functionality.
     */
    private void emptyFields() {
        LOGGER.info("Emptying fields.");
        tfIdItem.setText("");
        taIssuesItem.setText("");
        dpCreateDateItem.setValue(null);
        cbPackItem.getSelectionModel().select(-1);
        cbModelItem.getSelectionModel().select(-1);
    }

    /**
     * This methods enables all buttons. A regular idea on the window
     * functionality.
     */
    private void enableButtons() {
        LOGGER.info("Enabling buttons.");
        btnCreateItem.setDisable(false);
        btnModifyItem.setDisable(false);
        btnSearchItem.setDisable(false);
        btnDeleteItem.setDisable(false);
    }

    /**
     * This methods diables all fields. A regular idea on the window
     * functionality.
     */
    private void disableTextFields() {
        LOGGER.info("Disabling text fields.");
        tfIdItem.setDisable(true);
        taIssuesItem.setDisable(true);
        dpCreateDateItem.setDisable(true);
        cbModelItem.setDisable(true);
        cbPackItem.setDisable(true);
    }

    /**
     * Sets the stage for the ItemManagementsWIndow.
     *
     * @param stage
     */
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
                cbPackItem.setDisable(false);
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
                cbPackItem.setDisable(false);
                cbModelItem.setDisable(false);
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

    /**
     * This method converts the an object Date to LocalDate.
     *
     * @param dateToConvert The date in java.util.Date form
     * @return The date in java.time.LocalDate form
     */
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * This method converts the an object Date to LocalDate.
     *
     * @param localDateToConvert The date in java.time.LocalDate form
     * @return The date in java.util.Date form
     */
    public Date convertToDateViaInstant(LocalDate localDateToConvert) {
        return Date.from(localDateToConvert
                .atStartOfDay(ZoneId
                        .systemDefault())
                .toInstant());
    }

    /**
     * This method formats a {@link java.util.Date} object to a
     * {@link java.time.LocalDate} with the default format of the system.
     *
     * @param dateToFormat The Date to be formatted
     * @return A LocalDate containing the date with the desired format.
     */
    public LocalDate formatDate(Date dateToFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        convertToLocalDateViaInstant(dateToFormat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //return sdf.format(dateToFormat);
        LocalDate localDate = LocalDate.parse(sdf.format(dateToFormat), formatter);
        LOGGER.info(localDate.toString());
        return localDate;
    }

    /**
     * This method formats a {@link java.lang.String} object to a
     * {@link java.time.LocalDate} with the default format of the system.
     *
     * @param dateString The String to be formatted
     * @return A LocalDate containing the date with the desired format.
     */
    public LocalDate formatDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //return sdf.format(dateToFormat);
        LocalDate localDate = LocalDate.parse(sdf.format(dateString), formatter);
        LOGGER.info(localDate.toString());
        return localDate;
    }

    /**
     * This method listens for an event onClick on the TableView for it to
     * retrieve the information from the selected row and set it on the
     * respective fields.
     *
     * @param event The window event
     */
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
            cbPackItem.getSelectionModel().select(item.getPack());
            dpCreateDateItem.setValue(formatDate(item.getDateAdded()));
            formatDate(item.getDateAdded());
        }
    }
    
    /**
     * This method opens the Report template for Item and fills it with the
     * information in the table.
     *
     * @param event The window event
     */
    public void handleOnMouseClickHelp(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/ItemReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Item>) this.tvTableItem.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
//            JasperReport report
//                    = JasperCompileManager.compileReport(getClass().getResource("ItemReport.jrxml").getPath());
//            JRBeanCollectionDataSource dataItems
//                    = new JRBeanCollectionDataSource((Collection<Item>) this.tvTableItem.getItems());
//            Map<String, Object> parameters = new HashMap<>();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
//            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method changes from the bright to dark mode and viceversa. Meaning
     * most of the elements of the window change colours.
     *
     * @param event The window event
     */
    private void handleOnMouseClickDarkMode(ActionEvent event) {
        if (lblCreateDateItem.getTextFill().equals(Color.WHITE)) {
            // Los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiar???n de WHITE a #DDDDDD
            tfIdItem.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            taIssuesItem.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpCreateDateItem.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            cbModelItem.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            cbPackItem.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            // y la letra pasar??? de WHITE a BLACK.
            tfIdItem.setStyle("-fx-text-inner-color:BLACK");
            taIssuesItem.setStyle("-fx-text-inner-color:BLACK");
            dpCreateDateItem.setStyle("-fx-text-inner-color:BLACK");
            cbModelItem.setStyle("-fx-text-inner-color:BLACK");
            cbPackItem.setStyle("-fx-text-inner-color:BLACK");

            // Las label lblUsername, lblEmail, lblFullName, lblPassword, 
            //lblRepeatPassword cambiaran de color de la letra de WHITE a BLACK.
            lblCreateDateItem.setTextFill(Color.BLACK);
            lblIdItem.setTextFill(Color.BLACK);
            lblIssuesItem.setTextFill(Color.BLACK);
            lblModelItem.setTextFill(Color.BLACK);
            lblPackItem.setTextFill(Color.BLACK);
            // El fondo cambia el color de #333333, a WHITE.
            paneItem.setStyle("-fx-background-color:WHITE");
        } // En caso de que la imagen sea sol_light_mode, se cambiar??? por el sol_dark_mode.
        else {
            // En caso contrario, los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiar???n de #DDDDDD a WHITE
            tfIdItem.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            taIssuesItem.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            dpCreateDateItem.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            cbModelItem.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            cbPackItem.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            // la letra pasar??? de BLACK a WHITE
            tfIdItem.setStyle("-fx-text-inner-color:WHITE");
            taIssuesItem.setStyle("-fx-text-inner-color:WHITE");
            dpCreateDateItem.setStyle("-fx-text-inner-color:WHITE");
            cbModelItem.setStyle("-fx-text-inner-color:WHITE");
            cbPackItem.setStyle("-fx-text-inner-color:WHITE");
            // En caso contrario, las label lblUsername, lblEmail, lblFullName, 
            // lblPassword, lblRepeatPassword cambiaran de color de la letra de BLACK a WHITE.
            lblCreateDateItem.setTextFill(Color.WHITE);
            lblIdItem.setTextFill(Color.WHITE);
            lblIssuesItem.setTextFill(Color.WHITE);
            lblModelItem.setTextFill(Color.WHITE);
            lblPackItem.setTextFill(Color.WHITE);
            // En caso contrario, pasar??? a #333333
            paneItem.setStyle("-fx-background-color:#333333");
        }
    }

    /**
     * This method opens the Report template for Item and fills it with the
     * information in the table.
     *
     * @param event The window event
     */
    private void handleOnMouseClickLogOut(ActionEvent event) {
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

    /**
     * This method closes the current window and opens ModelManagementWindow.
     *
     * @param event The window event
     */
    private void handleOnMouseClickNavModel(ActionEvent event) {
        primaryStage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ModelManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelManagementWindowController controller = (ModelManagementWindowController) loader.getController();
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
//        primaryStage.close();
//        Stage stage = new Stage();
//        // Carga el document FXML y obtiene un objeto Parent
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserManagementWindow.fxml"));
//        // Crea una escena a partir del Parent
//        Parent root = null;
//        try {
//            root = (Parent) loader.load();
//        } catch (IOException ex) {
//            Logger.getLogger(ItemManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        UserManagementWindowController controller = (UserManagementWindowController) loader.getController();
//        // Establece la escena en el escensario (Stage) y la muestra
//        controller.setStage(stage);
//        controller.initStage(root);
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

    /**
     * This method opens the ItemHelpWindow and shows the user manual for the
     * ItemWindow.
     *
     * @param event The window event
     */
    private void handleOnMouseClickUserMan(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/HelpItem.fxml"));
            Parent root = (Parent) loader.load();
            ItemHelpWindowController helpController
                    = ((ItemHelpWindowController) loader.getController());
            //Initializes and shows help stage
            helpController.initAndShowStage(root);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error loading help window: {0}",
                    ex.getMessage());
            ex.printStackTrace();
        }
    }
}
