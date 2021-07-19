package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import unsw.loopmania.model.LoopManiaWorld;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.RareItem;
import unsw.loopmania.model.Character;
import unsw.loopmania.model.Potion;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.PathPosition;
import unsw.loopmania.model.cards.Card;
import unsw.loopmania.model.cards.TowerCard;
import unsw.loopmania.model.cards.VampireCastleCard;
import unsw.loopmania.model.cards.ZombiePitCard;
import unsw.loopmania.model.cards.VillageCard;
import unsw.loopmania.model.cards.BarracksCard;
import unsw.loopmania.model.cards.CampfireCard;
import unsw.loopmania.model.buildings.Building;
import unsw.loopmania.model.buildings.TrapBuilding;
import unsw.loopmania.model.buildings.TowerBuilding;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;

public class LoopManiaWorldTest {
    
    @Test
    public void testLoadCard() throws IOException {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // test initial number of card
        assertEquals(0, world.getCardEntities().size());

        // test card spawn after defeating enemy
        while (world.getCardEntities().size() != 8) {
            world.loadCard();
        }
        
        // test remove oldest card
        Card oldestCard = world.getCardEntities().get(0);
        Card secondCard = world.getCardEntities().get(1);
        Card newestCard;
        int gold = world.getCharacter().getGold();
        int xp = world.getCharacter().getXP();
       
        // load a new Card
        while (true) {
            if ((newestCard = world.loadCard()) != null) {
                break;
            }
        }
        
        // test success
        assertNotEquals(oldestCard, world.getCardEntities().get(0));
        assertEquals(newestCard, world.getCardEntities().get(7));
        assertEquals(7, newestCard.getX());
        
        // test shiftCardsDownFromXCoordinate method
        assertEquals(0, secondCard.getX());
        
        // test experience and gold gain after removing the oldest card
        assertEquals(gold + 100, world.getCharacter().getGold());
        assertEquals(xp + 100, world.getCharacter().getXP());
    }

