package card;

import effect.AddResourceEffect;
import game.Resource;
import player.EasyBot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    player.Player j1;
    NoEffectCard c1;

    @Before
    public void setUp() throws Exception {
        j1 = new EasyBot("Tester",1);
        c1 = new NoEffectCard(CardName.LesHerbesFolles, 12);
    }

    @Test
    public void testCard(){
        assertNotNull(c1);
        assertEquals(CardName.LesHerbesFolles, c1.getName());
        assertEquals(12, c1.getGlory());
        assertEquals(null, c1.getPlayer());
        c1.pickBy(j1);
        assertEquals(j1, c1.getPlayer());
    }

    @Test
    public void testImmediateCard(){
        Card c2 = new ImmediateCard(CardName.LesHerbesFolles,2, new AddResourceEffect(Resource.LUNAR, 5));
        assertNotNull(c2);
        assertEquals(0, j1.getLunar());
        c2.pickBy(j1);
        assertEquals(5, j1.getLunar());
    }

    @Test
    public void testNoEffectCard(){
        Card c5 = new NoEffectCard(CardName.LesHerbesFolles, 6);
        assertNotNull(c5);
        c5.pickBy(j1);
        assertEquals(0, j1.getGloryPoints());
        j1.addGloryFromCards();
        assertEquals(6, j1.getGloryPoints());

    }

}