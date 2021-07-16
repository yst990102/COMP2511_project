package unsw.loopmania.controller;

import org.javatuples.Pair;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import unsw.loopmania.model.Item;
import unsw.loopmania.model.Entity;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.weapons.Staff;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.equipments.weapons.Sword;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.rareItems.TheOneRing;
import unsw.loopmania.model.Store;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;

import java.io.File;
import java.io.IOException;
import java.lang.Integer;


enum ITEM_TYPE {
	SWORD, HELMET, SHIELD, STAKE, STAFF, ARMOUR, THE_ONE_RING, HEALTH_POTION
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
	private Image theOneRingImage;
	private Image inventorySlotImage;

	private int currentlySelectedItemPrice;
	private Entity currentlySelectedItem;
	private ITEM_TYPE currentlySelectedItemType;

	private boolean isStoreItemSelected;
	private boolean isHeroItemSelected;

	private BooleanProperty isStoreShowed;

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
		isStoreShowed = new SimpleBooleanProperty(false);

		inventorySlotImage = new Image((new File("src/assets/empty_slot.png")).toURI().toString());
		swordImage = new Image((new File("src/assets/basic_sword.png")).toURI().toString());
        helmetImage = new Image((new File("src/assets/helmet.png")).toURI().toString());
        shieldImage = new Image((new File("src/assets/shield.png")).toURI().toString());
        stakeImage = new Image((new File("src/assets/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/assets/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/assets/armour.png")).toURI().toString());
		theOneRingImage =  new Image((new File("src/assets/the_one_ring.png")).toURI().toString());
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
		if (isHeroItemSelected) {
			mainController.sellItemFromStore(currentlySelectedItem, currentlySelectedItemPrice);
			addHeroItems();
		}
	}

	@FXML
	void handleBuyButtonClick(ActionEvent event) {
		if (isStoreItemSelected) {
			int numHeroItems = mainController.getHeroItems().size();
			if (numHeroItems >= 16) {
				description.setText("Your inventory is full!\nPlease sell some items!");
				return;
			}

			Pair<Integer, Integer> firstAvailableSlot = mainController.getFirstAvailableSlotForItem();

			Item selectedItem = null;
			ImageView unequippedtemView = null;

			switch(currentlySelectedItemType) {
				case SWORD:
					Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = sword;
					unequippedtemView = new ImageView(swordImage);
					break;
				case STAKE:
					Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = stake;
					unequippedtemView = new ImageView(stakeImage);
					break;
				case STAFF:
					Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = staff;
					unequippedtemView = new ImageView(staffImage);
					break;
				case SHIELD:
					BasicShield shield = new BasicShield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = shield;
					unequippedtemView = new ImageView(shieldImage);
					break;
				case ARMOUR:
					BasicArmour armour = new BasicArmour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = armour;
					unequippedtemView = new ImageView(armourImage);
					break;
				case HELMET:
					BasicHelmet helmet = new BasicHelmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = helmet;
					unequippedtemView = new ImageView(helmetImage);
					break;
				case HEALTH_POTION:
					HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
					new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
					selectedItem = healthPotion;
					unequippedtemView = new ImageView(healthPotionImage);
				default:
					break; 
			}
			boolean playerHasEnoughGold = mainController.getWolrd().getCharacter().getGold() - currentlySelectedItemPrice >= 0;

			if (!playerHasEnoughGold) {
				description.setText("You do not have enough gold!");
			}

			if (selectedItem != null && unequippedtemView != null && playerHasEnoughGold && numHeroItems < 16) {
				mainController.buyItemFromStore(selectedItem, unequippedtemView, currentlySelectedItemPrice);
				addHeroItems();
			}
		}
	}

	@FXML
	void handleBackButtonClick(ActionEvent event) throws IOException {
		gameSwitcher.switchMenu();
		mainController.startTimer();
		mainController.updateNumStoreVisit();
		isStoreShowed.set(false);;
	}

	@FXML
	public void initialize() {

		isStoreShowed.addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue() == true)
			addHeroItems();
		});

		initStoreInventory();
		initHeroInventory();

		buyButton.setCursor(Cursor.HAND);
		sellButton.setCursor(Cursor.HAND);
		backButton.setCursor(Cursor.HAND);

		playerTotalGold.textProperty().bind(Bindings.convert(mainController.getWolrd().getCharacter().goldProperty()));
	}

	private void initStoreInventory() {
		// create empty slot
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 2; y++) {
				ImageView emptySlotView = new ImageView(inventorySlotImage);
				storeInventory.add(emptySlotView, x, y);
			}
		}

		// add store items
		int numStoreItems = store.getStoreItems().size();
		if (numStoreItems <= 16) {
			if (numStoreItems <= 8) {
				for (int x = 0; x < numStoreItems; x++) {
					ImageView itemView = onLoad(store.getStoreItems().get(x));
					storeInventory.add(itemView, x, 0);
				}
			} else {
				for (int x = 0; x < numStoreItems - 8; x++) {
					ImageView itemView = onLoad(store.getStoreItems().get(x + 8));
					storeInventory.add(itemView, x, 0);
				}
			}
		}
	}

	private void initHeroInventory() {
		// create empty slot
		heroInventory.getChildren().clear();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 2; y++) {
				ImageView emptySlotView = new ImageView(inventorySlotImage);
				heroInventory.add(emptySlotView, x, y);
			}
		}

	}

	private void addHeroItems() {
		initHeroInventory();
		int numHeroItems = mainController.getHeroItems().size();
		if (numHeroItems <= 16) {
			if (numHeroItems <= 8) {
				for (int x = 0; x < numHeroItems; x++) {
					ImageView itemView = onLoad(mainController.getHeroItems().get(x));
					heroInventory.add(itemView, x, 0);
				}
			} else {
				for (int x = 0; x < 8; x++) {
					ImageView itemView = onLoad(mainController.getHeroItems().get(x));
					heroInventory.add(itemView, x, 0);
				}
				for (int x = 0; x < numHeroItems - 8; x++) {
					ImageView itemView = onLoad(mainController.getHeroItems().get(x + 8));
					heroInventory.add(itemView, x, 1);
				}
			}
		}
	}

	

	public void setIsStoreShowed() {
		isStoreShowed.set(true);
	}

	private ImageView onLoad(Entity item) {
        ImageView view;

        if (item.getClass().equals(BasicHelmet.class)) {
            view = new ImageView(helmetImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.HELMET, item);
        } else if (item.getClass().equals(BasicShield.class)) {
            view = new ImageView(shieldImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.SHIELD, item);
        } else if (item.getClass().equals(BasicArmour.class)) {
            view = new ImageView(armourImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.ARMOUR, item);
        } else if (item.getClass().equals(Sword.class)) {
            view = new ImageView(swordImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.SWORD, item);
        } else if (item.getClass().equals(Stake.class)) {
            view = new ImageView(stakeImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.STAKE, item);
        } else if (item.getClass().equals(Staff.class)) {
            view = new ImageView(staffImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.STAFF, item);
        } else if (item.getClass().equals(HealthPotion.class)) {
            view = new ImageView(healthPotionImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.HEALTH_POTION, item);
        } else if (item.getClass().equals(TheOneRing.class)) {
			view = new ImageView(theOneRingImage);
			addClickEventHandlersForItem(view, ITEM_TYPE.THE_ONE_RING, item);
		} else {
			view = new ImageView();
		}

		view.setCursor(Cursor.HAND);

        return view;
    }

	private void addClickEventHandlersForItem(ImageView view, ITEM_TYPE clickType, Entity item) {
		view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				currentlySelectedItemType = clickType;
				currentlySelectedItem = item;
				
				if (view.getParent().getId().equals("storeInventory")) {
					isStoreItemSelected = true;
					sellButton.setStyle("-fx-text-fill: #7f7f7f");
					sellButton.setCursor(Cursor.DEFAULT);
					buyButton.setStyle("-fx-text-fill: #ffffff");
					buyButton.setCursor(Cursor.HAND);
				} else {
					isHeroItemSelected = true;
					sellButton.setStyle("-fx-text-fill: #ffffff");
					sellButton.setCursor(Cursor.HAND);
					buyButton.setStyle("-fx-text-fill: #7f7f7f");
					buyButton.setCursor(Cursor.DEFAULT);
				}

				switch(clickType) {
					case SWORD:
						Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(sword.getDescription());
						itemPrice.setText(Integer.toString(sword.getPrice()));
						currentlySelectedItemPrice = sword.getPrice();
						break;
					case STAKE:
						Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(stake.getDescription());
						itemPrice.setText(Integer.toString(stake.getPrice()));
						currentlySelectedItemPrice = stake.getPrice();
						break;
					case STAFF:
						Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(staff.getDescription());
						itemPrice.setText(Integer.toString(staff.getPrice()));
						currentlySelectedItemPrice = staff.getPrice();
						break;
					case SHIELD:
						BasicShield shield = new BasicShield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(shield.getDescription());
						itemPrice.setText(Integer.toString(shield.getPrice()));
						currentlySelectedItemPrice = shield.getPrice();
						break;
					case ARMOUR:
						BasicArmour armour = new BasicArmour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(armour.getDescription());
						itemPrice.setText(Integer.toString(armour.getPrice()));
						currentlySelectedItemPrice = armour.getPrice();
						break;
					case HELMET:
						BasicHelmet helmet = new BasicHelmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(helmet.getDescription());
						itemPrice.setText(Integer.toString(helmet.getPrice()));
						currentlySelectedItemPrice = helmet.getPrice();
						break;
					case THE_ONE_RING:
						TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(theOneRing.getDescription());
						itemPrice.setText(Integer.toString(theOneRing.getPrice()));
						currentlySelectedItemPrice = theOneRing.getPrice();
						break;
					case HEALTH_POTION:
						HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
						description.setText(healthPotion.getDescription());
						itemPrice.setText(Integer.toString(healthPotion.getPrice()));
						currentlySelectedItemPrice = healthPotion.getPrice();
					default:
						break; 
				}
			}
		});
	}
}


