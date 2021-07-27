package unsw.loopmania.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import unsw.loopmania.controller.LoopManiaWorldLoader.MAP_TYPE;

public class SelectMapController {

    private JSONObject json;
    private MenuSwitcher modeSwitcher;
    private LoopManiaWorldController gameController;

    public SelectMapController() throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("worlds/" + "world_with_twists_and_turns.json")));
    }

    @FXML
    private Button iceworldButton;

    @FXML
    private Button forestButton;

    @FXML
    private Button desertButton;

    @FXML
    void handleForestButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(json.getJSONObject("path"), MAP_TYPE.FOREST);
        gameController.loadInitialEntities(json.getJSONArray("entities"));
    }

    @FXML
    void handleIceworldButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(json.getJSONObject("path"), MAP_TYPE.ICEWORLD);
        gameController.loadInitialEntities(json.getJSONArray("entities"));
    }

    @FXML
    void handleDesertButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(json.getJSONObject("path"), MAP_TYPE.DESERT);
        gameController.loadInitialEntities(json.getJSONArray("entities"));
    }

    public void setModeSwitcher(MenuSwitcher modeSwitcher) {
        this.modeSwitcher = modeSwitcher;
    }

    public void setGameController(LoopManiaWorldController controller) {
        this.gameController = controller;
    }

}
