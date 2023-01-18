/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author 2dam
 */
public class BookingManagementWindowController implements Initializable {
    
    @FXML
    private Pane paneWindow;
    @FXML
    private Pane paneForm;
    @FXML
    private TextField tfId;
    @FXML
    private Label lblId;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblStartDate;
    @FXML
    private Label lblEndDate;
    @FXML
    private Label lblState;
    @FXML
    private Label lblPacks;
    @FXML
    private ComboBox cbState;
    @FXML
    private TextArea taDescription;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnSearch;
    @FXML
    private MenuBar menubar;
    @FXML
    private Menu mnGoBack;
    @FXML
    private Menu mnGoTo;
    @FXML
    private Menu mnDarkMode;
    @FXML
    private Menu mnHelp;
    @FXML
    private MenuItem miReport;
    @FXML
    private MenuItem miPack;
    @FXML
    private MenuItem miModel;
    @FXML
    private MenuItem miItem;    
    @FXML
    private MenuItem miUser;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
