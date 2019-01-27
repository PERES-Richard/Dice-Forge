package player;

import game.Resource;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

public class ChestTest {
    private Chest c;

    @Before
    public void setup(){
        this.c = new Chest(3);
    }

    @Test
    public void testModifyGold() throws Exception {
        c.modifyGold(17);
        assertEquals(c.getMaxGold(), c.getGold());
        c.modifyGold(-5);
        assertEquals(7, c.getGold());
        c.modifyGold(3);
        assertEquals(10, c.getGold());
        try {
            c.modifyGold(-12);
            fail("Expected an Exception to be thrown");
        } catch (Exception ignored) {}
    }

    @Test
    public void testModifySolar() throws Exception {
        c.modifySolar(17);
        assertEquals(c.getMaxSolar(), c.getSolar());
        c.modifySolar(-5);
        assertEquals(1, c.getSolar());
        c.modifySolar(3);
        assertEquals(4, c.getSolar());
        try {
            c.modifySolar(-5);
            fail("Expected an Exception to be thrown");
        } catch (Exception ignored) {}
    }

    @Test
    public void testModifyLunar() throws Exception {
        c.modifyLunar(17);
        assertEquals(c.getMaxLunar(), c.getLunar());
        c.modifyLunar(-5);
        assertEquals(1, c.getLunar());
        c.modifyLunar(3);
        assertEquals(4, c.getLunar());
        try {
            c.modifyLunar(-5);
            fail("Expected an Exception to be thrown");
        } catch (Exception ignored) {}
    }

    @Test
    public void testIncreaseMax() {
        c.increaseMax(Resource.GOLD, 3);
        assertEquals(15, c.getMaxGold());
        c.increaseMax(Resource.SOLAR, 3);
        assertEquals(9, c.getMaxSolar());
        c.increaseMax(Resource.LUNAR, 3);
        assertEquals(9, c.getMaxLunar());
    }

    @Test
    public void cloneTest() throws Exception{
        assertEquals(3, c.clone().getGold());
    }
}
