package face;

import game.Resource;

public class FaceAnd extends Face {

    public FaceAnd(int gloryAmount, int goldAmount, int solarAmount, int lunarAmount) {
        amounts = new int[] {gloryAmount, goldAmount, solarAmount, lunarAmount};
    }

    public int[] getResources() {
        return amounts;
    }

    public int getAmountOf(Resource res) {
        return amounts[res.getRank()];
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof FaceAnd){
            FaceAnd face = (FaceAnd) o;
            for (int i = 0; i<4; i++) {
                if (this.amounts[i] != face.amounts[i])
                    return false;
            }
            return true;
        }
        return false;
    }

}
