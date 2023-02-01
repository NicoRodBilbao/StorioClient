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
import javafx.scene.Scene;
import javafx.stage.Stage;
import windowController.UserManagementWindowController;

/**
 *
 * @author 2dam
 */
public class StorioClient extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserManagementWindow.fxml"));
		Parent root = (Parent) loader.load();
		UserManagementWindowController umController =
				(UserManagementWindowController) loader.getController();

		User user = new User();

		user.setId(1);
  		user.setLogin("joana");
  		user.setFullName("Joana Renteria");
		user.setPrivilege(UserPrivilege.USER);

		user.setPrivilege(UserPrivilege.USER);
		umController.initData(user);
		umController.setStage(stage);
		umController.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
