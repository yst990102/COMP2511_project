package unsw.loopmania.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;


public class SelectPathController {

    private MenuSwitcher mapSwitcher;
    private MenuSwitcher mainMenuSwitcher;
    private File pathFile;
    private JSONObject pathJson;
    private SelectMapController selectMapController;

    @FXML
    private Pane pane;

    @FXML
    private Button selectPathButton;

    @FXML
    private Button skipButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleSelectPathButtonClick() throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Path File");
        pathFile = chooser.showOpenDialog(pane.getScene().getWindow());
        if (pathFile != null) {
            pathJson = new JSONObject(new JSONTokener(new FileReader(pathFile)));
            if (pathJson != null) {
                pathJson = pathJson.getJSONObject("path");
                selectMapController.setPathJson(pathJson);
                mapSwitcher.switchMenu();
            }
        }
    }

    @FXML
    private void handleSkipButtonClick() {
        mapSwitcher.switchMenu();
    }

    @FXML
    private void handleBackButtonClick() {
        mainMenuSwitcher.switchMenu();
    }

    public void setMapSwitcher(MenuSwitcher switcher) {
        this.mapSwitcher = switcher;
    }

    public void setMainMenuSwitcher(MenuSwitcher switcher) {
        this.mainMenuSwitcher = switcher;
    }

    public void setSelectMapController(SelectMapController controller) {
        this.selectMapController = controller;
    }

}
