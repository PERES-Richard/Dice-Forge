package effect;

import player.Player;

public class MinorFavorEffect extends Effect {

	private int times;
	
	public MinorFavorEffect() {
		super(null);
		times = 1;
	}

	public MinorFavorEffect(int times) {
		super(null);
		this.times = times;
	}
	
	public MinorFavorEffect(Effect nextEffect) {
		super(nextEffect);
		times = 1;
	}

	public MinorFavorEffect(int times,Effect nextEffect) {
		super(nextEffect);
		this.times = times;
	}
	
	@Override
	public void doEffect(Player player) {
		for(int i = 0; i< times; i++)
			player.rollOneDice(player.bestDiceToRoll());
	}
}
