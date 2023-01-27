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
import java.util.Date;
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
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class PackManagementWindowController {

    protected static final Logger LOGGER = Logger.getLogger(PackManagementWindowController.class.getName());

    ObservableList<Pack> listPack;
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
        tvTablePack.setOnMouseClicked(event -> this.handleOnMouseClick(event));
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Storio?").showAndWait();
                if (action.get() == ButtonType.OK) {
                    stage.close();
                }
            }
        });
        
        disableTextFields();
        enableButtons();
        refreshTable();
        stage.show();

    }

    @FXML
    private void createPackButtonAction(ActionEvent event) {
        try {
            if (!btnModifyPack.isDisabled()) {
                tfIdPack.clear();
                taDescriptionPack.clear();
                cbTypePack.setValue("");
                cbStatePack.setValue("");
                dpCreateDatePack.setValue(LocalDate.now());

                btnModifyPack.setDisable(true);
                btnSearchPack.setDisable(true);
                btnDeletePack.setDisable(true);

                taDescriptionPack.setDisable(false);
                cbTypePack.setDisable(false);
                cbStatePack.setDisable(false);
                dpCreateDatePack.setDisable(false);

            } else {
                if (!taDescriptionPack.getText().isEmpty() && (cbTypePack.getValue() != null) && (cbStatePack.getValue() != null) && (dpCreateDatePack.getValue() != null)) {
                    Pack p = new Pack(null, taDescriptionPack.getText(),
                            convertToDateViaInstant(dpCreateDatePack.getValue()),
                            null,
                            PackState.valueOf(cbStatePack.getValue().toString()),
                            PackType.valueOf(cbTypePack.getValue().toString()),
                            null);
                    packManager.createPack(p);
                }
                disableTextFields();
                enableButtons();
                refreshTable();
            }

        } catch (PackManagerException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }

    }

    @FXML
    private void searchPackButtonAction(ActionEvent event) {
        if (!btnModifyPack.isDisabled()) {
            tfIdPack.clear();
            taDescriptionPack.clear();
            cbTypePack.setValue("");
            cbStatePack.setValue("");

            btnModifyPack.setDisable(true);
            btnCreatePack.setDisable(true);
            btnDeletePack.setDisable(true);

            tfIdPack.setDisable(false);
            cbTypePack.setDisable(false);
            cbStatePack.setDisable(false);
        } else {
            try {
                if (tfIdPack.getText().isEmpty()) {
                    if (cbTypePack.getValue().equals("")) {
                        if (cbStatePack.getValue().equals("")) {
                            new Alert(Alert.AlertType.INFORMATION, "find by one of the available field", ButtonType.OK).showAndWait();
                        } else {
                            listPack = FXCollections.observableArrayList(packManager.getPacksByState(cbStatePack.getValue().toString()));
                            tvTablePack.setItems(listPack);
                        }
                    } else {
                        listPack = FXCollections.observableArrayList(packManager.getPacksByType(cbTypePack.getValue().toString()));
                        tvTablePack.setItems(listPack);
                    }
                } else {
                    listPack = FXCollections.observableArrayList(packManager.getPackById(Integer.parseInt(tfIdPack.getText())));
                    tvTablePack.setItems(listPack);
                }
            } catch (PackManagerException ex) {
                Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
            disableTextFields();
            enableButtons();
        }
    }

    @FXML
    private void modifyPackButtonAction(ActionEvent event) {
        if (!btnCreatePack.isDisabled()) {
            btnSearchPack.setDisable(true);
            btnCreatePack.setDisable(true);
            btnDeletePack.setDisable(true);

            taDescriptionPack.setDisable(false);
            cbTypePack.setDisable(false);
            cbStatePack.setDisable(false);
            dpCreateDatePack.setDisable(false);

            if (tfIdPack.getText().isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "select one column in the table", ButtonType.OK).showAndWait();
                disableTextFields();
                enableButtons();
                refreshTable();
            }
        } else {
            try {
                if (!taDescriptionPack.getText().isEmpty() && (cbTypePack.getValue() != null) && (cbStatePack.getValue() != null) && (dpCreateDatePack.getValue() != null)) {
                    Pack p = new Pack(Integer.parseInt(tfIdPack.getText()), taDescriptionPack.getText(),
                            convertToDateViaInstant(dpCreateDatePack.getValue()),
                            null,
                            PackState.valueOf(cbStatePack.getValue().toString()),
                            PackType.valueOf(cbTypePack.getValue().toString()),
                            null);
                    packManager.updatePack(p);
                }
                disableTextFields();
                enableButtons();
                refreshTable();

            } catch (PackManagerException ex) {
                Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        }

    }

    @FXML
    private void deletePackButtonAction(ActionEvent event) {
        if (!btnCreatePack.isDisabled()) {
            btnSearchPack.setDisable(true);
            btnCreatePack.setDisable(true);
            btnModifyPack.setDisable(true);

            tfIdPack.setDisable(false);

        } else {
            try {
                if (!tfIdPack.getText().isEmpty()) {
                    packManager.deletePack(packManager.getPackById(Integer.parseInt(tfIdPack.getText())));
                }
                disableTextFields();
                enableButtons();
                refreshTable();
            } catch (PackManagerException ex) {
                Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    private void disableTextFields() {
        tfIdPack.setDisable(true);
        taDescriptionPack.setDisable(true);
        cbTypePack.setDisable(true);
        cbStatePack.setDisable(true);
        dpCreateDatePack.setDisable(true);
    }

    private void refreshTable() {
        try {
            LOGGER.info("Retrieving model data.");

            listPack = FXCollections.observableArrayList(packManager.getAllPacks());
            tbcolID.setCellValueFactory(new PropertyValueFactory<>("Id"));
            tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            tbcolState.setCellValueFactory(new PropertyValueFactory<>("State"));
            tbcolDateAdded.setCellValueFactory(new PropertyValueFactory<>("Date"));
            tbcolPackType.setCellValueFactory(new PropertyValueFactory<>("Type"));

            tvTablePack.setItems(listPack);

        } catch (PackManagerException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    private void WindowShowing(WindowEvent event) {
        LOGGER.info("Initiate window with WindowShowing");
        stage.setResizable(false);
        tfIdPack.setText("");
        taDescriptionPack.setText("");

        ObservableList<PackType> packtypes = FXCollections.observableArrayList(PackType.values());
        cbTypePack.setItems(packtypes);

        ObservableList<PackState> packStates = FXCollections.observableArrayList(PackState.values());
        cbStatePack.setItems(packStates);

    }

    private void enableButtons() {
        btnCreatePack.setDisable(false);
        btnModifyPack.setDisable(false);
        btnSearchPack.setDisable(false);
        btnDeletePack.setDisable(false);
    }

    private void handleOnMouseClick(MouseEvent event) {
        TableView table = (TableView) event.getSource();
        if (table.getSelectionModel().getSelectedItem() != null) {
            ObservableList selectedPacks = table.getSelectionModel().getSelectedItems();
            Pack pack = (Pack) selectedPacks.get(0);

            LOGGER.log(Level.INFO, "Selecting table row: {0}", pack.getId());
            if (!(btnModifyPack.isDisabled() && btnSearchPack.isDisabled() && btnDeletePack.isDisabled() && !btnCreatePack.isDisabled())) {
                tfIdPack.setText(String.valueOf(pack.getId()));
            }
            taDescriptionPack.setText(String.valueOf(pack.getDescription()));
            cbTypePack.setValue(pack.getType());
            cbStatePack.setValue(pack.getState());
            dpCreateDatePack.setValue(convertToLocalDateViaInstant((pack.getDatePackAdd())));
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
