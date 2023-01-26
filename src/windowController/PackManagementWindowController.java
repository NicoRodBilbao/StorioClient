/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import entities.Client;
import entities.Pack;
import entities.PackState;
import entities.PackType;
import entities.UserPrivilege;
import exceptions.PackManagerException;
import factories.PackFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import interfaces.Packable;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class PackManagementWindowController {

    private ObservableList<Pack> packs;
    protected static final Logger LOGGER = Logger.getLogger(PackManagementWindowController.class.getName());

    private Client user;
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem miBooking, miReport, miPack, miModel, miItem, miUser, miHelpReport, miWindowHelp;

    @FXML
    private Pane packManagementWindow;

    @FXML
    private Label lblIdPack, lblTypePack, lblCreateDatePack, lblStatePack, lblDescriptionPack;

    @FXML
    private ComboBox cbTypePack, cbStatePack;

    @FXML
    private TextField tfIdPack;

    @FXML
    private DatePicker dpCreateDatePack;

    @FXML
    private TextArea taDescriptionPack;

    @FXML
    private Button btnCreatePack, btnSearchPack, btnModifyPack, btnDeletePack;

    @FXML
    private TableView tvTablePack;

    @FXML
    private TableColumn tbcolID, tbcolDescription, tbcolState, tbcolDateAdded, tbcolPackType;

    Packable packManager = PackFactory.getAccessPack();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        tfIdPack.requestFocus();
        stage.setScene(scene);
        stage.setTitle("Pack Management");
        stage.setResizable(false);
        stage.setOnShowing(this::WindowShowing);
        stage.show();

    }

    private void createPackButtonAction() {

    }

    private void searchPackButtonAction() {

    }

    private void modifyPackButtonAction() {

    }

    private void deletePackButtonAction() {

    }

    private void WindowShowing(WindowEvent event) {
        try {
            LOGGER.info("Initiate window with WindowShowing");

            tfIdPack.setText("");
            tfIdPack.setDisable(true);
            taDescriptionPack.setText("");
            taDescriptionPack.setDisable(true);

            stage.setResizable(false);

            /**
             * Set all combobox disabled
             */
            cbTypePack.setDisable(true);
            cbStatePack.setDisable(true);

            /**
             * Set datePicker disabled
             */
            dpCreateDatePack.setDisable(true);

            /**
             * Set buttons disabled if the user profile is User
             */
//            if (user.getPrivilege().equals(UserPrivilege.USER)) {
//                btnCreatePack.setDisable(true);
//                btnModifyPack.setDisable(true);
//                btnSearchPack.setDisable(false);
//                btnDeletePack.setDisable(true);
//            }


            tbcolID.setCellFactory(new PropertyValueFactory<>("Id"));
            tbcolDescription.setCellFactory(new PropertyValueFactory<>("Description"));
            tbcolState.setCellFactory(new PropertyValueFactory<>("State"));
            tbcolID.setCellFactory(new PropertyValueFactory<>("Id"));
            tbcolID.setCellFactory(new PropertyValueFactory<>("Id"));

            ObservableList<PackType> packtypes = FXCollections.observableArrayList(PackType.values());
            cbTypePack.setItems(packtypes);

            ObservableList<PackState> packStates = FXCollections.observableArrayList(PackState.values());
            cbStatePack.setItems(packStates);

            packs = FXCollections.observableArrayList(packManager.getAllPacks());
            /*if(user.getPrivilege().equals(UserPrivilege.USER)){
                packs.clear();
                packs = FXCollections.observableArrayList(packManager.getPacksByState(PackState.AVAILABLE.toString()));
            }*/

            tvTablePack.setItems(packs);

        } catch (PackManagerException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
