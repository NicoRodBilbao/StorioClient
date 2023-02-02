package windowController;

import entities.Item;
import entities.Model;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private TextField tfIdItem;
    private ComboBox cbModelItem, cbPackItem;
    private DatePicker dpCreateDateItem;
    private TextArea taIssuesItem;
    private TableView tvTableItem;
    private Button btnCreateItem, btnSearchItem, btnModifyItem, btnDeleteItem;
    
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
    @Test
    public void test2_testCreateItemCorrect() {
        LOGGER.info("Testing the create Item method.");
        clickOn("#btnCreateItem");
        // Test enabled fields
        verifyThat("#tfIdItem", isDisabled());
        verifyThat("#dpCreateDateItem", isEnabled());
        verifyThat("#cbModelItem", isEnabled());
        verifyThat("#cbPackItem", isEnabled());
        verifyThat("#taIssuesItem", isEnabled());
        // Test enabled buttons
        verifyThat("#btnCreateItem", isEnabled());
        verifyThat("#btnModifyItem", isDisabled());
        verifyThat("#btnSearchItem", isDisabled());
        verifyThat("#btnDeleteItem", isDisabled());
        // Test click on table
        clickOn("NIKON 204");
        // Test fields are filled
        verifyThat("#tfIdItem", hasText(""));
        verifyThat("#taIssuesItem", hasText("Broken once"));
        verifyThat("#cbModelItem", isNotNull());
        verifyThat("#cbPackItem", isNotNull());
        clickOn("#taIssuesItem");
        eraseText(10);
        write("Test issues.");
        LOGGER.info("Starting create disabled test.");
        clickOn("#btnCreateItem");
        verifyThat("Test issues.", isVisible());
    }
    /**
     * Test of ModifyItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test3_testModifyItemCorrect() {
        LOGGER.info("Testing the modify Item method.");
        clickOn("#btnModifyItem");
        // Test enabled fields
        verifyThat("#tfIdItem", isDisabled());
        verifyThat("#dpCreateDateItem", isEnabled());
        verifyThat("#cbModelItem", isEnabled());
        verifyThat("#cbPackItem", isEnabled());
        verifyThat("#taIssuesItem", isEnabled());
        // Test enabled buttons
        verifyThat("#btnCreateItem", isDisabled());
        verifyThat("#btnModifyItem", isEnabled());
        verifyThat("#btnSearchItem", isDisabled());
        verifyThat("#btnDeleteItem", isDisabled());
        // Test click on table
        clickOn("Test issues.");
        // Test fields are filled
        verifyThat("#tfIdItem", hasText("4"));
        verifyThat("#taIssuesItem", hasText("Test issues."));
        verifyThat("#cbModelItem", isNotNull());
        verifyThat("#cbPackItem", isNotNull());
        clickOn("#taIssuesItem");
        eraseText(1);
        LOGGER.info("Starting modify disabled test.");
        clickOn("#btnModifyItem");
        verifyThat("Test issues", isVisible());
    }
    /**
     * Test of DindItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test4_testFindItemCorrect() {
        LOGGER.info("Testing the find Item method.");
        clickOn("#btnSearchItem");
        // Test enabled fields
        verifyThat("#tfIdItem", isEnabled());
        verifyThat("#dpCreateDateItem", isDisabled());
        verifyThat("#cbModelItem", isEnabled());
        verifyThat("#cbPackItem", isEnabled());
        verifyThat("#taIssuesItem", isDisabled());
        // Test enabled buttons
        verifyThat("#btnCreateItem", isDisabled());
        verifyThat("#btnModifyItem", isDisabled());
        verifyThat("#btnSearchItem", isEnabled());
        verifyThat("#btnDeleteItem", isDisabled());

        clickOn("#tfIdItem");
        write("2");
        // Test fields are filled
        LOGGER.info("Starting find disabled test.");
        clickOn("#btnSearchItem");
        assertEquals("Item by id search correctly", 1, tvTableItem.getItems().size());
    }
    
    /**
     * Test of FindItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test5_testFindItemCorrectModel() {
        clickOn("#btnSearchItem");
        clickOn("#cbModelItem");
        press(KeyCode.DOWN);
        clickOn("#btnSearchItem");
        assertEquals("Item by Model search correctly", 1, tvTableItem.getItems().size());
    }
    
    /**
     * Test of FindItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test6_testFindItemCorrectPack() {
        clickOn("#btnSearchItem");
        clickOn("#cbPackItem");
        press(KeyCode.DOWN);
        clickOn("#btnSearchItem");
        assertEquals("Item by Pack search correctly", 2, tvTableItem.getItems().size());
    }
    
    /**
     * Test of FindItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test6_testFindItemIncorrect() {
        clickOn("#btnSearchItem");
        clickOn("#btnSearchItem");
        verifyThat("Error", isVisible());
        clickOn("Aceptar");
    }


    /**
     * Test of DeleteItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test7_DeleteItem() {
        List<Item> packs = tvTableItem.getItems();
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
        verifyThat("#tfIdItem", hasText("4"));
        LOGGER.info("Starting find disabled test.");
        clickOn("#btnDeleteItem");
        clickOn("Cancelar");
        verifyThat("Test issues", isVisible());
        clickOn("Test issues");
        clickOn("#btnDeleteItem");
        clickOn("#btnDeleteItem");
        clickOn("Aceptar");
        assertEquals("Pack deleted Correctly", packs.size() - 1, tvTableItem.getItems().size());
    }
    
    /**
     * Test of DeleteItem method, of class ItemManagementWindowController.
     */
    @Test
    public void test8_DeleteItemIncorrect() {
        LOGGER.info("Testing the delete Item method.");
        clickOn("#btnDeleteItem");
        clickOn("#btnDeleteItem");
        verifyThat("Error", isVisible());
        clickOn("Aceptar");
    }
}
