package face;

import org.junit.Before;
import org.junit.Test;
import player.EasyBot;
import player.Player;

import static org.junit.Assert.*;

public class FaceTest {
    private Face fa, fao, fam, facex;
    private Player p1;
    @Before
    public void setUp() throws Exception {
        fa = new FaceAnd(1,0,1,0);
        fao = new FaceOr(0,3,0,1);
        fam = new FaceAbysalMirror();
        facex = new FaceX3();
        p1 = new EasyBot("p1",0);
    }

    @Test
    public void faceAndTest(){
        assertNotNull(fa);
        assertEquals(0,p1.getGloryPoints());
        assertEquals(3, p1.getGold());
        assertEquals(0, p1.getSolar());
        assertEquals(0, p1.getLunar());
        p1.getDice1().getFaces().clear();
        p1.getDice2().getFaces().clear();
        p1.getDice1().getFaces().add(fa);
        p1.rollOneDice(p1.getDice1());
        p1.pickResources();
        assertEquals(1,p1.getGloryPoints());
        assertEquals(3, p1.getGold());
        assertEquals(1, p1.getSolar());
        assertEquals(0, p1.getLunar());
    }

    @Test
    public void faceOrTest(){
        assertNotNull(fao);
        assertEquals(0,p1.getGloryPoints());
        assertEquals(3, p1.getGold());
        assertEquals(0, p1.getSolar());
        assertEquals(0, p1.getLunar());
        p1.getDice1().getFaces().clear();
        p1.getDice2().getFaces().clear();
        p1.getDice1().getFaces().add(fao);
        p1.rollOneDice(p1.getDice1());
        p1.getDice1();
        p1.pickResources();
        assertEquals(0,p1.getGloryPoints());
        assertEquals(6, p1.getGold());
        assertEquals(0, p1.getSolar());
        assertEquals(0, p1.getLunar());
    }

    @Test
    public void faceAbysalMirrorTest(){
        assertNotNull(fam);
    }

    @Test
    public void facexTest(){
        assertNotNull(facex);
    }
}