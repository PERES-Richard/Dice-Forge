package action;

import card.Card;
import card.CardName;
import card.NoEffectCard;
import face.FaceAnd;
import game.Game;
import org.junit.Before;
import org.junit.Test;
import player.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ActionManagerTest {
    private Game game;
    private Player player;
    private SimpleActionManager actionManager;
    private Card card;
    private ActionCard actionCard;

    @Before
    public void setup(){
        game = new Game("RandBot0", "RandBot1");
        game.setVerbose(false);
        player = game.getPlayers().get(0);
        actionManager = new SimpleActionManager(game);
        card = new NoEffectCard(CardName.LeTyphon, 12);
        actionCard = new ActionCard(game, player, card);
    }

    @Test
    public void calculatePotentialGloryTest(){
        player.getDice1().getFaces().forEach(face -> player.getDice1().changeFace(face, new FaceAnd(1,0,0,0)));
        player.getDice2().getFaces().forEach(face -> player.getDice2().changeFace(face, new FaceAnd(1,0,0,0)));
        actionCard.setNewDices();

        assertEquals(((actionManager.getRoundLeft() - 1) * 2 * 2 * 2)+ card.getGlory(), actionManager.calculatePotentialGlory(actionCard));
    }

    @Test
    public void mapActionsTest(){
        ArrayList<Action> actions = game.getActionFactory().generateActions(player);//new ArrayList<>(Arrays.asList());
        HashMap<Action, Integer> mappedActions = actionManager.mapActions(actions);
        assertEquals(actions.size(), mappedActions.size() );
    }

    @Test
    public void findBestActionTest(){
        HashMap<Action, Integer> mappedAction = new HashMap<>();
        Action[] actionsArray = new Action[]{new Action(game, player),
                new Action(game, player),
                new Action(game, player),
                new Action(game, player)};
        mappedAction.put(actionsArray[0], 40);
        mappedAction.put(actionsArray[1], 50);
        mappedAction.put(actionsArray[2], 30);
        mappedAction.put(actionsArray[3], 20);

        Action bestAction = actionManager.findBestAction(mappedAction);
        assertEquals(actionsArray[1], bestAction);
    }
}
