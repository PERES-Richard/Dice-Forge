package player;

import card.*;
import face.*;
import game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * A player that plays in a game
 *
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand
 *         BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha
 *         CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain
 *         MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard
 *         PERES</a>
 *
 */

public abstract class Player implements Cloneable{

	private Game game;
	private String name;
	private Dice dice1;
	private Dice dice2;
	private Face face1;
	private Face face2;
	private int gloryPoints;
	private Chest chest;
	private ArrayList<Card> cards;
	Random rand = new Random();

	public Player(String name, int order) {
		this.name = name;
		this.dice1 = new Dice(true);
		this.dice2 = new Dice(false);
		this.gloryPoints = 0;
		this.chest = new Chest(3 - order);
		cards = new ArrayList<>();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public int getGloryPoints() {
		return gloryPoints;
	}

	public int getGold() {
		return chest.getGold();
	}

	public int getSolar() {
		return chest.getSolar();
	}

	public int getLunar() {
		return chest.getLunar();
	}

	public int getResource(Resource resource) {
	    if (resource == Resource.GLORY)
	        return getGloryPoints();
	    else if (resource == Resource.GOLD)
	        return getGold();
	    else if (resource == Resource.SOLAR)
	        return getSolar();
	    else
	        return getLunar();
    }

	public Dice getDice1() {
		return dice1;
	}

	public Dice getDice2() {
		return dice2;
	}

    private ArrayList<Face> getCurrentFaces() {
	    ArrayList<Face> faces = new ArrayList<>();
	    faces.add(face1);
	    faces.add(face2);
        return faces;
    }

    public Dice[] getDices(){
		return new Dice[]{dice1, dice2};
	}

	public Chest getChest() {
		return chest;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void rollOneDice(Dice dice) {
		if(!dice.equals(dice1) && !dice.equals(dice2))
			System.err.println("rollOneDice : not a dice of the player");
		else {
			Face face1 = dice.roll();
            modifyResourceFromFace(face1);
		}
	}
	
	public void rollDice() {
		face1 = dice1.roll();
		face2 = dice2.roll();
	}

	public void pickResources() {
        if (face1 instanceof FaceAbysalMirror) {
            ArrayList<Face> faces = getOtherPlayerFaces();
            face1 = chooseFace(faces);
        }
        if (face2 instanceof FaceAbysalMirror) {
            ArrayList<Face> faces = getOtherPlayerFaces();
            face2 = chooseFace(faces);
        }
        if (face1 instanceof FaceX3) {
            modifyResourceFromFace(face2);
            modifyResourceFromFace(face2);
        }
        if (face2 instanceof FaceX3){
            modifyResourceFromFace(face1);
            modifyResourceFromFace(face1);
        }
        modifyResourceFromFace(face1);
        modifyResourceFromFace(face2);
    }
	
	public ArrayList<Face> noEffectRoll() {
		Face face1 = dice1.roll();
		Face face2 = dice2.roll();
		return new ArrayList<>(Arrays.asList(face1, face2));
	}
	
	public void minotaurRoll() {
		Face face1 = dice1.roll();
		Face face2 = dice2.roll();
        if(face1 instanceof FaceX3) {
            modifyResourceFromFaceMinotaur(face2);
            modifyResourceFromFaceMinotaur(face2);
            modifyResourceFromFaceMinotaur(face2);
        }
        else if (face2 instanceof FaceX3){
            modifyResourceFromFaceMinotaur(face1);
            modifyResourceFromFaceMinotaur(face1);
            modifyResourceFromFaceMinotaur(face1);
        }
        else {
            modifyResourceFromFaceMinotaur(face1);
            modifyResourceFromFaceMinotaur(face2);
        }
	}

	public float simulateRoll(int nbOfTimes, Resource r){
		return nbOfTimes * (dice1.average(r) + dice2.average(r));
	}

	public void modifyResourceFromFace(Face face) {
	    if (face instanceof FaceAnd) {
            int[] resources = face.getResources();
			modifyResource(Resource.GLORY, resources[0]);
			modifyResource(Resource.GOLD, resources[1]);
			modifyResource(Resource.SOLAR, resources[2]);
			modifyResource(Resource.LUNAR, resources[3]);
		}
		else if (face instanceof FaceOr) {
            int[] resources = face.getResources();
            Resource res = chooseResource(resources);
            modifyResource(res, face.getAmountOf(res));
        }
    }

	private void modifyResourceFromFaceMinotaur(Face face){
        int[] resources = face.getResources();
        if (face instanceof FaceAnd) {
            modifyResourceMinotaur(Resource.GLORY, -resources[0]);
            modifyResourceMinotaur(Resource.GOLD, -resources[1]);
            modifyResourceMinotaur(Resource.SOLAR, -resources[2]);
            modifyResourceMinotaur(Resource.LUNAR, -resources[3]);
        }
        else if (face instanceof FaceOr) {
            Resource res = chooseResource(resources);
            modifyResourceMinotaur(res, -face.getAmountOf(res));
        }
    }

    private ArrayList<Face> getOtherPlayerFaces() {
	    ArrayList<Face> faces = new ArrayList<>();
	    for (Player p : game.getPlayers()) {
	        if (this != p )
	            faces.addAll(p.getCurrentFaces());
        }
        return faces;
    }

    /**
     * @param amounts Array of int in this order : [glory, gold, solar, lunar]
     * @return the wanted resource
     */
    private Resource chooseResource(int[] amounts) {
        return Resource.GOLD;
    } // TODO : do it for each Bot

    private Face chooseFace(ArrayList<Face> faces) {
        return faces.get(0);
    } // TODO : do it for each Bot

	/**
	 * @param resToChange Resource to be changed
	 * @param amount      Value of the change
	 */
	public void modifyResource(Resource resToChange, int amount) {
		try {
			switch (resToChange) {
			case GOLD:
				chest.modifyGold(amount);
				break;
			case GLORY:
				if(amount>0 || gloryPoints + amount > 0)
					gloryPoints += amount;
				else
					gloryPoints = 0;
				break;
			case SOLAR:
				chest.modifySolar(amount);
				break;
			case LUNAR:
				chest.modifyLunar(amount);
			}
		} catch (Exception e) {
			System.err.println("|| Not enough " + resToChange.toString() + " ||" + "resTo = " + resToChange + " amount = " + amount + " "+ getGold() + " "+ getSolar() + " "+ getLunar());
		}
	}

	private void modifyResourceMinotaur(Resource resToChange, int amount) {
		switch (resToChange) {
			case GOLD:
				chest.modifyGoldWithoutException(amount);
				break;
			case GLORY:
				if(gloryPoints + amount > 0)
					gloryPoints += amount;
				else
					gloryPoints = 0;
				break;
			case SOLAR:
				chest.modifySolarWithoutException(amount);
				break;
			case LUNAR:
				chest.modifyLunarWithoutException(amount);
		}
	}
	
	/**
	 * @return An ArrayList containing all the cards that the player can buy.
	 */
    public ArrayList<Card> affordableCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Island island : game.getIslands()) {
            Card card;
            if(island.isBossIsland()){
                ArrayList<Card> bossCards = island.getBossCards();
                if(bossCards.size() != 0){
                    if(getSolar() >= island.getBossCardsCost() && getLunar() >= island.getBossCardsCost()){
                        card = island.getBossCards().get(0);
                        if (!cards.contains(card))
                        	cards.add(card);
                    }
                }
            }
            else{
                ArrayList<Card> leftCards = island.getLeftCards();
                ArrayList<Card> rightCards = island.getRightCards();
                if(island.getRes() == Resource.SOLAR) {
                    if (leftCards.size() != 0) {
                        card = island.getLeftCards().get(0);
                        if (getSolar() >= island.getLeftCardsCost())
							if (!cards.contains(card))
								cards.add(card);
                    }
                    if (rightCards.size() != 0) {
                        card = island.getRightCards().get(0);
                        if (getSolar() >= island.getRightCardsCost())
							if (!cards.contains(card))
								cards.add(card);
                    }
                }
                else if(island.getRes() == Resource.LUNAR) {
                    if (leftCards.size() != 0) {
                        card = island.getLeftCards().get(0);
                        if (getLunar() >= island.getLeftCardsCost())
							if (!cards.contains(card))
								cards.add(card);
                    }
                    if (rightCards.size() != 0) {
                        card = island.getRightCards().get(0);
                        if (getLunar() >= island.getRightCardsCost())
							if (!cards.contains(card))
								cards.add(card);
                    }
                }
            }
        }
        return cards;
    }