    @Test
    public void testGetTilePosAdjacentToPath() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        
        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);

        Pair<Integer, Integer> aGrassTilePos = new Pair<Integer,Integer>(1, 1);

        List<Pair<Integer, Integer>> pathPosAdjacentToGrassTile = world.getPathPosAdjacentToGrassTile(aGrassTilePos);

        assertEquals(4, pathPosAdjacentToGrassTile.size());
        // up path tile
        assertEquals(new Pair<Integer,Integer>(1, 0), pathPosAdjacentToGrassTile.get(0));
        // down path tile
        assertEquals(new Pair<Integer,Integer>(1, 2), pathPosAdjacentToGrassTile.get(1));
        // left path tile
        assertEquals(new Pair<Integer,Integer>(0, 1), pathPosAdjacentToGrassTile.get(2));
        // right path tile
        assertEquals(new Pair<Integer,Integer>(2, 1), pathPosAdjacentToGrassTile.get(3));
    }

    @Test
    public void testconvertCardToBuildingByCoordinates() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Card vampireCastleCard;
        Building vampireCastle;

        // load a vampire castle card
        while(true) {
            if ((vampireCastleCard = world.loadCard()) instanceof VampireCastleCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, vampireCastleCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(vampireCastleCard));

        // convert card to building
        vampireCastle = world.convertCardToBuildingByCoordinates(vampireCastleCard.getX(), vampireCastleCard.getY(), 1, 1, "VAMPIRE_CASTLE");
    
        // test card is removed
        assertEquals(-1, world.getCardEntities().indexOf(vampireCastleCard));

        // test building is added to world
        assertEquals("VampireCastleBuilding", vampireCastle.getClass().getSimpleName());
        assertNotEquals(-1, world.getBuildingEntities().indexOf(vampireCastle));
    }

    @Test
    public void testCheckVampireSpawn() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card vampireCastleCard;

        // load a vampire castle card
        while(true) {
            if ((vampireCastleCard = world.loadCard()) instanceof VampireCastleCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, vampireCastleCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(vampireCastleCard));

        // convert vamprie card to building
        world.convertCardToBuildingByCoordinates(vampireCastleCard.getX(), vampireCastleCard.getY(), 1, 1, "VAMPIRE_CASTLE");
        
        // update number of cycle to 4
        for (int i = 0; i < 5; i++) {
            world.updateNthCycle();
        }
        assertEquals(5, world.getNthCycle());

        // test vampire spawn
        List<Vampire> vampires = world.checkVampireSpawn();

        // test one vamprie caslte spawn three vampire
        assertEquals(1, vampires.size());
    }
    
    @Test
    public void testCheckZombieSpawn() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card zombiePitCard;

        // load a zombie pit card
        while(true) {
            if ((zombiePitCard = world.loadCard()) instanceof ZombiePitCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, zombiePitCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(zombiePitCard));

        // convert zombie pit card to building
        world.convertCardToBuildingByCoordinates(zombiePitCard.getX(), zombiePitCard.getY(), 1, 1, "ZOMBIE_PIT");

        // check zombie spawn
        List<Zombie> zombies = world.checkZombieSpawn();

        // test one zombie pit spawn one zombie
        assertEquals(2, zombies.size());
    }

    @Test
    public void testCheckHeroPassVillage() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card villageCard;

        // load a vampire castle card
        while(true) {
            if ((villageCard = world.loadCard()) instanceof VillageCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, villageCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(villageCard));

        // convert vamprie card to building
        world.convertCardToBuildingByCoordinates(villageCard.getX(), villageCard.getY(), 0, 0, "VILLAGE");
        
        // set character health
        character.setHP(250);
        int hp = character.getHP();

        // test health regain
        world.checkHeroPassVillage();
        assertEquals(hp + 20, character.getHP());
    }

    @Test
    public void testCheckHeroPassBarracks() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card barrackCard;

        // load a vampire castle card
        while(true) {
            if ((barrackCard = world.loadCard()) instanceof BarracksCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, barrackCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(barrackCard));

        // convert vamprie card to building
        world.convertCardToBuildingByCoordinates(barrackCard.getX(), barrackCard.getY(), 0, 0, "BARRACKS");

        // test get soldier when pass a barrack
        assertEquals(0, character.getNumSoldier());
        world.checkHeroPassBarracks();
        assertEquals(1, character.getNumSoldier());
    }

    @Test
    public void testCheckEnemyPassTrap() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card trapCard;
        Card zombiePitCard;

        while(true) {
            if ((trapCard = world.loadCard()) instanceof BarracksCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, trapCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(trapCard));

        // spawn enemies
         while(true) {
            if ((zombiePitCard = world.loadCard()) instanceof ZombiePitCard) {
                break;
            }
        }
        world.convertCardToBuildingByCoordinates(zombiePitCard.getX(), zombiePitCard.getY(), 1, 1, "ZOMBIE_PIT");
        List<Zombie> zombies = world.checkZombieSpawn();
        int zombieHP = zombies.get(0).getHP();
        
        // convert trap card to building
        Building building = world.convertCardToBuildingByCoordinates(trapCard.getX(), trapCard.getY(), zombies.get(0).getX(), zombies.get(0).getY(), "TRAP");
        world.checkEnemyPassTrap();

        // test trap is removed after 
        assertEquals(-1, world.getBuildingEntities().indexOf(building));

        // test enemy is attacked by zombie pit
        TrapBuilding trap = (TrapBuilding) building;
        assertEquals(zombieHP - trap.getTrapAttack(), zombies.get(0).getHP());
    }

    @Test
    public void testCheckEnemyInTowerRadius() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card towerCard;
        Card zombiePitCard;

        // load a tower card
        while(true) {
            if ((towerCard = world.loadCard()) instanceof TowerCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, towerCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(towerCard));

        // spawn enemies
        while(true) {
            if ((zombiePitCard = world.loadCard()) instanceof ZombiePitCard) {
                break;
            }
        }
        world.convertCardToBuildingByCoordinates(zombiePitCard.getX(), zombiePitCard.getY(), 1, 1, "ZOMBIE_PIT");
        List<Zombie> zombies = world.checkZombieSpawn();
        int zombieHP = zombies.get(0).getHP();
        
        // convert tower card to building
        Building building = world.convertCardToBuildingByCoordinates(towerCard.getX(), towerCard.getY(), zombies.get(0).getX(), zombies.get(0).getY(), "TOWER");
        world.checkEnemyInTowerRadius();

        // test enemy is attacked by zombie pit
        TowerBuilding tower = (TowerBuilding) building;
        assertEquals(zombieHP - tower.getTowerAttack(), zombies.get(0).getHP());
    }

    @Test
    public void testCheckHeroInCampfireRadius() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> pathPos3 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos4 = new Pair<Integer,Integer>(2, 1);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        orderedPath.add(pathPos3);
        orderedPath.add(pathPos4);
                
        Card campfireCard;

        // load a campfire card
        while(true) {
            if ((campfireCard = world.loadCard()) instanceof CampfireCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, campfireCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(campfireCard));

        // convert tower card to building
        world.convertCardToBuildingByCoordinates(campfireCard.getX(), campfireCard.getY(), 0, 0, "CAMPFIRE");
        int heroATK = world.getCharacter().getATK();
        world.checkHeroInCampfireRadius();

        // test hero ATK is doubled
        assertEquals(heroATK * 2, world.getCharacter().getATK());
    }

    @Test
    public void testCanHeroRevive() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        character.setHP(0);

        // test hero cannot revive without the one ring
        assertEquals(false, world.canHeroRevive());

        world.addRareItem();

        // test hero can revive wit the one ring
        assertEquals(true, world.canHeroRevive());
    }
    
    @Test
    public void testAddItemFromStore() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // test initial world state
        assertEquals(0, world.getHeroItems().size());

        world.addItemFromStore(new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));

        // test item is added to world
        assertEquals(1, world.getHeroItems().size());
    }

    @Test
    public void testGetFirstAvailableSlotForItem() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);

        // test initial world state
        assertEquals(new Pair<>(0, 0), world.getFirstAvailableSlotForItem());

        world.addUnequippedEquipment();
        // test after adding a item into inventory
        assertEquals(new Pair<>(1, 0), world.getFirstAvailableSlotForItem());
    }

    @Test
    public void testRemoveUnequippedInventoryItem() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);

        // test initial world state
        assertEquals(0, world.getHeroItems().size());

        Equipment equipment = world.addUnequippedEquipment();
        assertEquals(1, world.getHeroItems().size());
        // test remove a item from inventory
        world.removeUnequippedInventoryItem(equipment);
        assertEquals(0, world.getHeroItems().size());
    }

    @Test
    public void testRemoveUnequippedInventoryItemByCoordinates() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);

        // test initial world state
        assertEquals(0, world.getHeroItems().size());
        assertEquals(new Pair<>(0, 0), world.getFirstAvailableSlotForItem());

        world.addUnequippedEquipment();
        assertEquals(1, world.getHeroItems().size());
        // test remove a item from inventory
        world.removeUnequippedInventoryItemByCoordinates(0, 0);
        assertEquals(0, world.getHeroItems().size());
    }

    @Test
    public void testRunTickMoves() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Pair<Integer, Integer> pathPos1 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> pathPos2 = new Pair<Integer,Integer>(1, 0);

        orderedPath.add(pathPos1);
        orderedPath.add(pathPos2);
        
        // test initial hero state
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());

        // test hero move
        world.runTickMoves();
        assertEquals(0, character.getX());
        assertEquals(1, character.getY());
    }

    @Test
    public void testUpdateNthCycle() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        
        // test initial hero state
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());

        for (int i = 0; i < 5; i++) {
            world.updateNthCycle();
        }
        
        // test update number of cycle
        assertEquals(5, world.getNthCycle());
    }

    @Test
    public void testCanVisitStore() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        
        // test initial state
        assertEquals(false, world.canVisitStore());
        
        // test complete 1 cycle
        world.updateNthCycle();
        assertEquals(true, world.canVisitStore());
        world.updateNumStoreVisit();

        // test complete 3 cycles
        world.updateNthCycle();
        assertEquals(false, world.canVisitStore());
        world.updateNthCycle();
        assertEquals(true, world.canVisitStore());
        world.updateNumStoreVisit();

        // test complete 6 cycles
        world.updateNthCycle();
        assertEquals(false, world.canVisitStore());
        world.updateNthCycle();
        assertEquals(false, world.canVisitStore());
        world.updateNthCycle();
        assertEquals(true, world.canVisitStore());
    }

    @Test
    public void testAddUnequippedEquipment() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // test initial world state
        assertEquals(0, world.getHeroItems().size());

        Equipment equipment = world.addUnequippedEquipment();
        assertEquals(equipment, world.getHeroItems().get(0));
    }

    @Test
    public void testAddUnusedPotion() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // test initial world state
        assertEquals(0, world.getHeroItems().size());

        Potion potion = world.addUnusedPotion();
        assertEquals(potion, world.getHeroItems().get(0));
    }

    @Test
    public void testAddRareItem() {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // test initial world state
        assertEquals(0, world.getHeroItems().size());

        RareItem rareItem = world.addRareItem();
        assertEquals(rareItem, world.getHeroItems().get(0));
    }
}
