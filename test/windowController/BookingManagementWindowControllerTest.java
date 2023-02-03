/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.windowController;

import entities.Booking;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.springframework.util.Assert.hasText;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import storioclient.StorioClient;

/**
 *
 * @author 2dam
 */
public class BookingManagementWindowControllerTest extends ApplicationTest {

    protected static final Logger LOGGER = Logger.getLogger(BookingManagementWindowControllerTest.class.getName());

    private TextField tfId;
    private ComboBox cbState;
    private DatePicker dpCreateDatePack;
    private TextArea taDescription;
    private TableView tvBooking;
    private Button btnCreate, btnSearch, btnModify, btnDelete;

    @Override
    public void start(Stage stage) throws Exception {
        tfId = lookup("#tfId").query();
        taDescription = lookup("#taDescription").query();
        cbState = lookup("#cbState").queryComboBox();
        btnCreate = lookup("#btnCreate").query();
        btnSearch = lookup("#btnSearch").query();
        btnModify = lookup("#btnModify").query();
        btnDelete = lookup("#btnDelete").query();
        tvBooking = lookup("#tvBooking").queryTableView();
    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(StorioClient.class);
    }
    
     @Test
    public void test1_openSignUp() {
        clickOn("#tfUsername");
        write("superMarkel");
        clickOn("#tfPassword");
        write("abcd*1234");
        verifyThat("#BookingManagementWindow", isVisible());
    }
    /**
     * Tests that the window opens initializing it correctly.
     */
    
    @Test
    public void test2_InitialStateLogIn() {

        LOGGER.info("Starting set up test, all fields empty and disabled, buttons enabled.");
        verifyThat("#tfId", org.testfx.matcher.control.TextInputControlMatchers.hasText(""));
        verifyThat("#taDescription", org.testfx.matcher.control.TextInputControlMatchers.hasText(""));
        //verifyThat("#dpEndDate", isNull());
        //verifyThat("#dpStartDate", isNull());
        //verifyThat("#cbModelItem",);
        //verifyThat("#cbPackItem", isNull());
        // Test disabled
        verifyThat("#tfId", isDisabled());
        verifyThat("#taDescription", isDisabled());
        verifyThat("#dpEndDate", isDisabled());
        verifyThat("#dpStartDate", isDisabled());
        verifyThat("#cbState", isDisabled());
        // Test enabled buttons
        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnModify", isEnabled());
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#btnDelete", isEnabled());
        // Test table
        verifyThat("#tvBooking", isEnabled());
        verifyThat("#tvBooking", isNotNull());
        List<Booking> bookings = tvBooking.getItems();
        assertEquals("All packs search correctly", bookings.size(), tvBooking.getItems().size());

    }

    /**
     * Test of createBooking method, of class BookingManagementWindowController.
     */
    @Test
    public void test3_testCreateBookingCorrect() {
        LOGGER.info("Testing the create Booking method.");
        clickOn("#btnCreate");
        // Test enabled fields
        verifyThat("#tfId", isDisabled());
        verifyThat("#dpStartDate", isEnabled());
        verifyThat("#dpEndDate", isEnabled());
        verifyThat("#taDescription", isEnabled());
        verifyThat("#lvPacks", isEnabled());
        // Test enabled buttons
        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnSearch", isDisabled());
        verifyThat("#btnDelete", isDisabled());
        // Test click on table
        clickOn("Booking for work");
        // Test fields are filled
        verifyThat("#tfId", org.testfx.matcher.control.TextInputControlMatchers.hasText("1"));
        verifyThat("#taDescription", org.testfx.matcher.control.TextInputControlMatchers.hasText("Booking for work"));
        verifyThat("#cbState", isNotNull());
        doubleClickOn("#taDescription");
        clickOn("#taDescription");
        eraseText(1);
        write("Test description");
        LOGGER.info("Starting create disabled test.");
        clickOn("#btnCreate");
        clickOn("Aceptar");
        verifyThat("Test description", isVisible());
    }

    /**
     * Test of modifyBooking method, of class BookingManagementWindowController.
     */
    @Test
    public void test4_testModifyBookingCorrect() {
         assertNotEquals("Table has no data: Cannot test.", tvBooking.getItems().size(), 0);

        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Booking booking = (Booking) tvBooking.getSelectionModel().getSelectedItem();

        clickOn("#btnModify");
        doubleClickOn("#taDescription");
        clickOn("#taDescription");
        write("Modify test");
        clickOn("#btnModify");
        clickOn("Aceptar");

        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Booking b = (Booking) tvBooking.getSelectionModel().getSelectedItem();

        assertNotEquals("Modificado", b, booking);
    }

    

    /**
     * Test of searchBookingById method, of class BookingManagementWindowController.
     */
    @Test
    public void test5_searchBookingId() {
        assertNotEquals("Table has no data: Cannot test.", tvBooking.getItems().size(), 0);

        clickOn("#btnSearch");
        verifyThat("#tfId", isEnabled());
        verifyThat("#cbState", isDisabled());
        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnSearch");

        assertEquals("Pack by id search correctly", 1, tvBooking.getItems().size());

    }
    
    /**
     * Test of deleteBooking method, of class BookingManagementWindowController.
     */
    @Test
    public void test6_DeleteBooking() {
        assertNotEquals("Table has no data: Cannot test.", tvBooking.getItems().size(), 0);
        List<Booking> bookings = tvBooking.getItems();

        clickOn("#btnDelete");
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnDelete");
        verifyThat("Delete selected row?\n" + "This operation cannot be undone.", isVisible());
        clickOn("Aceptar");
        verifyThat("Correctly deleted", isVisible());
        clickOn("Aceptar");
        assertEquals("Booking deleted Correctly", bookings.size() - 1, tvBooking.getItems().size());
    }


}
