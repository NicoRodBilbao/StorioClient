package windowController;

import entities.Model;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import storioclient.StorioClient;

/**
 * This class tests the functionality of the ItemManagementeWindowController for its correct functioning.
 *
 * @author 2dam
 */
public class ItemManagementWindowControllerTest extends ApplicationTest {
    
    protected static final Logger LOGGER = Logger.getLogger(ItemManagementWindowControllerTest.class.getName());
    
    /**
     * Set up class for the ItemManagementWIndowController.
     * @throws TimeoutException 
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(StorioClient.class);
    }
    
    /**
     * Tests that all the all fields are empty, disabled, all buttons enabled, and table enabled and not null.
     */
    @Test
    public void test1_InitialState() {
        LOGGER.info("Starting set up test, all fields empty and disabled, buttons enabled.");
        verifyThat("#tfIdItem", hasText(""));
        verifyThat("#taIssuesItem", hasText(""));
        verifyThat("#dpCreateDataItem", isNull());
        //verifyThat("#cbModelItem",);
        //verifyThat("#cbPackItem", isNull());
        // Test disabled
        verifyThat("#tfIdItem", isDisabled());
        verifyThat("#dpCreateDateItem", isDisabled());
        verifyThat("#cbModelItem", isDisabled());
        verifyThat("#cbPackItem", isDisabled());
        verifyThat("#taIssuesItem", isDisabled());
        // Test enabled buttons
        verifyThat("#btnCreateItem", isEnabled());
        verifyThat("#btnModifyItem", isEnabled());
        verifyThat("#btnSearchItem", isEnabled());
        verifyThat("#btnDeleteItem", isEnabled());
        // Test table
        verifyThat("#tvTableItem", isEnabled());
        verifyThat("#tvTableItem", isNotNull());
    }
    /**
     * Test of CreateItem method, of class ItemManagementWindowController.
     */
//    @Test
//    public void test2_testCreateItemCorrect() {
//        LOGGER.info("Testing the create Item method.");
//        clickOn("#btnCreateItem");
//        // Test enabled fields
//        verifyThat("#tfIdItem", isDisabled());
//        verifyThat("#dpCreateDateItem", isEnabled());
//        verifyThat("#cbModelItem", isEnabled());
//        //verifyThat("#cbPackItem", isEnabled());
//        verifyThat("#taIssuesItem", isEnabled());
//        // Test enabled buttons
//        verifyThat("#btnCreateItem", isEnabled());
//        verifyThat("#btnModifyItem", isDisabled());
//        verifyThat("#btnSearchItem", isDisabled());
//        verifyThat("#btnDeleteItem", isDisabled());
//        // Test click on table
//        clickOn("Model chux");
//        // Test fields are filled
//        verifyThat("#tfIdItem", hasText("3"));
//        verifyThat("#taIssuesItem", hasText("No issues."));
//        //verifyThat("#dpCreateDateItem", hasText("2022-01-31"));
//        verifyThat("#cbModelItem", isNotNull());
//        //verifyThat("#cbPackItem", isNotNull());
//        clickOn("#taIssuesItem");
//        eraseText(10);
//        write("Test issues.");
//        LOGGER.info("Starting create disabled test.");
//        clickOn("#btnCreateItem");
//        clickOn("Model chux"); // Overwrite data fields
//        verifyThat("Test issues.", isVisible());
//    }
    /**
     * Test of ModifyItem method, of class ItemManagementWindowController.
     */
//    @Test
//    public void test3_testModifyItemCorrect() {
//        LOGGER.info("Testing the modify Item method.");
//        clickOn("#btnModifyItem");
//        // Test enabled fields
//        verifyThat("#tfIdItem", isDisabled());
//        verifyThat("#dpCreateDateItem", isEnabled());
//        verifyThat("#cbModelItem", isEnabled());
//        //verifyThat("#cbPackItem", isEnabled());
//        verifyThat("#taIssuesItem", isEnabled());
//        // Test enabled buttons
//        verifyThat("#btnCreateItem", isDisabled());
//        verifyThat("#btnModifyItem", isEnabled());
//        verifyThat("#btnSearchItem", isDisabled());
//        verifyThat("#btnDeleteItem", isDisabled());
//        // Test click on table
//        clickOn("Model chux");
//        // Test fields are filled
//        //verifyThat("#tfIdItem", hasText("3"));
//        verifyThat("#taIssuesItem", hasText("Test issues."));
//        //verifyThat("#dpCreateDateItem", hasText("2022-01-31"));
//        verifyThat("#cbModelItem", isNotNull());
//        //verifyThat("#cbPackItem", isNotNull());
//        clickOn("#taIssuesItem");
//        eraseText(1);
//        LOGGER.info("Starting modify disabled test.");
//        clickOn("#btnModifyItem");
//        clickOn("Model chux"); // Overwrite data fields
//        verifyThat("Test issues", isVisible());
//    }
    /**
     * Test of DindItem method, of class ItemManagementWindowController.
     */
//    @Test
//    public void test4_testFindItemCorrect() {
//        LOGGER.info("Testing the find Item method.");
//        clickOn("#btnSearchItem");
//        // Test enabled fields
//        verifyThat("#tfIdItem", isEnabled());
//        verifyThat("#dpCreateDateItem", isDisabled());
//        verifyThat("#cbModelItem", isDisabled());
//        verifyThat("#cbPackItem", isDisabled());
//        verifyThat("#taIssuesItem", isDisabled());
//        // Test enabled buttons
//        verifyThat("#btnCreateItem", isDisabled());
//        verifyThat("#btnModifyItem", isDisabled());
//        verifyThat("#btnSearchItem", isEnabled());
//        verifyThat("#btnDeleteItem", isDisabled());
//        // Test click on table
//        clickOn("Model chux");
//        // Test fields are filled
//        verifyThat("#tfIdItem", hasText("2"));
//        verifyThat("#taIssuesItem", hasText("No issues."));
//        verifyThat("#dpCreateDateItem", isNotNull());
//        verifyThat("#cbModelItem", isNotNull());
//        //verifyThat("#cbPackItem", isNotNull());
//        LOGGER.info("Starting find disabled test.");
//        clickOn("#btnSearchItem");
        //verifyThat("B", isInvisible()); // TODO Check the table has only one row
 //   }

    /**
     * Test of DeleteItem method, of class ItemManagementWindowController.
     */
    @Test
    public void testDeleteItem() {
        LOGGER.info("Testing the delete Item method.");
        clickOn("#btnDeleteItem");
        // Test enabled fields
        verifyThat("#tfIdItem", isEnabled());
        verifyThat("#dpCreateDateItem", isDisabled());
        verifyThat("#cbModelItem", isDisabled());
        verifyThat("#cbPackItem", isDisabled());
        verifyThat("#taIssuesItem", isDisabled());
        // Test enabled buttons
        verifyThat("#btnCreateItem", isDisabled());
        verifyThat("#btnModifyItem", isDisabled());
        verifyThat("#btnSearchItem", isDisabled());
        verifyThat("#btnDeleteItem", isEnabled());
        // Test click on table
        clickOn("Test issues");
        // Test fields are filled
        //verifyThat("#tfIdItem", hasText("3"));
        LOGGER.info("Starting find disabled test.");
        clickOn("#btnDeleteItem");
        clickOn("Cancelar");
        clickOn("No issues.");
        verifyThat("Test issues", isVisible());
        clickOn("Test issues");
        clickOn("#btnDeleteItem");
        clickOn("#btnDeleteItem");
        clickOn("Aceptar");
        //verifyThat("Test issues", isInvisible()); // TODO check its deleted
    }

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
    
}
