package player;

import action.ComplexActionManager;
import game.Game;

public class EasyBot extends Player implements Cloneable {

    private ComplexActionManager actionManager;

    public EasyBot(String name, int order){
        super(name, order);
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
        this.actionManager = new ComplexActionManager(game,0);
    }

    @Override
    public void plays() {
    	super.plays();
        actionManager.processActions(this);
        actionManager.getBestAction().doAction();
    }
    
}
