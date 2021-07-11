package unsw.loopmania.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import unsw.loopmania.model.Equipments.Armours.BasicArmour;
import unsw.loopmania.model.Equipments.Helmets.BasicHelmet;
import unsw.loopmania.model.Equipments.Shields.BasicShield;
import unsw.loopmania.model.Equipments.Weapons.Staff;
import unsw.loopmania.model.Equipments.Weapons.Stake;
import unsw.loopmania.model.Equipments.Weapons.Sword;
import unsw.loopmania.model.Potions.HealthPotion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
		mainController.updateNumStoreVisit();
	}

	@FXML
	public void initialize() {
		Image inventorySlotImage = new Image((new File("src/assets/empty_slot.png")).toURI().toString());

		for (int x=0; x < 8; x++) {
			for (int y=0; y < 2; y++) {
						ImageView addImage;
						if (x <= 6 && y == 0){
							continue;
						}else{
							addImage = new ImageView(inventorySlotImage);
							storeInventory.add(addImage, x, y);
						}
				}
		}

		for (int x=0; x < 8; x++) {
			for (int y=0; y < 2; y++) {
				ImageView emptySlotView = new ImageView(inventorySlotImage);
				heroInventory.add(emptySlotView, x, y);
			}
		}
	}

	@FXML
    void ShowArmourInfo(MouseEvent event) {
		BasicArmour SelledArmour = new BasicArmour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty ArmourDescription = new SimpleStringProperty();
		ArmourDescription.set(SelledArmour.getDescription());
		description.textProperty().bind(Bindings.convert(ArmourDescription));
    }

    @FXML
    void ShowHelmetInfo(MouseEvent event) {
		BasicHelmet SelledHelmet = new BasicHelmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty HelmetDescription = new SimpleStringProperty();
		HelmetDescription.set(SelledHelmet.getDescription());
		description.textProperty().bind(Bindings.convert(HelmetDescription));
    }

    @FXML
    void ShowPotionInfo(MouseEvent event) {
		HealthPotion SelledPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty HealthPotionDescription = new SimpleStringProperty();
		HealthPotionDescription.set(SelledPotion.getDescription());
		description.textProperty().bind(Bindings.convert(HealthPotionDescription));
    }

    @FXML
    void ShowShieldInfo(MouseEvent event) {
		BasicShield SelledShield = new BasicShield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty ShieldDescription = new SimpleStringProperty();
		ShieldDescription.set(SelledShield.getDescription());
		description.textProperty().bind(Bindings.convert(ShieldDescription));
    }

    @FXML
    void ShowStaffInfo(MouseEvent event) {
		Staff SelledStaff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty StaffDescription = new SimpleStringProperty();
		StaffDescription.set(SelledStaff.getDescription());
		description.textProperty().bind(Bindings.convert(StaffDescription));
    }

    @FXML
    void ShowStakeInfo(MouseEvent event) {
		Stake SelledStake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty StakeDescription = new SimpleStringProperty();
		StakeDescription.set(SelledStake.getDescription());
		description.textProperty().bind(Bindings.convert(StakeDescription));
    }

    @FXML
    void ShowSwordInfo(MouseEvent event) {
		Sword SelledSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
		StringProperty SwordDescription = new SimpleStringProperty();
		SwordDescription.set(SelledSword.getDescription());
		description.textProperty().bind(Bindings.convert(SwordDescription));
    }


}
