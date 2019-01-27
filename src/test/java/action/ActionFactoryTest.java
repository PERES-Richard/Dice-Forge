package action;

import game.Game;
import game.Resource;
import org.junit.Before;
import org.junit.Test;
import player.Player;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActionFactoryTest {
    private Game game;
    private Player player;

    @Before
    public void setup(){
        game = new Game("RandBot0", "RandBot1");
        game.setVerbose(false);
        player = game.getPlayers().get(0);
    }

    @Test
    public void generateActionsTest(){
        ArrayList<Action> actions = game.getActionFactory().generateActions(player);
        assertFalse(actions.isEmpty());
        actions.forEach(action -> assertEquals(game, action.getGame()));
        actions.forEach(action -> assertEquals(player, action.getPlayer()));
    }

    @Test
    public void generateActionsForClonesTest(){
        player.modifyResource(Resource.GOLD, 8); //Making sure that player has at least multiple actions to do

        ArrayList<Action> actions = game.getActionFactory().generateActionsForClones(player);
        assertFalse(actions.isEmpty());

        assertNotSame(game, actions.get(0).getGame());
        assertNotSame(actions.get(0).getGame(), actions.get(1).getGame());

        assertNotSame(player, actions.get(0).getPlayer());
        assertNotSame(actions.get(0).getPlayer(), actions.get(1).getPlayer());
    }
}
