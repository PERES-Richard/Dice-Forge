package card;

import player.Player;
import effect.Effect;

public class ImmediateCard extends Card {

	private Effect effect;
	
	public ImmediateCard(CardName name, int glory, Effect effect) {
		super(name, glory);
		this.effect = effect;
	}
	
	@Override
	public void pickBy(Player player) {
		super.pickBy(player);
		effect.doEffect(player);
	}

}
