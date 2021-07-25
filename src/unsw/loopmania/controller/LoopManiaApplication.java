package unsw.loopmania.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader(
                "world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("../view/LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../view/MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load the select mode menu
        SelectModeController selectModeController = new SelectModeController();
        selectModeController.setMainController(mainController);
        FXMLLoader selectModeLoader = new FXMLLoader(getClass().getResource("../view/SelectModeView.fxml"));
        selectModeLoader.setController(selectModeController);
        Parent selectModeRoot = selectModeLoader.load();

        // load the store
        StoreController storeController = new StoreController();
        storeController.setMainController(mainController);
        mainController.setStoreController(storeController);
        FXMLLoader storeLoader = new FXMLLoader(getClass().getResource("../view/StoreView.fxml"));
        storeLoader.setController(storeController);
        Parent storeRoot = storeLoader.load();

        // load the setting
        SettingController settingController = new SettingController();
        settingController.setMainController(mainController);
        settingController.setBgm();
        mainController.setSettingController(settingController);
        FXMLLoader settingLoader = new FXMLLoader(getClass().getResource("../view/Settings.fxml"));
        settingLoader.setController(settingController);
        Parent settingRoot = settingLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu   
        mainMenuController.setModeSwitcher(() -> {
            switchToRoot(scene, selectModeRoot, primaryStage);
        });

        selectModeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        mainController.setMainMenuSwitcher(() -> {
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });
        mainController.setStoreSwitcher(() -> {
            switchToRoot(scene, storeRoot, primaryStage);
        });
        mainController.setSettingSwitcher(() -> {
            switchToRoot(scene, settingRoot, primaryStage);
        });

        storeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
        });
        settingController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
        });

        // deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage) {
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
