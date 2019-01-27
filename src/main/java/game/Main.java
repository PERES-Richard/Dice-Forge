package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import player.Player;

public class Main {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
		System.out.println("Start :");
		for(int k = 0; k<2 ; k++) {
			final String[] players;
			if(k == 0)
				players = new String[] {"RandBot", "EasyBot", "MediumBot"};
			else
				players = new String[]{"MediumBot1", "MediumBot2", "MediumBot3", "MediumBot4"};
	
			final int nbPlayers = players.length;
			final boolean singleGame = false;
			final boolean log = false;
	
			if(log) {
				File file = new File("log.txt");
				FileOutputStream fos = new FileOutputStream(file);
				PrintStream ps = new PrintStream(fos);
				System.setOut(ps);
			    Date date= Calendar.getInstance().getTime();
			    DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
			    System.out.println("Date : "+ dateFormat.format(date) + "\n");
			}
			
			int numberOfGames;
			
			if(singleGame)
				numberOfGames = 1;
			else numberOfGames = 1000;
	
			int nbWinPlayer[] = new int[nbPlayers];
			int gloryPlayer[] = new int[nbPlayers];
			int draw = 0;
	
			long start = System.currentTimeMillis();
	
			if(singleGame) {
				
				Game game = new Game(players);
				game.verbose = true;
				
				System.out.println("\nNew Game! ");
				
				for (int j = 0; j < game.getNbRounds(); j++) {
					game.round();
				}
				ArrayList<Player> p = game.findWinner();
			}
			else {
				final int nbThread = Runtime.getRuntime().availableProcessors();
				
				Thread[] threads = new Thread[nbThread];
				MultipleGameRunnable runnable = new MultipleGameRunnable(players, nbWinPlayer, gloryPlayer, draw, numberOfGames/nbThread+numberOfGames%nbThread);
				threads[0] = new Thread(runnable);
				threads[0].start();
				threads[0].join();
				runnable.setNbGame(numberOfGames/nbThread);
				
				for(int i=1; i<nbThread; i++) {
					threads[i] = new Thread(runnable);
					threads[i].start();
				}		
				
				for(int i=0; i<nbThread; i++)
					threads[i].join();
					
				nbWinPlayer = runnable.getNbWinPlayer();
				gloryPlayer = runnable.getGloryPlayer();
				draw = runnable.getNbDraw();
				
				System.out.println("\nOn " + numberOfGames + " games : ");
				for(int i = 0; i<nbPlayers; i++)
					 System.out.println("\t" + players[i] + " won " + nbWinPlayer[i]+ " times (" + (int) ((float)  nbWinPlayer[i] / numberOfGames * 100) + "%) with on average " + (int) ((float)  gloryPlayer[i] / numberOfGames) + " glory");
				System.out.println("\tAnd " + draw + " draw ("+ (int) ((float) draw / numberOfGames * 100) + "%).\n");
				System.out.println("Executed in ["+ (System.currentTimeMillis() - start) / 1000.0 + "s]");
			}
		}
	}

}
