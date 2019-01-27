package effect;

import java.util.ArrayList;

import player.Player;

public class MinotaurEffect extends Effect {

	private ArrayList<Player> players;
	
	@SuppressWarnings("unchecked")
	public MinotaurEffect(ArrayList<Player> players) {
		super(null);
		this.players = ( ArrayList<Player> ) players.clone();
	}
	
	@Override
	public void doEffect(Player player) {
		if(players.remove(player))
			for (Player p : players)
				p.minotaurRoll();
	}

}
