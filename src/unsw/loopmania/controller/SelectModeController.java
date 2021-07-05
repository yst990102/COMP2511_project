package unsw.loopmania.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class SelectModeController {

	private MenuSwitcher gameSwitcher;

    @FXML
    private Button berserkerModeButton;

    @FXML
    private Button survivalModeButton;

    @FXML
    private Button standardModeButton;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
    	this.gameSwitcher = gameSwitcher;
    }

    @FXML
    void handleStandardModeButtonClick(ActionEvent event) throws IOException {
		gameSwitcher.switchMenu();
    }

    @FXML
    void handleBerserkerModeButtonClick(ActionEvent event) throws IOException {
		gameSwitcher.switchMenu();
    }

    @FXML
    void handleSurvivalModeButtonClick(ActionEvent event) throws IOException {
		gameSwitcher.switchMenu();
    }
	
}
