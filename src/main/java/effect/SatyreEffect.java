package effect;

import java.util.ArrayList;

import face.Face;
import player.Player;

public class SatyreEffect extends Effect {

	private ArrayList<Player> players;
	
	public SatyreEffect(ArrayList<Player> players) {
		super(null);
		this.players = players;
	}
	
	@Override
	public void doEffect(Player player) {
		ArrayList<Face> faces = new ArrayList<>();
		
		for(Player p : players)
			faces.addAll(p.noEffectRoll());
		
		Face face1 = player.findBestFaceToAdd(faces);
		faces.remove(face1);
		Face face2 = player.findBestFaceToAdd(faces);
		
		player.modifyResourceFromFace(face1);
		player.modifyResourceFromFace(face2);
		
	}

}
