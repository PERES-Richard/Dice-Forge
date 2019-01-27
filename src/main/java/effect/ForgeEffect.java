package effect;

import player.Player;
import face.Face;

public class ForgeEffect extends Effect{

	private Face face;
	
	public ForgeEffect(Face face, Effect nextEffect) {
		super(nextEffect);
		this.face = face;
	}
	
	public ForgeEffect(Face face) {
		super(null);
		this.face = face;
	}

	@Override
	public void doEffect(Player player) {
		player.forge(player.bestDiceToForgeOn(), player.bestFace(), this.face);
	}
	
}
