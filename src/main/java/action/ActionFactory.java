package action;

import card.Card;
import face.Face;
import game.Game;
import player.Dice;
import player.Player;

import java.util.ArrayList;

import static java.lang.System.exit;

public class ActionFactory implements Cloneable{

    private Game game;

    public ActionFactory(Game game){
        this.game = game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    ArrayList<Action> generateActions(Player player) {
        ArrayList<Action> actions = new ArrayList<>();

        Dice[] dices = new Dice[]{player.getDice1(), player.getDice2()};

        ArrayList<Face> affordableFaces = player.affordableFaces();
        ArrayList<Card> affordableCards = player.affordableCards();

        ArrayList<Face> usedFaces = new ArrayList<>();

        for (Face newFace : affordableFaces){
            for (Dice dice : dices) {
                for (Face prevFace : dice.getFaces()) {
                    if (!usedFaces.contains(prevFace)){
                        actions.add(new ActionForge(game, player, new Face[]{prevFace, newFace}, dice));
                        usedFaces.add(prevFace);
                    }
                }
            }
            usedFaces.clear();
        }

        for (Card card : affordableCards){
            actions.add(new ActionCard(game, player, card));
        }

        actions.add(new Action(game, player));

        return actions;
    }

    ArrayList<Action> generateActionsForClones(Player player) {
        Player copyPlayer;
        Game copyGame;
        ArrayList<Action> actions = new ArrayList<>();

        ArrayList<Face> affordableFaces = player.affordableFaces();
        ArrayList<Card> affordableCards = player.affordableCards();

        ArrayList<Face> usedFaces = new ArrayList<>();

        Action action;
        for (Face newFace : affordableFaces){
            try {
                for (int i = 0; i < player.getDices().length; i++) {
                    Dice dice = player.getDices()[i];
                    for (int j = 0; j < dice.getFaces().size(); j++) {
                        Face prevFace = dice.getFaces().get(j);
                        if (!usedFaces.contains(prevFace)) {
                            copyGame = game.clone();
                            copyPlayer = player.clone();
                            copyPlayer.setGame(copyGame);
                            action = new ActionForge(game, player, new Face[]{prevFace, newFace}, dice);
                            action.portToGameAndPlayer(copyGame, copyPlayer);
                            actions.add(action);
                            usedFaces.add(prevFace);
                        }
                    }
                }
                usedFaces.clear();
            }
            catch (CloneNotSupportedException e){
                System.err.println("Should not happen");
                exit(-2);
            }
        }

        for (Card card : affordableCards){
            try {
                copyGame = game.clone();
                copyPlayer = player.clone();
                copyPlayer.setGame(copyGame);
                actions.add(new ActionCard(copyGame, copyPlayer, card));
            }
            catch (CloneNotSupportedException e){
                System.err.println("Should not happen");
                exit(-2);
            }
        }

        actions.add(new Action(game, player));

        return actions;
    }

    @Override
    public ActionFactory clone() throws CloneNotSupportedException{
        return (ActionFactory) super.clone();
    }

}
