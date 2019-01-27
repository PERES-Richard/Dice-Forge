package player;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ChestExceptionTest {

    private Chest c;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp(){
        this.c = new Chest(2);
    }

    @Test
    public void ExceptionGoldTest() throws Exception{
        exceptionRule.expectMessage("Not enough gold");
        c.modifyGold(-3);
    }

    @Test
    public void ExceptionSolarTest() throws Exception{
        exceptionRule.expectMessage("Not enough solar");
        c.modifySolar(-1);
    }

    @Test
    public void ExceptionLunarTest() throws Exception{
        exceptionRule.expectMessage("Not enough lunar");
        c.modifyLunar(-1);
    }

}