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
        forest = new JSONObject(new JSONTokener(new FileReader("worlds/" + "world_with_twists_and_turns.json")));
        iceworld = new JSONObject(new JSONTokener(new FileReader("worlds/" + "iceworld.json")));
        desert = new JSONObject(new JSONTokener(new FileReader("worlds/" + "desert.json")));
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
        gameController.loadPath(forest.getJSONObject("path"), MAP_TYPE.FOREST);
        gameController.loadInitialEntities(forest.getJSONArray("BuildingEntities"), MAP_TYPE.FOREST);
        if (forest.has("character_info")) {
            gameController.loadCharacter(forest.getJSONObject("character_info"));
        } else {
            gameController.bornnewcharacter();
        }

        if (forest.has("cards")) {
            gameController.loadCards(forest.getJSONArray("cards"));
        }

        if (forest.has("rareitems")) {
            gameController.loadRareItems(forest.getJSONArray("rareitems"));
        }

        if (forest.has("unequippedequipments")) {
            gameController.loadUnequippedEquipment(forest.getJSONArray("unequippedequipments"));
        }

        if (forest.has("potions")) {
            gameController.loadPotions(forest.getJSONArray("potions"));
        }

        if (forest.has("enemies")) {
            gameController.loadEnemies(forest.getJSONArray("enemies"));
        }

        gameController.setMapType(MAP_TYPE.FOREST);
    }

    @FXML
    void handleIceworldButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(iceworld.getJSONObject("path"), MAP_TYPE.ICEWORLD);
        gameController.loadInitialEntities(iceworld.getJSONArray("BuildingEntities"), MAP_TYPE.ICEWORLD);
        if (iceworld.has("character_info")) {
            gameController.loadCharacter(iceworld.getJSONObject("character_info"));
        } else {
            gameController.bornnewcharacter();
        }

        if (iceworld.has("cards")) {
            gameController.loadCards(iceworld.getJSONArray("cards"));
        }

        if (iceworld.has("rareitems")) {
            gameController.loadRareItems(iceworld.getJSONArray("rareitems"));
        }

        if (iceworld.has("unequippedequipments")) {
            gameController.loadUnequippedEquipment(iceworld.getJSONArray("unequippedequipments"));
        }

        if (iceworld.has("potions")) {
            gameController.loadPotions(iceworld.getJSONArray("potions"));
        }

        if (iceworld.has("enemies")) {
            gameController.loadEnemies(iceworld.getJSONArray("enemies"));
        }

        gameController.setMapType(MAP_TYPE.ICEWORLD);
    }

    @FXML
    void handleDesertButtonClick() throws FileNotFoundException {
        modeSwitcher.switchMenu();
        gameController.loadPath(desert.getJSONObject("path"), MAP_TYPE.DESERT);
        gameController.loadInitialEntities(desert.getJSONArray("BuildingEntities"), MAP_TYPE.DESERT);
        if (desert.has("character_info")) {
            gameController.loadCharacter(desert.getJSONObject("character_info"));
        } else {
            gameController.bornnewcharacter();
        }

        if (desert.has("cards")) {
            gameController.loadCards(desert.getJSONArray("cards"));
        }

        if (desert.has("rareitems")) {
            gameController.loadRareItems(desert.getJSONArray("rareitems"));
        }

        if (desert.has("unequippedequipments")) {
            gameController.loadUnequippedEquipment(desert.getJSONArray("unequippedequipments"));
        }

        if (desert.has("potions")) {
            gameController.loadPotions(desert.getJSONArray("potions"));
        }

        if (desert.has("enemies")) {
            gameController.loadEnemies(desert.getJSONArray("enemies"));
        }

        gameController.setMapType(MAP_TYPE.DESERT);
    }

    public void setModeSwitcher(MenuSwitcher modeSwitcher) {
        this.modeSwitcher = modeSwitcher;
    }

    public void setGameController(LoopManiaWorldController controller) {
        this.gameController = controller;
    }

}
