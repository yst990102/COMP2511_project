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
import java.util.concurrent.ThreadLocalRandom;
import java.util.Iterator;
import java.lang.Integer;
import java.lang.Math;

import javax.lang.model.element.ExecutableElement;

import javax.swing.SwingUtilities;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jdk.nashorn.api.tree.ForInLoopTree;
import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;
import unsw.loopmania.model.enemies.boss.Doggie;
import unsw.loopmania.model.enemies.boss.ElanMuske;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.weapons.Staff;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.equipments.weapons.Sword;
import unsw.loopmania.model.friendlyforces.Soldier;
import unsw.loopmania.model.goal.GoalComposite;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.rareItems.Anduril;
import unsw.loopmania.model.rareItems.TheOneRing;
import unsw.loopmania.model.rareItems.TreeStump;
import unsw.loopmania.model.buildings.BarracksBuilding;
import unsw.loopmania.model.buildings.CampfireBuilding;
import unsw.loopmania.model.buildings.TowerBuilding;
import unsw.loopmania.model.buildings.TrapBuilding;
import unsw.loopmania.model.buildings.VampireCastleBuilding;
import unsw.loopmania.model.buildings.VillageBuilding;
import unsw.loopmania.model.buildings.ZombiePitBuilding;
import unsw.loopmania.model.cards.VampireCastleCard;
import unsw.loopmania.model.cards.ZombiePitCard;
import unsw.loopmania.model.coins.DoggieCoin;
import unsw.loopmania.model.cards.TowerCard;
import unsw.loopmania.model.cards.VillageCard;
import unsw.loopmania.model.cards.BarracksCard;
import unsw.loopmania.model.cards.TrapCard;
import unsw.loopmania.model.cards.CampfireCard;
import unsw.loopmania.strategy.ModeStrategy;

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
    private GoalComposite maingoal;
    private StringProperty goals;
    private boolean isGoalFinished;

    // enemies
    private List<Enemy> enemies;

    // cards
    private List<Card> cardEntities;

    // items
    private List<Item> unequippedInventoryItems;

    // buildings
    private List<Building> buildingEntities;

    // has elanmuske been spawn
    private boolean hasMuskeSpawn = false;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private ModeStrategy modeStrategy;

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
        
        // set goal
        this.goals = new SimpleStringProperty();
        this.maingoal = new GoalComposite(goalObject, this);
        setGoals(maingoal.getContent());
        this.isGoalFinished = maingoal.getLogicResult();
    }

    /**
     * update whether the goal is finished
     */
    public void updateIsGoalFinished() {
        this.isGoalFinished = maingoal.getLogicResult();
    }

    /**
     * Get the Width
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the Height
     * @return int
     */
    public int getHeight() {
        return height;
    }

    public void setOrderedPath(List<Pair<Integer, Integer>> path) {
        this.orderedPath = path;
    }

    /**
     * Get the Ordered Path
     * @return List<Pair<Integer, Integer>>
     */
    public List<Pair<Integer, Integer>> getOrderedPath() {
        return orderedPath;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
        this.character.setBag(unequippedInventoryItems);
    }

    /**
     * Get the Character
     * @return
     */
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
     * Get the Card Entity
     * @return List<Card>
     */
    public List<Card> getCardEntities() {
        return cardEntities;
    }

    /**
     * Get the Building Entity
     * @return List<Building>
     */
    public List<Building> getBuildingEntities() {
        return buildingEntities;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy) {
        // Gold and XP obtained when killing an enemy
        // - Slug: $50, XP 100
        // - Zombie: $100, XP 200
        // - Vampire: $200, XP 300

        int currentGold = this.character.getGold();
        this.character.setGold(currentGold + enemy.goldWhenKilled);

        int currentExp = this.character.getXP();
        this.character.setXP(currentExp + enemy.expWhenKilled);

        enemy.destroy();
        enemies.remove(enemy);
        setDescription("You killed a " + enemy.getClass().getSimpleName().toLowerCase() + ".");
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();

        // check if there is enemy in battle radius
        List<Enemy> enemies_battled = new ArrayList<Enemy>();
        for (Enemy e : enemies) {
            if (Math.pow((character.getX() - e.getX()), 2) + Math.pow((character.getY() - e.getY()), 2) <= Math
                    .pow(e.battleRadius, 2)) {
                enemies_battled.add(e);
            }
        }

        if (enemies_battled.isEmpty()) {
            return defeatedEnemies;
        } else {
            for (Enemy e : enemies) {
                if (enemies_battled.contains(e)) {
                    continue;
                } else {
                    if (Math.pow((character.getX() - e.getX()), 2) + Math.pow((character.getY() - e.getY()), 2) <= Math
                            .pow(e.supportRadius, 2)) {
                        enemies_battled.add(e);
                    }
                }
            }
        }

        for (int i = 0; i < enemies_battled.size(); i++) {
            Enemy e = enemies_battled.get(i);

            // fight...
            // System.out.println("enemy == " + e + " is in range");
            fight(e, enemies);
            setDescription("You are fighting with a " + e.getClass().getSimpleName().toLowerCase() + ".");
            if (e.hp <= 0) {
                defeatedEnemies.add(e);
            }

        }

        for (Enemy e : defeatedEnemies) {
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    /**
     * Let Character Attack Enemy
     * @param character
     * @param enemy
     * @param writer
     * @throws IOException
     */
    public void CharacterAttackEnemy(Character character, Enemy enemy, FileOutputStream writer) throws IOException {
        // character attack enemy
        if (character.getDressedWeapon() != null) {
            if (character.getDressedWeapon().getClass().equals(Staff.class)) {
                int trancePercentage = ((Staff) character.getDressedWeapon()).trancePercentage;
                int randint = new Random(System.currentTimeMillis()).nextInt(100);

                if (randint < trancePercentage) {
                    enemy.setHP(0);

                    Soldier newsoSoldier = new Soldier();
                    character.getSoldiers().add(newsoSoldier);

                    writer.write(("enemy converted into a soldier!!!!").getBytes());
                    return;
                }
            }
        }

        enemy.hp -= character.getATK(enemy);

        writer.write(("=== Character attack enemy, enemy lose " + character.getATK() + " HP." + "\n").getBytes());

    }

    /**
     * Let Enemy Attack Character
     * @param character
     * @param enemy
     * @param writer
     * @throws IOException
     */
    public void EnemyAttackCharacter(Character character, Enemy enemy, FileOutputStream writer) throws IOException {
        // enemy attack character
        int beforeHP = character.getHP();

        int HPLoss = 0;
        if (enemy.getClass().equals(Slug.class)) { // slug attack character
            writer.write(("=== slug attack character." + "\n").getBytes());

            int damage = 0;
            int enemyAttack = 0;

            if (character.getDressedHelmet() != null) {
                enemyAttack = enemy.getAttack() - character.getDressedHelmet().getEnemyAttackDecrease();
                writer.write(("character have helmet. enemyAttack == " + enemyAttack + "\n").getBytes());
            } else {
                enemyAttack = enemy.getAttack();
                writer.write(("character doesn't have helmet. enemyAttack == " + enemyAttack + "\n").getBytes());
            }

            damage = enemyAttack - character.getDEF();

            if (character.getDressedArmour() != null) {
                damage *= (1 - (Double
                        .valueOf(Double.valueOf(character.getDressedArmour().getDamageReducePercentage()) / 100)));
                writer.write(("character have armour, enemyAttack == " + enemyAttack + "\n").getBytes());
            }

            writer.write(("final damage == " + damage + "\n").getBytes());

            HPLoss = (damage < 0) ? 0 : damage;
            character.setHP(character.getHP() - HPLoss);

            int afterHP = character.getHP();
            writer.write(("After slug attack, Character HP LOSS === " + (beforeHP - afterHP) + "\n").getBytes());

        } else if (enemy.getClass().equals(Zombie.class)) { // zombie attack character
            writer.write(("=== zombie attack character." + "\n").getBytes());

            int damage = 0;
            int enemyAttack = 0;

            if (character.getDressedHelmet() != null) {
                enemyAttack = enemy.getAttack() - character.getDressedHelmet().getEnemyAttackDecrease();
                writer.write(("character have helmet. enemyAttack == " + enemyAttack + "\n").getBytes());
            } else {
                enemyAttack = enemy.getAttack();
                writer.write(("character doesn't have helmet. enemyAttack == " + enemyAttack + "\n").getBytes());
            }

            damage = enemyAttack - character.getDEF();

            if (character.getDressedArmour() != null) {
                damage *= (1 - (Double
                        .valueOf(Double.valueOf(character.getDressedArmour().getDamageReducePercentage()) / 100)));
                writer.write(("character have armour, enemyAttack == " + enemyAttack + "\n").getBytes());
            }

            writer.write(("final damage == " + damage + "\n").getBytes());

            HPLoss = (damage < 0) ? 0 : damage;
            character.setHP(character.getHP() - HPLoss);

            int afterHP = character.getHP();
            writer.write(("After zombie attack, Character HP LOSS === " + (beforeHP - afterHP) + "\n").getBytes());

        } else if (enemy.getClass().equals(Vampire.class)) { // vampire attack character
            writer.write(("=== vampire attack character." + "\n").getBytes());

            int damage = 0;

            int attackTimes = ThreadLocalRandom.current().nextInt(1, 4); // attack 1-3 times everytime

            writer.write(("total attack times == " + attackTimes + "\n").getBytes());

            while (attackTimes > 0) {

                int enemyAttack = 0;
                int criticalPercentageDecrease = 0;

                if (character.getDressedShield() != null) {
                    criticalPercentageDecrease = character.getDressedShield().getCriticalPercentageDecrease();
                    writer.write(("character have shield, critical percentage decrease by " + criticalPercentageDecrease
                            + "%" + "\n").getBytes());
                }

                if (character.getDressedHelmet() != null) {
                    enemyAttack = ((Vampire) enemy).getAttack(criticalPercentageDecrease)
                            - character.getDressedHelmet().getEnemyAttackDecrease();
                    writer.write(("character have helmet, enemyAttack == " + enemyAttack + "\n").getBytes());
                } else {
                    enemyAttack = ((Vampire) enemy).getAttack(criticalPercentageDecrease);
                    writer.write(("character have no armour, enemyAttack == " + enemyAttack + "\n").getBytes());
                }

                damage = enemyAttack - character.getDEF();

                if (character.getDressedArmour() != null) {
                    damage *= (1 - (Double
                            .valueOf(Double.valueOf(character.getDressedArmour().getDamageReducePercentage()) / 100)));
                    writer.write(("character have armour, enemyAttack == " + enemyAttack + "\n").getBytes());
                }

                writer.write((attackTimes + "'s time damage == " + damage + "\n").getBytes());

                HPLoss = (damage < 0) ? 0 : damage;
                character.setHP(character.getHP() - HPLoss);
                writer.write((attackTimes + "'s time HPLoss == " + HPLoss + "\n").getBytes());

                attackTimes--;
            }

        } else if (enemy.getClass().equals(Doggie.class)) {
            writer.write(("=== doggie attack character." + "\n").getBytes());

            int damage = 0;

            int attackTimes = 1;

            int counter = 0;
            while (counter < attackTimes) {
                int enemyAttack = 0;

                if (character.getDressedHelmet() != null) {
                    enemyAttack = ((Doggie) enemy).getAttack() - character.getDressedHelmet().getEnemyAttackDecrease();
                    writer.write(("character have helmet, enemyAttack == " + enemyAttack + "\n").getBytes());
                } else {
                    enemyAttack = ((Doggie) enemy).getAttack();
                    writer.write(("character have no armour, enemyAttack == " + enemyAttack + "\n").getBytes());
                }

                damage = enemyAttack - character.getDEF();

                if (character.getDressedArmour() != null) {
                    damage *= (1 - (Double
                            .valueOf(Double.valueOf(character.getDressedArmour().getDamageReducePercentage()) / 100)));
                    writer.write(("character have armour, enemyAttack == " + enemyAttack + "\n").getBytes());
                }

                writer.write((attackTimes + "'s time damage == " + damage + "\n").getBytes());

                HPLoss = (damage < 0) ? 0 : damage;
                character.setHP(character.getHP() - HPLoss);
                writer.write((attackTimes + "'s time HPLoss == " + HPLoss + "\n").getBytes());

                // add attack times when stunned
                int randint = new Random(System.currentTimeMillis()).nextInt(100);
                if (randint < ((Doggie) enemy).getStun_percentage()) {
                    attackTimes += ((Doggie) enemy).getStun_round();
                }

                counter++;
            }

        } else if (enemy.getClass().equals(ElanMuske.class)) {

        }

        int afterHP = character.getHP();
        writer.write(("After Vampire attack, Character HP LOSS === " + (beforeHP - afterHP) + "\n").getBytes());
    }

    /**
     * The function of a fight 
     * @param enemy
     */
    public void fight(Enemy enemy, List<Enemy> enemies) {
        FileOutputStream writer;
        try {
            writer = new FileOutputStream("fight.txt", true);

            while (true) {
                // Stage 00: Soldiers Attack Fight Enemy
                writer.write(("Battle between : soldier ==== " + enemy + "\n").getBytes());
                SoldiersFightEnemy(character.getSoldiers(), enemy, enemies, writer);

                writer.write(("Battle between : character ==== " + enemy + "\n").getBytes());
                // Stage 01 : Character Attack Enemy
                CharacterAttackEnemy(character, enemy, writer);
                // if enemy died, break
                if (enemy.hp <= 0) {
                    writer.write(("enemy died!!" + "\n\n").getBytes());
                    break;
                }

                // Stage 02 : Enemy Attack Character
                EnemyAttackCharacter(character, enemy, writer);
                // if character died, break
                if (character.getHP() <= 0) {
                    writer.write(("Character died!!" + "\n\n").getBytes());
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
     * This is fight between soldiers and enemy
     * @param soldiers
     * @param enemy
     * @param enemies
     * @param writer
     * @throws IOException
     */
    public void SoldiersFightEnemy(List<Soldier> soldiers, Enemy enemy, List<Enemy> enemies, FileOutputStream writer)
            throws IOException {
        for (int i = 0; i < soldiers.size(); i++) {
            Soldier soldier = soldiers.get(i);
            while (true) {
                // soldier attack enemy
                int enemyhpbefore = enemy.getHP();
                enemy.setHP(enemy.getHP() - soldier.getAttack());
                int enemyhpafter = enemy.getHP();

                writer.write(("enemy loss " + (enemyhpbefore - enemyhpafter) + "HP. \n").getBytes());

                if (enemy.hp <= 0) {
                    writer.write(("enemy died!!" + "\n\n").getBytes());
                    return;
                }

                // enemy attack soldier
                if (enemy.getClass().equals(Zombie.class)) {
                    Pair<Integer, Boolean> zombieAttack = ((Zombie) enemy).getAttackByCritical();

                    int attack = zombieAttack.getValue0();
                    boolean isCriticalBite = zombieAttack.getValue1();

                    int randomInfection = new Random(System.currentTimeMillis()).nextInt(100);
                    if (isCriticalBite && randomInfection < ((Zombie) enemy).getInfectionPercentage()) {
                        soldiers.remove(i);

                        // born infected soldier zombie on same location with the infector zombie
                        Zombie zombie = new Zombie(new PathPosition(0, orderedPath));
                        zombie.x().set(enemy.getX());
                        zombie.y().set(enemy.getY());

                        writer.write(("enemies == " + enemies + "\n").getBytes());
                        enemies.add(zombie);
                        writer.write(("enemies == " + enemies + "\n").getBytes());

                        // enemies.add(new Zombie(new PathPosition(0, orderedPath)));
                        writer.write(("soldier become a new zombie. !!!!!! \n").getBytes());
                        break;
                    } else {
                        int soldierhpbefore = soldier.getHp();
                        soldier.setHp(soldier.getHp() - enemy.getAttack());
                        int soldierhpafter = soldier.getHp();

                        writer.write(("soldier loss " + (soldierhpbefore - soldierhpafter) + "HP. \n").getBytes());
                    }
                } else {
                    int soldierhpbefore = soldier.getHp();
                    soldier.setHp(soldier.getHp() - enemy.getAttack());
                    int soldierhpafter = soldier.getHp();

                    writer.write(("soldier loss " + (soldierhpbefore - soldierhpafter) + "HP. \n").getBytes());
                }

                if (soldier.hp <= 0) {
                    soldiers.remove(i);
                    writer.write(("soldier is died!" + "\n").getBytes());
                    break;
                }
            }
        }
    }

    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard() {
        if (cardEntities.size() >= getWidth()) {
            removeCard(0);
            character.setGold(character.getGold() + 100);
            character.setXP(character.getXP() + 100);
        }

        int randomInt = new Random(System.currentTimeMillis()).nextInt(100);

        Card card = null;

        if (randomInt < 5) {
            card = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (randomInt < 10) {
            card = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (randomInt < 15) {
            card = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (randomInt < 20) {
            card = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (randomInt < 25) {
            card = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (randomInt < 91) {
            card = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (randomInt < 95) {
            card = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        }

        if (card != null) {
            cardEntities.add(card);
        }

        return card;
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
        int randomInt = new Random(System.currentTimeMillis()).nextInt(6);

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
    * spawn a potion in the world and return the potion entity
    * @return a potion to be spawned in the controller as a JavaFX node
    */
    public DoggieCoin addUnsoldCoins() {
        // TODO = expand this - we would like to be able to add multiple types of items, apart from equipments
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped potion and replace it... oldest potion is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest equipment
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // now we insert the new equipment, as we know we have at least made a slot available...

        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()), hasMuskeSpawn);
        unequippedInventoryItems.add(doggieCoin);
        return doggieCoin;
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

        int randint = new Random(System.currentTimeMillis()).nextInt(3);

        RareItem dropped_item;

        switch (randint) {
        case 1:
            dropped_item = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            break;
        case 0:
            dropped_item = new Anduril(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            break;

        default:
            dropped_item = new TreeStump(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                    new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            break;
        }

        unequippedInventoryItems.add(dropped_item);
        return dropped_item;

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
    public void removeUnequippedInventoryItem(Entity item) {
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
        int currentGold = this.character.getGold();
        int currentExp = this.character.getXP();

        this.character.setGold(currentGold + 100);
        this.character.setXP(currentExp + 100);

        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    public Pair<Integer, Integer> getFirstAvailableSlotForItem() {
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
        for (Enemy e : enemies) {
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
        Random rand = new Random(System.currentTimeMillis());
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
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY, String buildingType) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }

        // now spawn building
        Building newBuilding = null;

        switch (buildingType) {
        case "VAMPIRE_CASTLE":
            newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        case "ZOMBIE_PIT":
            newBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        case "TOWER":
            newBuilding = new TowerBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        case "VILLAGE":
            newBuilding = new VillageBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        case "BARRACKS":
            newBuilding = new BarracksBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        case "TRAP":
            newBuilding = new TrapBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        case "CAMPFIRE":
            newBuilding = new CampfireBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
            break;
        default:
            break;
        }

        if (newBuilding != null) {
            buildingEntities.add(newBuilding);
        }

        // destroy the card
        removeCard(cardNodeX);

        return newBuilding;
    }

    /**
     * Get the Description
     * @return String
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Set the Description
     * @param description
     */
    public void setDescription(String description) {
        if (description.length() <= 96) {
            this.description.set(description);
        } else {
            this.description.set(description.substring(0, 92) + "...");
        }
    }

    /**
     * Get the DescriptionProperty
     * @return StringProperty
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Get the Nth of Cycle
     * @return int 
     */
    public int getNthCycle() {
        return nthCycle;
    }

    /**
     * Update the Nth of Cycle
     */
    public void updateNthCycle() {
        if (character.getX() == 0 && character.getY() == 0) {
            nthCycle++;
            updateVampireCastleCycle();
            setDescription("You have completed " + nthCycle + (nthCycle > 1 ? " cycles." : " cycle."));
        }
    }

    /**
     * Get the NumStoreVisit
     * @return int
     */
    public int getNumStoreVisit() {
        return numStoreVisit;
    }

    /**
     * Update the NumStoreVisit
     */
    public void updateNumStoreVisit() {
        numStoreVisit++;
    }

    /**
     * Check whether can visit the store
     * @return boolean
     */
    public boolean canVisitStore() {
        boolean heroAtCastle = character.getX() == 0 && character.getY() == 0;
        if (nthCycle == (numStoreVisit + 1) * (numStoreVisit + 2) / 2 && heroAtCastle) {
            return true;
        }
        return false;
    }

    /**
     * Get the Goal Property
     * @return StringProperty
     */
    public StringProperty getGoalProperty() {
        return goals;
    }

    /**
     * Get the Goals
     * @return String
     */
    public String getGoals() {
        return this.goals.get();
    }

    /**
     * Set the Goals
     * @param goals
     */
    public void setGoals(String goals) {
        this.goals.set(goals);
    }

    /**
     * Get the PathPosAdjacentToGrassTile
     * @param pos
     * @return List<Pair<Integer, Integer>>
     */
    public List<Pair<Integer, Integer>> getPathPosAdjacentToGrassTile(Pair<Integer, Integer> pos) {
        List<Pair<Integer, Integer>> tilePosAdjacentToPath = new ArrayList<>();

        Pair<Integer, Integer> up = new Pair<>(pos.getValue0(), Math.floorMod(pos.getValue1() - 1, height));
        Pair<Integer, Integer> down = new Pair<>(pos.getValue0(), Math.floorMod(pos.getValue1() + 1, height));
        Pair<Integer, Integer> left = new Pair<>(Math.floorMod(pos.getValue0() - 1, width), pos.getValue1());
        Pair<Integer, Integer> right = new Pair<>(Math.floorMod(pos.getValue0() + 1, width), pos.getValue1());

        if (orderedPath.indexOf(up) != -1) {
            tilePosAdjacentToPath.add(up);
        }

        if (orderedPath.indexOf(down) != -1) {
            tilePosAdjacentToPath.add(down);
        }

        if (orderedPath.indexOf(left) != -1) {
            tilePosAdjacentToPath.add(left);
        }

        if (orderedPath.indexOf(right) != -1) {
            tilePosAdjacentToPath.add(right);
        }

        return tilePosAdjacentToPath;
    }

    /**
     * Update the Vampire Castle Cycle
     */
    private void updateVampireCastleCycle() {
        for (int i = 0; i < buildingEntities.size(); i++) {
            if (buildingEntities.get(i) instanceof VampireCastleBuilding) {
                VampireCastleBuilding vampireCastle = (VampireCastleBuilding) buildingEntities.get(i);
                vampireCastle.updateCycle();
            }
        }
    }

    /**
    * spawns enemies if the conditions warrant it, adds to world
    * @return list of the enemies to be displayed on screen
    */
    public List<Slug> checkSlugSpawn() {
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
     * Check whether the Vampire spawns
     * @return List<Vampire>
     */
    public List<Vampire> checkVampireSpawn() {
        ArrayList<Vampire> vampires = new ArrayList<>();
        for (int i = 0; i < buildingEntities.size(); i++) {
            if (buildingEntities.get(i) instanceof VampireCastleBuilding) {
                VampireCastleBuilding vampireCastle = (VampireCastleBuilding) buildingEntities.get(i);
                if (vampireCastle.canSpawnVampire(character)) {
                    Pair<Integer, Integer> vampireBuildingPos = new Pair<>(Integer.valueOf(vampireCastle.getX()),
                            Integer.valueOf(vampireCastle.getY()));
                    List<Pair<Integer, Integer>> tilePosAdjacentToPath = getPathPosAdjacentToGrassTile(
                            vampireBuildingPos);

                    if (tilePosAdjacentToPath.size() <= 0) {
                        continue;
                    }

                    int randomInt = new Random().nextInt(tilePosAdjacentToPath.size());
                    int indexInPath = orderedPath.indexOf(tilePosAdjacentToPath.get(randomInt));
                    Vampire vampire = new Vampire(new PathPosition(indexInPath, orderedPath));
                    enemies.add(vampire);
                    vampires.add(vampire);
                }
            }
        }
        return vampires;
    }

    /**
     * Check whether the Zombie spawns
     * @return List<Zombie>
     */
    public List<Zombie> checkZombieSpawn() {
        ArrayList<Zombie> zombies = new ArrayList<>();
        for (Building b : buildingEntities) {
            if (b instanceof ZombiePitBuilding) {
                if (character.getX() == 0 && character.getY() == 0) {
                    Pair<Integer, Integer> zombieBuildingPos = new Pair<>(Integer.valueOf(b.getX()),
                            Integer.valueOf(b.getY()));
                    List<Pair<Integer, Integer>> tilePosAdjacentToPath = getPathPosAdjacentToGrassTile(
                            zombieBuildingPos);

                    if (tilePosAdjacentToPath.size() <= 0) {
                        continue;
                    }

                    for (int j = 0; j < 2; j++) {
                        int randomInt = new Random(System.currentTimeMillis()).nextInt(tilePosAdjacentToPath.size());
                        int indexInPath = orderedPath.indexOf(tilePosAdjacentToPath.get(randomInt));
                        Zombie zombie = new Zombie(new PathPosition(indexInPath, orderedPath));
                        enemies.add(zombie);
                        zombies.add(zombie);
                    }
                }
            }
        }
        return zombies;
    }

    public ElanMuske checkMuskeSpawn() {
        if (hasMuskeSpawn == true) {
            return null;
        }

        if (nthCycle >= 40 && getCharacter().getXP() >= 10000) {
            int randint = new Random(System.currentTimeMillis()).nextInt(enemies.size());

            int x = enemies.get(randint).getX();
            int y = enemies.get(randint).getY();
            int indexInPath = orderedPath.indexOf(new Pair<Integer, Integer>(x, y));

            ElanMuske elanmuske = new ElanMuske(new PathPosition(indexInPath, orderedPath));
            enemies.add(elanmuske);

            hasMuskeSpawn = true;

            return elanmuske;
        } else {
            return null;
        }
    }

    public Doggie checkDoggieSpawn() {
        if (nthCycle >= 20) {

            if (enemies.size() <= 1) {
                return null;
            }

            int doggiecount = 0;
            for (Enemy e : enemies) {
                if (e instanceof Doggie) {
                    doggiecount++;
                }
            }
            if (doggiecount >= 3) {
                return null;
            }

            Enemy randenemy = enemies.get(new Random(System.currentTimeMillis()).nextInt(enemies.size()));
            int x = randenemy.getX();
            int y = randenemy.getY();
            int indexInPath = orderedPath.indexOf(new Pair<Integer, Integer>(x, y));

            Doggie doggie = new Doggie(new PathPosition(indexInPath, orderedPath));
            enemies.add(doggie);

            return doggie;
        } else {
            return null;
        }
    }

    /**
     * Check whether the Hero passes the Village
     */
    public void checkHeroPassVillage() {
        for (Building b : buildingEntities) {
            if (b instanceof VillageBuilding) {
                VillageBuilding village = new VillageBuilding(new SimpleIntegerProperty(b.getX()),
                        new SimpleIntegerProperty(b.getY()));
                if (character.getX() == b.getX() && character.getY() == b.getY()) {
                    character.setHP(character.getHP() + village.getRegainHp());
                }
            }
        }
    }

    /**
     * Check whether the Hero passes the Barracks
     */
    public void checkHeroPassBarracks() {
        for (Building b : buildingEntities) {
            if (b instanceof BarracksBuilding) {
                if (character.getX() == b.getX() && character.getY() == b.getY()) {
                    character.getSoldiers().add(new Soldier());
                }
            }
        }
    }

    /**
     * Check whether the Enemy passes the Traps
     */
    public void checkEnemyPassTrap() {
        Iterator<Building> buildingIterator = buildingEntities.iterator();
        Building b;
        Enemy e;

        while (buildingIterator.hasNext()) {
            b = buildingIterator.next();
            if (b instanceof TrapBuilding) {
                TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(b.getX()),
                        new SimpleIntegerProperty(b.getY()));
                Iterator<Enemy> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    e = enemyIterator.next();
                    if (b.getX() == e.getX() && b.getY() == e.getY()) {
                        // enemy is attacked by trap
                        e.setHP(e.getHP() - trap.getTrapAttack());
                        // if enemy is killed
                        if (e.getHP() == 0) {
                            character.setGold(character.getGold() + e.goldWhenKilled);
                            character.setXP(character.getXP() + e.expWhenKilled);
                            e.destroy();
                            enemyIterator.remove();
                        }
                        b.destroy();
                        buildingIterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Check whether the Enemy is in the Tower Radius
     */
    public void checkEnemyInTowerRadius() {
        Iterator<Building> buildingIterator = buildingEntities.iterator();
        Building b;
        Enemy e;

        while (buildingIterator.hasNext()) {
            b = buildingIterator.next();
            if (b instanceof TowerBuilding) {
                TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(b.getX()),
                        new SimpleIntegerProperty(b.getY()));
                Iterator<Enemy> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    e = enemyIterator.next();
                    if (Math.pow((e.getX() - b.getX()), 2) + Math.pow((e.getY() - b.getY()), 2) < Math
                            .pow(tower.getShootingRadius(), 2)) {
                        // enemy is attacked by tower
                        e.setHP(e.getHP() - tower.getTowerAttack());
                        // if enemy is killed
                        if (e.getHP() == 0) {
                            character.setGold(character.getGold() + e.goldWhenKilled);
                            character.setXP(character.getXP() + e.expWhenKilled);
                            e.destroy();
                            enemyIterator.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * Check whether the Hero is in the Campfire Radius
     */
    public void checkHeroInCampfireRadius() {
        character.setBuildingEntities(buildingEntities);
    }

    /**
     * Check whether the Hero can Revive
     * @return boolean
     */
    public boolean canHeroRevive() {
        boolean heroHasTheOneRing = false;
        if (character.getHP() == 0) {
            Iterator<Item> itemIterator = unequippedInventoryItems.iterator();
            Item item;
            while (itemIterator.hasNext()) {
                item = itemIterator.next();
                if (item instanceof TheOneRing) {
                    heroHasTheOneRing = true;
                    item.destroy();
                    itemIterator.remove();
                    setDescription("You used a ring to revive!");
                    character.setHP(300);
                }
            }
            if (heroHasTheOneRing) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Add Items From the Store
     * @param entity
     */
    public void addItemFromStore(Item entity) {
        unequippedInventoryItems.add(entity);
    }

    /**
     * Get the Hero Items
     * @return List<Item>
     */
    public List<Item> getHeroItems() {
        return unequippedInventoryItems;
    }

    /**
     * Set the ModeStrategy
     * @param strategy
     */
    public void setModeStrategy(ModeStrategy strategy) {
        modeStrategy = strategy;
    }

    /**
     * Get the ModeStrategy
     * @return ModeStrategy
     */
    public ModeStrategy getModeStrategy() {
        return modeStrategy;
    }

    /**
     * Get the symbol of whether the goal is finished
     * @return boolean
     */
    public boolean getIsGoalFinished() {
        return this.isGoalFinished;
    }

}
