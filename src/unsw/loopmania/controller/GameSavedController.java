package unsw.loopmania.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameSavedController {

    private MainMenuController mainmenuController;
    private MenuSwitcher mainMenuSwitcher;

    @FXML
    private Button savedgame01;

    @FXML
    private Button savedgame02;

    @FXML
    private Button savedgame03;

    @FXML
    private Button backbutton;

    @FXML
    void SaveProcessToRecord01() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        savedgame01.setText(currentTime);
    }

    @FXML
    void SaveProcessToRecord02() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        savedgame02.setText(currentTime);
    }

    @FXML
    void SaveProcessToRecord03() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        savedgame03.setText(currentTime);
    }

    @FXML
    void backToMainMenu() {
        mainMenuSwitcher.switchMenu();
    }

    public void setMainMenuController(MainMenuController controller) {
		this.mainmenuController = controller;
	}

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

}

