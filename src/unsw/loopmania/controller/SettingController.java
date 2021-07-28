package unsw.loopmania.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.lang.Integer;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SettingController {

    private boolean isMuscOn;
    private int speed;
    private Image onButtonImage;
    private Image offButtonImage;
    private MenuSwitcher mainMenuSwitcher;
    private MediaPlayer bgmPlayer;
    private Media bgm;
    private LoopManiaWorldController gameController;

    public SettingController() {
        isMuscOn = true;
        speed = 1;
        onButtonImage = new Image((new File("src/assets/on_button.png")).toURI().toString());
        offButtonImage = new Image((new File("src/assets/off_button.png")).toURI().toString());
        playBGM();
    }

    @FXML
    private ImageView plusButton;

    @FXML
    private Text gameSpeed;

    @FXML
    private ImageView minusButton;

    @FXML
    private ImageView musicButton;

    @FXML
    private Button backButton;

    @FXML
    private Slider volumeslider;

    @FXML
    private void initialize() {
        gameSpeed.setText(Integer.toString(speed));
        musicButton.setCursor(Cursor.HAND);
        minusButton.setCursor(Cursor.HAND);
        plusButton.setCursor(Cursor.HAND);
        backButton.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleMusicButtonClick() {
        if (isMuscOn) {
            musicButton.setImage(offButtonImage);
            isMuscOn = false;
            bgmPlayer.pause();
        } else {
            musicButton.setImage(onButtonImage);
            isMuscOn = true;
            bgmPlayer.play();
        }
    }

    @FXML
    private void handlePlusButtonClick() {
        if (speed < 10) {
            speed++;
            gameSpeed.setText(Integer.toString(speed));
            gameController.increaseGameSpeed();
        }
    }

    @FXML
    private void handleMinusButtonClick() {
        if (speed > 1) {
            speed--;
            gameSpeed.setText(Integer.toString(speed));
            gameController.decreaseGameSpeed();
        }
    }

    @FXML
    private void handleBackButtonClick() throws IOException {
        mainMenuSwitcher.switchMenu();
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    private void playBGM() {
        bgm = new Media(Paths.get("src/assets/bgm.mp3").toUri().toString());
        bgmPlayer = new MediaPlayer(bgm);
        bgmPlayer.setOnReady(new Runnable() {
            public void run() {
                bgmPlayer.volumeProperty().bind(volumeslider.valueProperty().divide(100));
                bgmPlayer.play();
            }
        });

    }

    public void setGameController(LoopManiaWorldController controller) {
        this.gameController = controller;
    }

}
