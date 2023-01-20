/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import java.awt.Image;
import java.net.URL;
import java.util.ResourceBundle;
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
import javax.swing.table.TableColumn;

/**
 *
 * @author 2dam
 */
public class BookingManagementWindowController{
    private Stage stage;
    protected static final Logger LOGGER = Logger.getLogger(BookingManagementWindowController.class.getName());
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
    private MenuItem miReport, miPack, miModel, miItem, miUser;
    @FXML
    private ListView lvPacks;
    @FXML
    private TableColumn tcId, tcDescription, tcStartDate, tcEndDate, tcState, tcPacks;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    public void initStage(Parent root) {
        LOGGER.info("Initializing SignUp stage.");
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // , se le pondrá el título de “Sign Up” a la ventana
        stage.setTitle("SignUp");
        //  será una ventana no resizable
        stage.setResizable(false);
        // Listens to the event of the window showing up
       taDescription.setDisable(true);
       tfId.setDisable(true);
       dpEndDate.setDisable(true);
       dpStartDate.setDisable(true);
       cbState.setDisable(true);
       lvPacks.setDisable(true);
       //if(userPrivilege==Client || userPrivilege == Manager){
       miItem.setDisable(true);
       miModel.setDisable(true);
       miPack.setDisable(true);
       miReport.setDisable(true);
       
       //}
       stage.show();
    }    
    @FXML
    /**
     * This method validates the TextfFields and executes signUp() if ir works
     * propperly.
     *
     * @param event The observed event
     */
    private void handleCreateButtonAction(ActionEvent event) {
        /*Se validará el campo tfUsername.
            En caso de que no se valide con más
            de 30 caracteres o que haya
            espacios en blanco, lanza
            IncorrectUserException.*/
        try {
            
        }catch(Exception e){
        
        }
    }
}
