package unsw.loopmania.controller;

import java.io.File;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class SettingController {

    private MenuSwitcher gameSwitcher;
    private LoopManiaWorldController mainController;

    @FXML
    private Button Back;

    @FXML
    private Button PlayOrPause;

    @FXML
    private Slider bgmVolume;

    @FXML
    private Button previousSong;

    @FXML
    private Button nextSong;

    @FXML
    void BackToGame(MouseEvent event) {
        gameSwitcher.switchMenu();
        mainController.startTimer();
    }

    @FXML
    void PlayOrPause(MouseEvent event) {
        if (mainController.getBgm().getStatus().equals(Status.PLAYING)) {
            mainController.getBgm().pause();

            Image image = new Image(getClass().getResourceAsStream("../../../assets/playpauseButton.png"));
            PlayOrPause.setGraphic(new ImageView(image));
        } else {
            mainController.getBgm().play();

            Image image = new Image(getClass().getResourceAsStream("../../../assets/playpauseButton.png"));
            PlayOrPause.setGraphic(new ImageView(image));
        }
    }

    @FXML
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
}
