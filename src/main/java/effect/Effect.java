package effect;

import player.Player;

public abstract class Effect {

	private Effect nextEffect;
	
	public Effect(Effect nextEffect) {
		this.nextEffect = nextEffect;
	}

	public Effect getNextEffect() {
		return nextEffect;
	}
	
	public void doEffect(Player player) {}

	@Override
	public Effect clone() throws CloneNotSupportedException{
		return (Effect) super.clone();
	}
}
