package face;
import game.*;

import java.util.Arrays;

/**
 * A face of a Dice, each Face give different Resource or a bonus
 *
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand
 *         BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha
 *         CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain
 *         MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard
 *         PERES</a>
 *
 */

public abstract class Face implements Cloneable{

    int amounts[];

    public abstract int[] getResources();
    public abstract int getAmountOf(Resource res);

    /*
	private int gloryAmount;
    private int goldAmount;
    private int solarAmount;
    private int lunarAmount;


	public Face(int gloryAmount, int goldAmount, int solarAmount, int lunarAmount) {
		this.gloryAmount = gloryAmount;
		this.goldAmount = goldAmount;
		this.solarAmount = solarAmount;
		this.lunarAmount = lunarAmount;
	}


    @Override
    public boolean equals(Object o){
		if (o instanceof Face){
			Face face = (Face) o;
            return this.gloryAmount == face.gloryAmount && this.goldAmount == face.goldAmount
                    && this.lunarAmount == face.lunarAmount && this.solarAmount == face.solarAmount;
		}
		return false;
	}

    */

    @Override
    public String toString() {
        return "Face " +
                Arrays.toString(amounts);
    }

    @Override
    public int hashCode() {
    	return super.hashCode();
    }

	@Override
	public Face clone() throws CloneNotSupportedException{
		return (Face) super.clone();
	}
}
