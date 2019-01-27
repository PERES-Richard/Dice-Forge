package effect;

import player.Player;

public class MajorFavorEffect extends Effect {
	
	private int times;
	
	public MajorFavorEffect() {
		super(null);
		times = 1;
	}

	public MajorFavorEffect(int times) {
		super(null);
		this.times = times;
	}
	
	public MajorFavorEffect(Effect nextEffect) {
		super(nextEffect);
		times = 1;
	}

	public MajorFavorEffect(int times,Effect nextEffect) {
		super(nextEffect);
		this.times = times;
	}
	
	public void doEffect(Player player) {
		for(int i = 0; i< times; i++)
			player.rollDice();
	}
}
