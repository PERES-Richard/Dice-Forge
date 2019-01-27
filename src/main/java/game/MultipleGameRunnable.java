package game;

import java.util.ArrayList;

import player.Player;

public class MultipleGameRunnable implements Runnable{

	private String[] players;
	private int[] nbWinPlayer;
	private int[] gloryPlayer;
	private int nbDraw;
	private int nbGame;
	
	public MultipleGameRunnable(String[] players, int[] nbWinPlayer, int gloryPlayer[], int nbDraw, int nbGame) {
		this.players = players;
		this.nbWinPlayer = nbWinPlayer;
		this.nbDraw = nbDraw;
		this.nbGame = nbGame;
		this.gloryPlayer = gloryPlayer;
	}

	public void run() {
		int nbPlayers = players.length;
		int nbRounds = (nbPlayers == 3) ? 10 : 9;

		for(int i=0; i<nbGame; i++) {
			Game game = new Game(players);
			game.verbose = false;
			
			if (game.verbose)
				System.out.println("\nNew Game! (number " + i + ")");
			
			for (int j = 0; j < nbRounds; j++) {
				game.round();	
			}
			ArrayList<Player> winners = game.findWinner();

			if (game.verbose)
				Game.printWinner(winners);
						
			if (winners.isEmpty()) {
				synchronized (this) {
					nbDraw++;
				}
			}
			else {
					for(Player p : winners) {
						for(int j=0; j<nbPlayers; j++) {
							if (p.getName().equals(game.getPlayers().get(j).getName())) {
								synchronized (this) {
									nbWinPlayer[j]++;
							}
						}
					}
				}
			}
			for(int k=0; k<nbPlayers; k++)
				synchronized (this) {
					gloryPlayer[k] += game.getPlayers().get(k).getResource(Resource.GLORY);
				}
		}
	}
	
	
	public void setNbGame(int nbGame) {
		this.nbGame = nbGame;
	}

	public int[] getNbWinPlayer() {
		return nbWinPlayer;
	}

	public int getNbDraw() {
		return nbDraw;
	}

	public int[] getGloryPlayer() {
		return gloryPlayer;
	}

	public int getNbGame() {
		return nbGame;
	}
	
}
