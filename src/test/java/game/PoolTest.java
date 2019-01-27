package game;

import face.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PoolTest {
    private Pool pooltest;
    private Face gloryface;
    @Before
    public void setUp() throws Exception {
        ArrayList<Face> faces = new ArrayList<>();
        gloryface = new FaceAnd(1,0,0,0);
        faces.add(gloryface);
        pooltest = new Pool(2, faces);
    }

    @Test
    public void constructorTest(){
        assertNotNull(pooltest);
        assertEquals(2, pooltest.getPrice());
        assertEquals(new FaceAnd(1,0,0,0),pooltest.getAFace());
    }

    @Test
    public void deleteFaceTest(){
        FaceOr faceor = new FaceOr(1,1,1,1);
        assertEquals(1, pooltest.getFaces().size());
        pooltest.deleteFace(gloryface);
        assertEquals(0, pooltest.getFaces().size());
        pooltest.getFaces().add(gloryface);
        pooltest.getFaces().add(faceor);
        assertEquals(2, pooltest.getFaces().size());
        pooltest.deleteFace();
        assertEquals(gloryface, pooltest.getFaces().get(0));
    }

    @Test
    public void hasResourceTest(){
        assertTrue(pooltest.hasResource(Resource.GLORY));
        assertFalse(pooltest.hasResource(Resource.GOLD));
    }

    @Test
    public void containsTest(){
        assertTrue(pooltest.contains(gloryface));
        pooltest.deleteFace();
        assertFalse(pooltest.contains(gloryface));
    }

    @Test
    public void cloneTest() throws Exception{
        assertEquals(pooltest.getFaces(), pooltest.clone().getFaces());
    }
}