package game;

/**
 * Enum used tu identify the different resources of the game
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

public enum Resource {
	GLORY(0),
	GOLD(1),
	SOLAR(2),
	LUNAR(3);

    private int rank;

	Resource(int i) {
		this.rank = i;
	}

    public int getRank() {
        return rank;
    }
}
