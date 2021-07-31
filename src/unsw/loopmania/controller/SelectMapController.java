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

    private JSONObject forest;
    private JSONObject iceworld;
    private JSONObject desert;
    private JSONObject path;
    private MenuSwitcher modeSwitcher;
    private LoopManiaWorldController gameController;

    public SelectMapController() throws FileNotFoundException {
        forest = new JSONObject(new JSONTokener(new FileReader("worlds/" + "forest.json")));
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

        gameController.setMapType(MAP_TYPE.FOREST);
        gameController.setjsonfile(forest);
        gameController.loadWholeMapByJson();
    }

    @FXML
    void handleIceworldButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();

        gameController.setMapType(MAP_TYPE.ICEWORLD);
        gameController.setjsonfile(iceworld);
        gameController.loadWholeMapByJson();
    }

    @FXML
    void handleDesertButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();

        gameController.setMapType(MAP_TYPE.DESERT);
        gameController.setjsonfile(desert);
        gameController.loadWholeMapByJson();
    }

    public void setModeSwitcher(MenuSwitcher modeSwitcher) {
        this.modeSwitcher = modeSwitcher;
    }

    public void setGameController(LoopManiaWorldController controller) {
        this.gameController = controller;
    }

}
