package action;

import game.Game;
import game.Resource;
import player.Dice;
import player.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class ComplexActionManager extends SimpleActionManager {
    private final int stepsAhead;
    //private int[] potentialResource = {0, 0, 0, 0};
    private int potentialGlory;

    public ComplexActionManager(Game game, int stepsAhead){
        super(game);
        potentialGlory = 0;
        this.stepsAhead = stepsAhead;
    }

    public ComplexActionManager(Game game, int roundLeft, int stepsAhead){
        super(game, roundLeft);
        potentialGlory = 0;
        this.stepsAhead = stepsAhead;
    }

    @Override
    public void processActions(Player player) {
        ArrayList<Action> actions;
        if (stepsAhead == 0 || getRoundLeft() == 0) {
            actions = getGame().getActionFactory().generateActions(player);
            Action bestAction = findBestAction(mapActions(actions));
            setBestAction(bestAction);
            potentialGlory = calculatePotentialGlory(bestAction);
        } else {
            try {
                Game scalableGame = getGame().clone();
                scalableGame.setVerbose(false);
                Player scalablePlayer = player.clone();
                scalablePlayer.setGame(scalableGame);

                actions = scalableGame.getActionFactory().generateActionsForClones(scalablePlayer);

                HashMap<Action, Integer> map = recursivelyMapActions(actions);
                Action a;
                
                if(findBestAction(map) == null)
                	a= new Action(scalableGame, player);
                else
                	a = findBestAction(map);
                
                setBestAction(a);
                potentialGlory = map.get(a);

                getBestAction().portToGameAndPlayer(getGame(), player);

            } catch (CloneNotSupportedException e) {
                System.err.println("Should never happen");
                exit(-2);
            }
        }
        advanceOneRound();
    }

    private HashMap<Action, Integer> recursivelyMapActions(ArrayList<Action> actions) {
        HashMap<Action, Integer> mappedActions = new HashMap<>();
        int thisTurnPotentialGlory;
        ComplexActionManager recursiveActionManager;

        for (Action action: actions) {
            thisTurnPotentialGlory = simulatePlayerTurn(action);
            recursiveActionManager = new ComplexActionManager(action.getGame(), getRoundLeft()-1, stepsAhead - 1);
            recursiveActionManager.processActions(action.getPlayer());
            mappedActions.put(action, recursiveActionManager.potentialGlory + thisTurnPotentialGlory);
        }
        return mappedActions;
    }

    private int simulatePlayerTurn(Action action) {

        action.doAction();

        int increase;
        int glory = 0;
        int nbPlayers = action.getGame().getPlayers().size();
        for (Resource r : Resource.values()){
            increase = 0;
            for (Dice dice : action.getPlayer().getDices()) {
                increase += (int) (((nbPlayers == 2)?4:nbPlayers) * dice.average(r));
                if (r == Resource.GLORY)
                    glory = increase;
                else
                    action.getPlayer().modifyResource(r, increase);
            }
        }
        if (action instanceof ActionCard)
            glory += ((ActionCard) action).getCard().getGlory() + 5;

        return glory;
    }

}
