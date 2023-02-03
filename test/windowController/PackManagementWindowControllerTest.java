/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowController;

import entities.Pack;
import entities.PackState;
import entities.PackType;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import storioclient.StorioClient;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PackManagementWindowControllerTest extends ApplicationTest {

    private TextField tfIdPack;
    private ComboBox cbTypePack, cbStatePack;
    private DatePicker dpCreateDatePack;
    private TextArea taDescriptionPack;
    private TableView tvTablePack;
    private Button btnCreatePack, btnSearchPack, btnModifyPack, btnDeletePack;

    @Override
    public void start(Stage stage) throws Exception {
        new StorioClient().start(stage);
        tfIdPack = lookup("#tfIdPack").query();
        taDescriptionPack = lookup("#taDescriptionPack").query();
        cbTypePack = lookup("#cbTypePack").queryComboBox();
        cbStatePack = lookup("#cbStatePack").queryComboBox();

        btnCreatePack = lookup("#btnCreatePack").query();
        btnSearchPack = lookup("#btnSearchPack").query();
        btnModifyPack = lookup("#btnModifyPack").query();
        btnDeletePack = lookup("#btnDeletePack").query();
        tvTablePack = lookup("#tvTablePack").queryTableView();
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
        clickOn("#mnGoTo");
        clickOn("#miPack");
        verifyThat("#packManagementWindow", isVisible());
    }

    /**
     * Test Window initial stage Method from PackManagerWindowController
     */
    @Test
    public void test2_initialStateSignUp() {
        verifyThat("#tfIdPack", hasText(""));
        verifyThat("#taDescriptionPack", hasText(""));
        verifyThat("#btnCreatePack", isEnabled());
        verifyThat("#btnSearchPack", isEnabled());
        verifyThat("#btnModifyPack", isEnabled());
        verifyThat("#btnDeletePack", isEnabled());
        verifyThat("#tvTablePack", isVisible());
        verifyThat("#tbcolID", isVisible());
        verifyThat("#tbcolDescription", isVisible());
        verifyThat("#tbcolState", isVisible());
        verifyThat("#tbcolDateAdded", isVisible());
        verifyThat("#tbcolPackType", isVisible());
    }
    /**
     * Test Create Pack Method from PackManagerWindowController
     */
    @Test
    public void test4_createCompletePack() {
        List<Pack> packs = tvTablePack.getItems();

        clickOn("#btnCreatePack");
        verifyThat("#cbTypePack", isEnabled());
        verifyThat("#dpCreateDatePack", isEnabled());
        verifyThat("#cbStatePack", isEnabled());
        verifyThat("#taDescriptionPack", isEnabled());

        verifyThat("#btnSearchPack", isDisabled());
        verifyThat("#btnModifyPack", isDisabled());
        verifyThat("#btnDeletePack", isDisabled());

        clickOn("#taDescriptionPack");
        write("Any description");
        clickOn("#cbTypePack");
        press(KeyCode.DOWN);
        clickOn("#btnCreatePack");
        verifyThat("New pack created", isVisible());
        clickOn("Aceptar");

        assertEquals("New pack created correctly", (packs.size() + 1), tvTablePack.getItems().size());

    }

    /**
     * Test Search all Packs Method from PackManagerWindowController
     */
    @Test
    public void test5_searchAllPack() {
        assertNotEquals("Table has no data: Cannot test.", tvTablePack.getItems().size(), 0);
        clickOn("#btnSearchPack");
        List<Pack> packs = tvTablePack.getItems();

        verifyThat("#tfIdPack", isEnabled());
        verifyThat("#cbTypePack", isEnabled());
        verifyThat("#cbStatePack", isEnabled());

        verifyThat("#btnCreatePack", isDisabled());
        verifyThat("#btnModifyPack", isDisabled());
        verifyThat("#btnDeletePack", isDisabled());

        clickOn("#btnSearchPack");
        assertEquals("All packs search correctly", packs.size(), tvTablePack.getItems().size());
    }

    /**
     * Test Seacrh Pack by Id Method from PackManagerWindowController
     */
    @Test
    public void test6_searchPackById() {
        assertNotEquals("Table has no data: Cannot test.", tvTablePack.getItems().size(), 0);

        clickOn("#btnSearchPack");
        verifyThat("#tfIdPack", isEnabled());
        verifyThat("#cbTypePack", isEnabled());
        verifyThat("#cbStatePack", isEnabled());

        verifyThat("#btnCreatePack", isDisabled());
        verifyThat("#btnModifyPack", isDisabled());
        verifyThat("#btnDeletePack", isDisabled());

        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Pack pack = (Pack) tvTablePack.getSelectionModel().getSelectedItem();
        String id = pack.getId().toString();

        clickOn("#btnSearchPack");
        verifyThat("Only can search for one parameter", isVisible());
        clickOn("Aceptar");

        clickOn("#btnSearchPack");
        clickOn("#tfIdPack");
        write(id);
        clickOn("#btnSearchPack");

        assertEquals("Pack by id search correctly", 1, tvTablePack.getItems().size());

    }

    /**
     * Test Search by Pack Method from PackManagerWindowController
     */
    @Test
    public void test7_searchPackByType() {
        assertNotEquals("Table has no data: Cannot test.", tvTablePack.getItems().size(), 0);
        clickOn("#btnSearchPack");
        List<Pack> packs = tvTablePack.getItems();
        Collection packsSound = packs.stream().filter(p -> p.getType().equals(PackType.SOUND)).collect(Collectors.toList());

        verifyThat("#tfIdPack", isEnabled());
        verifyThat("#cbTypePack", isEnabled());
        verifyThat("#cbStatePack", isEnabled());

        verifyThat("#btnCreatePack", isDisabled());
        verifyThat("#btnModifyPack", isDisabled());
        verifyThat("#btnDeletePack", isDisabled());

        clickOn("#cbTypePack");
        press(KeyCode.DOWN);
        clickOn("#btnSearchPack");

        assertEquals("Pack by Type search correctly", packsSound.size(), tvTablePack.getItems().size());

    }

    /**
     * Test Search by State Method from PackManagerWindowController
     */
    @Test
    public void test8_searchPackByState() {
        assertNotEquals("Table has no data: Cannot test.", tvTablePack.getItems().size(), 0);
        clickOn("#btnSearchPack");
        List<Pack> packs = tvTablePack.getItems();
        Collection packsAvailable = packs.stream().filter(p -> p.getState().equals(PackState.AVAILABLE)).collect(Collectors.toList());

        verifyThat("#tfIdPack", isEnabled());
        verifyThat("#cbTypePack", isEnabled());
        verifyThat("#cbStatePack", isEnabled());

        verifyThat("#btnCreatePack", isDisabled());
        verifyThat("#btnModifyPack", isDisabled());
        verifyThat("#btnDeletePack", isDisabled());

        clickOn("#cbStatePack");
        press(KeyCode.DOWN);

        clickOn("#btnSearchPack");
        assertEquals("Pack by Type search correctly", packsAvailable.size(), tvTablePack.getItems().size());

    }

    /**
     * Test Modify Method from PackManagerWindowController
     */
    @Test
    public void test9_ModifyPack() {
        assertNotEquals("Table has no data: Cannot test.", tvTablePack.getItems().size(), 0);
        clickOn("#btnModifyPack");
        verifyThat("select one column in the table", isVisible());
        clickOn("Aceptar");

        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Pack pack = (Pack) tvTablePack.getSelectionModel().getSelectedItem();

        clickOn("#btnModifyPack");
        clickOn("#cbStatePack");
        press(KeyCode.DOWN);
        press(KeyCode.DOWN);
        clickOn("#btnModifyPack");
        clickOn("Aceptar");

        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Pack p = (Pack) tvTablePack.getSelectionModel().getSelectedItem();

        assertNotEquals("Modificado", p, pack);

    }

    /**
     * Test Delete Method from PackManagerWindowController
     */
    @Test
    public void testB1_DeletePack() {
        assertNotEquals("Table has no data: Cannot test.", tvTablePack.getItems().size(), 0);
        List<Pack> packs = tvTablePack.getItems();

        clickOn("#btnDeletePack");
        Node row = lookup(".table-row-cell").nth(0).query();
        Pack pack = (Pack) tvTablePack.getSelectionModel().getSelectedItem();
        clickOn(row);
        clickOn("#btnDeletePack");
        verifyThat("Are you sure you want to delete this pack?", isVisible());
        clickOn("Aceptar");
        assertEquals("Pack deleted Correctly", packs.size() - 1, tvTablePack.getItems().size());
    }

}
