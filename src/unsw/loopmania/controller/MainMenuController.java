package unsw.loopmania.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {

    private MenuSwitcher modeSwitcher;
    private MenuSwitcher gameSwitcher;
    private MenuSwitcher settingSwitcher;
    private MenuSwitcher mapSwitcher;
    private MenuSwitcher GameSavedSwitcher;
    private MenuSwitcher mapBuilderSwitcher;

    private boolean isGameStarted;

    @FXML
    private Button startGameButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button quitGameButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button mapBuilderButton;

    public void setModeSwitcher(MenuSwitcher modeSwitcher) {
        this.modeSwitcher = modeSwitcher;
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    public void setSettingSwitcher(MenuSwitcher settingSwitcher) {
        this.settingSwitcher = settingSwitcher;
    }

    public void setMapSwitcher(MenuSwitcher mapSwitcher) {
        this.mapSwitcher = mapSwitcher;
    }

    public void setGameSavedSwitcher(MenuSwitcher GameSavedSwitcher) {
        this.GameSavedSwitcher = GameSavedSwitcher;
    }

    public void setMapBuilderSwitcher(MenuSwitcher mapBuilderSwitcher) {
        this.mapBuilderSwitcher = mapBuilderSwitcher;
    }

    public void setIsGameStarted(boolean status) {
        isGameStarted = status;
        startGameButton.setText("Continue");
    }

    @FXML
    public void handleSettingButtonClick() {
        settingSwitcher.switchMenu();
    }

    @FXML
    public void handleQuitGameButtonClick() {
        System.exit(0);
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        if (isGameStarted) {
            gameSwitcher.switchMenu();
        } else {
            // modeSwitcher.switchMenu();
            mapSwitcher.switchMenu();
        }
    }

    @FXML
    void switchToSavedGame() {
        GameSavedSwitcher.switchMenu();
    }

    @FXML
    public void handleMapBuilderButtonClick() {
        mapBuilderSwitcher.switchMenu();
    }

}
