package player;

import java.util.ArrayList;

import action.ComplexActionManager;
import face.Face;
import face.FaceAbysalMirror;
import face.FaceAnd;
import face.FaceOr;
import face.FaceX3;
import game.Game;
import game.Resource;

public class MediumBot extends Player implements Cloneable {

    private ComplexActionManager actionManager;

    public MediumBot(String name, int order){
        super(name, order);
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
        this.actionManager = new ComplexActionManager(game, 1);
    }

    @Override
    public void plays() {
    	super.plays();
        actionManager.processActions(this);
        actionManager.getBestAction().doAction();
    }

	@Override
	public int findBestResourceToAdd(ArrayList<Resource> res, ArrayList<Integer> amount) {
		ArrayList<Integer> unwanted = new ArrayList<>();
		ArrayList<Integer> maybe = new ArrayList<>();
		ArrayList<Integer> overflow = new ArrayList<>();
		ArrayList<Integer> sure = new ArrayList<>();
		ArrayList<Integer> left = new ArrayList<>();
		
		for(int i=0; i<res.size(); i++) {
			if(getChest().getAmountLeftOf(res.get(i)) == 0) {
				unwanted.add(i);
			}
			else if(getChest().getAmountLeftOf(res.get(i)) < amount.get(i)) {
				maybe.add(i);
				overflow.add(-(getChest().getAmountLeftOf(res.get(i)) - amount.get(i)));
			}
			else {
				sure.add(i);
				left.add(getChest().getAmountLeftOf(res.get(i)) - amount.get(i));
			}
		}
		
		if(!sure.isEmpty()) {
			if(sure.size() == 1)
				return sure.get(0);
			else {
				int max = left.get(0), idmax = 0;
				for(int i=1; i<left.size();i++) {
					if(left.get(i) > max) {
						max = left.get(i);
						idmax = i;
					}
				}
				return sure.get(idmax);
			}
		}
		else if(!maybe.isEmpty()) {
			if(maybe.size() == 1)
				return maybe.get(0);
			else {
				ArrayList<Integer> indexSolar = new ArrayList<>();				
				ArrayList<Integer> indexLunar = new ArrayList<>();				
				
				for(int i = 0; i<maybe.size(); i++) {
					if(res.get(maybe.get(i)) == Resource.SOLAR || res.get(maybe.get(i)) == Resource.LUNAR)
						indexSolar.add(maybe.get(i));
					if(res.get(maybe.get(i)) == Resource.LUNAR)
						indexLunar.add(maybe.get(i));
				}
				if(indexSolar.size() == 0 && indexLunar.size() == 0)
					return maybe.get(rand.nextInt(maybe.size()));
				else {
					if(getChest().getAmountLeftOf(Resource.SOLAR) > getChest().getAmountLeftOf(Resource.LUNAR))
						return indexSolar.get(rand.nextInt(indexSolar.size()));
					else
						return indexLunar.get(rand.nextInt(indexLunar.size()));
					}
				}
			}
		
		else if(res.contains(Resource.GOLD))
			return res.indexOf(Resource.GOLD);
					
		return rand.nextInt(res.size());
	}

	@Override
	public boolean shouldBuyGlory(int cost, int amount) {
		
		if(getChest().getAmountLeftOf(Resource.GOLD) == getChest().getMaxGold())
			return false;
		
		int nbPlayers = getGame().getPlayers().size();
		int turn = getGame().getTurn();
		
		if(nbPlayers == 3)
			if(turn >= 8 || getChest().getAmountLeftOf(Resource.GOLD) < 1)
				return true;
			else return false;
		else 
			if(turn >= 7 || getChest().getAmountLeftOf(Resource.GOLD) < 1)
				return true;
			else return false;
	}

	@Override
	public Dice bestDiceToRoll() {
		int betterD1 = 0;
		int betterD2 = 0;
		
		Dice d1 = getDice1();
		Dice d2 = getDice2();
		
		for(int i = 0; i < d1.getFaces().size(); i++) {
			if(d1.getFace(i).getClass().equals(FaceX3.class))
				betterD1 -= 3;
			else if(d1.getFace(i).getClass().equals(FaceAbysalMirror.class))
				betterD1 += 3;
			else if((d1.getFace(i).getClass().equals(FaceOr.class)))
				betterD1 += 2;
			else if((d1.getFace(i).getClass().equals(FaceAnd.class)))
				betterD1 += d1.getFace(i).getResources()[0] + d1.getFace(i).getResources()[1] + d1.getFace(i).getResources()[2] + d1.getFace(i).getResources()[3];
		}
		
		for(int i = 0; i < d2.getFaces().size(); i++) {
			if(d2.getFace(i).getClass().equals(FaceX3.class))
				betterD2 -= 3;
			else if(d2.getFace(i).getClass().equals(FaceAbysalMirror.class))
				betterD2 += 3;
			else if((d2.getFace(i).getClass().equals(FaceOr.class)))
				betterD2 += 2;
			else if((d2.getFace(i).getClass().equals(FaceAnd.class)))
				betterD2 += d1.getFace(i).getResources()[0] + d1.getFace(i).getResources()[1] + d1.getFace(i).getResources()[2] + d1.getFace(i).getResources()[3];
		}
		
		if(betterD1 > betterD2)
			return d1;
		else
			return d2;
	}

	@Override
	public Dice bestDiceToForgeOn() {
		if(getDice1().equals(bestDiceToRoll()))
			return getDice2();
		else return getDice1();
	}

	@Override
	public Face findBestFaceToAdd(ArrayList<Face> faces) {
		int best = 0;
		int bestNbRessource = 0;
		
		for(int i=0; i<faces.size(); i++) {
			if(faces.get(i).getClass().equals(FaceAnd.class)) {
				int total = faces.get(i).getResources()[0] + faces.get(i).getResources()[1] + faces.get(i).getResources()[2] + faces.get(i).getResources()[3];
				if(total > bestNbRessource) {
					bestNbRessource = total;
					best = i;
				}
			}
		}
		
		return faces.get(best);
	}
	
	@Override
	public Face bestFace() {
		Dice d;
		
		if(bestDiceToForgeOn().equals(getDice1()))
			d = getDice1();
		else d = getDice2();
		
		int best = 0;
		int bestNbRessource = d.getFaces().get(0).getResources()[0] + d.getFaces().get(0).getResources()[1] + d.getFaces().get(0).getResources()[2] + d.getFaces().get(0).getResources()[3];
		
		for(int i=1; i<d.getFaces().size(); i++) {
			if(d.getFaces().get(i).getClass().equals(FaceAnd.class)) {
				int total = d.getFaces().get(i).getResources()[0] + d.getFaces().get(i).getResources()[1] + d.getFaces().get(i).getResources()[2] + d.getFaces().get(i).getResources()[3];
				if(total < bestNbRessource) {
					bestNbRessource = total;
					best = i;
				}
			}
		}
		
		return d.getFace(best);
	}
}
