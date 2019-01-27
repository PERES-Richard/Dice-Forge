package game;

import face.*;

import java.util.ArrayList;

/**
 * Allow player to buy new face for their dice
 *
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard PERES</a>
 *
 */
public class Pool implements Cloneable{
	private final int price;
	private ArrayList<Face> faces;

	public Pool(int price, ArrayList<Face> faces) {
		this.price = price;
		this.faces = faces;
	}

	public int getPrice() {
		return price;
	}

	public ArrayList<Face> getFaces() {
		return faces;
	}

	public boolean contains(Face face){
		return faces.contains(face);
	}

	void deleteFace() {
		faces.remove(faces.size() - 1);
	}

	public void deleteFace(Face face) {
		faces.remove(face);
	}

	public Face getAFace() {
		return faces.get(faces.size() - 1);
	}

	public boolean hasResource(Resource resource){
		for (Face face : faces) {
			if (face.getResources()[resource.getRank()] > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public Pool clone() throws CloneNotSupportedException{
		Pool clone = (Pool) super.clone();
		clone.faces = new ArrayList<>();
		for (Face face : faces) {
			clone.faces.add(face.clone());
		}
		return clone;
	}
}
