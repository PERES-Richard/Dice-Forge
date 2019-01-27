package player;

import face.FaceAnd;
import game.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {
    private Dice dice1;
    private Dice dice2;

    @Before
    public void setup() {
        this.dice1 = new Dice(true);
        this.dice2 = new Dice(false);
    }

    @Test
    public void hasFaceTest() {
        assertTrue(dice1.hasFace(new FaceAnd(0 ,0, 1, 0)));
        assertTrue(dice2.hasFace(new FaceAnd(2, 0, 0, 0)));
        assertFalse(dice1.hasFace(new FaceAnd(0, 0, 3, 0)));
        assertFalse(dice2.hasFace(new FaceAnd(4, 0, 0, 0)));
    }

    @Test
    public void averageTest() {
        assertEquals(5.0/6.0, dice1.average(Resource.GOLD), 0.000001);
        assertEquals(2.0/6.0, dice2.average(Resource.GLORY), 0.000001);
    }

    @Test
    public void cloneTest() throws Exception{
        assertEquals(dice1.getFaces(), dice1.clone().getFaces());
    }
}