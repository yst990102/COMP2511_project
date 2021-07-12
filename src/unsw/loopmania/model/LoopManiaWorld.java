package unsw.loopmania.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.equipments.Armours.BasicArmour;
import unsw.loopmania.model.equipments.Helmets.BasicHelmet;
import unsw.loopmania.model.equipments.Shields.BasicShield;
import unsw.loopmania.model.equipments.Weapons.Staff;
import unsw.loopmania.model.equipments.Weapons.Stake;
import unsw.loopmania.model.equipments.Weapons.Sword;
import unsw.loopmania.model.goal.GoalComposite;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.rareitems.TheOneRing;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    // TODO = add additional backend functionality

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    private StringProperty description;

    private int nthCycle;
    private int numStoreVisit;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    // goals
    private StringProperty goals;

    // TODO = add more lists for other entities, for equipped inventory items, etc...

    // TODO = expand the range of enemies
    private List<Slug> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Entity> unequippedInventoryItems;

    // TODO = expand the range of buildings
    private List<VampireCastleBuilding> buildingEntities;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath, JSONObject goalObject) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        description = new SimpleStringProperty();
        nthCycle = 0;
        numStoreVisit = 0;

        // set goal
        this.goals = new SimpleStringProperty();
        setGoals(new GoalComposite(goalObject).getContent());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<Slug> possiblySpawnEnemies() {
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<Slug> spawningEnemies = new ArrayList<>();
        if (pos != null) {
            int indexInPath = orderedPath.indexOf(pos);
            Slug enemy = new Slug(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }
        return spawningEnemies;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Slug enemy) {
        // 杀掉enemy的时候需要增加gold和exp
        // - Slug: $50, XP 100
        // - Zombie: $100, XP 200
        // - Vampire: $200, XP 300
        if (enemy.getClass().equals(Slug.class)) {
            int current_gold = this.character.getGold();
            this.character.setGold(current_gold + enemy.getGold_whenkilled());

            int current_exp = this.character.getXP();
            this.character.setXP(current_exp + enemy.getExp_whenkilled());
        }

        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<Slug> runBattles() {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<Slug> defeatedEnemies = new ArrayList<Slug>();
        for (Slug e : enemies) {
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (Math.pow((character.getX() - e.getX()), 2) + Math.pow((character.getY() - e.getY()), 2) < 4) {
                // fight...
                FightEnemy(e);
                if (e.getHp() <= 0) {
                    defeatedEnemies.add(e);
                }
            }
        }
        for (Slug e : defeatedEnemies) {
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    public void FightEnemy(Slug enemy) {
        FileOutputStream writer;
        try {
            writer = new FileOutputStream("fight.txt", true);

            writer.write(("Battle between : character ==== " + enemy + "\n").getBytes());

            while (true) {
                enemy.setHp(enemy.getHp() - character.getATK());
                writer.write(("Character attack enemy, enemy lose " + character.getATK() + " HP." + "\n").getBytes());

                if (enemy.getHp() <= 0) {
                    writer.write(("enemy died!!" + "\n").getBytes());
                    break;
                }

                int hpLoss = enemy.getAttack() - character.getDEF();
                character.setHP(character.getHP() - ((hpLoss >= 0) ? hpLoss : 0));
                writer.write(("Enemy attack character, character lose " + ((hpLoss >= 0) ? hpLoss : 0) + " HP." + "\n").getBytes());
                if (character.getHP() <= 0) {
                    writer.write(("Character died!!" + "\n").getBytes());
                    break;
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public VampireCastleCard loadVampireCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            // TODO = give some cash/experience/item rewards for the discarding of the oldest card
            removeCard(0);
        }
        VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index) {
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * spawn a equipment in the world and return the equipment entity
     * @return a equipment to be spawned in the controller as a JavaFX node
     */
    public Equipment addUnequippedEquipment() {
        // TODO = expand this - we would like to be able to add multiple types of items, apart from equipments
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped equipment and replace it... oldest equipment is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest equipment
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // now we insert the new equipment, as we know we have at least made a slot available...
        int randomInt = new Random().nextInt(6);

        if (randomInt == 0) {
            BasicHelmet helmet = new BasicHelmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(helmet);
            return helmet;
        } else if (randomInt == 1) {
            BasicShield shield = new BasicShield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(shield);
            return shield;
        } else if (randomInt == 2) {
            Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(sword);
            return sword;
        } else if (randomInt == 3) {
            Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(stake);
            return stake;
        } else if (randomInt == 4) {
            Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(staff);
            return staff;
        } else {
            BasicArmour armour = new BasicArmour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(armour);
            return armour;
        }
    }

    /**
     * spawn a potion in the world and return the potion entity
     * @return a potion to be spawned in the controller as a JavaFX node
     */
    public Potion addUnusedPotion() {
        // TODO = expand this - we would like to be able to add multiple types of items, apart from equipments
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped potion and replace it... oldest potion is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest equipment
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // now we insert the new equipment, as we know we have at least made a slot available...

        HealthPotion healthpotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(healthpotion);
        return healthpotion;
    }

    /**
     * spawn a RareItem in the world and return the RareItem entity
     * @return a RareItem to be spawned in the controller as a JavaFX node
     */
    public RareItem addRareItem() {
        // TODO = expand this - we would like to be able to add multiple types of items, apart from equipments
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped RareItem and replace it... oldest RareItem is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest equipment
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // now we insert the new rareitem

        RareItem the_one_ring = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(the_one_ring);
        return the_one_ring;

    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y) {
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves() {
        character.moveDownPath();
        moveBasicEnemies();
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item) {
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y) {
        for (Entity e : unequippedInventoryItems) {
            if ((e.getX() == x) && (e.getY() == y)) {
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index) {
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();

        // Everytime remove an item, need to increase 100 gold and 100 exp
        int current_gold = this.character.getGold();
        int current_exp = this.character.getXP();

        this.character.setGold(current_gold + 100);
        this.character.setXP(current_exp + 100);

        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y = 0; y < unequippedInventoryHeight; y++) {
            for (int x = 0; x < unequippedInventoryWidth; x++) {
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null) {
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c : cardEntities) {
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        // TODO = expand to more types of enemy
        for (Slug e : enemies) {
            e.move();
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition() {
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 2)) {
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
            int endNotAllowed = (indexPosition + 3) % orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates
                    .get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public VampireCastleBuilding convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }

        // now spawn building
        VampireCastleBuilding newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX),
                new SimpleIntegerProperty(buildingNodeY));
        buildingEntities.add(newBuilding);

        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        if (description.length() <= 96) {
            this.description.set(description);
        } else {
            this.description.set(description.substring(0, 92) + "...");
        }
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getNthCycle() {
        return nthCycle;
    }

    public void updateNthCycle() {
        if (character.getX() == 0 && character.getY() == 0) {
            nthCycle++;
        }
    }

    public int getNumStoreVisit() {
        return numStoreVisit;
    }

    public void updateNumStoreVisit() {
        numStoreVisit++;
    }

    public StringProperty getGoalProperty() {
        return goals;
    }

    public String getGoals() {
        return this.goals.get();
    }

    public void setGoals(String goals) {
        this.goals.set(goals);
    }

}
