package unsw.loopmania.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.scene.Cursor;
import javafx.util.Duration;
import javafx.beans.binding.Bindings;

import java.util.EnumMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import unsw.loopmania.model.Character;
import unsw.loopmania.model.LoopManiaWorld;
import unsw.loopmania.model.PathPosition;
import unsw.loopmania.model.Potion;
import unsw.loopmania.model.RareItem;
import unsw.loopmania.model.Shield;
import unsw.loopmania.model.Weapon;
import unsw.loopmania.model.Entity;
import unsw.loopmania.model.DragIcon;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Armour;
import unsw.loopmania.model.Building;
import unsw.loopmania.model.Card;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Helmet;
import unsw.loopmania.model.Item;
import unsw.loopmania.model.buildings.VampireCastleBuilding;
import unsw.loopmania.model.buildings.ZombiePitBuilding;
import unsw.loopmania.model.buildings.TowerBuilding;
import unsw.loopmania.model.buildings.VillageBuilding;
import unsw.loopmania.model.buildings.BarracksBuilding;
import unsw.loopmania.model.buildings.TrapBuilding;
import unsw.loopmania.model.buildings.CampfireBuilding;
import unsw.loopmania.model.cards.VampireCastleCard;
import unsw.loopmania.model.cards.ZombiePitCard;
import unsw.loopmania.model.coins.DoggieCoin;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.rareItems.Anduril;
import unsw.loopmania.model.rareItems.TheOneRing;
import unsw.loopmania.model.rareItems.TreeStump;
import unsw.loopmania.model.cards.TowerCard;
import unsw.loopmania.model.cards.VillageCard;
import unsw.loopmania.model.cards.BarracksCard;
import unsw.loopmania.model.cards.TrapCard;
import unsw.loopmania.model.cards.CampfireCard;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.weapons.Staff;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.equipments.weapons.Sword;
import unsw.loopmania.model.friendlyforces.Soldier;
import unsw.loopmania.model.enemies.Boss;
import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;
import unsw.loopmania.model.enemies.boss.Doggie;
import unsw.loopmania.model.enemies.boss.ElanMuske;
import unsw.loopmania.strategy.ModeStrategy;
import unsw.loopmania.controller.LoopManiaWorldLoader.MAP_TYPE;

