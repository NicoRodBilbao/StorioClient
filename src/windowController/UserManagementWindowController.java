package windowController;

import entities.User;
import entities.UserPrivilege;
import factories.UserFactory;
import interfaces.Userable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Joana
 */
public class UserManagementWindowController {
	
	@FXML
	private TableView tvTableUser;

	@FXML
	private TableColumn tbcolId;

	@FXML
	private TableColumn tbcolFullName;

	@FXML
	private TableColumn tbcolEmail;

	@FXML
	private TableColumn tbcolLogin;

	@FXML
	private TableColumn tbcolPhoneNumber;

	@FXML
	private TableColumn tbcolStatus;

	@FXML
	private Button btnCreateUser;

	@FXML
	private Button btnSearchUser;

	@FXML
	private Button btnDeleteUser;

	@FXML
	private Button btnModifyUser;

	@FXML
	private TextField tfIdUser;

	@FXML
	private TextField tfFullNameUser;

	@FXML
	private TextField tfEmailUser;

	@FXML
	private TextField tfLoginUser;

	@FXML
	private TextField tfPhoneNumberUser;

	@FXML
	private ComboBox cbStatusUser;

	@FXML
	private CheckBox cbManager;

	@FXML
	private Label lblEmailUser;

	@FXML
	private Label lblIdUser;

	@FXML
	private Label lblStatusUser;

	@FXML
	private Label lblPhoneNumberUser;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu mnGoBack;

	@FXML
	private Menu mnGoBack1;

	@FXML
	private Menu mnGoTo;

	@FXML
	private MenuItem miBooking;

	@FXML
	private MenuItem miReport;

	@FXML
	private MenuItem miPack;

	@FXML
	private MenuItem miModel;

	@FXML
	private MenuItem miItem;

	@FXML
	private Menu mnDarkMode;

	/**
	 * Log window functionality
	 */
	protected static final Logger LOGGER = Logger.getLogger(UserManagementWindowController.class.getName());

	private User user;

	private List<User> userList;

	private boolean isAdmin = false;

	Stage primaryStage;
	Userable userable = UserFactory.getAccessUser();

	public void setStage(Parent root) {
		LOGGER.info("Initializing UserManagementWindow");

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		if(isAdmin)
			primaryStage.setTitle("User Management");
		else
			primaryStage.setTitle("Profile Management");
        primaryStage.setResizable(false);
        primaryStage.setOnShowing(this::windowShowing);
        tvTableUser.setOnMouseClicked(event -> this.handleOnMouseClick(event));
        refreshData();
        primaryStage.show();
    }

    private void windowShowing(WindowEvent event) {
        tfIdUser.requestFocus();
        tfIdUser.setDisable(true);
		cbStatusUser.setDisable(true);
		// Set field status depending on the type of logged in user
		tfFullNameUser.setDisable(!isAdmin);
		tfEmailUser.setDisable(!isAdmin);
		tfLoginUser.setDisable(!isAdmin);
		tfPhoneNumberUser.setDisable(!isAdmin);
        btnCreateUser.setDisable(isAdmin);
        btnModifyUser.setDisable(isAdmin);
        btnSearchUser.setDisable(isAdmin);
        btnDeleteUser.setDisable(isAdmin);
    }

	private void handleOnMouseClick(MouseEvent event) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void refreshData() {
		if(isAdmin) {
			LOGGER.info("Retrieving user list data");
			tbcolId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tbcolLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
			tbcolEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			tbcolFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
			tbcolPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
			tbcolStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			userList = userable.findAllUsers();
			tvTableUser.setItems(FXCollections.observableArrayList(userList));
		} else {
			LOGGER.info("Retrieving user data");
			user = userable.findUserById(user.getId());
		}
	}

	public void initData(User u) {
		LOGGER.info("Loading inital data");
		if(u == null)
			throw new ClientErrorException("Failed loading user", Response.Status.NOT_FOUND);
		if(u.getPrivilege().equals(UserPrivilege.ADMIN))
			isAdmin = true;
		user = u;
	}

}
