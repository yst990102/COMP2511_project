package unsw.loopmania.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import unsw.loopmania.strategy.StandardModeStrategy;
import unsw.loopmania.strategy.SurvivalModeStrategy;
import unsw.loopmania.strategy.BerserkerModeStrategy;

public class SelectModeController {

  private MenuSwitcher gameSwitcher;
  private LoopManiaWorldController mainController;
  private MainMenuController mainMenuController;

  @FXML
  private Button berserkerModeButton;

  @FXML
  private Button survivalModeButton;

  @FXML
  private Button standardModeButton;

  @FXML
  private Button confusionModeButton;

  public void setGameSwitcher(MenuSwitcher gameSwitcher) {
    this.gameSwitcher = gameSwitcher;
  }

  @FXML
  void handleStandardModeButtonClick(ActionEvent event) throws IOException {
    gameSwitcher.switchMenu();
    mainController.setModeStrategy(new StandardModeStrategy());
    mainMenuController.setIsGameStarted(true);
  }

  @FXML
  void handleBerserkerModeButtonClick(ActionEvent event) throws IOException {
    gameSwitcher.switchMenu();
    mainController.setModeStrategy(new BerserkerModeStrategy());
    mainMenuController.setIsGameStarted(true);
  }

  @FXML
  void handleSurvivalModeButtonClick(ActionEvent event) throws IOException {
    gameSwitcher.switchMenu();
    mainController.setModeStrategy(new SurvivalModeStrategy());
    mainMenuController.setIsGameStarted(true);
  }

  @FXML
  void handleConfusionModeButtonClick(ActionEvent event) throws IOException {
    gameSwitcher.switchMenu();
    mainController.setModeStrategy(new StandardModeStrategy());
    mainMenuController.setIsGameStarted(true);
  }

  public void setMainController(LoopManiaWorldController controller) {
    mainController = controller;
  }

  public void setMainMenuController(MainMenuController controller) {
    mainMenuController = controller;
  }

}
