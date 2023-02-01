/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storioclient;

import entities.User;
import entities.UserPrivilege;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import windowController.*;

/**
 *
 * @author 2dam
 */
public class StorioClient extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LogInWindow.fxml"));
		Parent root = (Parent) loader.load();
		LogInWindowController loginController
			= (LogInWindowController) loader.getController();

		loginController.setStage(stage);
		loginController.initStage(root);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
