/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storioclient;

import entities.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import windowController.ItemManagementWindowController;
import windowController.ModelManagementWindowController;

/**
 *
 * @author 2dam
 */
public class StorioClient extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ItemManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = (Parent) loader.load();
        ItemManagementWindowController controller = (ItemManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.setStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
