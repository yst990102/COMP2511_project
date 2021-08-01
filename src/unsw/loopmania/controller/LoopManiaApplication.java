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

    // Stage
    private Stage primaryStage;

    // Controller Loader
    private LoopManiaWorldControllerLoader loopManiaLoader;
    private MainMenuController mainMenuController;
    private SettingController settingController;
    private SelectMapController selectMapController;
    private SelectModeController selectModeController;
    private StoreController storeController;
    private MapBuilderController mapBuilderController;
    private SelectPathController selectPathController;

    // FXMLLoader
    private FXMLLoader gameLoader;
    private FXMLLoader menuLoader;
    private FXMLLoader settingLoader;
    private FXMLLoader selectMapLoader;
    private FXMLLoader selectModeLoader;
    private FXMLLoader storeLoader;
    private FXMLLoader mapBuilderLoader;
    private FXMLLoader selectPathLoader;

    // Roots
    private Parent gameRoot;
    private Parent mainMenuRoot;
    private Parent settingRoot;
    private Parent selectMapRoot;
    private Parent selectModeRoot;
    private Parent storeRoot;
    private Parent mapBuilderRoot;
    private Parent selectPathRoot;

    // scene
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        this.loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        this.gameLoader = new FXMLLoader(getClass().getResource("../view/LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        this.gameRoot = gameLoader.load();

        // load the main menu
        this.mainMenuController = new MainMenuController();
        this.menuLoader = new FXMLLoader(getClass().getResource("../view/MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load the setting menu
        this.settingController = new SettingController();
        settingController.setGameController(mainController);
        this.settingLoader = new FXMLLoader(getClass().getResource("../view/SettingView.fxml"));
        settingLoader.setController(settingController);
        this.settingRoot = settingLoader.load();

        // laod the select map view
        this.selectMapController = new SelectMapController();
        selectMapController.setGameController(mainController);
        this.selectMapLoader = new FXMLLoader(getClass().getResource("../view/SelectMapView.fxml"));
        selectMapLoader.setController(selectMapController);
        this.selectMapRoot = selectMapLoader.load();

        // load select path view
        this.selectPathController = new SelectPathController();
        this.selectPathLoader = new FXMLLoader(getClass().getResource("../view/SelectPathView.fxml"));
        this.selectPathLoader.setController(selectPathController);
        this.selectPathRoot = selectPathLoader.load();
        this.selectPathController.setSelectMapController(selectMapController);

        // load the select mode menu
        this.selectModeController = new SelectModeController();
        selectModeController.setMainController(mainController);
        selectModeController.setMainMenuController(mainMenuController);
        this.selectModeLoader = new FXMLLoader(getClass().getResource("../view/SelectModeView.fxml"));
        selectModeLoader.setController(selectModeController);
        this.selectModeRoot = selectModeLoader.load();

        // load the store
        this.storeController = new StoreController();
        storeController.setMainController(mainController);
        mainController.setStoreController(storeController);
        this.storeLoader = new FXMLLoader(getClass().getResource("../view/StoreView.fxml"));
        storeLoader.setController(storeController);
        this.storeRoot = storeLoader.load();

        // load the saved game menu
        GameSavedController gamesavedController = new GameSavedController();
        gamesavedController.setMainMenuController(mainMenuController);
        gamesavedController.setMainWorldController(mainController);
        FXMLLoader gamesaveLoader = new FXMLLoader(getClass().getResource("../view/GameSavedView.fxml"));
        gamesaveLoader.setController(gamesavedController);
        Parent gamesavedRoot = gamesaveLoader.load();
        gamesavedController.LoadSavedFile();

        // load the map builder view
        this.mapBuilderController = new MapBuilderController();
        this.mapBuilderLoader = new FXMLLoader(getClass().getResource("../view/MapBuilderView.fxml"));
        this.mapBuilderLoader.setController(mapBuilderController);
        this.mapBuilderRoot = mapBuilderLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        this.scene = new Scene(mainMenuRoot);

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu   
        mainMenuController.setMapSwitcher(() -> {
            switchToRoot(scene, selectMapRoot, primaryStage);
        });
        mainMenuController.setModeSwitcher(() -> {
            switchToRoot(scene, selectModeRoot, primaryStage);
        });
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
        });
        mainMenuController.setSettingSwitcher(() -> {
            switchToRoot(scene, settingRoot, primaryStage);
        });
        mainMenuController.setGameSavedSwitcher(() -> {
            switchToRoot(scene, gamesavedRoot, primaryStage);
        });
        mainMenuController.setMapBuilderSwitcher(() -> {
            switchToRoot(scene, mapBuilderRoot, primaryStage);
        });
        mainMenuController.setSelectPathSwitcher(() -> {
            switchToRoot(scene, selectPathRoot, primaryStage);
        });

        settingController.setMainMenuSwitcher(() -> {
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });

        selectPathController.setMainMenuSwitcher(() -> {
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });

        selectPathController.setMapSwitcher(() -> {
            switchToRoot(scene, selectMapRoot, primaryStage);
        });

        selectMapController.setModeSwitcher(() -> {
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

        storeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
        });

        storeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
        });

        gamesavedController.setMainMenuSwitcher(() -> {
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });

        mapBuilderController.setMainMenuSwitcher(() -> {
            switchToRoot(scene, mainMenuRoot, primaryStage);
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
