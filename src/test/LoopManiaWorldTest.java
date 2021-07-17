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


public class LoopManiaWorldTest {
    @Test
    public void testLoadCard() throws IOException {
        // initialize world
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<Integer,Integer>(0, 0));
        JSONObject goalObject = new JSONObject();
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath, goalObject);
        world.setCharacter(new Character(new PathPosition(0, orderedPath)));

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
}
