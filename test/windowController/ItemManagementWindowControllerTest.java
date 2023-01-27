package windowController;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import storioclient.StorioClient;

/**
 *
 * @author 2dam
 */
public class ItemManagementWindowControllerTest extends ApplicationTest {

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(StorioClient.class);
    }
    
    @Test
    public void test1_InitialStateI() {
        verifyThat("#tfIdItem", hasText(""));
        //verifyThat("#dpCreateDateItem", hasText(""));
        verifyThat("#cbModelItem", hasText(""));
    }
    
    /**
     * Test of setStage method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testSetStage_Parent() {
//        System.out.println("setStage");
//        Parent root = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        instance.setStage(root);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of findItem method, of class ItemManagementWindowController.
     */
    /*@Test
    public void testFindItem() {
        System.out.println("findItem");
        ActionEvent event = null;
        ItemManagementWindowController instance = new ItemManagementWindowController();
        instance.findItem(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of deleteItem method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testDeleteItem() {
//        System.out.println("deleteItem");
//        ActionEvent event = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        instance.deleteItem(event);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setStage method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testSetStage_Stage() {
//        System.out.println("setStage");
//        Stage stage = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        instance.setStage(stage);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of convertToLocalDateViaInstant method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testConvertToLocalDateViaInstant() {
//        System.out.println("convertToLocalDateViaInstant");
//        Date dateToConvert = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        LocalDate expResult = null;
//        LocalDate result = instance.convertToLocalDateViaInstant(dateToConvert);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of convertToDateViaInstant method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testConvertToDateViaInstant() {
//        System.out.println("convertToDateViaInstant");
//        LocalDate localDateToConvert = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        Date expResult = null;
//        Date result = instance.convertToDateViaInstant(localDateToConvert);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of formatDate method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testFormatDate() {
//        System.out.println("formatDate");
//        Date dateToFormat = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        LocalDate expResult = null;
//        LocalDate result = instance.formatDate(dateToFormat);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of handleOnMouseClickHelp method, of class ItemManagementWindowController.
     */
//    @Test
//    public void testHandleOnMouseClickHelp() {
//        System.out.println("handleOnMouseClickHelp");
//        ActionEvent event = null;
//        ItemManagementWindowController instance = new ItemManagementWindowController();
//        instance.handleOnMouseClickHelp(event);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
