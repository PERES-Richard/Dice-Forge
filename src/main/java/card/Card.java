package card;

import player.Player;

/**
 *
 * @author <a href="mailto:Armand.BOULANGER@etu.univ-cotedazur.fr">Armand BOULANGER</a>
 * @author <a href="mailto:Sacha.CARNIERE@etu.univ-cotedazur.fr">Sacha CARNIERE</a>
 * @author <a href="mailto:Sylvain.MASIA@etu.univ-cotedazur.fr">Sylvain MASIA</a>
 * @author <a href="mailto:Richard.PERES@etu.univ-cotedazur.fr">Richard PERES</a>
 *
 */

public abstract class Card implements Cloneable{
    private final CardName name;
    private final int glory;
    private Player player;

    public Card(CardName name, int glory){
        this.name = name;
        this.glory = glory;
    }

    public int getGlory(){
        return glory;
    }

    public void pickBy(Player player) {
        this.player = player;
        player.getCards().add(this);
    }

	public Player getPlayer() {
		return player;
	}

	public CardName getName() {
		return name;
	}

    @Override
    public int hashCode() {
        return super.hashCode();
    }

	@Override
    public boolean equals(Object object){
         if (object instanceof Card) {
             return this.name == (((Card) object).name);
         }
         return false;
    }

    @Override
    public Card clone() throws CloneNotSupportedException{
        return (Card) super.clone();
    }
    
 }