    public boolean buyCard(Card card) {
    	boolean bought = false;

        for (Island island : game.getIslands()) {
            if (island.contains(card)) {
                if (island.isBossIsland()) {
                    if (island.getLeftCards().contains(card) && island.getCost(card) <= getResource(island.getRes())) {
                        modifyResource(island.getRes(), -island.getCost(card));
                        bought = true;
                    } else if (island.getRightCards().contains(card) && island.getCost(card) <= getResource(island.getRes2())) {
                        modifyResource(island.getRes2(), -island.getCost(card));
                        bought = true;
                    } else if (island.getCost(card) <= getResource(island.getRes()) && island.getCost(card) <= getResource(island.getRes2())){
                        modifyResource(island.getRes(), -island.getBossCardsCost());
                        modifyResource(island.getRes2(), -island.getBossCardsCost());
                        bought = true;
                    }
                }
                else if (island.getCost(card) <= getResource(island.getRes())){
                    modifyResource(island.getRes(), -island.getCost(card));
                    bought = true;
                }
                if (bought) {
                    island.pickedBy(card, this);
                    island.enterIsland(this);
                }
            }
        }


        if (bought && game.verbose)
            System.out.println(name + " buys the card : " + card.getName());

        return bought;
    }

	public ArrayList<Face> affordableFaces() {
		ArrayList<Face> tmp = new ArrayList<>();

		ArrayList<Pool> pools = game.getPools();
		for (Pool pool: pools) {
			if (getGold() >= pool.getPrice())
				pool.getFaces().forEach(face -> {if(!tmp.contains(face)) tmp.add(face);});
		}
		return tmp;
	}

