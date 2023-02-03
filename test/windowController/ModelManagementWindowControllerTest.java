/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 2dam
 */
public class ModelManagementWindowControllerTest {
    
    public ModelManagementWindowControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setStage method, of class ModelManagementWindowController.
     */
    @Test
    public void testSetStage_Parent() {
        System.out.println("setStage");
        Parent root = null;
        ModelManagementWindowController instance = new ModelManagementWindowController();
        instance.setStage(root);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createModel method, of class ModelManagementWindowController.
     */
    @Test
    public void testCreateModel() {
        System.out.println("createModel");
        ActionEvent event = null;
        ModelManagementWindowController instance = new ModelManagementWindowController();
        instance.createModel(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateModel method, of class ModelManagementWindowController.
     */
    @Test
    public void testUpdateModel() {
        System.out.println("updateModel");
        ActionEvent event = null;
        ModelManagementWindowController instance = new ModelManagementWindowController();
        instance.updateModel(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findModel method, of class ModelManagementWindowController.
     */
    @Test
    public void testFindModel() {
        System.out.println("findModel");
        ActionEvent event = null;
        ModelManagementWindowController instance = new ModelManagementWindowController();
        instance.findModel(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteModel method, of class ModelManagementWindowController.
     */
    @Test
    public void testDeleteModel() {
        System.out.println("deleteModel");
        ActionEvent event = null;
        ModelManagementWindowController instance = new ModelManagementWindowController();
        instance.deleteModel(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStage method, of class ModelManagementWindowController.
     */
    @Test
    public void testSetStage_Stage() {
        System.out.println("setStage");
        Stage stage = null;
        ModelManagementWindowController instance = new ModelManagementWindowController();
        instance.setStage(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
