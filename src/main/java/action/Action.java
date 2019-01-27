package action;

import game.Game;
import player.Dice;
import player.Player;

import static java.lang.System.exit;

/**
 * Actions are created by ActionFactory, processed by ActionManager (to find the best one) and used by Bots
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard PERES</a>
 */

public class Action {
    protected Player player;
    protected Game game;
    private Dice[] newDices = new Dice[2];
    //private boolean replay;

    Action(Game game, Player p){
        this.game = game;
        this.player = p;
        setNewDices();
    }

    Player getPlayer(){
        return player;
    }

    public Game getGame() {
        return game;
    }

    Dice[] getNewDices(){
        return newDices;
    }

    void setNewDices() {
        try {
            newDices[0] = player.getDice1().clone();
            newDices[1] = player.getDice2().clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Should never happen");
            exit(-2);
        }
    }

    /**
     * bot will call this method to play
     */
    public void doAction(){
        if (getGame().verbose){
            System.out.println(getPlayer().getName() + " does nothing");
        }
    }

    void portToGameAndPlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

}
