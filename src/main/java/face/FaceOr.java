package face;

import game.Resource;

public class FaceOr extends Face{

    public FaceOr(int gloryAmount, int goldAmount, int solarAmount, int lunarAmount) {
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
        if (o instanceof FaceOr){
            FaceOr face = (FaceOr) o;
            for (int i = 0; i<4; i++) {
                if (this.amounts[i] != face.amounts[i])
                    return false;
            }
            return true;
        }
        return false;
    }
}
