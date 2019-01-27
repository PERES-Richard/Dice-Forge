package action;

import card.Card;
import face.Face;
import game.Game;
import game.Resource;
import org.junit.Before;
import org.junit.Test;
import player.Player;

import static org.junit.Assert.*;

public class ActionTest {
    private Game game;
    private Player player;
    private Card card;
    private Face prevFace, newFace;
    private ActionCard actionCard;
    private ActionForge actionForge;

    @Before
    public void setup(){
        game = new Game("RandBot0", "RandBot1");
        game.setVerbose(false);
        player = game.getPlayers().get(0);
        card = game.getIslands().get(0).getRightCards().get(0);
        actionCard = new ActionCard(game, player, card);
        prevFace = player.getDice1().getFaces().get(0);
        newFace = game.getPools().get(0).getFaces().get(0);
        actionForge = new ActionForge(game, player, new Face[]{prevFace, newFace}, player.getDice1());
    }

    @Test
    public void ActionCardTest(){
        player.modifyResource(game.getIslands().get(0).getRes(), 8);
        assertTrue(player.getCards().isEmpty());
        actionCard.doAction();
        assertTrue(player.getCards().contains(card));
    }

    @Test
    public void ActionForge(){
        player.modifyResource(Resource.GOLD, 8);
        assertEquals(prevFace, player.getDice1().getFaces().get(0));
        actionForge.doAction();
        assertEquals(newFace, player.getDice1().getFaces().get(0));
    }

    @Test
    public void setNewDicesTest(){
        actionCard.setNewDices();

        assertNotSame(actionCard.getNewDices()[0], actionCard.getPlayer().getDices()[0]);
        assertEquals(actionCard.getNewDices()[0], actionCard.getPlayer().getDices()[0]);
    }

    @Test
    public void forgeNewDicesTest(){
        actionForge.forgeNewDices();

        assertNotEquals(actionForge.getNewDices()[0], actionForge.getPlayer().getDices()[0]);
    }
}
