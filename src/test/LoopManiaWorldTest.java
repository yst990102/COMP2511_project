package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.javatuples.Pair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import unsw.loopmania.model.LoopManiaWorld;
import unsw.loopmania.model.Character;
import unsw.loopmania.model.PathPosition;
import unsw.loopmania.model.cards.Card;
import unsw.loopmania.model.cards.VampireCastleCard;
import unsw.loopmania.model.cards.ZombiePitCard;
import unsw.loopmania.model.cards.VillageCard;
import unsw.loopmania.model.cards.BarracksCard;
import unsw.loopmania.model.buildings.Building;
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

        System.err.println(world.getCardEntities().size());
        
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
        for (int i = 0; i < 4; i++) {
            world.updateNthCycle();
        }
        assertEquals(4, world.getNthCycle());

        // test vampire spawn
        List<Vampire> vampires = world.checkVampireSpawn();

        // test one vamprie caslte spawn three vampire
        assertEquals(3, vampires.size());
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

        // load a vampire castle card
        while(true) {
            if ((zombiePitCard = world.loadCard()) instanceof ZombiePitCard) {
                break;
            }
        }

        // test card is added to the world
        assertNotEquals(null, zombiePitCard);
        assertNotEquals(-1, world.getCardEntities().indexOf(zombiePitCard));

        // convert vamprie card to building
        world.convertCardToBuildingByCoordinates(zombiePitCard.getX(), zombiePitCard.getY(), 1, 1, "ZOMBIE_PIT");

        // check zombie spawn
        List<Zombie> zombies = world.checkZombieSpawn();

        // test one zombie pit spawn one zombie
        assertEquals(1, zombies.size());
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

}
