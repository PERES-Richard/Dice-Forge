package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import action.ActionFactory;
import card.*;
import player.*;

/**
 * The game itself
 *
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand
 *         BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha
 *         CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain
 *         MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard
 *         PERES</a>
 */

public class Game implements Cloneable{

	public boolean verbose = true;
	private ActionFactory actionFactory;
	private ArrayList<Player> players;
	private ArrayList<Pool> pools;
	private ArrayList<Island> islands;
	private int nbRounds;
	private int turn;

	public Game(String... players) {
		int nbPlayers = players.length;
		
		nbRounds = (nbPlayers == 3) ? 10 : 9;
		turn = 0;

		this.players = new ArrayList<>();
		
		for(int i=0; i<nbPlayers; i++) {
			if(players[i].contains("RandBot"))
				this.players.add(new RandBot(players[i], i));
			else if(players[i].contains("EasyBot"))
				this.players.add(new EasyBot(players[i], i));
			else if(players[i].contains("MediumBot"))	
				this.players.add(new MediumBot(players[i], i));
			else
				System.err.println("wrong bot");
		}
		
		this.players.forEach(p -> p.setGame(this));


		this.pools = new PoolFactory(this.players.size()).instantiate();

		islands = new IslandFactory(nbPlayers, this.players).instantiate();

		actionFactory = new ActionFactory(this);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Pool> getPools() {
		return pools;
	}

	public ArrayList<Island> getIslands() {
		return islands;
	}

	public ActionFactory getActionFactory() {
		return actionFactory;
	}

	public int getNbRounds() {
		return nbRounds;
	}

	public void setVerbose(boolean verbose){
		this.verbose = verbose;
	}

	void round() {
		if (verbose) {
			turn++;
            System.out.println("\n################### New Round (turn "+turn+") ###################");
            for (Player player : players) {
                printStats();
                System.out.println("\t#### Start " + player.getName() + "'s turn. ####\n");
                everyoneRolls();
                System.out.println();
                player.plays();
                System.out.println("\n\n\t#### End of " + player.getName() + "'s turn.####\n");
                System.out.println("#########################################################");
            }
        }
        else {
        	turn++;
            for (Player player : players) {
                everyoneRolls();
                player.plays();
            }
        }
	}

	private void printStats() {
		System.out.println("\n  ────────────────────────────────────────────");
		System.out.println(
				String.format(" | %-10s | %5s | %5s | %5s | %5s |", "Player", "Gold", "Lunar", "Solar", "Glory"));
		System.out.println("  ────────────────────────────────────────────");
		for (Player p : players) {
			System.out.println(String.format(" | %-10s | %5s | %5s | %5s | %5s |", p.getName(),
					p.getGold() + "/" + p.getChest().getMaxGold(), p.getLunar() + "/" + p.getChest().getMaxLunar(),
					p.getSolar() + "/" + p.getChest().getMaxSolar(), p.getGloryPoints()));
			System.out.println("  ────────────────────────────────────────────");
		}
		System.out.println();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Player> findWinner() {
		ArrayList<Player> winners = new ArrayList<>();
		ArrayList<Player> playersClone = (ArrayList<Player>) players.clone();
		
		for(Player p : playersClone) {
			for (Card c : p.getCards())
				p.modifyResource(Resource.GLORY, c.getGlory());
		}

		Collections.sort(playersClone, new Comparator<Player>() {
	        @Override
	        public int compare(Player p1, Player p2) {
	            return  -p1.compareTo(p2);
	        }
	    });
		
		winners.add(playersClone.get(0));
		int maxGlory = playersClone.get(0).getGloryPoints();
		int j = 1;
		
		while(j<players.size() && playersClone.get(j).getGloryPoints() == maxGlory) {
			winners.add(playersClone.get(j));
			j++;
		}
		
		if(winners.size() == players.size())
			winners.clear();
		
		if (verbose)
			printWinner(winners);
		
		return winners;
	}

	static void printWinner(ArrayList<Player> winners) {
		if(winners.isEmpty())
			System.out.println("Draw");
		else {
			String print = "The winner is " ;
			for(Player p : winners)
					print += p+", ";
			
			System.out.println(print.substring(0, print.length()-2));
		}
	}

	private void everyoneRolls() {
		for (Player player : players) {
			if (verbose) System.out.println(player.getName() + " roll his dices");
			player.rollDice();
		}
		for (Player player : players) {
			player.pickResources();
		}

		if (players.size() == 2 ) {
            for (Player player : players) {
                if (verbose) System.out.println(player.getName() + " roll his dices");
                player.rollDice();
            }
            for (Player player : players) {
                player.pickResources();
            }
        }
	}

	public int getTurn() {
		return turn;
	}

	@Override
	public Game clone() throws CloneNotSupportedException{
		Game clone = (Game) super.clone();
		clone.actionFactory = actionFactory.clone();
		clone.actionFactory.setGame(clone);
		clone.pools = new ArrayList<>();
		for (Pool pool : pools) {
			clone.pools.add(pool.clone());
		}
		clone.islands = new ArrayList<>();
		for (Island island : islands) {
			clone.islands.add(island.clone());
		}
		return clone;
	}
}


