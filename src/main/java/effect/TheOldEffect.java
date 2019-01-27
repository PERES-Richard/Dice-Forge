package effect;

import game.Resource;
import player.Player;

public class TheOldEffect extends Effect {

	private final int cost;
	private final int amount;
	
	public TheOldEffect() {
		super(null);
		cost = 3;
		amount = 4;
	}
	
	@Override
	public void doEffect(Player player) {

		if(player.shouldBuyGlory(cost, amount)) {
			player.addGlory(amount);
			player.modifyResource(Resource.GOLD, cost);
		}
	}

}
