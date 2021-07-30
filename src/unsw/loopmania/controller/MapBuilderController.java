package unsw.loopmania.controller;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.io.File;
import java.io.PrintWriter;

public class MapBuilderController {

    @FXML
    private TextField fileName;

    @FXML
    private TextField xInput;

    @FXML
    private TextField yInput;

    @FXML
    private GridPane map;

    @FXML
    private Text prompt;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    private MenuSwitcher mainMenuSwitcher;

    private int width;
    private int height;
    private int startX;
    private int startY;
    private boolean isStartingPointEntered;
    private Image tileImage;
    private List<Pair<Integer, Integer>> paths;
    List<String> directions;

    public MapBuilderController() {
        width = 8;
        height = 14;
        startX = 0;
        startY = 0;
        isStartingPointEntered = false;
        tileImage = new Image((new File("src/assets/mud_tile.png")).toURI().toString());
        paths = new ArrayList<>();
        directions = new ArrayList<>();
    }

    @FXML
    private void handleStartButtonClick() {
        if (!isStartingPointEntered) {
            startX = Integer.parseInt(xInput.getText());
            startY = Integer.parseInt(yInput.getText());
            isStartingPointEntered = true;
            Pair<Integer, Integer> pos = new Pair<>(Integer.valueOf(startX), Integer.valueOf(startY));
            paths.add(pos);
            renderPath();
        }
    }

    @FXML
    private void handleResetButtonClick() {
        paths.clear();
        directions.clear();
        map.getChildren().clear();
        isStartingPointEntered = false;
    }

    @FXML
    private void handleUndoButtonClick() {
        if (paths.size() > 0) {
            paths.remove(paths.size() - 1);
            if (directions.size() > 0) {
                directions.remove(directions.size() - 1);
            }
            map.getChildren().remove(map.getChildren().size() - 1);
            if (paths.size() == 0) {
                isStartingPointEntered = false;
            }
        }
    }

    @FXML
    private void handleSaveButtonClick() throws Exception {
        if (!isConnectedPath()) {
            prompt.setText("Invalid Path");
            return;
        }

        if (fileName.getText().equals("")) {
            prompt.setText("Invalid Filename");
            return;
        }

        PrintWriter pathFile = new PrintWriter("worlds/" + fileName.getText() + ".json", "UTF-8");
        pathFile.println(toJSON().toString());
        pathFile.close();
        prompt.setText("File has been saved");
    }

    @FXML
    private void handleBackButtonClick() {
        mainMenuSwitcher.switchMenu();
    }

    @FXML
    private void handleUpButtonClick() {
        if (paths.size() > 0) {
            Pair<Integer, Integer> last = paths.get(paths.size() - 1);
            Pair<Integer, Integer> newPos = new Pair<>(Integer.valueOf(last.getValue0()), Integer.valueOf(last.getValue1() - 1)); 
            
            if (pathContain(newPos)) {
                prompt.setText("Duplicate tile");
                return;
            }
            
            if (newPos.getValue1() < 0) {
                prompt.setText("Out of bound");
                return;
            }
            

            paths.add(newPos);
            directions.add("UP");
            renderPath();
        
        }
    }

    @FXML
    private void handleDownButtonClick() {
        if (paths.size() > 0) {
            Pair<Integer, Integer> last = paths.get(paths.size() - 1);
            Pair<Integer, Integer> newPos = new Pair<>(Integer.valueOf(last.getValue0()), Integer.valueOf(last.getValue1() + 1)); 
            
            if (pathContain(newPos)) {
                prompt.setText("Duplicate tile");
                return;
            }
            
            if (newPos.getValue1() >= height) {
                prompt.setText("Out of bound");
                return;
            }
            

            paths.add(newPos);
            directions.add("DOWN");
            renderPath();
            
        }
    }

    @FXML
    private void handleLeftButtonClick() {
        if (paths.size() > 0) {
            Pair<Integer, Integer> last = paths.get(paths.size() - 1);
            Pair<Integer, Integer> newPos = new Pair<>(Integer.valueOf(last.getValue0() - 1), Integer.valueOf(last.getValue1())); 
            
            if (pathContain(newPos)) {
                prompt.setText("Duplicate tile");
                return;
            }
            
            if (newPos.getValue0() < 0) {
                prompt.setText("Out of bound");
                return;
            }
            
           
            paths.add(newPos);
            directions.add("LEFT");
            renderPath();
            
        }
    }

    @FXML
    private void handleRightButtonClick() {
        if (paths.size() > 0) {
            Pair<Integer, Integer> last = paths.get(paths.size() - 1);
            Pair<Integer, Integer> newPos = new Pair<>(Integer.valueOf(last.getValue0() + 1), Integer.valueOf(last.getValue1())); 

            if (pathContain(newPos)) {
                prompt.setText("Duplicate tile");
                return;
            }
            
            if (newPos.getValue0() >= width) {
                prompt.setText("Out of bound");
                return;
            }

            
            paths.add(newPos);
            directions.add("RIGHT");
            renderPath();
            
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        System.out.println(event.getCode());
        switch (event.getCode()) {
            case W:
                handleUpButtonClick();
                break;
            case S:
                handleDownButtonClick();
                break;
            case A:
                handleLeftButtonClick();
                break;
            case D:
                handleRightButtonClick();
                break;
            case BACK_SPACE:
                handleUndoButtonClick();
                break;
            case DELETE:
                handleResetButtonClick();
                break;
            default:
                break;
            }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    private boolean pathContain(Pair<Integer, Integer> in) {
        for (Pair<Integer, Integer> pos : paths) {
            if (pos.getValue0() ==  in.getValue0() && pos.getValue1() == in.getValue1()) {
                return true;
            }
        }
        return false;
    }

    private boolean isConnectedPath() {
        if (paths.size() > 0) {
            Pair<Integer, Integer> first = paths.get(0);
            Pair<Integer, Integer> last = paths.get(paths.size() - 1);
    
            boolean adjacentUP = first.getValue0() == last.getValue0() && last.getValue1() - 1 == first.getValue1();
            boolean adjacentDOWN = first.getValue0() == last.getValue0() && last.getValue1() + 1 == first.getValue1();
            boolean adjacentLEFT = first.getValue0() - 1 == last.getValue0() && last.getValue1() == first.getValue1();
            boolean adjacentRIGHT = first.getValue0() + 1 == last.getValue0() && last.getValue1() == first.getValue1();
    
            if (adjacentUP || adjacentDOWN || adjacentLEFT || adjacentRIGHT) {
                if (adjacentUP) {
                    directions.add("UP");
                } else if (adjacentDOWN) {
                    directions.add("DOWN");
                } else if (adjacentLEFT) {
                    directions.add("LEFT");
                } else if (adjacentRIGHT) {
                    directions.add("RIGHT");
                }
                return true;
            }
        }
        return false;
    }

    private void renderPath() {
        map.getChildren().clear();
        for (Pair<Integer, Integer> pos : paths) {
            ImageView view = new ImageView(tileImage);
            map.add(view, pos.getValue0(), pos.getValue1());
        }
    }

    private JSONObject toJSON() {
        JSONObject result = new JSONObject();
        JSONObject path = new JSONObject();
        
        path.put("type", "path_tile");
        path.put("x", startX);
        path.put("y", startY);
        path.put("path", directions);
        result.put("path", path);
        return result;
    }

}
