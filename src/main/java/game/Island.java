package game;

import java.util.ArrayList;

import card.Card;
import card.CardName;
import player.Player;;

public class Island implements Cloneable{
	private Player playerIn;
	private ArrayList<Card> leftCards;
	private ArrayList<Card> rightCards;
	private int leftCardsCost;
	private int rightCardsCost;
	private Resource res;

	private boolean bossIsland;
	private Resource res2;
	private ArrayList<Card> bossCards;
	private int bossCardsCost;


	public Island(Resource res, ArrayList<Card> leftCards, ArrayList<Card> rightCards, int leftCardsCost, int rightCardsCost) {
		this.leftCards = leftCards;
		this.rightCards = rightCards;
		this.leftCardsCost = leftCardsCost;
		this.rightCardsCost = rightCardsCost;
		this.res = res;
		bossIsland = false;
	}

	public Island(Resource res, Resource res2, ArrayList<Card> leftCards, ArrayList<Card> bossCards,ArrayList<Card> rightCards, int leftCardsCost, int rightCardsCost, int bossCardsCost) {
		this.leftCards = leftCards;
		this.rightCards = rightCards;
		this.leftCardsCost = leftCardsCost;
		this.rightCardsCost = rightCardsCost;
		this.bossCardsCost = bossCardsCost;
		this.res=res;
		bossIsland = true;
		this.res2 = res2;
		this.bossCards = bossCards;
	}


    public boolean isBossIsland() {
        return bossIsland;
    }

	public void enterIsland(Player player) {
		if(playerIn != null && !player.equals(playerIn))
			playerIn.rollDice();
			
        playerIn = player;
	}


	public ArrayList<Card> getLeftCards() {
		return leftCards;
	}

	public ArrayList<Card> getRightCards() {
		return rightCards;
	}

    public Player getPlayerIn() {
        return playerIn;
    }

    public Resource getRes() {
        return res;
    }

    public Resource getRes2() {
		if(isBossIsland())
			return res2;
		else return null;
	}

	public ArrayList<Card> getBossCards() {
		if(isBossIsland())
			return bossCards;
		return new ArrayList<Card>();
	}
	
	public boolean pickedBy(Card card, Player player) {
		
		if(leftCards.contains(card)) {
			card.pickBy(player);
			leftCards.remove(card);
			return true;
		}
		if(rightCards.contains(card)) {
			card.pickBy(player);
			rightCards.remove(card);
			return true;
		}
		if(isBossIsland() && bossCards.contains(card)) {
				card.pickBy(player);
				bossCards.remove(card);
				return true;
			}
		
		return false;
	}

	public int getCost(Card card) {

		if(leftCards.contains(card))
			return leftCardsCost;
		if(rightCards.contains(card))
			return rightCardsCost;
		if(isBossIsland() && bossCards.contains(card))
			return bossCardsCost;

		return -1;
	}

	public boolean contains(Card card) {

		if(leftCards.contains(card) || rightCards.contains(card) || (isBossIsland() && bossCards.contains(card)))
			return true;

		return false;
	}

	public boolean contains(CardName card) {

		for(Card c : leftCards){
			if (card == c.getName()){
				return true;
			}
		}
		for(Card c : rightCards){
			if (card == c.getName()){
				return true;
			}
		}

		if (isBossIsland()){
			for(Card c : bossCards){
				if (card == c.getName()){
					return true;
				}
			}
		}

		return false;
	}

	public int getLeftCardsCost() {
		return leftCardsCost;
	}

	public int getRightCardsCost() {
		return rightCardsCost;
	}

	public int getBossCardsCost() {
		return bossCardsCost;
	}

	@Override
	public Island clone() throws CloneNotSupportedException{
		Island clone = (Island) super.clone();
		clone.leftCards = new ArrayList<>();
		for (Card card : leftCards) {
			clone.leftCards.add(card.clone());
		}
		clone.rightCards = new ArrayList<>();
		for (Card card : rightCards) {
			clone.rightCards.add(card.clone());
		}
		return clone;
	}

}
