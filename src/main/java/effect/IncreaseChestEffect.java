package effect;

import game.Resource;
import player.Player;

public class IncreaseChestEffect extends Effect {

	private static final int INCREASE_GOLD = 4;
	private static final int INCREASE_SOLAR = 3;
	private static final int INCREASE_LUNAR = 3;
	
	public IncreaseChestEffect(Effect nextEffect) {
		super(nextEffect);
	}
	
	public IncreaseChestEffect() {
		super(null);
	}
	
	@Override
	public void doEffect(Player player) {
		player.getChest().increaseMax(Resource.GOLD, INCREASE_GOLD);
		player.getChest().increaseMax(Resource.SOLAR, INCREASE_SOLAR);
		player.getChest().increaseMax(Resource.LUNAR, INCREASE_LUNAR);
	}
	
}
