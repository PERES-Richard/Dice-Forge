package action;

import face.Face;
import game.Game;
import player.Dice;
import player.Player;

import java.util.ArrayList;

public class ActionForge extends Action {

    private ArrayList<Face[]> facesToForge;
    private ArrayList<Dice> diceToForge;

    ActionForge(Game game, Player player, ArrayList<Face[]> facesToForge, ArrayList<Dice> diceToForge){
        super(game, player);
        this.facesToForge = facesToForge;
        this.diceToForge = diceToForge;
        forgeNewDices();
    }

    ActionForge(Game game, Player player, Face[] facesToForge, Dice diceToForge){
        super(game, player);
        this.facesToForge = new ArrayList<>();
        this.facesToForge.add(facesToForge);
        this.diceToForge = new ArrayList<>();
        this.diceToForge.add(diceToForge);
        forgeNewDices();
        //setPotentialGlory((newDices[0].average(Resource.GLORY) + newDices[1].average(Resource.GLORY)) * game.getNbRounds());
    }

    ArrayList<Face[]> getFacesToForge(){
        return facesToForge;
    }

    void forgeNewDices(){
        super.setNewDices();

        for(int i = 0; i < facesToForge.size(); i++){
            if(diceToForge.get(i) == player.getDice1())
                getNewDices()[0].changeFace(facesToForge.get(i)[0], facesToForge.get(i)[1]);
            else if(diceToForge.get(i) == player.getDice2())
                getNewDices()[1].changeFace(facesToForge.get(i)[0], facesToForge.get(i)[1]);
        }
    }

    /**
     * bot will call this method to play
     */
    @Override
    public void doAction(){
        for(int i = 0; i < facesToForge.size(); i++){
            getPlayer().forge(diceToForge.get(i), facesToForge.get(i)[0], facesToForge.get(i)[1]);
        }
    }

    @Override
    void portToGameAndPlayer(Game game, Player player) {
        super.portToGameAndPlayer(game, player);
        for (int i = 0; i < diceToForge.size(); i++) {
            if (diceToForge.get(i).equals(player.getDice1())){
                diceToForge.set(i, player.getDice1());
            }else
                diceToForge.set(i, player.getDice2());
        }
    }
}
