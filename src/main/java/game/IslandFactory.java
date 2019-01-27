package game;

import java.util.ArrayList;

import card.*;
import effect.AddResourceEffect;
import effect.ForgeEffect;
import effect.IncreaseChestEffect;
import effect.MajorFavorEffect;
import effect.MinorFavorEffect;
import effect.MinotaurEffect;
import effect.SatyreEffect;
import effect.TheOldEffect;
import face.FaceAbysalMirror;
import face.FaceX3;
import player.Player;

public class IslandFactory {

	private final int nbPlayer;
	private final ArrayList<Player> players;
	
	public IslandFactory(int nbPlayer, ArrayList<Player> players) {
		this.nbPlayer = nbPlayer;
		this.players = players;
	}

	public ArrayList<Island> instantiate() {
		ArrayList<Island> islands = new ArrayList<>();

		islands.add(instantiateLunar1());
		islands.add(instantiateSolar1());

		islands.add(instantiateLunar2());
		islands.add(instantiateSolar2());

		islands.add(instantiateLunar3());
		islands.add(instantiateSolar3());

		islands.add(instantiateBossIsland());

		return islands;
	}

	private Island instantiateSolar3() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
				leftCards.add(new NoEffectCard(CardName.LaMéduse, 14));
				rightCards.add(new ImmediateCard(CardName.LeMiroirAbyssal, 10, new ForgeEffect(new FaceAbysalMirror())));
		}

		return new Island(Resource.SOLAR, leftCards, rightCards, 4, 5);
	}

	private Island instantiateLunar3() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
				leftCards.add(new NoEffectCard(CardName.LePasseur, 12));
				rightCards.add(new ImmediateCard(CardName.LeCasqueDInvisibilité, 4, new ForgeEffect(new FaceX3())));
		}

		return new Island(Resource.LUNAR, leftCards, rightCards, 4, 5);
	}

	private Island instantiateSolar2() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
			leftCards.add(new ReinforcementCard(CardName.LesAilesDeLaGardienne, 4,
					new AddResourceEffect(Resource.GOLD, Resource.SOLAR, Resource.LUNAR, 1)));
			rightCards.add(new ImmediateCard(CardName.LeMinotaure, 8, new MinotaurEffect(players)));
		}
		
		return new Island(Resource.SOLAR, leftCards, rightCards, 2, 3);
	}

	private Island instantiateLunar2() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
			leftCards.add(new ReinforcementCard(CardName.LesSabotsDArgent, 2, new MinorFavorEffect()));
			rightCards.add(new ImmediateCard(CardName.LesSatyres, 6, new SatyreEffect(players)));
		}

		return new Island(Resource.LUNAR, leftCards, rightCards, 2, 3);
	}

	private Island instantiateBossIsland() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> bossCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
			leftCards.add(new ImmediateCard(CardName.LEnigme, 10, new MinorFavorEffect(4)));
			rightCards.add(new ImmediateCard(CardName.LaPince, 8, new MajorFavorEffect(2)));
			bossCards.add(new NoEffectCard(CardName.LHydre, 26));
		}

		return new Island(Resource.SOLAR, Resource.LUNAR, leftCards, bossCards, rightCards, 6, 6, 5);
	}

	private Island instantiateLunar1() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
			//leftCards.add(new ReinforcementCard(CardName.LeMarteauDuForgeron, 0));
			rightCards.add(new ImmediateCard(CardName.LeCoffreDuForgeron, 2, new IncreaseChestEffect()));
		}

		return new Island(Resource.LUNAR, leftCards, rightCards, 1, 1);
	}

	private Island instantiateSolar1() {
		ArrayList<Card> leftCards = new ArrayList<>();
		ArrayList<Card> rightCards = new ArrayList<>();

		for (int i = 0; i < nbPlayer; i++) {
			leftCards.add(new ReinforcementCard(CardName.LAncien, 0, new TheOldEffect()));
			rightCards.add(new ImmediateCard(CardName.LesHerbesFolles, 2,
					new AddResourceEffect(Resource.GOLD, 3, new AddResourceEffect(Resource.LUNAR, 3))));
		}

		return new Island(Resource.SOLAR, leftCards, rightCards, 1, 1);

	}

}