	boolean buyFace(Face face) {
        ArrayList<Pool> pools = game.getPools();
		for (Pool pool : pools) {
			if (pool.contains(face) && getGold() >= pool.getPrice()) {
				pool.deleteFace(face);
				modifyResource(Resource.GOLD, -pool.getPrice());
				return true;
			}
		}
		return false;
	}

    /**
     * @param prevFace The face that will be replaced
     * @param newFace  The face that will replace the previous one
     */
    public void forge(Dice dice, Face prevFace, Face newFace) {
        if (buyFace(newFace)) {
            dice.changeFace(prevFace, newFace);
            if (game.verbose)
                System.out.println(this.getName() + " forges " + newFace + " on " + prevFace);
        }
    }

	public void addGloryFromCards() {
		for(Card c : cards)
			gloryPoints+=c.getGlory();
	}

	public void plays() { 
		if (game.verbose)
			System.out.println("\n==> " +getName() + " plays !\n");
		for(Card c : cards) {
			if(c.getClass().equals(ReinforcementCard.class)) {
				if(game.verbose)
					System.out.println(getName() + " use the ability of " + c.getName());
				((ReinforcementCard) c).doEffect();
			}
		}
	}


	@Override
	public String toString() {
		return (name + " with :" + "   " + gloryPoints + " glory points and " + getGold() + " gold and " + getSolar()
				+ " solar and " + getLunar() + " lunar.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Player clone() throws CloneNotSupportedException{
		Player clone = (Player) super.clone();
		clone.dice1 = dice1.clone();
		clone.dice2 = dice2.clone();
		clone.chest = chest.clone();
		clone.cards = (ArrayList<Card>) cards.clone();
		return clone;
	}

	public Face findBestFaceToAdd(ArrayList<Face> faces) {
		return faces.get(rand.nextInt(faces.size()));
	}

	public int compareTo(Player p2) {
		return Integer.compare(getGloryPoints(), p2.getGloryPoints());
	}

	public void setGlory(int gloryPoints2) {
		gloryPoints = gloryPoints2;
	}

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
				Collections.sort(left, new Comparator<Integer>() {
					public int compare(Integer a, Integer b) {
						return -a.compareTo(b);
					}
				});
				return left.get(0);
			}
		}
		else if(!maybe.isEmpty()) {
			if(maybe.size() == 1)
				return maybe.get(0);
			else {
				return maybe.get(rand.nextInt(maybe.size()));
			}
		}
		else if(res.contains(Resource.GOLD))
			return res.indexOf(Resource.GOLD);
					
		return rand.nextInt(res.size());
	}

	public void addGlory(int amount) {
		gloryPoints += amount;
	}

	public boolean shouldBuyGlory(int cost, int amount) {
		if(getChest().getAmountLeftOf(Resource.GOLD) < cost)
			return false;
		
		return rand.nextBoolean();
	}

	public Dice bestDiceToRoll() {
		if(rand.nextBoolean())
			return dice1;
		else return dice2;
	}

	public Dice bestDiceToForgeOn() {
		if(rand.nextBoolean())
			return dice1;
		else return dice2;
	}

	public Face bestFace() {
		if(rand.nextBoolean())
			return dice1.getFace(rand.nextInt(dice1.getFaces().size()));
		else
			return dice2.getFace(rand.nextInt(dice2.getFaces().size()));
	}

	public Game getGame() {
		return game;
	}
}
