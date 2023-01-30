/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import entities.Booking;
import entities.BookingState;
import entities.Client;
import entities.Item;
import entities.Pack;
import entities.PackState;
import entities.PackType;
import entities.User;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import factories.BookingFactory;
import interfaces.Bookingable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import factories.PackFactory;
import interfaces.Packable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 *
 * @author 2dam
 */
public class BookingManagementWindowController {

    private Stage primaryStage;
    protected static final Logger LOGGER = Logger.getLogger(BookingManagementWindowController.class.getName());

    private BookingFactory bookingFactory = new BookingFactory();
    private Bookingable bookingable = bookingFactory.getAccessBooking();
    private Pane paneWindow;
    @FXML
    private Pane paneForm;
    @FXML
    private TextField tfId;
    @FXML
    private Label lblId, lblPacks, lblState, lblEndDate, lblStartDate, lblDescription;
    @FXML
    private ComboBox cbState;
    @FXML
    private TextArea taDescription;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private Button btnCreate, btnSearch, btnDelete, btnModify;
    @FXML
    private Menu mnDarkMode, mnHelp, mnGoTo, mnGoBack;
    @FXML
    private MenuItem miReport, miPack, miModel, miItem, miUser, miUsersManual, miPrintReport, miChangeMode;
    @FXML
    private ListView lvPacks;
    @FXML
    private TableColumn tcId, tcDescription, tcStartDate, tcEndDate, tcState, tcPacks;
    @FXML
    private TableView tvBooking;
    private ObservableList<Booking> bookingsData;
    private List<Pack> packs = null;
    private Packable packManager = PackFactory.getAccessPack();
    private List<Item> items = null;
    private Pack pack = new Pack(1, "siu", Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), items, PackState.AVAILABLE, PackType.TRIPOD, bookingsData);
    private Client user = new Client();
    @FXML
    private MenuBar menuBar;

    public void initStage(Parent root, User user) {
        try {
            LOGGER.info("Initializing BookingController stage.");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Booking Management");
            primaryStage.setResizable(false);
            taDescription.setDisable(true);
            tfId.setDisable(true);
            dpEndDate.setDisable(true);
            dpStartDate.setDisable(true);
            cbState.setDisable(true);
            lvPacks.setDisable(true);
            lblDescription.setVisible(true);
            lblEndDate.setVisible(true);
            lblId.setVisible(true);
            lblPacks.setVisible(true);
            //packs.add(0, pack);
            //lvPacks.setItems(FXCollections.observableList(packs));
            //packs = FXCollections.observableArrayList(packManager.getAllPacks());
            //lvPacks.setItems( packs);
            lblStartDate.setVisible(true);
            lblState.setVisible(true);
            cbState.getItems().addAll(BookingState.values());
            miChangeMode.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    darkMode();
                }
            });
            //if(user.getPrivilege().equals("USER") || user.getPrivilege().equals("MANAGER")){
            miItem.setDisable(true);
            miModel.setDisable(true);
            miPack.setDisable(true);
            miReport.setDisable(true);
            tvBooking.setOnMouseClicked(event -> this.handleOnMouseClick(event));

            refreshTable();
            primaryStage.show();
            //}else{
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * This method validates all the fields in the window and if they're okay,
     * creates a new Booking. propperly.
     *
     * @param event The observed event
     */
    private void handleCreateButtonAction(ActionEvent event) {
        try {
            refreshTable();
            if (btnDelete.isDisabled() && btnModify.isDisabled() && btnSearch.isDisabled()) {
                dpEndDate.setDisable(true);
                dpStartDate.setDisable(true);
                cbState.setDisable(true);
                lvPacks.setDisable(true);
                taDescription.setDisable(true);
                btnDelete.setDisable(false);
                btnModify.setDisable(false);
                btnSearch.setDisable(false);
                if (!taDescription.toString().trim().isEmpty() && dpEndDate.getValue() != null && dpStartDate.getValue() != null && dpEndDate.getValue().isAfter(dpStartDate.getValue())) {
                    bookingable.createBooking_XML(new Booking(user, packs, Date.from(dpStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(dpEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), taDescription.getText(), BookingState.PENDING));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correctly created", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, please check that are the fields are filled and that the start date is before the end date.", ButtonType.OK);
                    alert.showAndWait();
                }
                taDescription.setText("");
                dpEndDate.setValue(null);
                dpStartDate.setValue(null);
                refreshTable();
            } else {
                btnDelete.setDisable(true);
                btnModify.setDisable(true);
                btnSearch.setDisable(true);
                taDescription.setDisable(false);
                dpEndDate.setDisable(false);
                dpStartDate.setDisable(false);
                lvPacks.setDisable(false);
                cbState.setDisable(true);

            }
        } catch (Exception e) {

        }
    }

    @FXML
    /**
     * This method validates all the fields in the window and if they're okay,
     * creates a new Booking. propperly.
     *
     * @param event The observed event
     */
    private void handleModifyButtonAction(ActionEvent event) {
        try {
            refreshTable();
            if (btnDelete.isDisabled() && btnCreate.isDisabled() && btnSearch.isDisabled()) {
                Booking booking = (Booking) tvBooking.getSelectionModel().getSelectedItem();
                booking.setDescription(taDescription.getText().trim());
                booking.setEndDate(Date.from(dpEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                booking.setStartDate(Date.from(dpStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                booking.setPacks(lvPacks.getItems());
                booking.setState((BookingState) cbState.getSelectionModel().getSelectedItem());

                btnSearch.setDisable(false);
                if (booking.getEndDate().after(booking.getStartDate())) {
                    bookingable.updateBooking_XML(booking);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correctly modified", ButtonType.OK);
                    alert.showAndWait();
                }

                taDescription.setText("");
                dpEndDate.setValue(null);
                dpStartDate.setValue(null);
                lvPacks.setItems(null);
                cbState.setItems(null);
                refreshTable();
            } else {
                btnDelete.setDisable(true);
                btnCreate.setDisable(true);
                btnSearch.setDisable(true);
                taDescription.setDisable(false);
                dpEndDate.setDisable(false);
                dpStartDate.setDisable(false);
                lvPacks.setDisable(false);
                cbState.setDisable(false);

            }
        } catch (Exception e) {

        }
    }

    @FXML
    /**
     * This method validates all the fields in the window and if they're okay,
     * creates a new Booking. propperly.
     *
     * @param event The observed event
     */
    private void handleRemoveButtonAction(ActionEvent event) {
        try {
            refreshTable();
            if (btnModify.isDisabled() && btnCreate.isDisabled() && btnSearch.isDisabled()) {
                dpEndDate.setDisable(true);
                dpStartDate.setDisable(true);
                cbState.setDisable(true);
                lvPacks.setDisable(false);
                tfId.setDisable(true);
                taDescription.setDisable(true);
                btnCreate.setDisable(false);
                btnModify.setDisable(false);
                btnSearch.setDisable(false);
                if (!tfId.toString().trim().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected row?\n" + "This operation cannot be undone.", ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> result = alert.showAndWait();
                    //If OK to deletion
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        bookingable.removeBooking(tfId.getText());
                        Alert alertConfirm = new Alert(Alert.AlertType.INFORMATION, "Correctly deleted", ButtonType.OK);
                        alertConfirm.showAndWait();
                    }
                    tfId.setText("");
                    taDescription.setText("");
                    dpEndDate.setValue(null);
                    dpStartDate.setValue(null);
                    lvPacks.setItems(null);
                    refreshTable();
                }
            } else {
                btnCreate.setDisable(true);
                btnModify.setDisable(true);
                btnSearch.setDisable(true);
                taDescription.setDisable(true);
                dpEndDate.setDisable(true);
                dpStartDate.setDisable(true);
                lvPacks.setDisable(true);
                cbState.setDisable(true);
                tfId.setDisable(false);

            }

        } catch (Exception e) {

        }
    }

    @FXML
    /**
     * This method validates all the fields in the window and if they're okay,
     * creates a new Booking. propperly.
     *
     * @param event The observed event
     */
    private void handleSearchButtonAction(ActionEvent event) {
        try {
            refreshTable();
            if (btnModify.isDisabled() && btnCreate.isDisabled() && btnDelete.isDisabled()) {
                List<Booking> bookings = new ArrayList<>();
                Booking booking = new Booking();
                String id = tfId.getText();
                tfId.setDisable(true);
                taDescription.setDisable(true);
                btnDelete.setDisable(false);
                btnModify.setDisable(false);
                btnCreate.setDisable(false);
                booking = bookingable.find_XML(Booking.class, id);
                bookings.add(booking);
                tvBooking.setItems(FXCollections.observableArrayList(bookings));
                tfId.setText("");
                taDescription.setText("");
                dpEndDate.setValue(null);
                dpStartDate.setValue(null);
                lvPacks.setItems(null);
            } else {
                btnDelete.setDisable(true);
                btnModify.setDisable(true);
                btnCreate.setDisable(true);
                taDescription.setDisable(true);
                dpEndDate.setDisable(true);
                dpStartDate.setDisable(true);
                lvPacks.setDisable(true);
                cbState.setDisable(true);
                tfId.setDisable(false);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void closeWindow(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    public void refreshTable() {
        this.tcId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        this.tcDescription.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        this.tcStartDate.setCellValueFactory(
                new PropertyValueFactory<>("startDate")
        );
        this.tcStartDate.setCellFactory(column -> {
            TableCell<Booking, Date> cell = new TableCell<Booking, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        this.tcEndDate.setCellValueFactory(
                new PropertyValueFactory<>("endDate")
        );
        this.tcEndDate.setCellFactory(column -> {
            TableCell<Booking, Date> cell = new TableCell<Booking, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        this.tcState.setCellValueFactory(
                new PropertyValueFactory<>("state")
        );
        this.tcPacks.setCellValueFactory(
                new PropertyValueFactory<>("packs")
        );
        List<Booking> bookings = bookingable.findAll_XML(new GenericType<List<Booking>>() {
        });

        tvBooking.setItems(FXCollections.observableArrayList(bookings));
    }

    /**
     * This method changes atributes of the window in order to change the visual
     * theme.
     */
    private void darkMode() {

        if (lblDescription.getTextFill().equals(Color.WHITE)) {
            // Los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiar�n de #3A3A3A a #DDDDDD
            tfId.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            taDescription.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpEndDate.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpStartDate.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            lvPacks.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            // y la letra pasar� de WHITE a BLACK.
            tfId.setStyle("-fx-text-inner-color:BLACK");
            taDescription.setStyle("-fx-text-inner-color:BLACK");
            dpEndDate.setStyle("-fx-text-inner-color:BLACK");
            dpStartDate.setStyle("-fx-text-inner-color:BLACK");
            lvPacks.setStyle("-fx-text-inner-color:BLACK");

            // Las label lblUsername, lblEmail, lblFullName, lblPassword, 
            //lblRepeatPassword cambiaran de color de la letra de WHITE a BLACK.
            lblEndDate.setTextFill(Color.BLACK);
            lblDescription.setTextFill(Color.BLACK);
            lblId.setTextFill(Color.BLACK);
            lblPacks.setTextFill(Color.BLACK);
            lblStartDate.setTextFill(Color.BLACK);
            lblState.setTextFill(Color.BLACK);
            // El fondo cambia el color de #333333, a WHITE.
            paneWindow.setStyle("-fx-background-color:WHITE");
            paneForm.setStyle("-fx-background-color:WHITE");
        } // En caso de que la imagen sea sol_light_mode, se cambiar� por el sol_dark_mode.
        else {
            // En caso contrario, los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiar�n de #DDDDDD a #3A3A3A
            tfId.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            taDescription.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpEndDate.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpStartDate.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            lvPacks.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            // la letra pasar� de BLACK a WHITE
            tfId.setStyle("-fx-text-inner-color:WHITE");
            taDescription.setStyle("-fx-text-inner-color:WHITE");
            dpStartDate.setStyle("-fx-text-inner-color:WHITE");
            dpEndDate.setStyle("-fx-text-inner-color:WHITE");
            lvPacks.setStyle("-fx-text-inner-color:WHITE");
            // En caso contrario, las label lblUsername, lblEmail, lblFullName, 
            // lblPassword, lblRepeatPassword cambiaran de color de la letra de BLACK a WHITE.
            lblDescription.setTextFill(Color.WHITE);
            lblEndDate.setTextFill(Color.WHITE);
            lblId.setTextFill(Color.WHITE);
            lblPacks.setTextFill(Color.WHITE);
            lblStartDate.setTextFill(Color.WHITE);
            lblState.setTextFill(Color.WHITE);
            // En caso contrario, pasar� a #333333
            paneForm.setStyle("-fx-background-color:#333333");
            paneWindow.setStyle("-fx-background-color:#333333");

        }
    }

    private void handleOnMouseClick(MouseEvent event) {
        TableView tv = (TableView) event.getSource();
        if (tv.getSelectionModel().getSelectedItem() != null) { // Checks if the table view is selected
            ObservableList selectedItems = tv.getSelectionModel().getSelectedItems();
            Booking booking = (Booking) selectedItems.get(0);

            LOGGER.log(Level.INFO, "Selecting table row: {0}", booking.getId());
            tfId.setText(String.valueOf(booking.getId()));
            taDescription.setText(booking.getDescription());
            dpEndDate.setValue(booking.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            dpStartDate.setValue(booking.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            cbState.setValue(booking.getState());
            //List<Pack> packs = booking.getPacks();
            //lvPacks.setItems(FXCollections.observableArrayList(packs));

        }
    }

}
