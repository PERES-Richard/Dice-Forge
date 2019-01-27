package game;

import card.*;
import effect.*;
import org.junit.Before;
import org.junit.Test;
import player.EasyBot;
import player.Player;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IslandTest {
    private Island normalIsland;
    private Island bossIsland;
    private Card passeur;
    private Player p1;
    private Player p2;
    private ArrayList<Card> leftCards;
    private ArrayList<Card> rightCards;
    private ArrayList<Card> bossCards;
    @Before
    public void setUp() {
        leftCards = new ArrayList<>();
        rightCards = new ArrayList<>();
        bossCards = new ArrayList<>();
        passeur = new NoEffectCard(CardName.LePasseur, 12);
        leftCards.add(new ReinforcementCard(CardName.LesSabotsDArgent, 2, new MinorFavorEffect()));
        bossCards.add(new NoEffectCard(CardName.LePasseur, 12));
        rightCards.add(new NoEffectCard(CardName.LHydre, 26));
        normalIsland = new Island(Resource.LUNAR, leftCards, rightCards, 2, 3);
        bossIsland = new Island(Resource.SOLAR, Resource.LUNAR, leftCards, bossCards, rightCards, 6, 6, 5);
        p1 = new EasyBot("p1",0);
        p2 = new EasyBot("p2",1);
    }

    @Test
    public void constructorTest(){
        assertNotNull(normalIsland);
        assertNotNull(bossIsland);
        assertFalse(normalIsland.isBossIsland());
        assertTrue(bossIsland.isBossIsland());
        assertNotNull(normalIsland.getLeftCards());
        assertEquals(6, bossIsland.getLeftCardsCost());
        assertEquals(5, bossIsland.getBossCardsCost());
        assertEquals(1, normalIsland.getLeftCards().size());
    }

    @Test
    public void containsTest(){
        assertTrue(normalIsland.contains(CardName.LesSabotsDArgent));
        assertFalse(normalIsland.contains(CardName.LAncien));
        assertTrue(bossIsland.contains(CardName.LePasseur));
        assertTrue(bossIsland.contains(passeur));
        assertFalse(normalIsland.contains(passeur));
    }

    @Test
    public void costTest(){
       assertEquals(bossIsland.getBossCardsCost(), bossIsland.getCost(passeur));
    }

    @Test
    public void enterInTest(){
        assertNull(bossIsland.getPlayerIn());
        bossIsland.enterIsland(p1);
        assertEquals(p1, bossIsland.getPlayerIn());
        bossIsland.enterIsland(p2);
        assertNotEquals(p1, bossIsland.getPlayerIn());
    }

    @Test
    public void pickedTest(){
        assertNull(passeur.getPlayer());
        assertEquals(1, bossIsland.getBossCards().size());
        bossIsland.pickedBy(passeur, p1);
        assertEquals(p1, passeur.getPlayer());
        assertEquals(0, bossIsland.getBossCards().size());
    }

    @Test
    public void cloneTest() throws Exception{
        assertEquals(leftCards, normalIsland.clone().getLeftCards());
        assertEquals(bossCards, bossIsland.clone().getBossCards());
        assertEquals(rightCards, bossIsland.clone().getRightCards());
    }

}