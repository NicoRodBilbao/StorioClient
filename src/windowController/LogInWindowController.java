package windowController;

import entities.User;
import exceptions.UserManagerException;
import factories.UserFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Joana
 */
public class LogInWindowController {

	Stage primaryStage;
	protected static final Logger LOGGER = Logger.getLogger(LogInWindowController.class.getName());

	private final Image dmImg = new Image("ui/sol_dark_mode.png");
	private final Image lmImg = new Image("ui/sol_light_mode.png");

	@FXML
	private Pane paneLogInWindow;
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	@FXML
	private Button btnLogIn, btnSignUp, btnDarkMode;
	@FXML
	private Label lblUsername, lblPassword, lblTitle, lblForgotPassword;
	@FXML
	private Separator decorUsername, decorPassword;
	@FXML
	private ImageView btnImgDarkMode;

	private User user;
	@FXML
	private Rectangle decoLogo;
	@FXML
	private ImageView imgLogo;

	/**
	 * The stage is initialized in this method, setting all the listeners and
	 * other requirements.
	 *
	 * @param root the base of all the elements in the Scene
	 */
	public void initStage(Parent root) {
		LOGGER.info("Initializing login stage");

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Signin");
		primaryStage.setResizable(false);
		primaryStage.setOnShowing(this::windowShowing);
		tfUsername.focusedProperty().addListener(this::focusChanged);
		tfPassword.focusedProperty().addListener(this::focusChanged);
		tfUsername.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		tfPassword.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
		btnLogIn.setDisable(true);
		primaryStage.show();

	}

