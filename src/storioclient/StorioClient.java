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
import javafx.scene.Scene;
import javafx.stage.Stage;
import windowController.BookingManagementWindowController;

/**
 *
 * @author 2dam
 */
public class StorioClient extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        User user = new User(); 
         // Carga el document FXML y obtiene un objeto Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/BookingManagementWindow.fxml"));
        // Crea una escena a partir del Parent
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(StorioClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookingManagementWindowController controller = (BookingManagementWindowController) loader.getController();
        // Establece la escena en el escensario (Stage) y la muestra
        controller.setStage(stage);
        controller.initStage(root, user);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
