package windowController;

import entities.Client;
import entities.UserPrivilege;
import entities.UserStatus;
import exceptions.UserManagerException;
import factories.UserFactory;
import interfaces.Userable;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Joana
 */
public class SignUpWindowController {

	private Stage stage;

	private final Image dmImg = new Image("ui/sol_dark_mode.png");
	private final Image lmImg = new Image("ui/sol_light_mode.png");
	protected static final Logger LOGGER = Logger.getLogger(SignUpWindowController.class.getName());
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX // Email pattern for validation of format
		= Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private Userable userable = null;

	@FXML
	private Pane paneSignUpWindow;
	@FXML
	private Label lblUsername, lblFullName, lblEmail, lblPassword, lblRepeatPassword;
	@FXML
	private TextField tfUsername, tfEmail, tfFullName;
	@FXML
	private PasswordField tfPassword, tfRepeatPassword;
	@FXML
	private Button btnSignUp, btnGoBack, btnDarkMode;
	@FXML
	private Separator decorUsername, decorEmail, decorPassword, decorFullName, decorRepeatPassword;
	@FXML
	private ImageView btnImgDarkMode;

	/**
	 * This method initializes the window.
	 *
	 * @param root There parent of all the children on the scene
	 */
	public void initStage(Parent root) {
		LOGGER.info("Initializing SignUp stage.");

		userable = UserFactory.getAccessUser();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		// , se le pondrá el título de “Sign Up” a la ventana
		stage.setTitle("SignUp");
		//  será una ventana no resizable
		stage.setResizable(false);
		// Listens to the event of the window showing up
		stage.setOnShowing(this::handleSignUpWindowShowing);
		tfUsername.focusedProperty().addListener(this::focusChanged);
		tfEmail.focusedProperty().addListener(this::focusChanged);
		tfFullName.focusedProperty().addListener(this::focusChanged);
		tfPassword.focusedProperty().addListener(this::focusChanged);
		tfRepeatPassword.focusedProperty().addListener(this::focusChanged);
		tfUsername.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		tfEmail.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		tfFullName.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		tfPassword.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		tfRepeatPassword.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		stage.show();

	}

	private void textChange(EventType<KeyEvent> KEY_TYPED) {
		if (!tfUsername.getText().trim().isEmpty()
			&& !tfEmail.getText().trim().isEmpty()
			&& !tfFullName.getText().trim().isEmpty()
			&& !tfPassword.getText().trim().isEmpty()
			&& !tfRepeatPassword.getText().trim().isEmpty()) {
			btnSignUp.setDisable(false);
		}
		if (tfUsername.getText().trim().isEmpty()
			|| tfEmail.getText().trim().isEmpty()
			|| tfFullName.getText().trim().isEmpty()
			|| tfPassword.getText().trim().isEmpty()
			|| tfRepeatPassword.getText().trim().isEmpty()) {
			btnSignUp.setDisable(true);
		}
	}

	private boolean validateFieldString(String txtValidate) {
		Boolean flag = true;
		if (txtValidate.contains(" ") || txtValidate.length() >= 30) {
			flag = !flag;
		}
		return flag;
	}