	/**
	 * This method sends a string to a server and expects a User in return in
	 * order to open the next window, if a Username is not found, or is not
	 * validated an alert shows.
	 *
	 * @param event The observed event
	 */
	public void logIn() {
		LOGGER.info("LogIn Action");
		try {
			//En caso de que no se valide el campo de usuario con más de 255 caracteres o que haya espacios en blanco, llama al UserManagerException
			// El decorUsername se mostrará en rojo en caso de que falle tfUsername
			if (tfUsername.getText().length() > 255 || tfUsername.getText().contains(" ")) {
				decorUsername.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
				LOGGER.severe("Username!!");
				throw new UserManagerException("Invalid Username: Spaces aren't allowed and the character limit is 255");
			}
			//En caso de que la contrase�a no sea válida con más de 30 caracteres o que haya espacios en blanco, llama al IncorrectPasswordException
			//DecorPassword se mostrará en rojo en caso de que falle tfPassword
			if (tfPassword.getText().length() > 30 || tfPassword.getText().contains(" ")) {
				decorPassword.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
				LOGGER.severe("Password!!");
				throw new UserManagerException("Invalid credentials");
			} else {
				LOGGER.info("OK!!");
				boolean login = UserFactory.getAccessUser().loginUser(tfUsername.getText(), tfPassword.getText());
				if (!login) {
					decorPassword.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
					throw new UserManagerException("Invalid credentials");
				} else {
					primaryStage.close();
					Stage stage = new Stage();
					// Carga el document FXML y obtiene un objeto Parent
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ApplicationWindow.fxml"));
					// Crea una escena a partir del Parent
					Parent root = (Parent) loader.load();
					BookingManagementWindowController controller = (BookingManagementWindowController) loader.getController();
					// Establece la escena en el escensario (Stage) y la muestra
					controller.setStage(stage);
					controller.setUser(user);
					controller.initStage(root);
					new Alert(Alert.AlertType.INFORMATION, "You are now logged in", ButtonType.OK).showAndWait();
				}
			}
		} catch (UserManagerException e) {
			LOGGER.severe(e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		} catch (IOException ex) {
			Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * This method sends a User to a server and either confirms its
	 * successfulness or shows an alert in case of an error ocurring.
	 */
	public void signUp() {
		LOGGER.info("signUp Action");
		try {
			Stage stage = new Stage();
			primaryStage.close();
			// Carga el document FXML y obtiene un objeto Parent
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/SignUpWindow.fxml"));
			// Crea una escena a partir del Parent
			Parent root = (Parent) loader.load();
			SignUpWindowController controller = (SignUpWindowController) loader.getController();
			// Establece la escena en el escensario (Stage) y la muestra
			controller.setStage(stage);
			controller.initStage(root);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	private void windowShowing(WindowEvent event) {
		tfUsername.requestFocus();
		decorUsername.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
		btnLogIn.setDisable(true);
		btnSignUp.setDisable(false);

	}

	private void focusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
		if (newValue) {
			if (tfPassword.isFocused()) {
				decorUsername.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
				decorPassword.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
		if (oldValue) {
			if (tfUsername.isFocused()) {
				decorUsername.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
				decorPassword.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
	}

	private void textChange(EventType<KeyEvent> KEY_TYPED) {
		if (!tfUsername.getText().trim().isEmpty() && !tfPassword.getText().trim().isEmpty()) {
			btnLogIn.setDisable(false);
		}
		if (tfUsername.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
			btnLogIn.setDisable(true);
		}
	}

	@FXML
	/**
	 * This method changes atributes of the window in order to change the visual
	 * theme.
	 *
	 * @param event The observed event
	 */
	private void handleDarkModeButtonAction(ActionEvent event) {
		LOGGER.info("darkMode Action");
		int i = 53, j = 18;
		// Si pulsas el bot�n, comprueba el tema que tiene la ventana. 
		// En caso de que la imagen sea sol_dark_mode, se cambiar� por el sol_light_mode.
		if (btnImgDarkMode.getImage().getPixelReader().getArgb(i, j) == dmImg.getPixelReader().getArgb(i, j)) {
			btnImgDarkMode.setImage(lmImg);
			// Los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiar�n de #3A3A3A a #DDDDDD
			tfUsername.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfPassword.setBackground(new Background(new BackgroundFill(Color.valueOf("#DDDDDD"), CornerRadii.EMPTY, Insets.EMPTY)));
			// y la letra pasar� de WHITE a BLACK.
			tfUsername.setStyle("-fx-text-inner-color:BLACK");
			tfPassword.setStyle("-fx-text-inner-color:BLACK");
			// Las label lblUsername, lblEmail, lblFullName, lblPassword, 
			//lblRepeatPassword cambiaran de color de la letra de WHITE a BLACK.
			lblUsername.setTextFill(Color.BLACK);
			lblPassword.setTextFill(Color.BLACK);
			lblTitle.setTextFill(Color.BLACK);
			// El fondo cambia el color de #333333, a WHITE.
			paneLogInWindow.setStyle("-fx-background-color:WHITE");
			btnDarkMode.setStyle("-fx-background-color:WHITE");
		} // En caso de que la imagen sea sol_light_mode, se cambiar� por el sol_dark_mode.
		else {
			btnImgDarkMode.setImage(dmImg);
			// En caso contrario, los background de los tfUsername, tfEmail, tfFullName, tfPassword y tfRepeatPassword cambiar�n de #DDDDDD a #3A3A3A
			tfUsername.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			tfPassword.setBackground(new Background(new BackgroundFill(Color.valueOf("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
			// la letra pasar� de BLACK a WHITE
			tfUsername.setStyle("-fx-text-inner-color:WHITE");
			tfPassword.setStyle("-fx-text-inner-color:WHITE");
			// En caso contrario, las label lblUsername, lblEmail, lblFullName, 
			// lblPassword, lblRepeatPassword cambiaran de color de la letra de BLACK a WHITE.
			lblUsername.setTextFill(Color.WHITE);
			lblPassword.setTextFill(Color.WHITE);
			lblTitle.setTextFill(Color.WHITE);
			// En caso contrario, pasar� a #333333
			paneLogInWindow.setStyle("-fx-background-color:#333333");
			btnDarkMode.setStyle("-fx-background-color:#333333");
		}
	}

	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}
}
