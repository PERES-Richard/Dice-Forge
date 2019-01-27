package game;


import static org.junit.Assert.*;

import face.Face;
import face.FaceAnd;
import player.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class GameTest {
	private Player j1, j2;
	private Pool p;
	
	private Game game;
	@Before
	public void setup() {
		game = new Game("RandBot", "EasyBot");
		j1 = game.getPlayers().get(0);
		j2 = game.getPlayers().get(1);

		ArrayList<Face> pool = new ArrayList<>();
		for(int i=0; i<4; i++) pool.add(new FaceAnd(1, 0, 0, 0));
		p = new Pool(2, pool);
	}

	@Test
	public void testWinner() {
		j1.modifyResource(Resource.GLORY,5);
		j2.modifyResource(Resource.GLORY, 7);
		assertEquals(j2, game.findWinner().get(0));
		assertEquals(5, j1.getGloryPoints());
	}

	@Test
	public void testGold() {
		assertEquals(2, j2.getGold());
		j2.modifyResource(Resource.GOLD, 1);
		assertEquals(3, j2.getGold());
	}

	@Test
	public void testSolar() {
		assertEquals(0, j2.getSolar());
		j2.modifyResource(Resource.SOLAR, 1);
		assertEquals(1, j2.getSolar());
	}

	@Test
	public void testLunar() {
		assertEquals(0, j2.getLunar());
		j2.modifyResource(Resource.LUNAR, 1);
		assertEquals(1, j2.getLunar());
	}

	@Test
	public void testPool() {
		assertEquals(2, p.getPrice());
		p.deleteFace();
		assertEquals(3, p.getFaces().size());
	}

	/*@Test
	public void testCard(){
		/*Card cardTest = new Card(2, Resource.SOLAR, 3);
		assertNotNull(cardTest);
		assertEquals(Resource.SOLAR, cardTest.getCostResource());
		assertEquals(2, cardTest.getCost());
		assertEquals(3, cardTest.getGain());
	}*/

	/*@Test
	public void testRound() {
		/*j1.increaseResource(Resource.GOLD, 6, false);
		j1.plays(game);
		assertEquals(1, j1.getGold());
		assertEquals(3, game.getPools().get(2).getFaces().size()); //Todo : Testing differently.
	}*/
}