	private boolean validateEmail(String txtEmail) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(txtEmail);
		return matcher.find();
	}

	@FXML
	/**
	 * This method validates the TextfFields and executes signUp() if ir
	 * works propperly.
	 *
	 * @param event The observed event
	 */
	private void handleSignUpButtonAction(ActionEvent event) {
		/*Se validará el campo tfUsername.
            En caso de que no se valide con más
            de 30 caracteres o que haya
            espacios en blanco, lanza
            IncorrectUserException.*/
		try {
			if (!validateFieldString(tfUsername.getText())) {
				decorUsername.setStyle("-fx-background-color:RED;");
				throw new UserManagerException("Incorrect user");
			}
			/* Se validará el campo tfPassword.
                En caso de que no valide con más de
                30 caracteres o que haya espacios
                en blanco, lanza
                IncorrectPasswordException*/
			if (!validateFieldString(tfPassword.getText())) {
				decorPassword.setStyle("-fx-background-color:RED;");
				throw new UserManagerException("Incorrect password");
			}
			/*Se validará el campo de tfRepeatPassword.
                    En caso de que no valide con más de
                    30 caracteres o que haya espacios
                    en blanco, lanza
                    IncorrectPasswordException.*/
			if (!validateFieldString(tfRepeatPassword.getText())) {
				decorRepeatPassword.setStyle("-fx-background-color:RED;");
				throw new UserManagerException("Incorrect password");
			}
			/*Se validará el campo tfEmail.
                        En caso de que no sea válido con un
                        formato correcto, lanza
                        EmailAlreadyExistsException.*/
			if (!validateEmail(tfEmail.getText())) {
				decorEmail.setStyle("-fx-background-color:RED;");
				throw new UserManagerException("Incorrect email");
			}
			/*Se validará que la tfPassword y
                            tfRepeatPassword sean iguales.
                            En el caso de que el tfPassword
                            como el tfRepeatPassword sean
                            distintos, lanza IncorrectPasswordException.*/
			if (!tfPassword.getText().equals(tfRepeatPassword.getText())) {
				decorPassword.setStyle("-fx-background-color:RED;");
				decorRepeatPassword.setStyle("-fx-background-color:RED;");
				throw new UserManagerException("Password doesn't match");
			}
			/*Se validará el campo de tfFullName.
                                En caso de que no valide con más de
                                50 caracteres lanza
                                IncorrectUserException.*/
			if (tfFullName.getText().length() >= 50) {
				decorFullName.setStyle("-fx-background-color:RED;");
				throw new UserManagerException("Full name is incorrect");
			}

			// String login, String email, String fullName, UserStatus status, UserPrivilege privilege, String password
			Client user = new Client();
			user.setLogin(tfUsername.getText());
			user.setEmail(tfEmail.getText());
			user.setFullName(tfFullName.getText());
			user.setStatus(UserStatus.ENABLED);
			user.setPrivilege(UserPrivilege.USER);
			user.setPassword(tfPassword.getText());
			userable.registerClient(user);
			new Alert(Alert.AlertType.INFORMATION, "The user has been successfully registered.", ButtonType.OK).showAndWait();
			//En caso de que alguna validación sea errónea. 
		} catch (UserManagerException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	@FXML
	/**
	 * This method closes this window and returns to LogInWindow.
	 *
	 * @param event The observed event
	 */
	private void handleGoBackButtonAction(ActionEvent event) {
		try {
			Stage secondaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LogInWindow.fxml"));
			// Carga el document FXML y obtiene un objeto Parent
			Parent root = (Parent) loader.load();

			LogInWindowController controller
				= ((LogInWindowController) loader.getController());
			controller.setStage(secondaryStage);
			controller.initStage(root);
			stage.close();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	@FXML
	/**
	 * This method changes atributes of the window in order to change the
	 * visual theme.
	 *
	 * @param event The observed event
	 */
	private void handleDarkModeButtonAction(ActionEvent event) {
		int i = 53, j = 18;
		// Si pulsas el botón, comprueba el tema que tiene la ventana. 
		// En caso de que la imagen sea sol_dark_mode, se cambiará por el sol_light_mode.
		if (btnImgDarkMode.getImage().getPixelReader().getArgb(i, j) == dmImg.getPixelReader().getArgb(i, j)) {
			btnImgDarkMode.setImage(lmImg);
			// Los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiarán de #3A3A3A a #DDDDDD
			tfUsername.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfEmail.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfFullName.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfPassword.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfRepeatPassword.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			// y la letra pasará de WHITE a BLACK.
			tfUsername.setStyle("-fx-text-inner-color:BLACK");
			tfEmail.setStyle("-fx-text-inner-color:BLACK");
			tfFullName.setStyle("-fx-text-inner-color:BLACK");
			tfPassword.setStyle("-fx-text-inner-color:BLACK");
			tfRepeatPassword.setStyle("-fx-text-inner-color:BLACK");
			// Las label lblUsername, lblEmail, lblFullName, lblPassword, 
			//lblRepeatPassword cambiaran de color de la letra de WHITE a BLACK.
			lblUsername.setTextFill(Color.BLACK);
			lblEmail.setTextFill(Color.BLACK);
			lblFullName.setTextFill(Color.BLACK);
			lblPassword.setTextFill(Color.BLACK);
			lblRepeatPassword.setTextFill(Color.BLACK);
			// El fondo cambia el color de #333333, a WHITE.
			paneSignUpWindow.setStyle("-fx-background-color:WHITE");
			btnDarkMode.setStyle("-fx-background-color:WHITE");
		} // En caso de que la imagen sea sol_light_mode, se cambiará por el sol_dark_mode.
		else {
			btnImgDarkMode.setImage(dmImg);
			// En caso contrario, los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiarán de #DDDDDD a #3A3A3A
			tfUsername.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfEmail.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfFullName.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfPassword.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfRepeatPassword.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			// la letra pasará de BLACK a WHITE
			tfUsername.setStyle("-fx-text-inner-color:WHITE");
			tfEmail.setStyle("-fx-text-inner-color:WHITE");
			tfFullName.setStyle("-fx-text-inner-color:WHITE");
			tfPassword.setStyle("-fx-text-inner-color:WHITE");
			tfRepeatPassword.setStyle("-fx-text-inner-color:WHITE");
			// En caso contrario, las label lblUsername, lblEmail, lblFullName, 
			// lblPassword, lblRepeatPassword cambiaran de color de la letra de BLACK a WHITE.
			lblUsername.setTextFill(Color.WHITE);
			lblEmail.setTextFill(Color.WHITE);
			lblFullName.setTextFill(Color.WHITE);
			lblPassword.setTextFill(Color.WHITE);
			lblRepeatPassword.setTextFill(Color.WHITE);
			// En caso contrario, pasará a #333333
			paneSignUpWindow.setStyle("-fx-background-color:#333333");
			btnDarkMode.setStyle("-fx-background-color:#333333");
		}
	}

	/**
	 * This method is used when a listener is triggered by focusing on a
	 * field.
	 *
	 * @param observable The value listened
	 * @param oldValue The old value obtained
	 * @param newValue The new value obtained
	 */
	private void focusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
		if (newValue) {
			if (tfUsername.isFocused()) {
				decorUsername.setStyle("-fx-background-color:AQUA;");
			} else if (tfEmail.isFocused()) {
				decorEmail.setStyle("-fx-background-color:AQUA;");
			} else if (tfFullName.isFocused()) {
				decorFullName.setStyle("-fx-background-color:AQUA;");
			} else if (tfPassword.isFocused()) {
				decorPassword.setStyle("-fx-background-color:AQUA;");
			} else if (tfRepeatPassword.isFocused()) {
				decorRepeatPassword.setStyle("-fx-background-color:AQUA;");
			}
		}
		if (oldValue) {
			if (!tfUsername.isFocused()) {
				decorUsername.setStyle("-fx-background-color:GRAY;");
			}
			if (!tfEmail.isFocused()) {
				decorEmail.setStyle("-fx-background-color:GRAY;");
			}
			if (!tfFullName.isFocused()) {
				decorFullName.setStyle("-fx-background-color:GRAY;");
			}
			if (!tfPassword.isFocused()) {
				decorPassword.setStyle("-fx-background-color:GRAY;");
			}
			if (!tfRepeatPassword.isFocused()) {
				decorRepeatPassword.setStyle("-fx-background-color:GRAY;");
			}
		}
	}

	/**
	 * This method initializes the window showing state.
	 *
	 * @param event The event listened
	 */
	private void handleSignUpWindowShowing(WindowEvent event) {
		LOGGER.info("Beggining SignInWindowController::handleSignUpWindowShowing");
		//  btnSignUp estará deshabilitado
		btnSignUp.setDisable(true);
		//  el btnGoBack estará habilitado
		btnGoBack.setDisable(false);
		//  y el foco estará en el campo tfUsername
		tfUsername.requestFocus();
		// los campos vacíos
		tfUsername.setText("");
		tfEmail.setText("");
		tfFullName.setText("");
		tfPassword.setText("");
		tfRepeatPassword.setText("");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

}
