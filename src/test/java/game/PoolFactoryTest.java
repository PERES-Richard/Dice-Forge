package game;

import face.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PoolFactoryTest {
    private PoolFactory pftest;

    @Before
    public void setUp() throws Exception {
        pftest = new PoolFactory(3);
    }

    @Test
    public void instantiateTest(){
        assertNotNull(pftest);
        ArrayList<Pool> pools =  pftest.instantiate();
        assertNotNull(pools);
        assertEquals(4, pools.get(0).getFaces().size());
    }

    @Test
    public void instantiatePool1(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(0);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,3,0,0), pool.getAFace());
        assertEquals(2, pool.getPrice());
    }

    @Test
    public void instantiatePool1bis(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(1);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,0,0,1), pool.getAFace());
        assertEquals(2, pool.getPrice());
    }

    @Test
    public void instantiatePool2(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(2);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,4,0,0), pool.getAFace());
        assertEquals(3, pool.getPrice());
    }

    @Test
    public void instantiatePool2bis(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(3);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,0,1,0), pool.getAFace());
        assertEquals(3, pool.getPrice());
    }


    @Test
    public void instantiatePool3(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(4);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,6,0,0), pool.getFaces().get(0));
        assertEquals(new FaceAnd(1,0,1,0), pool.getFaces().get(1));
        assertEquals(new FaceAnd(0,2,0,1), pool.getFaces().get(2));
        assertEquals(new FaceOr(0,1,1,1), pool.getFaces().get(3));
        assertEquals(4, pool.getPrice());
    }

    @Test
    public void instantiatePool4(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(5);
        assertNotNull(pool);
        assertEquals(new FaceOr(2,3,0,0), pool.getAFace());
        assertEquals(5, pool.getPrice());
    }

    @Test
    public void instantiatePool5(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(6);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,0,0,2), pool.getAFace());
        assertEquals(6, pool.getPrice());
    }

    @Test
    public void instantiatePool6(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(7);
        assertNotNull(pool);
        assertEquals(new FaceAnd(0,0,2,0), pool.getAFace());
        assertEquals(8, pool.getPrice());
    }

    @Test
    public void instantiatePool6bis(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(8);
        assertNotNull(pool);
        assertEquals(new FaceAnd(3,0,0,0), pool.getAFace());
        assertEquals(8, pool.getPrice());
    }

    @Test
    public void instantiatePool7(){
        ArrayList<Pool> pools =  pftest.instantiate();
        Pool pool = pools.get(9);
        assertNotNull(pool);
        assertEquals(new FaceAnd(4,0,0,0), pool.getFaces().get(0));
        assertEquals(new FaceAnd(1,1,1,1), pool.getFaces().get(1));
        assertEquals(new FaceAnd(2,0,0,2), pool.getFaces().get(2));
        assertEquals(new FaceOr(0,2,2,2), pool.getFaces().get(3));
        assertEquals(12, pool.getPrice());
    }
}