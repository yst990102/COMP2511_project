package unsw.loopmania.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.lang.Integer;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
        offButtonImage= new Image((new File("src/assets/off_button.png")).toURI().toString());
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
<<<<<<< HEAD
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
        bgmPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
              bgmPlayer.seek(Duration.ZERO);
            }
        });
        bgmPlayer.play();
    }

    public void setGameController(LoopManiaWorldController controller) {
        this.gameController = controller;
    }
    
=======
    void NextSong(MouseEvent event) {

    }

    @FXML
    void PreviousSong(MouseEvent event) {

    }

    public void setMainController(LoopManiaWorldController controller) {
        this.mainController = controller;
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    public void setBgm() {
        // bgm
        String bgmPath = "src/unsw/loopmania/bgm/bgm01_RISE.mp3";
        Media media = new Media(Paths.get(bgmPath).toUri().toString());

        MediaPlayer bgm = new MediaPlayer(media);

        mainController.setBgm(bgm);

        bgm.setOnReady(new Runnable() {
            @Override
            public void run() {
                bgm.volumeProperty().bind(bgmVolume.valueProperty());
                bgm.play();
            }
        });
    }
>>>>>>> 5ea36d02ea9a27653f4d6f2261155dfadc89f747
}
