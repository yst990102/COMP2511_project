package unsw.loopmania.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Item;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.weapons.Staff;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.equipments.weapons.Sword;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.Store;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

import org.javatuples.Pair;

enum ITEM_TYPE {
	SWORD, HELMET, SHIELD, STAKE, STAFF, ARMOUR, HEALTH_POTION
}

public class StoreController {
	private Store store;

	private MenuSwitcher gameSwitcher;
	private LoopManiaWorldController mainController;

	private Image swordImage;
    private Image helmetImage;
    private Image shieldImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
	private Image healthPotionImage;

	private ImageView currentlyClickedImage;
	private List<ImageView> storeItemViews;
	private Item currentlySelectedItem;
	private ITEM_TYPE currentlySelectedItemType;

	boolean isStoreItemClicked;
	boolean isHeroItemClicked;

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
	private Text playerTotalGold;

	@FXML
	private Button buyButton;

	public StoreController() {
		this.store = new Store();

		swordImage = new Image((new File("src/assets/basic_sword.png")).toURI().toString());
        helmetImage = new Image((new File("src/assets/helmet.png")).toURI().toString());
        shieldImage = new Image((new File("src/assets/shield.png")).toURI().toString());
        stakeImage = new Image((new File("src/assets/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/assets/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/assets/armour.png")).toURI().toString());
		healthPotionImage = new Image((new File("src/assets/health_potion.png")).toURI().toString());

	}

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
		if (isHeroItemClicked) {
		}
	}

	@FXML
	void handleBuyButtonClick(ActionEvent event) {
		if (isStoreItemClicked ) {
			Pair<Integer, Integer> firstAvailableSlot = mainController.getFirstAvailableSlotForItem();

			Item selectedItem = null;
			ImageView itemView = null;
			int price = 0;

			switch(currentlySelectedItemType) {
				case SWORD:
					Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = sword;
					price = sword.getPrice();
					itemView = new ImageView(swordImage);
					break;
				case STAKE:
					Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = stake;
					price = stake.getPrice();
					itemView = new ImageView(stakeImage);
					break;
				case STAFF:
					Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = staff;
					price = staff.getPrice();
					itemView = new ImageView(staffImage);
					break;
				case SHIELD:
					BasicShield shield = new BasicShield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = shield;
					price = shield.getPrice();
					itemView = new ImageView(shieldImage);
					break;
				case ARMOUR:
					BasicArmour armour = new BasicArmour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = armour;
					price = armour.getPrice();
					itemView = new ImageView(armourImage);
					break;
				case HELMET:
					BasicHelmet helmet = new BasicHelmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = helmet;
					price = helmet.getPrice();
					itemView = new ImageView(helmetImage);
					break;
				case HEALTH_POTION:
					HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = healthPotion;
					price = healthPotion.getPrice();
					itemView = new ImageView(healthPotionImage);
				default:
					break; 
			}
			boolean playerHasEnoughGold = mainController.getWolrd().getCharacter().getGold() - price >= 0;

			if (!playerHasEnoughGold) {
				description.setText("You do not have enough gold!");
			}

			if (selectedItem != null && itemView != null && playerHasEnoughGold) {
				mainController.buyItemFromStore(selectedItem, itemView, price);
			}
		}
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

		// initialize store inventory
		for (int x=0; x < 8; x++) {
			for (int y=0; y < 2; y++) {
				ImageView emptySlotView = new ImageView(inventorySlotImage);
				storeInventory.add(emptySlotView, x, y);
			}
		}

		// add store items
		for (int x=0; x < 7; x++) {
			ImageView itemView = onLoad(store.getStoreItems().get(x));
			storeInventory.add(itemView, x, 0);
		}

		// initialize hero inventory
		for (int x=0; x < 8; x++) {
			for (int y=0; y < 2; y++) {
				ImageView emptySlotView = new ImageView(inventorySlotImage);
				heroInventory.add(emptySlotView, x, y);
			}
		}

		buyButton.setCursor(Cursor.HAND);
		sellButton.setCursor(Cursor.HAND);

		playerTotalGold.textProperty().bind(Bindings.convert(mainController.getWolrd().getCharacter().goldProperty()));
	}

	private ImageView onLoad(Item item) {
        ImageView view;

        if (item.getClass().equals(BasicHelmet.class)) {
            view = new ImageView(helmetImage);
			addClickEventHandlers(view, ITEM_TYPE.HELMET);
        } else if (item.getClass().equals(BasicShield.class)) {
            view = new ImageView(shieldImage);
			addClickEventHandlers(view, ITEM_TYPE.SHIELD);
        } else if (item.getClass().equals(BasicArmour.class)) {
            view = new ImageView(armourImage);
			addClickEventHandlers(view, ITEM_TYPE.ARMOUR);
        } else if (item.getClass().equals(Sword.class)) {
            view = new ImageView(swordImage);
			addClickEventHandlers(view, ITEM_TYPE.SWORD);
        } else if (item.getClass().equals(Stake.class)) {
            view = new ImageView(stakeImage);
			addClickEventHandlers(view, ITEM_TYPE.STAKE);
        } else if (item.getClass().equals(Staff.class)) {
            view = new ImageView(staffImage);
			addClickEventHandlers(view, ITEM_TYPE.STAFF);
        } else if (item.getClass().equals(HealthPotion.class)) {
            view = new ImageView(healthPotionImage);
			addClickEventHandlers(view, ITEM_TYPE.HEALTH_POTION);
        } else {
			view = new ImageView();
		}

		view.setCursor(Cursor.HAND);

        return view;
    }

	private void addClickEventHandlers(ImageView view, ITEM_TYPE clickType) {

		view.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				currentlySelectedItemType = clickType;
				currentlyClickedImage = view;
				
				if (view.getParent().getId().equals("storeInventory")) {
					isStoreItemClicked = true;
					sellButton.setStyle("-fx-text-fill: #7f7f7f");
					sellButton.setCursor(Cursor.DEFAULT);
					buyButton.setStyle("-fx-text-fill: #ffffff");
					buyButton.setCursor(Cursor.HAND);
				} else {
					isHeroItemClicked = true;
					sellButton.setStyle("-fx-text-fill: #000000");
					sellButton.setCursor(Cursor.HAND);
					buyButton.setStyle("-fx-text-fill: #ffffff");
					buyButton.setCursor(Cursor.DEFAULT);
				}

				switch(clickType) {
					case SWORD:
						Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						currentlySelectedItem = sword;
						description.setText(sword.getDescription());
						itemPrice.setText(Integer.toString(sword.getPrice()));
						break;
					case STAKE:
						Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						currentlySelectedItem = stake;
						description.setText(stake.getDescription());
						itemPrice.setText(Integer.toString(stake.getPrice()));
						break;
					case STAFF:
						Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						currentlySelectedItem = staff;
						description.setText(staff.getDescription());
						itemPrice.setText(Integer.toString(staff.getPrice()));
						break;
					case SHIELD:
						BasicShield shield = new BasicShield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(shield.getDescription());
						itemPrice.setText(Integer.toString(shield.getPrice()));
						break;
					case ARMOUR:
						BasicArmour armour = new BasicArmour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						currentlySelectedItem = armour;
						description.setText(armour.getDescription());
						itemPrice.setText(Integer.toString(armour.getPrice()));
						break;
					case HELMET:
						BasicHelmet helmet = new BasicHelmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						currentlySelectedItem = helmet;
						description.setText(helmet.getDescription());
						itemPrice.setText(Integer.toString(helmet.getPrice()));
						break;
					case HEALTH_POTION:
						HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						currentlySelectedItem = healthPotion;
						description.setText(healthPotion.getDescription());
						itemPrice.setText(Integer.toString(healthPotion.getPrice()));
					default:
						break; 
				}
			}
		});
   }

}
