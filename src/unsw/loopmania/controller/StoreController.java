package unsw.loopmania.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class StoreController {

	private MenuSwitcher gameSwitcher;
	private LoopManiaWorldController mainController;

	@FXML
	private Button sellButton;

	@FXML
	private GridPane heroInventory;

	@FXML
	private GridPane storeInventory;

	@FXML
	private Button backButton;

	@FXML
	private Text description;

	@FXML
	private Text itemPrice;

	@FXML
	private Button buyButton;

	public void setGameSwitcher(MenuSwitcher gameSwitcher){
		this.gameSwitcher = gameSwitcher;
	}

	public void setMainController(LoopManiaWorldController controller) {
		this.mainController = controller;
	}

	public LoopManiaWorldController getMainController() {
		return mainController;
	}

	@FXML
	void handleSellButtonClick(ActionEvent event) {

	}

	@FXML
	void handleBuyButtonClick(ActionEvent event) {

	}

	@FXML
	void handleBackButtonClick(ActionEvent event) throws IOException {
		gameSwitcher.switchMenu();
		mainController.startTimer();
	}

	@FXML
	public void initialize() {
		Image inventorySlotImage = new Image((new File("src/assets/empty_slot.png")).toURI().toString());

		// add the empty slot images for the store inventory
		for (int x=0; x < 8; x++) {
			for (int y=0; y < 4; y++) {
						ImageView emptySlotView = new ImageView(inventorySlotImage);
						storeInventory.add(emptySlotView, x, y);
				}
		}

		for (int x=0; x < 8; x++) {
			for (int y=0; y < 2; y++) {
				ImageView emptySlotView = new ImageView(inventorySlotImage);
				heroInventory.add(emptySlotView, x, y);
			}
		}
	}

}
