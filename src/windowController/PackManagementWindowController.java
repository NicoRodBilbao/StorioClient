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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
    private MenuItem miBooking, miReport, miModel, miItem, miUser, miHelpReport, miWindowHelp, miChangeMode, miLogOut;

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

    /**
     * Set all properties of the window
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("windowController/images/logo.png"));
        tfIdPack.requestFocus();
        stage.setScene(scene);
        stage.setTitle("Pack Management");
        stage.setResizable(false);
        stage.setResizable(false);
        tfIdPack.setText("");
        taDescriptionPack.setText("");

        ObservableList<PackType> packtypes = FXCollections.observableArrayList(PackType.values());
        cbTypePack.setItems(packtypes);

        ObservableList<PackState> packStates = FXCollections.observableArrayList(PackState.values());
        cbStatePack.setItems(packStates);
        tvTablePack.setOnMouseClicked(event -> this.handleOnMouseClick(event));
        /**
         * When press ESCAPE Button, ask confirmation to close the app
         */
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Storio?").showAndWait();
                if (action.get() == ButtonType.OK) {
                    stage.close();
                }
            }
        });
        /**
         * When user click to exit form the window, ask confirmation to close
         * the app
         */
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Storio?").showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    return;
                }
                event.consume();
            }
        });
        disableTextFields();
        enableButtons();
        refreshTable();
        stage.show();
    }

    private void clearFields() {
        tfIdPack.clear();
        taDescriptionPack.clear();
        cbTypePack.setValue("");
        cbStatePack.setValue("");
        dpCreateDatePack.setValue(LocalDate.now());
        tvTablePack.setDisable(false);
    }

    /**
     * Al pulsar este botón se alternará entre dos estados: Estado Activado: Los
     * Button btnSearch, btnModify y btnDelete se deshabilitarán. Se habilitarán
     * los DatePickers(dpCreateDate), los ComboBox(cbState y cbPackType) y el
     * TextArea(taDescription). Estado Desactivado: Los Button btnSearch,
     * btnModify y btnDelete se habilitarán. Se deshabilitará los
     * DatePickers(dpCreateDate), los ComboBox(cbState y cbPackType) y el
     * TextArea(taDescription). Cuando pase de estado activado a estado
     * desactivado se recogerán los datos introducidos y se llamará al método
     * createPack(). Si se está en estado activado, todos los campos del
     * formulario no están rellenos y se clicka btnCreate se pasa a modo
     * deshabilitado sin llamar a ningún método.
     *
     * @param event
     */
    @FXML
    private void createPackButtonAction(ActionEvent event) {
        try {
            if (!btnModifyPack.isDisabled()) {
                tfIdPack.clear();
                taDescriptionPack.clear();
                cbTypePack.setValue("");
                cbStatePack.getSelectionModel().selectFirst();
                dpCreateDatePack.setValue(LocalDate.now());
                tvTablePack.setDisable(true);

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
                    new Alert(Alert.AlertType.CONFIRMATION, "New pack created", ButtonType.OK).showAndWait();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Pack data is't complete, please complete all fields", ButtonType.OK).showAndWait();
                }
                disableTextFields();
                clearFields();
                enableButtons();
                refreshTable();
            }

        } catch (PackManagerException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
    /**
     * Al pulsar este botón se alternará entre dos estados: Estado Activado: Los
     * Button btnCreate, btnModify y btnDelete se deshabilitarán. Se habilitarán
     * el ComboBox(cbPackType) y el TextField(tfId). En caso de que sea Admin,
     * también se habilitará el ComboBox(cbPackState) Estado Desactivado: Los
     * Button btnCreate, btnModify y btnDelete se habilitarán. Se deshabilitará
     * el ComboBox(cbPackType) y el TextField(tfId). En caso de que sea Admin,
     * también se deshabilitará el ComboBox(cbPackState) Cuando pase de estado
     * activado a estado desactivado se recogerá el dato introducido y se
     * llamará al método findPack(). Si se está en estado activado, todos los
     * campos del formulario están rellenos, ninguno está relleno y se clicka
     * btnSearch se pasa a modo deshabilitado sin llamar a ningún método. Sólo
     * se puede hacer una búsqueda por un criterio a la vez
     *
     * @param event
     */
    @FXML
    private void searchPackButtonAction(ActionEvent event) {
        if (!btnModifyPack.isDisabled()) {
            clearFields();

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
                            refreshTable();
                        } else {
                            listPack = FXCollections.observableArrayList(packManager.getPacksByState(cbStatePack.getValue().toString()));
                            tvTablePack.setItems(listPack);
                        }
                    } else {
                        if (cbStatePack.getValue() != "") {
                            new Alert(Alert.AlertType.ERROR, "only can search for one parameter", ButtonType.OK).showAndWait();
                        } else {
                            listPack = FXCollections.observableArrayList(packManager.getPacksByType(cbTypePack.getValue().toString()));
                            tvTablePack.setItems(listPack);
                        }
                    }
                } else {
                    if (cbTypePack.getValue() != "" || cbStatePack.getValue() != "") {
                        new Alert(Alert.AlertType.ERROR, "Only can search for one parameter", ButtonType.OK).showAndWait();
                    } else {
                        listPack = FXCollections.observableArrayList(packManager.getPackById(Integer.parseInt(tfIdPack.getText())));
                        tvTablePack.setItems(listPack);
                    }
                }
            } catch (PackManagerException ex) {
                Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
            disableTextFields();
            enableButtons();
        }
    }

    /**
     * Al pulsar este botón se alternará entre dos estados: Estado Activado: Los
     * Button btnSearch, btnCreate y btnDelete se deshabilitarán. Se habilitarán
     * los DatePickers(dpCreateDate), los ComboBox(cbState y cbPackType) y el
     * TextArea(taDescription). Estado Desactivado: Los Button btnSearch,
     * btnCreate y btnDelete se habilitarán. Se deshabilitará los
     * DatePickers(dpCreateDate), los ComboBox(cbState y cbPackType) y el
     * TextArea(taDescription). Cuando pase de estado activado a estado
     * desactivado se recogerán los datos introducidos y se llamará al método
     * modifyPack(). Si se está en estado activado, todos los campos del
     * formulario no están rellenos y se clicka btnModify se pasa a modo
     * deshabilitado sin llamar a ningún método.
     *
     * @param event
     */
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
                clearFields();
                disableTextFields();
                enableButtons();
                refreshTable();
            }
        } else {
            Pack pack = (Pack) tvTablePack.getSelectionModel().getSelectedItem();
            try {
                if (!taDescriptionPack.getText().isEmpty() && (cbTypePack.getValue() != null) && (cbStatePack.getValue() != null) && (dpCreateDatePack.getValue() != null)) {
                    Pack p = new Pack(Integer.parseInt(tfIdPack.getText()), taDescriptionPack.getText(),
                            convertToDateViaInstant(dpCreateDatePack.getValue()),
                            null,
                            PackState.valueOf(cbStatePack.getValue().toString()),
                            PackType.valueOf(cbTypePack.getValue().toString()),
                            null);
                    if (!pack.equals(p)) {
                        packManager.updatePack(p);
                        new Alert(Alert.AlertType.CONFIRMATION, "Pack updated", ButtonType.OK).showAndWait();
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "No changes", ButtonType.OK).showAndWait();
                    }
                }
                clearFields();
                disableTextFields();
                enableButtons();
                refreshTable();

            } catch (PackManagerException ex) {
                Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    /**
     * Al pulsar este botón se alternará entre dos estados: Estado Activado: Los
     * Button btnSearch, btnCreate y btnModify se deshabilitarán. Se habilitará
     * el TextField(tfId). Estado Desactivado: Los Button btnSearch, btnCreate y
     * btnModify se habilitarán. Se deshabilitará el TextField(tfId). Cuando
     * pase de estado activado a estado desactivado se recogerá el dato
     * introducido y se llamará al método deletePack(). Si se está en estado
     * activado, tfId está vacío y se clicka btnDelete se pasa a modo
     * deshabilitado sin llamar a ningún método.
     *
     * @param event
     */
    @FXML
    private void deletePackButtonAction(ActionEvent event
    ) {
        if (!btnCreatePack.isDisabled()) {
            clearFields();
            btnSearchPack.setDisable(true);
            btnCreatePack.setDisable(true);
            btnModifyPack.setDisable(true);

            tfIdPack.setDisable(false);
        } else {
            try {
                if (!tfIdPack.getText().isEmpty()) {
                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this pack?").showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        packManager.deletePack(packManager.getPackById(Integer.parseInt(tfIdPack.getText())));
                        clearFields();
                        disableTextFields();
                        enableButtons();
                        refreshTable();
                        new Alert(Alert.AlertType.CONFIRMATION, "Pack delete correctly", ButtonType.OK).showAndWait();
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Pack delete cancel", ButtonType.OK).showAndWait();
                        event.consume();
                        clearFields();
                        disableTextFields();
                        enableButtons();
                        refreshTable();
                    }

                }

            } catch (PackManagerException ex) {
                Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
            clearFields();
            disableTextFields();
            enableButtons();
            refreshTable();
        }
    }

    /**
     * Go to BookingWindow
     *
     * @param event
     */
    @FXML
    private void goToBookingWindow(ActionEvent event) {
        stage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/BookingManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookingManagementWindowController controller = (BookingManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.initStage(root);
    }

    /**
     * Go to ReportWindow
     *
     * @param event
     */
    @FXML
    private void goToReportWindow(ActionEvent event) {

    }

    /**
     * Go to ModelWindow
     *
     * @param event
     */
    @FXML
    private void goToModelWindow(ActionEvent event) {
        stage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ModelManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelManagementWindowController controller = (ModelManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.setStage(root);
    }

    /**
     * Go to ItemWindow
     *
     * @param event
     */
    @FXML
    private void goToItemWindow(ActionEvent event) {
        stage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ItemManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemManagementWindowController controller = (ItemManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.setStage(root);
    }

    /**
     * Go to UserWindow
     *
     * @param event
     */
    @FXML
    private void goToUserWindow(ActionEvent event) {
//        stage.close();
//        Stage stage = new Stage();
//        // Carga el document FXML y obtiene un objeto Parent
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserManagementWindow.fxml"));
//        // Crea una escena a partir del Parent
//        Parent root = null;
//        try {
//            root = (Parent) loader.load();
//        } catch (IOException ex) {
//            Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        UserManagementWindowController controller = (UserManagementWindowController) loader.getController();
//        // Establece la escena en el escensario (Stage) y la muestra
//        controller.setStage(stage);
//        controller.initStage(root);
    }

    /**
     * Create a report from the data of the table
     *
     * @param event
     */
    @FXML
    private void goToHelpReportWindow(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/PackReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Pack>) this.tvTablePack.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
//            JasperReport report = JasperCompileManager.compileReport("/reports/PackReport.jrxml");
//            JRBeanCollectionDataSource dataPack = new JRBeanCollectionDataSource((Collection<Pack>) this.tvTablePack.getItems());
//            Map<String, Object> parameters = new HashMap<>();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataPack);
//            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//            jasperViewer.setVisible(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void goToHelpWindow(ActionEvent event) {
        /*try {
            stage.close();
            Stage secondStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/BookingManagementWindow.fxml"));
            Parent root = (Parent) loader.load();
            ReportWindowController controller = (ReportWindowController) loader.getController();
            controller.setStage(secondStage);
            controller.initStage(root);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }*/
    }

    @FXML
    private void logOut(ActionEvent event) {
        
        stage.close();
        Stage stage = new Stage();
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LogInWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (Exception ex) {
            Logger.getLogger(PackManagementWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        LogInWindowController controller = (LogInWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.initStage(root);
         
    }

    @FXML
    private void changeWindow(ActionEvent event) {
        if (lblIdPack.getTextFill().equals(Color.WHITE)) {
            // Los background de los tfIdPack, taDescriptionPack y dpCreateDatePack cambiar�n de #3A3A3A a #DDDDDD
            tfIdPack.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            taDescriptionPack.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpCreateDatePack.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
            // y la letra pasar� de WHITE a BLACK.
            tfIdPack.setStyle("-fx-text-inner-color:BLACK");
            taDescriptionPack.setStyle("-fx-text-inner-color:BLACK");
            dpCreateDatePack.setStyle("-fx-text-inner-color:BLACK");

            // Las label lblIdPack, lblTypePack, lblCreateDatePack, lblStatePack, 
            //lblDescriptionPack cambiaran de color de la letra de WHITE a BLACK.
            lblIdPack.setTextFill(Color.BLACK);
            lblTypePack.setTextFill(Color.BLACK);
            lblCreateDatePack.setTextFill(Color.BLACK);
            lblStatePack.setTextFill(Color.BLACK);
            lblDescriptionPack.setTextFill(Color.BLACK);
            // El fondo cambia el color de #333333, a WHITE.
            packManagementWindow.setStyle("-fx-background-color:WHITE");
            tvTablePack.setStyle("-fx-background-color:WHITE");

        } else {
            // Los background de los tfIdPack, taDescriptionPack y dpCreateDatePack cambiar�n de #DDDDDD a #3A3A3A
            tfIdPack.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            taDescriptionPack.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            dpCreateDatePack.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
            // la letra pasar� de BLACK a WHITE
            tfIdPack.setStyle("-fx-text-inner-color:WHITE");
            taDescriptionPack.setStyle("-fx-text-inner-color:WHITE");
            dpCreateDatePack.setStyle("-fx-text-inner-color:WHITE");
            // Las label lblIdPack, lblTypePack, lblCreateDatePack, lblStatePack, 
            //lblDescriptionPack cambiaran de color de la letra de BlLACK a WHITE.
            lblIdPack.setTextFill(Color.WHITE);
            lblTypePack.setTextFill(Color.WHITE);
            lblCreateDatePack.setTextFill(Color.WHITE);
            lblStatePack.setTextFill(Color.WHITE);
            lblDescriptionPack.setTextFill(Color.WHITE);
            // En caso contrario, pasar� a #333333
            packManagementWindow.setStyle("-fx-background-color:#333333");
            tvTablePack.setStyle("-fx-background-color:#333333");
        }
    }

    private void disableTextFields() {
        tfIdPack.setDisable(true);
        taDescriptionPack.setDisable(true);
        cbTypePack.setDisable(true);
        cbStatePack.setDisable(true);
        dpCreateDatePack.setDisable(true);
    }

    /**
     * Create the table
     */
    private void refreshTable() {
        try {
            LOGGER.info("Retrieving model data.");

            listPack = FXCollections.observableArrayList(packManager.getAllPacks());
            tbcolID.setCellValueFactory(new PropertyValueFactory<>("Id"));
            tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            tbcolState.setCellValueFactory(new PropertyValueFactory<>("State"));
            this.tbcolDateAdded.setCellValueFactory(new PropertyValueFactory<>("datePackAdd"));
            this.tbcolDateAdded.setCellFactory(column -> {
                TableCell<Pack, Date> cell = new TableCell<Pack, Date>() {
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
            tbcolPackType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            tvTablePack.setItems(listPack);

        } catch (PackManagerException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
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
            dpCreateDatePack.setValue(convertToLocalDateViaInstant(pack.getDatePackAdd()));
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

    public LocalDate formatDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //return sdf.format(dateToFormat);
        LocalDate localDate = LocalDate.parse(sdf.format(dateString), formatter);
        LOGGER.info(localDate.toString());
        return localDate;
    }
}
