package card;

import effect.Effect;
import player.Player;

public class ReinforcementCard extends Card {

	private Effect effect;
	
	public ReinforcementCard(CardName name, int glory, Effect effect) {
		super(name, glory);
		this.effect = effect;
	}
	
	public void pickBy(Player player) {
		super.pickBy(player);
	}

	public void doEffect() {
		effect.doEffect(getPlayer());
	}

}
