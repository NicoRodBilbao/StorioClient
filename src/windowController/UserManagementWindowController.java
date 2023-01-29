package windowController;

import entities.User;
import entities.UserPrivilege;
import factories.UserFactory;
import interfaces.Userable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
	private Button btnChangePassword;

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
		// Set field status depending on the type of logged in user
        tfIdUser.setDisable(true);
		cbStatusUser.setDisable(true);
		tfFullNameUser.setDisable(!isAdmin);
		tfEmailUser.setDisable(!isAdmin);
		tfLoginUser.setDisable(!isAdmin);
		tfPhoneNumberUser.setDisable(!isAdmin);
        btnCreateUser.setDisable(isAdmin);
        btnModifyUser.setDisable(true);
        btnSearchUser.setDisable(isAdmin);
        btnDeleteUser.setDisable(isAdmin);

		// Button visibility
		btnCreateUser.setVisible(isAdmin);
		btnSearchUser.setVisible(isAdmin);
		btnDeleteUser.setVisible(isAdmin);
		btnChangePassword.setVisible(!isAdmin);

		// managed?
		btnCreateUser.setManaged(isAdmin);
		btnSearchUser.setManaged(isAdmin);
		btnDeleteUser.setManaged(isAdmin);
		btnChangePassword.setManaged(!isAdmin);
    }

	private void handleOnMouseClick(MouseEvent event) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void handleBtnSearchUserAction(MouseEvent event) {
		if(btnSearchUser.isDisabled())
			btnSearchUser.setDisable(false);
		else
			btnSearchUser.setDisable(true);
		if(btnSearchUser.isDisabled()) {
			btnCreateUser.setDisable(false);
			btnModifyUser.setDisable(false);
			btnDeleteUser.setDisable(false);
			cbStatusUser.setDisable(true);
			tfEmailUser.setDisable(true);
			tfFullNameUser.setDisable(true);
			tfLoginUser.setDisable(true);
			tfPhoneNumberUser.setDisable(true);
		} else {
			btnCreateUser.setDisable(true);
			btnModifyUser.setDisable(true);
			btnDeleteUser.setDisable(true);
			cbStatusUser.setDisable(false);
			tfEmailUser.setDisable(false);
			tfFullNameUser.setDisable(false);
			tfLoginUser.setDisable(false);
			tfPhoneNumberUser.setDisable(false);
		}
	}

	public void handleBtnCreateUserAction(MouseEvent event) {
		if(btnCreateUser.isDisabled())
			btnCreateUser.setDisable(false);
		else
			btnCreateUser.setDisable(true);
		if(btnCreateUser.isDisabled()) {
			btnSearchUser.setDisable(false);
			btnModifyUser.setDisable(false);
			btnDeleteUser.setDisable(false);
			cbStatusUser.setDisable(true);
			tfEmailUser.setDisable(true);
			tfFullNameUser.setDisable(true);
			tfLoginUser.setDisable(true);
			tfPhoneNumberUser.setDisable(true);
		} else {
			btnSearchUser.setDisable(true);
			btnModifyUser.setDisable(true);
			btnDeleteUser.setDisable(true);
			cbStatusUser.setDisable(false);
			tfEmailUser.setDisable(false);
			tfFullNameUser.setDisable(false);
			tfLoginUser.setDisable(false);
			tfPhoneNumberUser.setDisable(false);
		}
	}

	public void handleBtnDeleteUserAction(MouseEvent event) {
		if(btnDeleteUser.isDisabled())
			btnDeleteUser.setDisable(false);
		else
			btnDeleteUser.setDisable(true);
	}

	public void handleBtnModifyUserAction(MouseEvent event) {
		if(btnModifyUser.isDisabled())
			btnModifyUser.setDisable(false);
		else
			btnModifyUser.setDisable(true);
	}

	public void handleBtnChangePasswordAction(MouseEvent event) {
		if(btnChangePassword.isDisabled())
			btnChangePassword.setDisable(false);
		else
			btnChangePassword.setDisable(true);
	}

	private void findUser() {
		// TODO
		// find user by login or id and put the
		//user on the table
		if(!tfIdUser.getText().equals("") && tfLoginUser.getText().equals("")) {
			// find
		} else {
			// ERROR only one field needs to be informed
		}
		User result = null;
		result = userable.findUserById(Integer.parseInt(tfIdUser.getText()));
		result = userable.findUserByLogin(tfLoginUser.getText());
	}

	private void createUser() {
		// TODO
		// create a new user
	}

	private void deleteUser() {
		// TODO
		// delete de selected user
	}

	private void ModifyUser() {
		// TODO
		// modify the selected user with data from
		//the informed fields
	}

	/**
	 * Refresh the table data if the logged in user is an admin
	 * or refresh the single user data (fields) if the user is not an admin
	 */
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

	/**
	 * Initialize the window passing it the data it requires
	 * @param u The logged in user
	 */
	public void initData(User u) {
		LOGGER.info("Loading inital data");
		if(u == null)
			throw new ClientErrorException("Failed loading user", Response.Status.NOT_FOUND);
		if(u.getPrivilege().equals(UserPrivilege.ADMIN))
			isAdmin = true;
		user = u;
	}

}
