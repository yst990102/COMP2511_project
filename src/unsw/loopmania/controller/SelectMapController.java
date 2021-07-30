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
    private JSONObject iceworld;
    private JSONObject desert;
    private JSONObject path;
    private MenuSwitcher modeSwitcher;
    private LoopManiaWorldController gameController;

    public SelectMapController() throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("worlds/" + "world_with_twists_and_turns.json")));
        iceworld = new JSONObject(new JSONTokener(new FileReader("worlds/" + "iceworld.json")));
        desert = new JSONObject(new JSONTokener(new FileReader("worlds/" + "desert.json")));
        path = new JSONObject(new JSONTokener(new FileReader("worlds/" + "path1.json")));
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
        gameController.loadPath(path.getJSONObject("path"), MAP_TYPE.FOREST);
        gameController.loadInitialEntities(json.getJSONArray("entities"), MAP_TYPE.FOREST);
        gameController.setMapType(MAP_TYPE.FOREST);
    }

    @FXML
    void handleIceworldButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(iceworld.getJSONObject("path"), MAP_TYPE.ICEWORLD);
        gameController.loadInitialEntities(iceworld.getJSONArray("entities"), MAP_TYPE.ICEWORLD);
        gameController.setMapType(MAP_TYPE.ICEWORLD);
    }

    @FXML
    void handleDesertButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(desert.getJSONObject("path"), MAP_TYPE.DESERT);
        gameController.loadInitialEntities(desert.getJSONArray("entities"), MAP_TYPE.DESERT);
        gameController.setMapType(MAP_TYPE.DESERT);
    }

    public void setModeSwitcher(MenuSwitcher modeSwitcher) {
        this.modeSwitcher = modeSwitcher;
    }

    public void setGameController(LoopManiaWorldController controller) {
        this.gameController = controller;
    }

}
