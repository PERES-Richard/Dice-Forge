package action;

import card.Card;
import game.Game;
import player.Player;

public class ActionCard extends Action {

    private Card card;

    ActionCard(Game game, Player player, Card card){
        super(game, player);
        this.card = card;
    }

    Card getCard(){
        return card;
    }

    @Override
    public void doAction() {
        getPlayer().buyCard(card);
    }
}
