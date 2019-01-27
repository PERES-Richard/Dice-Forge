package game;

import org.junit.Test;
import card.CardName;
import org.junit.Before;
import player.EasyBot;
import player.Player;
import player.RandBot;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IslandFactoryTest {
    private ArrayList<Player> players;
    private EasyBot p1;
    private RandBot p2;
    private IslandFactory fctest;

    @Before
    public void setUp() throws Exception {
        p1 = new EasyBot("EB",0);
        p2 = new RandBot("RB", 1);
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        fctest = new IslandFactory(2,players);
    }

    @Test
    public void instantiateTest(){
        assertNotNull(fctest);
        ArrayList<Island> islands = fctest.instantiate();
        assertNotNull(islands);
        assertEquals(2, islands.get(1).getLeftCards().size());
        players.add(new EasyBot("EB2",2));
        islands = fctest.instantiate();
        assertEquals(2, islands.get(1).getLeftCards().size());
        fctest = new IslandFactory(players.size(), players);
        islands = fctest.instantiate();
        assertEquals(3, islands.get(1).getLeftCards().size());
    }
    @Test
    public void instantiateLunar1Test(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.LUNAR,islands.get(0).getRes());
        Island Lunar1 = islands.get(0);
        assertNotNull(Lunar1);
        assertFalse(Lunar1.isBossIsland());
        assertEquals(1,Lunar1.getLeftCardsCost());
        assertEquals(1,Lunar1.getRightCardsCost());
        assertFalse(Lunar1.contains(CardName.LeMarteauDuForgeron));
        assertTrue(Lunar1.contains(CardName.LeCoffreDuForgeron));
    }

    @Test
    public void instantiateSolar1Test(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.SOLAR,islands.get(1).getRes());
        Island Solar1 = islands.get(1);
        assertNotNull(Solar1);
        assertFalse(Solar1.isBossIsland());
        assertEquals(1,Solar1.getLeftCardsCost());
        assertEquals(1,Solar1.getRightCardsCost());
        assertTrue(Solar1.contains(CardName.LAncien));
        assertTrue(Solar1.contains(CardName.LesHerbesFolles));
    }

    @Test
    public void instantiateLunar2Test(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.LUNAR,islands.get(2).getRes());
        Island Lunar2 = islands.get(2);
        assertNotNull(Lunar2);
        assertFalse(Lunar2.isBossIsland());
        assertEquals(2,Lunar2.getLeftCardsCost());
        assertEquals(3,Lunar2.getRightCardsCost());
        assertTrue(Lunar2.contains(CardName.LesSabotsDArgent));
        assertTrue(Lunar2.contains(CardName.LesSatyres));
    }

    @Test
    public void instantiateSolar2Test(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.SOLAR,islands.get(3).getRes());
        Island Solar2 = islands.get(3);
        assertNotNull(Solar2);
        assertFalse(Solar2.isBossIsland());
        assertEquals(2,Solar2.getLeftCardsCost());
        assertEquals(3,Solar2.getRightCardsCost());
        assertTrue(Solar2.contains(CardName.LesAilesDeLaGardienne));
        assertTrue(Solar2.contains(CardName.LeMinotaure));
    }

    @Test
    public void instantiateLunar3Test(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.LUNAR,islands.get(4).getRes());
        Island Lunar3 = islands.get(4);
        assertNotNull(Lunar3);
        assertFalse(Lunar3.isBossIsland());
        assertEquals(4,Lunar3.getLeftCardsCost());
        assertEquals(5,Lunar3.getRightCardsCost());
        assertTrue(Lunar3.contains(CardName.LePasseur));
        assertTrue(Lunar3.contains(CardName.LeCasqueDInvisibilité));
    }

    @Test
    public void instantiateSolar3Test(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.SOLAR,islands.get(5).getRes());
        Island Solar3 = islands.get(5);
        assertNotNull(Solar3);
        assertFalse(Solar3.isBossIsland());
        assertEquals(4,Solar3.getLeftCardsCost());
        assertEquals(5,Solar3.getRightCardsCost());
        assertTrue(Solar3.contains(CardName.LaMéduse));
        assertTrue(Solar3.contains(CardName.LeMiroirAbyssal));
    }

    @Test
    public void instantiateBossTest(){
        ArrayList<Island> islands = fctest.instantiate();
        assertEquals(Resource.SOLAR,islands.get(6).getRes());
        assertEquals(Resource.LUNAR,islands.get(6).getRes2());
        Island boss = islands.get(6);
        assertNotNull(boss);
        assertTrue(boss.isBossIsland());
        assertEquals(6,boss.getLeftCardsCost());
        assertEquals(5,boss.getBossCardsCost());
        assertEquals(6,boss.getRightCardsCost());
        assertTrue(boss.contains(CardName.LEnigme));
        assertTrue(boss.contains(CardName.LaPince));
        assertTrue(boss.contains(CardName.LHydre));
    }
}