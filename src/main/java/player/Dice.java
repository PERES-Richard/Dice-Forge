package player;
import face.*;
import game.Resource;

import java.util.ArrayList;

/**
 * A dice that have different faces
 *
 * @author <a href="mailto:armand.boulanger@etu.univ-cotedazur.fr">Armand BOULANGER</a>
 * @author <a href="mailto:sacha.carniere@etu.univ-cotedazur.fr">Sacha CARNIERE</a>
 * @author <a href="mailto:sylvain.masia@etu.univ-cotedazur.fr">Sylvain MASIA</a>
 * @author <a href="mailto:richard.peres@etu.univ-cotedazur.fr">Richard PERES</a>
 *
 */

public class Dice implements Cloneable{

	private ArrayList<Face> faces = new ArrayList<>();

	Dice(boolean first) {
		if (first){
			faces.add(new FaceAnd(0, 1, 0, 0));
			faces.add(new FaceAnd(0, 1, 0, 0));
			faces.add(new FaceAnd(0, 1, 0, 0));
			faces.add(new FaceAnd(0, 1, 0, 0));
			faces.add(new FaceAnd(0, 1, 0, 0));
			faces.add(new FaceAnd(0, 0, 1, 0));
		} else {
            faces.add(new FaceAnd(0, 1, 0, 0));
            faces.add(new FaceAnd(0, 1, 0, 0));
            faces.add(new FaceAnd(0, 1, 0, 0));
            faces.add(new FaceAnd(0, 1, 0, 0));
            faces.add(new FaceAnd(0, 0, 0, 1));
            faces.add(new FaceAnd(2, 0, 0, 0));
		}

	}

	public ArrayList<Face> getFaces(){
		return faces;
	}

	public Face getFace(int i){
		return faces.get(i);
	}

	boolean hasFace(Face face){
		for (Face f : faces){
			if (face.equals(f))
				return true;
		}
		return false;
	}

	Face roll() {
		return faces.get((int)(Math.random() * faces.size()));
	}


	public void changeFace(Face previousFace, Face newFace) {
		/*for (int i = 0; i < faces.size(); i++){
			if (previousFace.equals(faces.get(i))){
				faces.set(i, newFace);
				return;
			}
		}
		System.err.println("Face does not exist on Dice.");*/
		int i=0;
		while(!previousFace.equals(faces.get(i)) && i < (faces.size()-1)){i++;}
		if(previousFace.equals(faces.get(i)))
			faces.set(i, newFace);
	}

	public float average(Resource r){
		float sum = 0;
		int rank = r.getRank();
		for(Face f : faces){
		    sum += f.getResources()[rank];
		}
		return sum/6;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Dice){
			Dice dice = (Dice) o;
			boolean allFacesEqual = true;
			for (int i = 0; i < this.faces.size(); i++) {
				if (!(this.faces.get(i) == dice.faces.get(i)))
					allFacesEqual = false;
			}
			return allFacesEqual;
		}
		return false;
	}

	@Override
    public int hashCode() {
    	return super.hashCode();
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public Dice clone() throws CloneNotSupportedException{
		Dice clone = (Dice) super.clone();
		clone.faces = (ArrayList<Face>) faces.clone();
		return clone;
	}
}