/**
 * the draggable types. If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE {
    CARD, ITEM, VAMPIRE_CASTLE_CARD, ZOMBIE_PIT_CARD, TOWER_CARD, VILLAGE_CARD, BARRACKS_CARD, TRAP_CARD, CAMPFIRE_CARD,
    SWORD, STAKE, STAFF, ARMOUR, SHIELD, HELMET, GOLD, HEALTH_POTION, THE_ONE_RING, ANDURIL, TREESTUMP, DOGGIECOIN
}

enum CLICKABLE_TYPE {
    HEALTH_POTION
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application
 * thread:
 * https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 * Note in
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html
 * under heading "Threading", it specifies animation timelines are run in the
 * application thread. This means that the starter code does not need locks
 * (mutexes) for resources shared between the timeline KeyFrame, and all of the
 * event handlers (including between different event handlers). This will make
 * the game easier for you to implement. However, if you add time-consuming
 * processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend: using Task
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by
 * itself or within a Service
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 * Tasks ensure that any changes to public properties, change notifications for
 * errors or cancellation, event handlers, and states occur on the JavaFX
 * Application thread, so is a better alternative to using a basic Java Thread:
 * https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm The Service class
 * is used for executing/reusing tasks. You can run tasks without Service,
 * however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need
 * to implement locks on resources shared with the application thread (i.e.
 * Timeline KeyFrame and drag Event handlers). You can check whether code is
 * running on the JavaFX application thread by running the helper method
 * printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and
 * https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using
 * Platform.runLater
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 * This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass,
     * buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private ImageView weaponequiped;

    @FXML
    private ImageView helmetequiped;

    @FXML
    private ImageView shieldequiped;

    @FXML
    private ImageView armourequiped;

    @FXML
    private Button pauseButton;

    @FXML
    private Tooltip pauseButtondescription;

    @FXML
    private Button exitButton;

    @FXML
    private Tooltip exitButtondescription;

    @FXML
    private Text goal;

    @FXML
    private Text hp;

    @FXML
    private Text gold;

    @FXML
    private Text xp;

    @FXML
    private Text soldier;

    @FXML
    private Tooltip characterdescription;

    @FXML
    private Text description;

    @FXML
    private ImageView goalIcon;

    @FXML
    private Button resetbutton;

    // all image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;
    private List<ImageView> initialEntities;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here
     * and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;
    private StoreController storeController;

    /**
     * runs the periodic game logic - second-by-second moving of character through
     * maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    // Character
    private Image characterImage;

    // Card Images
    private Image heroCastleImage;
    private Image vampireCastleCardImage;
    private Image zombiePitCardImage;
    private Image towerCardImage;
    private Image villageCardImage;
    private Image barracksCardImage;
    private Image trapCardImage;
    private Image campfireCardImage;

    // Enemy Images
    private Image slugEnemyImage;
    private Image zombieEnemyImage;
    private Image vampireEnemyImage;
    private Image doggieEnemyImage;
    private Image elanMuskeEnemyImage;
    private Image christmasSlugEnemyImage;
    private Image christmasZombieEnemyImage;
    private Image christmasVampireEnemyImage;
    private Image christmasDoggieEnemyImage;

    // Equipment Images
    private Image swordImage;
    private Image helmetImage;
    private Image shieldImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;

    // Potions
    private Image healthPotionImage;

    // RareItems
    private Image theOneRingImage;
    private Image andurilImage;
    private Image treeStumpImage;

    // Building Images
    private Image vampireCastleBuildingImage;
    private Image zombiePitBuildingImage;
    private Image towerBuildingImage;
    private Image villageBuildingImage;
    private Image barracksBuildingImage;
    private Image trapBuildingImage;
    private Image campfireBuildingImage;

    // coins
    private Image doggieCoinImage;

    private Image equippedSlotBackground;

    // Tile Image
    private Image forestTileImage;
    private Image iceTileImage;
    private Image desertTileImage;

    /**
     * the image currently being dragged, if there is one, otherwise null. Holding
     * the ImageView being dragged allows us to spawn it again in the drop location
     * if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the
    // building/item which should be dropped
    private ImageView currentlyDraggedImage;

    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged into the boundaries of its appropriate
     * gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged outside of the boundaries of its
     * appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    private MenuSwitcher storeSwitcher;

    private double gameSpeed;

    private JSONObject jsonfile;
    private MAP_TYPE mapType;

    /**
     * @param world           world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be
     *                        loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        gameSpeed = 0.3;
        this.entityImages = new ArrayList<>(initialEntities);
        this.initialEntities = initialEntities;

        // Character
        characterImage = new Image((new File("src/assets/human_new.png")).toURI().toString());

        // Cards
        vampireCastleCardImage = new Image((new File("src/assets/vampire_castle_card.png")).toURI().toString());
        zombiePitCardImage = new Image((new File("src/assets/zombie_pit_card.png")).toURI().toString());
        towerCardImage = new Image((new File("src/assets/tower_card.png")).toURI().toString());
        villageCardImage = new Image((new File("src/assets/village_card.png")).toURI().toString());
        barracksCardImage = new Image((new File("src/assets/barracks_card.png")).toURI().toString());
        trapCardImage = new Image((new File("src/assets/trap_card.png")).toURI().toString());
        campfireCardImage = new Image((new File("src/assets/campfire_card.png")).toURI().toString());

        // Enemies
        slugEnemyImage = new Image((new File("src/assets/slug.png")).toURI().toString());
        zombieEnemyImage = new Image((new File("src/assets/zombie.png")).toURI().toString());
        vampireEnemyImage = new Image((new File("src/assets/vampire.png")).toURI().toString());
        doggieEnemyImage = new Image((new File("src/assets/doggie.png")).toURI().toString());
        elanMuskeEnemyImage = new Image((new File("src/assets/ElanMuske.png")).toURI().toString());
        christmasSlugEnemyImage = new Image((new File("src/assets/christmas_slug.png")).toURI().toString());
        christmasZombieEnemyImage = new Image((new File("src/assets/christmas_zombie.png")).toURI().toString());
        christmasVampireEnemyImage = new Image((new File("src/assets/christmas_vampire.png")).toURI().toString());
        christmasDoggieEnemyImage = new Image((new File("src/assets/christmas_doggie.png")).toURI().toString());

        // Item - Equipments
        swordImage = new Image((new File("src/assets/basic_sword.png")).toURI().toString());
        helmetImage = new Image((new File("src/assets/helmet.png")).toURI().toString());
        shieldImage = new Image((new File("src/assets/shield.png")).toURI().toString());
        stakeImage = new Image((new File("src/assets/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/assets/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/assets/armour.png")).toURI().toString());

        // Potions
        healthPotionImage = new Image((new File("src/assets/health_potion.png")).toURI().toString());

        // RareItems
        theOneRingImage = new Image((new File("src/assets/the_one_ring.png")).toURI().toString());
        andurilImage = new Image((new File("src/assets/anduril_flame_of_the_west.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/assets/tree_stump.png")).toURI().toString());

        // Buildings
        heroCastleImage = new Image((new File("src/assets/hero_castle.png")).toURI().toString());
        vampireCastleBuildingImage = new Image((new File("src/assets/vampire_castle.png")).toURI().toString());
        zombiePitBuildingImage = new Image((new File("src/assets/zombie_pit.png")).toURI().toString());
        towerBuildingImage = new Image((new File("src/assets/tower.png")).toURI().toString());
        villageBuildingImage = new Image((new File("src/assets/village.png")).toURI().toString());
        barracksBuildingImage = new Image((new File("src/assets/barracks.png")).toURI().toString());
        trapBuildingImage = new Image((new File("src/assets/trap.png")).toURI().toString());
        campfireBuildingImage = new Image((new File("src/assets/campfire.png")).toURI().toString());

        // Coins
        doggieCoinImage = new Image((new File("src/assets/doggiecoin.png")).toURI().toString());

        equippedSlotBackground = new Image((new File("src/assets/silver_background.png")).toURI().toString());

        // Tiles
        forestTileImage = new Image((new File("src/assets/32x32GrassAndDirtPath.png")).toURI().toString());
        iceTileImage = new Image((new File("src/assets/32x32SnowAndIcePath.png")).toURI().toString());
        desertTileImage = new Image((new File("src/assets/32x32SandAndStonePath.png")).toURI().toString());

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    void resetworlddata() throws FileNotFoundException, JSONException {

        this.getWolrd().ResetWorldData(this.world.getWidth(), this.world.getHeight(), this.world.getOrderedPath(),
                this.world.getGoalObject());

        this.entityImages = new ArrayList<>(initialEntities);

        onLoad(this.world.getCharacter());

        this.initialize();

        equippedItems.getChildren().clear();
        // squares.getChildren().clear();

        initialiseEquippedItemsPane();

        // loadWholeMapByJson();
        // loadPath(this.jsonfile.getJSONObject("path"), mapType);

        System.out.println(entityImages);

        // for (ImageView iv : entityImages) {
        //     System.out.println(iv.getImage().getUrl());

        // }

    }

    public void loadWholeMapByJson() throws FileNotFoundException, JSONException {
        loadPath(this.jsonfile.getJSONObject("path"), mapType);
        loadInitialEntities(this.jsonfile.getJSONArray("BuildingEntities"), mapType);
        if (this.jsonfile.has("character_info")) {
            loadCharacter(this.jsonfile.getJSONObject("character_info"));
        } else {
            bornnewcharacter();
        }

        if (this.jsonfile.has("cards")) {
            loadCards(this.jsonfile.getJSONArray("cards"));
        }

        if (this.jsonfile.has("rareitems")) {
            loadRareItems(this.jsonfile.getJSONArray("rareitems"));
        }

        if (this.jsonfile.has("unequippedequipments")) {
            loadUnequippedEquipment(this.jsonfile.getJSONArray("unequippedequipments"));
        }

        if (this.jsonfile.has("potions")) {
            loadPotions(this.jsonfile.getJSONArray("potions"));
        }

        if (this.jsonfile.has("enemies")) {
            loadEnemies(this.jsonfile.getJSONArray("enemies"));
        }
    }

    public void initialiseEquippedItemsPane() {
        ImageView helmetslot = new ImageView(new Image(new File("src/assets/helmet_slot.png").toURI().toString()));
        ImageView armourslot = new ImageView(new Image(new File("src/assets/armour_slot.png").toURI().toString()));
        ImageView shieldslot = new ImageView(
                new Image(new File("src/assets/shield_unequipped.png").toURI().toString()));
        ImageView weaponslot = new ImageView(new Image(new File("src/assets/sword_unequipped.png").toURI().toString()));

        for (int i = 0; i < equippedItems.getRowCount(); i++) {
            for (int j = 0; j < equippedItems.getColumnCount(); j++) {
                if (i == 0 && j == 1) {
                    equippedItems.add(helmetslot, j, i);
                }

                if (i == 1 && j == 0) {
                    equippedItems.add(weaponslot, j, i);
                }

                if (i == 1 && j == 1) {
                    equippedItems.add(armourslot, j, i);
                }

                if (i == 1 && j == 2) {
                    equippedItems.add(shieldslot, j, i);
                }

            }
        }
    }

    @FXML
    public void initialize() {
        // TODO = load more images/entities during initialization

        Image inventorySlotImage = new Image((new File("src/assets/empty_slot.png")).toURI().toString());
        Image cardSlotImage = new Image((new File("src/assets/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // add the ground underneath the cards
        for (int x = 0; x < world.getWidth(); x++) {
            ImageView groundView = new ImageView(cardSlotImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        // set goal
        goal.textProperty().bind(Bindings.convert(world.getGoalProperty()));

        // bind world description to frontend property
        description.textProperty().bind(Bindings.convert(world.descriptionProperty()));

        // set tip of button
        pauseButtondescription.setText("Game running. Click to pause.");
        exitButtondescription.setText("Click to exit.");
    }

    public void updateCharacterDescription() {
        Character currentPlayer = world.getCharacter();

        String characterProperty = "HP : " + currentPlayer.getHP() + "\n" + "Gold : " + currentPlayer.getGold() + "\n"
                + "XP : " + currentPlayer.getXP() + "\n" + "numSoldier : " + currentPlayer.getSoldiers().size() + "\n"
                + "Soldiers : " + currentPlayer.getSoldiers() + "\n" + "\n" + "attack : " + currentPlayer.getATK()
                + "\n" + "defence : " + currentPlayer.getDEF() + "\n\n" + "Weapon : "
                + ((currentPlayer.getDressedWeapon() == null) ? " no weapon "
                        : currentPlayer.getDressedWeapon().getClass().getSimpleName())
                + "\n" + "Armour : "
                + ((currentPlayer.getDressedArmour() == null) ? " no armour "
                        : currentPlayer.getDressedArmour().getClass().getSimpleName())
                + "\n" + "Shield : "
                + ((currentPlayer.getDressedShield() == null) ? " no shield "
                        : currentPlayer.getDressedShield().getClass().getSimpleName())
                + "\n" + "Helmet : " + ((currentPlayer.getDressedHelmet() == null) ? " no helmet "
                        : currentPlayer.getDressedHelmet().getClass().getSimpleName())
                // + "\n" + "Bag : " + world.getCharacter().getBag()
                + "\n" + "IsGoalFinished : " + world.getIsGoalFinished();

        characterdescription.setText(characterProperty);
    }

    /**
     * create and run the timer
     */
    public void startTimer() {
        // TODO = handle more aspects of the behaviour required by the specification
        System.out.println("starting timer");
        isPaused = false;
        pauseButton.setText("Pause");
        pauseButtondescription.setText("Game running. Click to pause.");
        // trigger adding code to process main game logic to queue. JavaFX will target
        // framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(gameSpeed), event -> {
            world.runTickMoves();
            world.updateNthCycle();
            updateCharacterInfo();
            updateCharacterDescription();
            world.updateIsGoalFinished();
            checkStoreVisit();
            checkGoalComplete();
            checkHeroAlive();

            List<Enemy> defeatedEnemies = world.runBattles();

            for (Enemy e : defeatedEnemies) {
                reactToEnemyDefeat(e);
            }

            List<Slug> newEnemies = world.checkSlugSpawn();
            for (Slug newEnemy : newEnemies) {
                onLoad(newEnemy);
            }

            List<Vampire> vampires = world.checkVampireSpawn();
            for (Vampire vampire : vampires) {
                onLoad(vampire);
            }

            List<Zombie> zombies = world.checkZombieSpawn();
            for (Zombie zombie : zombies) {
                onLoad(zombie);
            }

            ElanMuske elanMuske = world.checkMuskeSpawn();
            if (elanMuske != null) {
                onLoad(elanMuske);
            }

            Doggie doggie = world.checkDoggieSpawn();
            if (doggie != null) {
                onLoad(doggie);
            }

            world.checkHeroPassVillage();
            world.checkHeroPassBarracks();
            world.checkEnemyPassTrap();
            world.checkEnemyInTowerRadius();
            world.checkHeroInCampfireRadius();

            // printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateCharacterInfo() {
        hp.textProperty().bind(Bindings.convert(world.getCharacter().hpPercentageProperty()));
        gold.textProperty().bind(Bindings.convert(world.getCharacter().goldProperty()));
        xp.textProperty().bind(Bindings.convert(world.getCharacter().xpProperty()));
        soldier.textProperty()
                .bind(Bindings.convert(new SimpleIntegerProperty(world.getCharacter().getSoldiers().size())));
    }

    /**
     * pause the execution of the game loop the human player can still drag and drop
     * items during the game pause
     */
    public void pause() {
        isPaused = true;
        pauseButton.setText("Continue");
        pauseButtondescription.setText("Game paused. Click to continue.");
        timeline.stop();
    }

    public void terminate() {
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * 
     * @param entity backend entity to be paired with view
     * @param view   frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a dropped card from the world, and pair it with an image in the GUI
     */
    private void loadDroppedCard() {
        Card card = world.loadCard();
        if (card != null) {
            onLoad(card);
        }
    }

    /**
     * load a sword from the world, and pair it with an image in the GUI
     */
    private void loadDroppedEquipments(Enemy enemy) {
        // start by getting first available coordinates

        // [0,3] equipment, [0,1] health potion, 5% chance of obtaining a rare item
        int droppedEquipment_amount = new Random().nextInt(4);
        int droppedPotion_amount = new Random().nextInt(2);
        int IfRareItemDropped = new Random().nextInt(20);

        while (droppedEquipment_amount > 0) {
            Equipment droppedEquipment = world.addUnequippedEquipment();
            onLoad(droppedEquipment);
            droppedEquipment_amount--;
        }

        while (droppedPotion_amount > 0) {
            Potion droppedPoition = world.addUnusedPotion();
            onLoad(droppedPoition);
            droppedPotion_amount--;
        }

        if (IfRareItemDropped == 0) {
            RareItem droppedRareItem = world.addRareItem();
            onLoad(droppedRareItem);
        }

        if (enemy instanceof Doggie) {
            int droppedDoggieCoin_amount = new Random().nextInt(2);
            while (droppedDoggieCoin_amount > 0) {
                DoggieCoin droppedDoggieCoin = world.addUnsoldCoins();
                onLoad(droppedDoggieCoin);
                droppedDoggieCoin_amount--;
            }
        }
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning
     * items/experience/gold
     * 
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Enemy enemy) {
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the enemy based on the type of
        // enemy
        loadDroppedEquipments(enemy);
        loadDroppedCard();
    }

    private void onLoad(Card card) {
        ImageView view;

        if (card.getClass().equals(VampireCastleCard.class)) {
            view = new ImageView(vampireCastleCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.VAMPIRE_CASTLE_CARD, cards, squares);
        } else if (card.getClass().equals(ZombiePitCard.class)) {
            view = new ImageView(zombiePitCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.ZOMBIE_PIT_CARD, cards, squares);
        } else if (card.getClass().equals(TowerCard.class)) {
            view = new ImageView(towerCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.TOWER_CARD, cards, squares);
        } else if (card.getClass().equals(VillageCard.class)) {
            view = new ImageView(villageCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.VILLAGE_CARD, cards, squares);
        } else if (card.getClass().equals(BarracksCard.class)) {
            view = new ImageView(barracksCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.BARRACKS_CARD, cards, squares);
        } else if (card.getClass().equals(TrapCard.class)) {
            view = new ImageView(trapCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.TRAP_CARD, cards, squares);
        } else if (card.getClass().equals(CampfireCard.class)) {
            view = new ImageView(campfireCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.CAMPFIRE_CARD, cards, squares);
        } else {
            view = new ImageView();
        }

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    private void onLoad(Building building) {
        ImageView view;

        if (building.getClass().equals(VampireCastleBuilding.class)) {
            view = new ImageView(vampireCastleBuildingImage);
        } else if (building.getClass().equals(ZombiePitBuilding.class)) {
            view = new ImageView(zombiePitBuildingImage);
        } else if (building.getClass().equals(TowerBuilding.class)) {
            view = new ImageView(towerBuildingImage);
        } else if (building.getClass().equals(VillageBuilding.class)) {
            view = new ImageView(villageBuildingImage);
        } else if (building.getClass().equals(BarracksBuilding.class)) {
            view = new ImageView(barracksBuildingImage);
        } else if (building.getClass().equals(TrapBuilding.class)) {
            view = new ImageView(trapBuildingImage);
        } else if (building.getClass().equals(CampfireBuilding.class)) {
            view = new ImageView(campfireBuildingImage);
        } else {
            view = new ImageView();
        }

        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load an equipment into the GUI. Particularly, we must connect to the drag
     * detection event handler, and load the image into the unequippedInventory
     * GridPane.
     * 
     * @param equipment
     */
    private void onLoad(Equipment equipment) {
        ImageView view;

        if (equipment.getClass().equals(BasicHelmet.class)) {
            view = new ImageView(helmetImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.HELMET, unequippedInventory, equippedItems);
        } else if (equipment.getClass().equals(BasicShield.class)) {
            view = new ImageView(shieldImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
        } else if (equipment.getClass().equals(BasicArmour.class)) {
            view = new ImageView(armourImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.ARMOUR, unequippedInventory, equippedItems);
        } else if (equipment.getClass().equals(Sword.class)) {
            view = new ImageView(swordImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.SWORD, unequippedInventory, equippedItems);
        } else if (equipment.getClass().equals(Stake.class)) {
            view = new ImageView(stakeImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.STAKE, unequippedInventory, equippedItems);
        } else if (equipment.getClass().equals(Staff.class)) {
            view = new ImageView(staffImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.STAFF, unequippedInventory, equippedItems);
        } else {
            view = new ImageView();
        }

        addEntity(equipment, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Character character) {
        ImageView view = new ImageView(characterImage);
        addEntity(character, view);
        squares.getChildren().add(view);
    }

    /**
     * load an Item into the GUI.
     * 
     * @param item
     */
    private void onLoad(Item item) {
        ImageView view;

        if (item.getClass().equals(HealthPotion.class)) {
            view = new ImageView(healthPotionImage);
            view.setCursor(Cursor.HAND);
            addClickEventHandlers(view, CLICKABLE_TYPE.HEALTH_POTION);
        } else if (item.getClass().equals(TheOneRing.class)) {
            view = new ImageView(theOneRingImage);
        } else if (item.getClass().equals(Anduril.class)) {
            view = new ImageView(andurilImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.ANDURIL, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(TreeStump.class)) {
            view = new ImageView(treeStumpImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.TREESTUMP, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(DoggieCoin.class)) {
            view = new ImageView(doggieCoinImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.DOGGIECOIN, unequippedInventory, equippedItems);
        } else {
            view = new ImageView();
        }

        addEntity(item, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * 
     * @param enemy
     */
    private void onLoad(Enemy enemy) {
        ImageView view;

        if (enemy instanceof Slug) {
            if (mapType == MAP_TYPE.ICEWORLD) {
                view = new ImageView(christmasSlugEnemyImage);
            } else {
                view = new ImageView(slugEnemyImage);
            }
        } else if (enemy instanceof Vampire) {
            if (mapType == MAP_TYPE.ICEWORLD) {
                view = new ImageView(christmasVampireEnemyImage);
            } else {
                view = new ImageView(vampireEnemyImage);
            }
        } else if (enemy instanceof Zombie) {
            if (mapType == MAP_TYPE.ICEWORLD) {
                view = new ImageView(christmasZombieEnemyImage);
            } else {
                view = new ImageView(zombieEnemyImage);
            }
        } else if (enemy instanceof Doggie) {
            if (mapType == MAP_TYPE.ICEWORLD) {
                view = new ImageView(christmasDoggieEnemyImage);
            } else {
                view = new ImageView(doggieEnemyImage);
            }
        } else if (enemy instanceof ElanMuske) {
            view = new ImageView(elanMuskeEnemyImage);
        } else {
            return;
        }

        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the
     * background, dropping over the background. These are not attached to invidual
     * items such as swords/cards.
     * 
     * @param draggableType  the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to
     *                       (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane, ImageView view) {
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path,
        // whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped,
                // consider applying additional if-statement logic
                /*
                 * you might want to design the application so dropping at an invalid location
                 * drops at the most recent valid location hovered over, or simply allow the
                 * card/item to return to its slot (the latter is easier, as you won't have to
                 * store the last valid drop location!)
                 */

                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        Pair<Integer, Integer> targetPos = new Pair<>(Integer.valueOf(x), Integer.valueOf(y));
                        boolean targetPosInPath = world.getOrderedPath().indexOf(targetPos) != -1 ? true : false;
                        boolean targetPosInHelmetSlot = x == 1 && y == 0 ? true : false;
                        boolean targetPosInWeaponSlot = x == 0 && y == 1 ? true : false;
                        boolean targetPosInArmourSlot = x == 1 && y == 1 ? true : false;
                        boolean targetPosInShieldSlot = x == 2 && y == 1 ? true : false;
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());
                        ImageView silverBackground = new ImageView(equippedSlotBackground);

                        node.setOpacity(1);

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        Building newBuilding;

                        switch (draggableType) {
                        case VAMPIRE_CASTLE_CARD:
                            if (!targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "VAMPIRE_CASTLE");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case ZOMBIE_PIT_CARD:
                            if (!targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "ZOMBIE_PIT");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case TOWER_CARD:
                            if (!targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "TOWER");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case VILLAGE_CARD:
                            if (targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "VILLAGE");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case BARRACKS_CARD:
                            if (targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "BARRACKS");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case TRAP_CARD:
                            if (targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "TRAP");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case CAMPFIRE_CARD:
                            if (!targetPosInPath) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y, "CAMPFIRE");
                                onLoad(newBuilding);
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case SWORD:
                            if (targetPosInWeaponSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedWeapon(sword);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case STAKE:
                            if (targetPosInWeaponSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedWeapon(stake);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case STAFF:
                            if (targetPosInWeaponSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedWeapon(staff);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case ARMOUR:
                            if (targetPosInArmourSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Armour armour = new BasicArmour(new SimpleIntegerProperty(0),
                                        new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedArmour(armour);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case SHIELD:
                            if (targetPosInShieldSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Shield shield = new BasicShield(new SimpleIntegerProperty(0),
                                        new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedShield(shield);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        case HELMET:
                            if (targetPosInHelmetSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Helmet helmet = new BasicHelmet(new SimpleIntegerProperty(0),
                                        new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedHelmet(helmet);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;

                        case ANDURIL:
                            if (targetPosInWeaponSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                Anduril anduril = new Anduril(new SimpleIntegerProperty(0),
                                        new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedWeapon(anduril);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;

                        case TREESTUMP:
                            if (targetPosInShieldSlot) {
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(silverBackground, x, y, 1, 1);
                                targetGridPane.add(image, x, y, 1, 1);
                                TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0),
                                        new SimpleIntegerProperty(0));
                                world.getCharacter().setDressedShield(treeStump);
                                updateCharacterDescription();
                            } else {
                                view.setVisible(true);
                            }
                            break;
                        default:
                            break;
                        }

                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;

                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a
                // sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read
                // https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for
        // dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>() {
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null) {
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for
        // dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != anchorPaneRoot && db.hasImage()) {
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                // let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where
     * the card was dropped
     * 
     * @param cardNodeX     the x coordinate of the card which was dragged, from 0
     *                      to width-1
     * @param cardNodeY     the y coordinate of the card which was dragged (in
     *                      starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY, String buildingType) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY,
                buildingType);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in
     * the unequipped inventory gridpane
     * 
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * 
     * @param view           the view to attach drag event handlers to
     * @param draggableType  the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be
     *                       dragged
     * @param targetGridPane the relevant gridpane to which the entity would be
     *                       dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can
                                              // detect it...
                currentlyDraggedType = draggableType;
                // Drag was detected, start drap-and-drop gesture
                // Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

                // Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane, view);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType) {
                case CARD:
                    draggedEntity.setImage(vampireCastleCardImage);
                    break;
                case ITEM:
                    draggedEntity.setImage(swordImage);
                    break;
                case VAMPIRE_CASTLE_CARD:
                    draggedEntity.setImage(vampireCastleCardImage);
                    break;
                case ZOMBIE_PIT_CARD:
                    draggedEntity.setImage(zombiePitCardImage);
                    break;
                case TOWER_CARD:
                    draggedEntity.setImage(towerCardImage);
                    break;
                case VILLAGE_CARD:
                    draggedEntity.setImage(villageCardImage);
                    break;
                case BARRACKS_CARD:
                    draggedEntity.setImage(barracksCardImage);
                    break;
                case TRAP_CARD:
                    draggedEntity.setImage(trapCardImage);
                    break;
                case CAMPFIRE_CARD:
                    draggedEntity.setImage(campfireCardImage);
                    break;
                case SWORD:
                    draggedEntity.setImage(swordImage);
                    break;
                case STAKE:
                    draggedEntity.setImage(stakeImage);
                    break;
                case STAFF:
                    draggedEntity.setImage(staffImage);
                    break;
                case ARMOUR:
                    draggedEntity.setImage(armourImage);
                    break;
                case SHIELD:
                    draggedEntity.setImage(shieldImage);
                    break;
                case HELMET:
                    draggedEntity.setImage(helmetImage);
                    break;
                default:
                    break;
                }

                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED,
                        anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n : targetGridPane.getChildren()) {
                    // events for entering and exiting are attached to squares children because that
                    // impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be
                        // dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                Integer cIndex = GridPane.getColumnIndex(n);
                                Integer rIndex = GridPane.getRowIndex(n);
                                int targetX = cIndex == null ? 0 : cIndex;
                                int targetY = rIndex == null ? 0 : rIndex;
                                Pair<Integer, Integer> targetPos = new Pair<>(Integer.valueOf(targetX),
                                        Integer.valueOf(targetY));
                                boolean targetPosInPath = world.getOrderedPath().indexOf(targetPos) != -1 ? true
                                        : false;
                                boolean targetPosInHelmetSlot = targetX == 1 && targetY == 0 ? true : false;
                                boolean targetPosInSwordSlot = targetX == 0 && targetY == 1 ? true : false;
                                boolean targetPosInArmourSlot = targetX == 1 && targetY == 1 ? true : false;
                                boolean targetPosInShieldSlot = targetX == 2 && targetY == 1 ? true : false;

                                if (event.getGestureSource() != n && event.getDragboard().hasImage()) {
                                    switch (draggableType) {
                                    case VAMPIRE_CASTLE_CARD:
                                        if (!targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case ZOMBIE_PIT_CARD:
                                        if (!targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case TOWER_CARD:
                                        if (!targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case VILLAGE_CARD:
                                        if (targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case BARRACKS_CARD:
                                        if (targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case TRAP_CARD:
                                        if (targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case CAMPFIRE_CARD:
                                        if (!targetPosInPath) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case SWORD:
                                        if (targetPosInSwordSlot) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case STAKE:
                                        if (targetPosInSwordSlot) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case STAFF:
                                        if (targetPosInSwordSlot) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case ARMOUR:
                                        if (targetPosInArmourSlot) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case SHIELD:
                                        if (targetPosInShieldSlot) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    case HELMET:
                                        if (targetPosInHelmetSlot) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    default:
                                        break;
                                    }
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you
                        // could program the game so if the new highlight location is invalid the
                        // highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                n.setOpacity(1);
                            }

                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }

        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events this is
     * particularly important for slower machines such as over VLAB.
     * 
     * @param draggableType  either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane) {
        // remove event handlers from nodes in children squares, from anchorPaneRoot,
        // and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n : targetGridPane.getChildren()) {
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    private void addClickEventHandlers(ImageView view, CLICKABLE_TYPE clickType) {

        view.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                int nodeX = GridPane.getColumnIndex(view);
                int nodeY = GridPane.getRowIndex(view);
                switch (clickType) {
                case HEALTH_POTION:
                    world.getCharacter().useHealthPotion();
                    removeItemByCoordinates(nodeX, nodeY);
                    updateCharacterDescription();
                    break;
                default:
                    break;
                }
            }
        });
    }

    /**
     * handle the pressing of keyboard keys. Specifically, we should pause when
     * pressing SPACE
     * 
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case SPACE:
            if (isPaused) {
                startTimer();
            } else {
                pause();
            }
            break;
        case LEFT:
            try {
                LoopManiaWorldControllerLoader loader = new LoopManiaWorldControllerLoader("world_1.json");
                world = loader.load();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        default:
            break;
        }
    }

    @FXML
    public void handlePauseButtonClick() {
        if (isPaused) {
            startTimer();
        } else {
            pause();
        }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * 
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    public void setStoreSwitcher(MenuSwitcher storeSwitcher) {
        this.storeSwitcher = storeSwitcher;
    }

    public void checkStoreVisit() {
        if (world.canVisitStore()) {
            pause();
            storeController.setIsStoreShowed();
            storeSwitcher.switchMenu();
        }
    }

    public void updateNumStoreVisit() {
        world.updateNumStoreVisit();
    }

    public void checkGoalComplete() {
        if (world.getIsGoalFinished()) {
            goalIcon.setImage(new Image((new File("src/assets/tick.png")).toURI().toString()));
            pause();
            world.setDescription("You win!!!");
        }
    }

    public void checkHeroAlive() {
        if (!world.canHeroRevive()) {
            pause();
            pauseButton.setVisible(false);
            exitButton.setText("Quit Game");
            exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    System.exit(0);
                }
            });
            world.setDescription("You die!!!");
        }
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we
     * need to track positions of spawned entities such as enemy or items which
     * might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So
     * it is vital this is handled in this Controller class
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from
        // equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                .onAttach((o, l) -> o.addListener(xListener)).onDetach((o, l) -> {
                    o.removeListener(xListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                .onAttach((o, l) -> o.addListener(yListener)).onDetach((o, l) -> {
                    o.removeListener(yListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here,
        // position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    public Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        return world.getFirstAvailableSlotForItem();
    }

    public List<Item> getHeroItems() {
        return world.getHeroItems();
    }

    public void buyItemFromStore(Item item, ImageView view, int price) {
        if (item.getClass().equals(BasicHelmet.class)) {
            addDragEventHandlers(view, DRAGGABLE_TYPE.HELMET, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(BasicShield.class)) {
            addDragEventHandlers(view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(BasicArmour.class)) {
            addDragEventHandlers(view, DRAGGABLE_TYPE.ARMOUR, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(Sword.class)) {
            addDragEventHandlers(view, DRAGGABLE_TYPE.SWORD, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(Stake.class)) {
            addDragEventHandlers(view, DRAGGABLE_TYPE.STAKE, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(Staff.class)) {
            addDragEventHandlers(view, DRAGGABLE_TYPE.STAFF, unequippedInventory, equippedItems);
        } else if (item.getClass().equals(HealthPotion.class)) {
            addClickEventHandlers(view, CLICKABLE_TYPE.HEALTH_POTION);
            view.setCursor(Cursor.HAND);
        }
        addEntity(item, view);
        unequippedInventory.getChildren().add(view);
        world.addItemFromStore(item);
        world.getCharacter().setGold(world.getCharacter().getGold() - price);
    }

    public void sellItemFromStore(Entity item, int price) {
        world.removeUnequippedInventoryItem(item);
        world.getCharacter().setGold(world.getCharacter().getGold() + price);
    }

    public LoopManiaWorld getWolrd() {
        return world;
    }

    public void setStoreController(StoreController controller) {
        this.storeController = controller;
    }

    public void setModeStrategy(ModeStrategy strategy) {
        world.setModeStrategy(strategy);
    }

    public ModeStrategy getModeStrategy() {
        return world.getModeStrategy();
    }

    public void increaseGameSpeed() {
        if (gameSpeed - 0.03 > 0.028) {
            gameSpeed -= 0.03;
        }
    }

    public void decreaseGameSpeed() {
        if (gameSpeed + 0.03 < 0.31) {
            gameSpeed += 0.03;
        }
    }

    public void loadPath(JSONObject path, MAP_TYPE type) throws FileNotFoundException {
        LoopManiaWorldControllerLoader loader;

        switch (type) {
        case FOREST:
            loader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
            break;
        case ICEWORLD:
            loader = new LoopManiaWorldControllerLoader("iceworld.json");
            break;
        case DESERT:
            loader = new LoopManiaWorldControllerLoader("desert.json");
            break;
        default:
            // set forest as default selection
            loader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
            break;
        }

        world.setOrderedPath(loader.loadPathTiles(path, world.getWidth(), world.getHeight(), type, entityImages));

        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        Image pathTilesImage = null;

        // check map type
        switch (type) {
        case FOREST:
            pathTilesImage = forestTileImage;
            break;
        case ICEWORLD:
            pathTilesImage = iceTileImage;
            break;
        case DESERT:
            pathTilesImage = desertTileImage;
            break;
        default:
            break;
        }

        // load ground image
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }
    }

    public void loadPotions(JSONArray potions) {
        for (int i = 0; i < potions.length(); i++) {
            JSONObject potion = (JSONObject) (potions.get(i));

            String type = potion.getString("type");
            int x = potion.getInt("x");
            int y = potion.getInt("y");

            Potion newpotion = switchPotionTypeToPotion(type, x, y);
            world.getUnequippedInventoryItems().add(newpotion);

            onLoad(newpotion);
            world.addEntity(newpotion);
        }
    }

    public void loadUnequippedEquipment(JSONArray unequippedequipments) {
        for (int i = 0; i < unequippedequipments.length(); i++) {
            JSONObject unequippedequipment = (JSONObject) (unequippedequipments.get(i));

            String type = unequippedequipment.getString("type");
            int x = unequippedequipment.getInt("x");
            int y = unequippedequipment.getInt("y");

            Equipment newequipment = switchEquipmentTypeToEquipment(type, x, y);
            world.getUnequippedInventoryItems().add(newequipment);

            onLoad(newequipment);
            world.addEntity(newequipment);

        }
    }

    public void loadRareItems(JSONArray rareitems) {
        for (int i = 0; i < rareitems.length(); i++) {
            JSONObject rareitem = (JSONObject) (rareitems.get(i));

            String type = rareitem.getString("type");
            int x = rareitem.getInt("x");
            int y = rareitem.getInt("y");

            RareItem newrareitem = switchRareItemTypeToRareItem(type, x, y);
            world.getUnequippedInventoryItems().add(newrareitem);

            onLoad(newrareitem);
            world.addEntity(newrareitem);

        }
    }

    public void loadCards(JSONArray cards) {
        for (int i = 0; i < cards.length(); i++) {
            JSONObject card = (JSONObject) (cards.get(i));

            String type = card.getString("type");
            int x = card.getInt("x");
            int y = card.getInt("y");

            Card newcard = switchCardTypeToCard(type, x, y);
            world.getCardEntities().add(newcard);

            onLoad(newcard);
            world.addEntity(newcard);
        }
    }

    public void loadEnemies(JSONArray enemies) {
        for (int i = 0; i < enemies.length(); i++) {
            JSONObject enemy = (JSONObject) (enemies.get(i));

            String type = enemy.getString("type");
            int x = enemy.getInt("x");
            int y = enemy.getInt("y");

            Enemy newenemy = switchEnemyTypeToEnemy(type, x, y);
            world.getEnemies().add(newenemy);

            onLoad(newenemy);
            world.addEntity(newenemy);
        }
    }

    public void loadInitialEntities(JSONArray jsonEntities, MAP_TYPE type) throws FileNotFoundException {
        LoopManiaWorldControllerLoader loader;

        switch (type) {
        case FOREST:
            loader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
            break;
        case ICEWORLD:
            loader = new LoopManiaWorldControllerLoader("iceworld.json");
            break;
        case DESERT:
            loader = new LoopManiaWorldControllerLoader("desert.json");
            break;
        default:
            // set forest as default selection
            loader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
            break;
        }

        // load initialy non-path entities
        for (int i = 0; i < jsonEntities.length(); i++) {
            loader.loadBuildingEntity(world, jsonEntities.getJSONObject(i), world.getOrderedPath(), entityImages);
        }

        // load entities image
        for (ImageView entity : entityImages) {
            squares.getChildren().add(entity);
        }

    }

    public void loadCharacterSoldiers(JSONObject character_info, Character character) {
        for (int i = 0; i < character_info.getJSONArray("soldiers").length(); i++) {
            JSONObject soldier = (JSONObject) character_info.getJSONArray("soldiers").get(i);

            int hp = soldier.getInt("hp");
            int attack = soldier.getInt("attack");

            Soldier newsoldier = new Soldier();
            newsoldier.setHp(hp);
            newsoldier.setAttack(attack);

            character.getSoldiers().add(newsoldier);
        }
    }

    public void loadCharacterEquipped(JSONObject character_info, Character character) {
        if (character_info.getJSONObject("equipments").has("Weapon")) {
            String weapontype = character_info.getJSONObject("equipments").getJSONObject("Weapon").getString("type");
            int x = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("x");
            int y = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("y");
            Weapon weapon = switchWeaponTypeToWeapon(weapontype, x, y);
            character.setDressedWeapon(weapon);
        }

        if (character_info.getJSONObject("equipments").has("Armour")) {
            String armourtype = character_info.getJSONObject("equipments").getJSONObject("Armour").getString("type");
            int x = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("x");
            int y = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("y");
            Armour armour = switchArmourTypeToArmour(armourtype, x, y);
            character.setDressedArmour(armour);
        }

        if (character_info.getJSONObject("equipments").has("Shield")) {
            String shieldtype = character_info.getJSONObject("equipments").getJSONObject("Shield").getString("type");
            int x = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("x");
            int y = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("y");
            Shield shield = switchShieldTypeToShield(shieldtype, x, y);
            character.setDressedShield(shield);
        }

        if (character_info.getJSONObject("equipments").has("Helmet")) {
            String helmettype = character_info.getJSONObject("equipments").getJSONObject("Helmet").getString("type");
            int x = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("x");
            int y = character_info.getJSONObject("equipments").getJSONObject("Weapon").getInt("y");
            Helmet helmet = switchHelmetTypeToHelmet(helmettype, x, y);
            character.setDressedHelmet(helmet);
        }
    }

    public void loadCharacter(JSONObject character_info) throws FileNotFoundException {

        int x = character_info.getInt("x");
        int y = character_info.getInt("y");
        int indexInPath = world.getOrderedPath().indexOf(new Pair<Integer, Integer>(x, y));
        Character character = new Character(new PathPosition(indexInPath, world.getOrderedPath()));

        character.setHP(character_info.getInt("hp"));
        character.setGold(character_info.getInt("gold"));
        character.setXP(character_info.getInt("xp"));

        character.setATK(character_info.getInt("atk"));
        character.setDEF(character_info.getInt("def"));

        loadCharacterEquipped(character_info, character);
        loadCharacterSoldiers(character_info, character);

        onLoad(character);
        world.setCharacter(character);
        world.addEntity(character);

        // bind character status to frontend
        hp.textProperty().bind(Bindings.convert(world.getCharacter().hpPercentageProperty()));
        gold.textProperty().bind(Bindings.convert(world.getCharacter().goldProperty()));
        xp.textProperty().bind(Bindings.convert(world.getCharacter().xpProperty()));
        soldier.textProperty()
                .bind(Bindings.convert(new SimpleIntegerProperty(world.getCharacter().getSoldiers().size())));

        // bind player total gold to frontend
        storeController.bindPlayerGold(world.getCharacter());
    }

    public void bornnewcharacter() throws FileNotFoundException {

        int x = 0;
        int y = 0;
        int indexInPath = world.getOrderedPath().indexOf(new Pair<Integer, Integer>(x, y));
        Character character = new Character(new PathPosition(indexInPath, world.getOrderedPath()));

        onLoad(character);
        world.setCharacter(character);
        world.addEntity(character);

        // bind character status to frontend
        hp.textProperty().bind(Bindings.convert(world.getCharacter().hpPercentageProperty()));
        gold.textProperty().bind(Bindings.convert(world.getCharacter().goldProperty()));
        xp.textProperty().bind(Bindings.convert(world.getCharacter().xpProperty()));
        soldier.textProperty()
                .bind(Bindings.convert(new SimpleIntegerProperty(world.getCharacter().getSoldiers().size())));

        // bind player total gold to frontend
        storeController.bindPlayerGold(world.getCharacter());
    }

    public Enemy switchEnemyTypeToEnemy(String type, int x, int y) {
        int indexInPath = world.getOrderedPath().indexOf(new Pair<Integer, Integer>(x, y));

        if (type.equals(Slug.class.getSimpleName())) {
            return new Slug(new PathPosition(indexInPath, world.getOrderedPath()));
        } else if (type.equals(Zombie.class.getSimpleName())) {
            return new Zombie(new PathPosition(indexInPath, world.getOrderedPath()));
        } else if (type.equals(Vampire.class.getSimpleName())) {
            return new Vampire(new PathPosition(indexInPath, world.getOrderedPath()));
        } else if (type.equals(Doggie.class.getSimpleName())) {
            return new Doggie(new PathPosition(indexInPath, world.getOrderedPath()));
        } else if (type.equals(ElanMuske.class.getSimpleName())) {
            return new ElanMuske(new PathPosition(indexInPath, world.getOrderedPath()));
        } else {
            return null;
        }
    }

    public Equipment switchEquipmentTypeToEquipment(String type, int x, int y) {
        if (type.equals(Sword.class.getSimpleName())) {
            return new Sword(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(Stake.class.getSimpleName())) {
            return new Stake(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(Staff.class.getSimpleName())) {
            return new Staff(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(BasicHelmet.class.getSimpleName())) {
            return new BasicHelmet(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(BasicShield.class.getSimpleName())) {
            return new BasicShield(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(BasicArmour.class.getSimpleName())) {
            return new BasicArmour(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public Weapon switchWeaponTypeToWeapon(String type, int x, int y) {
        if (type.equals(Sword.class.getSimpleName())) {
            return new Sword(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(Stake.class.getSimpleName())) {
            return new Stake(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(Staff.class.getSimpleName())) {
            return new Staff(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(Anduril.class.getSimpleName())) {
            return new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public Helmet switchHelmetTypeToHelmet(String type, int x, int y) {
        if (type.equals(BasicHelmet.class.getSimpleName())) {
            return new BasicHelmet(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public Shield switchShieldTypeToShield(String type, int x, int y) {
        if (type.equals(BasicShield.class.getSimpleName())) {
            return new BasicShield(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(TreeStump.class.getSimpleName())) {
            return new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public Armour switchArmourTypeToArmour(String type, int x, int y) {
        if (type.equals(BasicArmour.class.getSimpleName())) {
            return new BasicArmour(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public RareItem switchRareItemTypeToRareItem(String type, int x, int y) {
        if (type.equals(Anduril.class.getSimpleName())) {
            return new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(TreeStump.class.getSimpleName())) {
            return new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(TheOneRing.class.getSimpleName())) {
            return new TheOneRing(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public Potion switchPotionTypeToPotion(String type, int x, int y) {
        if (type.equals(HealthPotion.class.getSimpleName())) {
            return new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public Card switchCardTypeToCard(String type, int x, int y) {
        if (type.equals(BarracksCard.class.getSimpleName())) {
            return new BarracksCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(CampfireCard.class.getSimpleName())) {
            return new CampfireCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(TowerCard.class.getSimpleName())) {
            return new TowerCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(TrapCard.class.getSimpleName())) {
            return new TrapCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(VampireCastleCard.class.getSimpleName())) {
            return new VampireCastleCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(VillageCard.class.getSimpleName())) {
            return new VillageCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals(ZombiePitCard.class.getSimpleName())) {
            return new ZombiePitCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else {
            return null;
        }
    }

    public void setjsonfile(JSONObject jsonfile) {
        this.jsonfile = jsonfile;
    }

    public void setMapType(MAP_TYPE type) {
        this.mapType = type;
    }

    public MAP_TYPE getMapType() {
        return mapType;
    }

    public void spawnFightEffect() {

    }

    /**
     * we added this method to help with debugging so you could check your code is
     * running on the application thread. By running everything on the application
     * thread, you will not need to worry about implementing locks, which is outside
     * the scope of the course. Always writing code running on the application
     * thread will make the project easier, as long as you are not running
     * time-consuming tasks. We recommend only running code on the application
     * thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */

    private void printThreadingNotes(String currentMethodLabel) {
        System.out.println("\n###########################################");
        System.out.println("current method = " + currentMethodLabel);
        System.out.println("In application thread? = " + Platform.isFxApplicationThread());
        System.out.println("Current system time = " + java.time.LocalDateTime.now().toString().replace('T', ' '));
    }
}
