package unsw.loopmania.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import unsw.loopmania.controller.LoopManiaWorldLoader.MAP_TYPE;
import unsw.loopmania.model.LoopManiaWorld;

public class GameSavedController {

    private MainMenuController mainmenuController;
    private MenuSwitcher mainMenuSwitcher;
    private LoopManiaWorldController mainWorldController;

    private JSONObject savedfile01;
    private JSONObject savedfile02;
    private JSONObject savedfile03;

    @FXML
    private CheckBox selection1;

    @FXML
    private Button savebutton;

    @FXML
    private CheckBox selection3;

    @FXML
    private CheckBox selection2;

    @FXML
    private Button loadbutton;

    @FXML
    private Button backbutton;

    @FXML
    private Text saved01;

    @FXML
    private Text saved02;

    @FXML
    private Text saved03;

    @FXML
    void selectrecord01() throws JSONException, FileNotFoundException {

        selection1.selectedProperty().set(true);
        selection2.selectedProperty().set(false);
        selection3.selectedProperty().set(false);

        LoadSavedFile();
    }

    @FXML
    void selectrecord02() throws JSONException, FileNotFoundException {

        selection1.selectedProperty().set(false);
        selection2.selectedProperty().set(true);
        selection3.selectedProperty().set(false);

        LoadSavedFile();
    }

    @FXML
    void selectrecord03() throws JSONException, FileNotFoundException {

        selection1.selectedProperty().set(false);
        selection2.selectedProperty().set(false);
        selection3.selectedProperty().set(true);

        LoadSavedFile();

    }

    public void LoadSavedFile() throws JSONException, FileNotFoundException {
        File file1 = new File("worlds/savedRecord01.json");
        File file2 = new File("worlds/savedRecord02.json");
        File file3 = new File("worlds/savedRecord03.json");

        if (file1.exists()) {
            savedfile01 = new JSONObject(new JSONTokener(new FileReader(file1.getPath())));
            saved01.textProperty().set(savedfile01.getString("saved_time") + " + " + savedfile01.getString("map_type"));
        }

        if (file2.exists()) {
            savedfile02 = new JSONObject(new JSONTokener(new FileReader(file2.getPath())));
            saved02.textProperty().set(savedfile02.getString("saved_time") + " + " + savedfile01.getString("map_type"));
        }

        if (file3.exists()) {
            savedfile03 = new JSONObject(new JSONTokener(new FileReader(file3.getPath())));
            saved03.textProperty().set(savedfile03.getString("saved_time") + " + " + savedfile01.getString("map_type"));
        }

    }

    @FXML
    void save() throws IOException {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);

        File json;
        FileWriter fileWriter;

        if (selection1.isSelected()) {
            saved01.textProperty().set(currentTime);

            json = new File("worlds/savedRecord01.json");
            if (!json.exists()) {
                json.createNewFile();
            }
            fileWriter = new FileWriter("worlds/savedRecord01.json");

            JSONObject currentworldInfo = this.mainWorldController.getWolrd().SaveCurrentProcess();

            WriteJsonObjectTOFile(fileWriter, currentworldInfo);

            fileWriter.close();

        } else if (selection2.isSelected()) {
            saved02.textProperty().set(currentTime);

            json = new File("worlds/savedRecord02.json");
            if (!json.exists()) {
                json.createNewFile();
            }
            fileWriter = new FileWriter("worlds/savedRecord02.json");

            JSONObject currentworldInfo = this.mainWorldController.getWolrd().SaveCurrentProcess();

            WriteJsonObjectTOFile(fileWriter, currentworldInfo);

            fileWriter.close();

        } else if (selection3.isSelected()) {
            saved03.textProperty().set(currentTime);

            json = new File("worlds/savedRecord03.json");
            if (!json.exists()) {
                json.createNewFile();
            }
            fileWriter = new FileWriter("worlds/savedRecord03.json");

            JSONObject currentworldInfo = this.mainWorldController.getWolrd().SaveCurrentProcess();

            WriteJsonObjectTOFile(fileWriter, currentworldInfo);

            fileWriter.close();
        }
    }

    @FXML
    void load() throws FileNotFoundException, JSONException {
        LoopManiaWorld world = this.mainWorldController.getWolrd();

        world.ResetWorldData(world.getWidth(), world.getHeight(), world.getOrderedPath(), world.getGoalObject());

        this.mainWorldController.initialiseEquippedItemsPane();
        this.mainWorldController.initialiseUnequippedInventoryPane();
        this.mainWorldController.initialisecardsPane();

        if (selection1.isSelected()) {
            this.mainWorldController.jsonfile = savedfile01;
        } else if (selection2.isSelected()) {
            this.mainWorldController.jsonfile = savedfile02;
        } else if (selection3.isSelected()) {
            this.mainWorldController.jsonfile = savedfile03;
        }

        this.mainWorldController.loadWholeMapByJson(this.mainWorldController.jsonfile);

    }

    public MAP_TYPE StringToMapType(String map_type) {
        switch (map_type) {
        case "FOREST":
            return MAP_TYPE.FOREST;
        case "ICEWORLD":
            return MAP_TYPE.ICEWORLD;
        case "DESERT":
            return MAP_TYPE.DESERT;
        default:
            return MAP_TYPE.FOREST;
        }
    }

    @FXML
    void backToMainMenu() {
        mainMenuSwitcher.switchMenu();
    }

    public void setMainMenuController(MainMenuController controller) {
        this.mainmenuController = controller;
    }

    public void setMainMenuSwitcher(MenuSwitcher switcher) {
        this.mainMenuSwitcher = switcher;
    }

    public void setMainWorldController(LoopManiaWorldController controller) {
        this.mainWorldController = controller;
    }

    public void WriteJsonObjectTOFile(FileWriter fileWriter, JSONObject jsonObject) throws IOException {
        fileWriter.write(jsonObject.toString());
    }
}
