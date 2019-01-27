package game;

import face.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is in charge of the instantiation of the pools of the game.
 *
 * @author <a href="mailto:armand.boulanger@etu.univ-cotedazur.fr">Armand BOULANGER</a>
 * @author <a href="mailto:sacha.carniere@etu.univ-cotedazur.fr">Sacha CARNIERE</a>
 * @author <a href="mailto:sylvain.masia@etu.univ-cotedazur.fr">Sylvain MASIA</a>
 * @author <a href="mailto:richard.peres@etu.univ-cotedazur.fr">Richard PERES</a>
 *
 */
class PoolFactory {
    private int nbPlayer;
    private final int[] sanctuaryPrices = new int[]{2,3,4,5,6,8,12};
    private Random random = new Random();

    PoolFactory(int nbPlayer){
        this.nbPlayer = nbPlayer;
    }

    ArrayList<Pool> instantiate(){
        ArrayList<Pool> pools = new ArrayList<>();
        pools.add(instantiatePool1());
        pools.add(instantiatePool1Bis());
        pools.add(instantiatePool2());
        pools.add(instantiatePool2Bis());
        pools.add(instantiatePool3());
        pools.add(instantiatePool4());
        pools.add(instantiatePool5());
        pools.add(instantiatePool6());
        pools.add(instantiatePool6bis());
        pools.add(instantiatePool7());
        return pools;
    }

    private Pool instantiatePool1() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(0, 3, 0, 0));
        }
        return new Pool(sanctuaryPrices[0], pool);
    }

    private Pool instantiatePool1Bis() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(0, 0, 0, 1));
        }
        return new Pool(sanctuaryPrices[0], pool);
    }

    private Pool instantiatePool2() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(0, 4, 0, 0));
        }
        return new Pool(sanctuaryPrices[1], pool);
    }

    private Pool instantiatePool2Bis() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(0, 0, 1, 0));
        }
        return new Pool(sanctuaryPrices[1], pool);
    }

    private Pool instantiatePool3() {
        ArrayList<Face> pool = new ArrayList<>();
        pool.add(new FaceAnd(0, 6, 0, 0));
        pool.add(new FaceAnd(1, 0, 1, 0));
        pool.add(new FaceAnd(0, 2, 0, 1));
        pool.add(new FaceOr(0, 1, 1, 1));
        if (nbPlayer == 2) {
            pool.remove(random.nextInt(4));
            pool.remove(random.nextInt(3));
        }
        return new Pool(sanctuaryPrices[2], pool);
    }

    private Pool instantiatePool4() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceOr(2, 3, 0, 0));
        }
        return new Pool(sanctuaryPrices[3], pool);
    }

    private Pool instantiatePool5() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(0, 0, 0, 2));
        }
        return new Pool(sanctuaryPrices[4], pool);
    }

    private Pool instantiatePool6() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(0, 0, 2, 0));
        }
        return new Pool(sanctuaryPrices[5], pool);
    }

    private Pool instantiatePool6bis() {
        ArrayList<Face> pool = new ArrayList<>();
        int max = (nbPlayer == 2) ? 2 : 4;
        for(int i=0; i<max ; i++) {
            pool.add(new FaceAnd(3, 0, 0, 0));
        }
        return new Pool(sanctuaryPrices[5], pool);
    }

    private Pool instantiatePool7() {
        ArrayList<Face> pool = new ArrayList<>();
        pool.add(new FaceAnd(4, 0, 0, 0));
        pool.add(new FaceAnd(1, 1, 1, 1));
        pool.add(new FaceAnd(2, 0, 0, 2));
        pool.add(new FaceOr(0, 2, 2, 2));
        if (nbPlayer == 2) {
            Random random = new Random();
            pool.remove(random.nextInt(4));
            pool.remove(random.nextInt(3));
        }
        return new Pool(sanctuaryPrices[6], pool);
    }
}