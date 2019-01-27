package effect;

import face.Face;
import face.FaceAnd;
import game.*;
import player.*;
import game.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EffectTest {

    private Player j1;
    private Pool pool1;

    @Before
    public void setUp() {
        j1 = new EasyBot("Tester",1);

        ArrayList<Face> pool = new ArrayList<>();
        for(int i=0; i<4; i++) pool.add(new FaceAnd(0, 0, 0, 1));
        pool1 = new Pool(2, pool);
    }

    @Test
    public void testForgeEffect(){
        ForgeEffect fe = new ForgeEffect(pool1.getAFace());
        assertNotNull(fe);
        //fe.doEffect(j1, pool1.getAFace());

    }

    @Test
    public void testAddResourceEffect(){
        AddResourceEffect ade = new AddResourceEffect(Resource.SOLAR, 2);
        assertNotNull(ade);
        assertEquals(0, j1.getSolar());
        ade.doEffect(j1);
        assertEquals(2, j1.getSolar());
    }

    @Test
    public void testIncreaseChestEffect(){
        IncreaseChestEffect ice = new IncreaseChestEffect();
        assertEquals(6,j1.getChest().getMaxLunar());
        assertEquals(12,j1.getChest().getMaxGold());
        ice.doEffect(j1);
        assertEquals(9,j1.getChest().getMaxLunar());
        assertEquals(16,j1.getChest().getMaxGold());
        ice.doEffect(j1);
        assertEquals(12,j1.getChest().getMaxLunar());
        assertEquals(20,j1.getChest().getMaxGold());
    }

    @Test
    public void majorFavorEffectTest(){
        MajorFavorEffect mfe = new MajorFavorEffect();
        assertEquals(0, j1.getLunar());
        j1.getDice1().getFaces().clear();
        j1.getDice1().getFaces().add(new FaceAnd(0, 0, 0, 1));
        j1.getDice2().getFaces().clear();
        j1.getDice2().getFaces().add(new FaceAnd(0, 0, 0, 1));
        mfe.doEffect(j1);
        j1.pickResources();
        assertEquals(2, j1.getLunar());
    }

    @Test
    public void minotaurEffectTest() throws Exception{
        Player j2 = new EasyBot("Testing2",0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(j1);
        players.add(j2);
        MinotaurEffect me = new MinotaurEffect(players);
        j2.getDice1().getFaces().clear();
        j2.getDice1().getFaces().add(new FaceAnd(0, 0, 0, 1));
        j2.getDice2().getFaces().clear();
        j2.getDice2().getFaces().add(new FaceAnd(0, 0, 0, 1));
        j2.getChest().modifyLunar(4);
        Chest j2before = j2.getChest().clone();
        me.doEffect(j1);
        assertEquals(1, Integer.compare(j2before.getLunar(), j2.getChest().getLunar()));
    }
}