package unsw.loopmania.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

public class GameSavedController {

    private MainMenuController mainmenuController;
    private MenuSwitcher mainMenuSwitcher;
    private LoopManiaWorldController mainWorldController;

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
    void selectrecord01() {
        if (selection1.isSelected()) {
            return;
        } else {
            selection1.selectedProperty().set(true);
            selection2.selectedProperty().set(false);
            selection3.selectedProperty().set(false);
        }
    }

    @FXML
    void selectrecord02() {
        if (selection2.isSelected()) {
            return;
        } else {
            selection1.selectedProperty().set(false);
            selection2.selectedProperty().set(true);
            selection3.selectedProperty().set(false);
        }
    }

    @FXML
    void selectrecord03() {
        if (selection3.isSelected()) {
            return;
        } else {
            selection1.selectedProperty().set(false);
            selection2.selectedProperty().set(false);
            selection3.selectedProperty().set(true);
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
    void load() {

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
