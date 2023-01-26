/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import entities.Booking;
import entities.BookingState;
import entities.Client;
import entities.Pack;
import entities.PackState;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.apache.velocity.runtime.directive.Foreach;

/**
 *
 * @author 2dam
 */
public class BookingManagementWindowController {

    private Stage primaryStage;
    protected static final Logger LOGGER = Logger.getLogger(BookingManagementWindowController.class.getName());

    private BookingFactory bookingFactory = new BookingFactory();
    private Bookingable bookingable = bookingFactory.getAccessBooking();
    @FXML
    private Pane paneWindow, paneForm;
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
    private MenuBar menubar;
    @FXML
    private Menu mnDarkMode, mnHelp, mnGoTo, mnGoBack;
    @FXML
    private MenuItem miReport, miPack, miModel, miItem, miUser, miUsersManual, miPrintReport;
    @FXML
    private ListView lvPacks;
    @FXML
    private TableColumn tcId, tcDescription, tcStartDate, tcEndDate, tcState, tcPacks;
    @FXML
    private TableView tvBooking;
    private ObservableList<Booking> bookingsData;
    private ObservableList<ArrayList> packs = FXCollections.observableArrayList();
    private Client user;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    public void initStage(Parent root, User user) {
        try {
            LOGGER.info("Initializing BookingController stage.");
            this.user = (Client) user;
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
            lblStartDate.setVisible(true);
            lblState.setVisible(true);
            cbState.getItems().addAll(BookingState.values());

            lvPacks.setItems(packs);

            //if(user.getPrivilege().equals("USER") || user.getPrivilege().equals("MANAGER")){
            miItem.setDisable(true);
            miModel.setDisable(true);
            miPack.setDisable(true);
            miReport.setDisable(true);
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
     * This method validates the TextfFields and executes signUp() if ir works
     * propperly.
     *
     * @param event The observed event
     */
    private void handleCreateButtonAction(ActionEvent event) {
        /*Al pulsar este botón se alternará entre dos estados:
Estado Activado: Los Button btnSearch, btnModify y btnDelete se deshabilitarán. Se habilitarán los DatePickers(dpStartDate y dpEndDate), el ComboBox(cbState), la ListView(lvPacks) y el TextArea(taDescription).
Estado Desactivado: Los Button btnSearch, btnModify y btnDelete se habilitarán. Se deshabilitarán los DatePickers(dpStartDate y dpEndDate), elComboBox(cbState),la ListView(lvPacks) y el TextArea(taDescription).
Cuando pase de estado activado a estado desactivado se recogerán los datos introducidos y se llamará al método createBooking().
Si se está en estado activado, todos los campos del formulario no están rellenos y se clicka btnCreate se pasa a modo deshabilitado sin llamar a ningún método.
         */
        try {
            if (btnDelete.isDisabled() && btnModify.isDisabled() && btnSearch.isDisabled()) {
                Booking booking = null;
                ObservableList<Pack> packs = lvPacks.getItems();
                dpEndDate.setDisable(true);
                dpStartDate.setDisable(true);
                cbState.setDisable(true);
                lvPacks.setDisable(true);
                taDescription.setDisable(true);
                btnDelete.setDisable(false);
                btnModify.setDisable(false);
                btnSearch.setDisable(false);
                if(!taDescription.toString().trim().isEmpty() && dpEndDate.getValue() == null )
                bookingable.createBooking_XML(new Booking(0, user, packs, Date.valueOf(dpStartDate.getValue()), Date.valueOf(dpEndDate.getValue()), taDescription.toString(), BookingState.PENDING ));
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
        this.tcEndDate.setCellValueFactory(
                new PropertyValueFactory<>("endDate")
        );
        this.tcState.setCellValueFactory(
                new PropertyValueFactory<>("state")
        );
        this.tcPacks.setCellValueFactory(
                new PropertyValueFactory<>("packs")
        );
        List<Booking> bookings = bookingable.findAll_XML(new GenericType<List<Booking>>() {
        });
        //bookingsData = FXCollections.observableArrayList(bookingable.findAll_XML(new GenericType<List<Booking>>(){}));
        tvBooking.setItems(FXCollections.observableArrayList(bookings));
    }

}
