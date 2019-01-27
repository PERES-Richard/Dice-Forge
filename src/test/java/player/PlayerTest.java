package player;

import card.*;
import effect.*;
import face.Face;
import face.FaceAnd;
import game.Game;
import game.Resource;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player j1;

    @Before
    public void setup() {
        Game game = new Game("RandBot", "RandBot", "EasyBot");
        j1 = game.getPlayers().get(0);
    }

    @Test
    public void testRollDice() {
        j1 = new EasyBot("Richard", 0);
        j1.rollDice();
        /*assertTrue(j1.getGloryPoints() > j.getGloryPoints() || j1.getGold() > j.getGold()
                || j1.getSolar() > j.getSolar() || j1.getLunar() > j.getLunar());*/
    }

    @Test
    public void testModifyResource(){
        j1.modifyResource(Resource.GLORY, 2);
        assertEquals(2, j1.getGloryPoints());
        j1.modifyResource(Resource.GOLD, 3);
        assertEquals(6, j1.getGold());
        j1.modifyResource(Resource.SOLAR, 3);
        assertEquals(3, j1.getSolar());
        j1.modifyResource(Resource.LUNAR, 3);
        assertEquals(3, j1.getLunar());
    }

    @Test
    public void testAffordableCards(){
        ArrayList<Card> aff;

        ArrayList<Card> af = new ArrayList<>();
        af.add(new ImmediateCard(CardName.LeCoffreDuForgeron, 2, new IncreaseChestEffect()));

        j1.modifyResource(Resource.LUNAR, 1);
        aff = j1.affordableCards();
        assertEquals(af, aff);

        af.add(new ImmediateCard(CardName.LesHerbesFolles, 2,
                new AddResourceEffect(Resource.GOLD, 3, new AddResourceEffect(Resource.LUNAR, 3))));
        j1.modifyResource(Resource.SOLAR, 1);
        aff = j1.affordableCards();
        //assertEquals(af, aff);


        // TODO : Complete with other Test
    }

    @Test
    public void testBuyCard() {
        j1.modifyResource(Resource.LUNAR, 1);
        j1.modifyResource(Resource.SOLAR, 1);
        ArrayList<Card> CardsToBuy = j1.affordableCards();
        //Card c  = new ReinforcementCard(CardName.LeMarteauDuForgeron, 0);
        assertTrue(j1.buyCard(CardsToBuy.get(0)));
        assertEquals(1, j1.getCards().size());

        assertTrue(j1.buyCard(CardsToBuy.get(1)));
        assertEquals(2, j1.getCards().size());
    }

    @Test
    public void testAffordableFaces(){
        assertEquals(3, j1.getGold());
        ArrayList<Face> aff;

        final Face gold3 = new FaceAnd(0, 3, 0, 0);
        final Face gold4 = new FaceAnd(0, 4, 0, 0);
        final Face lunar1 = new FaceAnd(0, 0, 0, 1);
        final Face solar1 = new FaceAnd(0, 0, 1, 0);
        ArrayList<Face> af = new ArrayList<>();
        /*for (int i=0; i<4; i++)*/ af.add(gold3);
        /*for (int i=0; i<4; i++)*/ af.add(lunar1);
        /*for (int i=0; i<4; i++)*/ //af.add(gold4);
        /*for (int i=0; i<4; i++)*/ af.add(solar1);

        aff = j1.affordableFaces();
        assertEquals(af.get(0), aff.get(0));
        //assertEquals(af.get(1), aff.get(2));
        //assertEquals(af.get(2), aff.get(1));

        j1.modifyResource(Resource.GOLD,-1);
        for (int i=0; i<4; i++) af.remove(gold4);
        for (int i=0; i<4; i++) af.remove(solar1);

        aff = j1.affordableFaces();
        assertEquals(af, aff);
    }

    @Test
    public void testBuyFace(){
        final Face gold3 = new FaceAnd(0, 3, 0, 0);

        assertTrue(j1.buyFace(gold3));

        //Not enough resources
        assertFalse(j1.buyFace(gold3));

        //Emptying the Pool
        j1.modifyResource(Resource.GOLD, 7);
        assertTrue(j1.buyFace(gold3));
        assertTrue(j1.buyFace(gold3));
        assertTrue(j1.buyFace(gold3));

        //Can't buy cause of an empty Pool
        assertFalse(j1.buyFace(gold3));
    }

    @Test
    public void testForge() {
        Face gold1 = new FaceAnd(0, 1, 0 ,0);
        Face gold3 = new FaceAnd(0, 3, 0, 0);
        Face gold5 = new FaceAnd(0, 5, 0, 0);
        Face lunar2 = new FaceAnd(0, 0, 0, 2);

        j1.forge(j1.getDice1(), gold1, gold3);
        assertTrue(j1.getDice1().hasFace(gold3));

        //Not enough GOLD
        j1.forge(j1.getDice1(), gold1, lunar2);
        assertFalse(j1.getDice1().hasFace(lunar2));

        //There's not the face in the dice
        j1.modifyResource(Resource.GOLD, 12);
        j1.forge(j1.getDice1(), gold5, lunar2);
        assertFalse(j1.getDice1().hasFace(lunar2));

        //Emptying the pool
        j1.forge(j1.getDice1(), gold1, gold3);
        j1.forge(j1.getDice1(), gold1, gold3);
        j1.forge(j1.getDice1(), gold1, gold3);

        //There's no more the face in pools
        j1.forge(j1.getDice2(), gold1, gold3);
        assertFalse(j1.getDice2().hasFace(gold3));
    }

    @Test
    public void testAddGloryFromCards() {
        j1.modifyResource(Resource.LUNAR, 6);
        j1.buyCard(new ImmediateCard(CardName.LeCoffreDuForgeron, 2, new IncreaseChestEffect()));
        j1.buyCard(new ImmediateCard(CardName.LeCoffreDuForgeron, 2, new IncreaseChestEffect()));
        j1.buyCard(new ImmediateCard(CardName.LeCoffreDuForgeron, 2, new IncreaseChestEffect()));

        j1.addGloryFromCards();

        assertEquals(6, j1.getGloryPoints());

    }

    @Test
    public void cloneTest() throws Exception{
        Player jclone = j1.clone();
        assertNotNull(jclone);
        assertEquals(j1.getChest(), jclone.getChest());
        assertEquals(j1.getCards(), jclone.getCards());
        assertEquals(j1.getDice1(), jclone.getDice1());
        assertEquals(j1.getDice2(), jclone.getDice2());
    }
}
