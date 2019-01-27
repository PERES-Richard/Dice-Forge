package player;

import card.Card;
import face.Face;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandBot extends Player {
    private final Random rand = new Random();

    public RandBot(String name, int i){
        super(name, i);
    }

    @Override
    public void plays() {
    	super.plays();
        ArrayList<Face> whatCanIForge = affordableFaces();
        ArrayList<Card> whatCanIBuy = affordableCards();

        if(whatCanIForge.size()!=0 && whatCanIBuy.size()!=0) {
            if (rand.nextInt(2) == 0)
            	randPlayFace(whatCanIForge);
            else
            	randPlayCard(whatCanIBuy);
        }
        else if (whatCanIBuy.size()!=0) {
        	randPlayCard(whatCanIBuy);
        }
        else if (whatCanIForge.size()!=0) {
        	randPlayFace(whatCanIForge);
        }
        else
        	System.out.println(getName() + " does nothing");
    }

    private void randPlayFace(ArrayList<Face> faces) {
        int whichNewFace = rand.nextInt(faces.size());
        int whichOldFace = rand.nextInt(6);
        if (faces.size() != 0) {
            if (rand.nextBoolean())
            	forge(getDice2(), getDice2().getFace(whichOldFace), faces.get(whichNewFace));
            else
            	forge(getDice1(), getDice1().getFace(whichOldFace), faces.get(whichNewFace));
        }
    }

    private void randPlayCard(ArrayList<Card> cards){
        int whichCard = rand.nextInt(cards.size());
        if (cards.size() != 0) {
            buyCard(cards.get(whichCard));
        }
    }
    
    @Override
    public Face findBestFaceToAdd(final ArrayList<Face> faces) {
    	Collections.shuffle(faces);
    	return faces.get(0);
	}
}
