package action;

import game.Game;
import game.Resource;
import player.Dice;
import player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Bots will be able to get the best Action to do
 *
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard PERES</a>
 */

public class SimpleActionManager {
    private Action bestAction; //best Action for now
    private int roundLeft;
    private Game game;

    SimpleActionManager(Game game){
        this.game = game;
        this.roundLeft = game.getNbRounds() - 1;
    }

    SimpleActionManager(Game game, int roundLeft){
        this.game = game;
        this.roundLeft = roundLeft;
    }

    int getRoundLeft() {
        return roundLeft;
    }

    Game getGame() {
        return game;
    }

    void setBestAction(Action action){
        this.bestAction = action;
    }

    public Action getBestAction() {
        return bestAction;
    }

    void advanceOneRound(){
        roundLeft--;
    }

    int calculatePotentialGlory(Action action){
        int result = 0;

        int nbOfTimes = (roundLeft-1) * game.getPlayers().size() * ((game.getPlayers().size() == 2)?2:1);
        for (Dice dice : action.getNewDices())
            result += nbOfTimes * dice.average(Resource.GLORY);

        if (action instanceof ActionCard)
            result += ((ActionCard)action).getCard().getGlory();

        return result;
    }

    /**
     * Creates all the actions available for a player and finds the one that will give the most glory
     * @param player
     */
    public void processActions(Player player){
        ArrayList<Action> actions = game.getActionFactory().generateActions(player);
        setBestAction(findBestAction(mapActions(actions)));
        advanceOneRound();
    }

    /**
     * @param actions
     * @return a map that associate an action with the glory it will end up giving to a player
     */
    HashMap<Action, Integer> mapActions(ArrayList<Action> actions){
        HashMap<Action, Integer> mappedActions = new HashMap<>();

        for (Action action : actions)
                mappedActions.put(action, calculatePotentialGlory(action));

        return mappedActions;
    }


    /**
     *
     * @param mappedActions
     * @return the action that will end up giving the most glory to a player
     */
    Action findBestAction(HashMap<Action, Integer> mappedActions){
        HashMap.Entry<Action, Integer> maxEntry = null;

        for (HashMap.Entry<Action, Integer> entry : mappedActions.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;
            else if (!(maxEntry.getKey() instanceof ActionForge) && !(maxEntry.getKey() instanceof ActionCard) && entry.getValue().compareTo(maxEntry.getValue()) == 0)
                maxEntry = entry;
        }
        if(maxEntry != null)
        	bestAction = maxEntry.getKey();
        return bestAction;
    }

    /*int compareResources(HashMap.Entry<Action, Integer> entry1,HashMap.Entry<Action, Integer> entry2){

    }*/

}
