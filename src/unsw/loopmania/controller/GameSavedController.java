package unsw.loopmania.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameSavedController {

    private MainMenuController mainmenuController;
    private MenuSwitcher mainMenuSwitcher;
    private LoopManiaWorldController mainWorldController;

    @FXML
    private Button savedgame01;

    @FXML
    private Button savedgame02;

    @FXML
    private Button savedgame03;

    @FXML
    private Button backbutton;

    @FXML
    void SaveProcessToRecord01() throws IOException {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        savedgame01.setText(currentTime);

        File json1 = new File("worlds/savedRecord01.json");
        if (!json1.exists()) {
            json1.createNewFile();
        }
        FileWriter fileWriter = new FileWriter("worlds/savedRecord01.json");
        JSONObject currentworldInfo = this.mainWorldController.getWolrd().SaveCurrentProcess();

        WriteJsonObjectTOFile(fileWriter, currentworldInfo);

        fileWriter.close();
    }

    @FXML
    void SaveProcessToRecord02() throws IOException {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        savedgame02.setText(currentTime);

        File json2 = new File("worlds/savedRecord02.json");
        if (!json2.exists()) {
            json2.createNewFile();
        }
        FileWriter fileWriter = new FileWriter("worlds/savedRecord02.json");
        JSONObject currentworldInfo = this.mainWorldController.getWolrd().SaveCurrentProcess();

        WriteJsonObjectTOFile(fileWriter, currentworldInfo);

        fileWriter.close();
    }

    @FXML
    void SaveProcessToRecord03() throws IOException {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        savedgame03.setText(currentTime);

        File json3 = new File("worlds/savedRecord03.json");
        if (!json3.exists()) {
            json3.createNewFile();
        }
        FileWriter fileWriter = new FileWriter("worlds/savedRecord03.json");
        JSONObject currentworldInfo = this.mainWorldController.getWolrd().SaveCurrentProcess();

        WriteJsonObjectTOFile(fileWriter, currentworldInfo);

        fileWriter.close();
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
