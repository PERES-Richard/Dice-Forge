package player;

import game.Resource;

public class Chest implements Cloneable{
	private int maxGold = 12;
	private int maxSolar = 6;
	private int maxLunar = 6;
	private int gold;
	private int solar;
	private int lunar;

	public Chest(int gold) {
		this.gold = gold;
		this.solar = 0;
		this.lunar = 0;
	}

	private Chest(int gold, int solar, int lunar, int maxGold, int maxSolar, int maxLunar){
		this.gold = gold;
		this.solar = solar;
		this.lunar = lunar;
		this.maxGold = maxGold;
		this.maxSolar = maxSolar;
		this.maxLunar = maxLunar;
	}

	public int getGold() {
		return gold;
	}

	public int getSolar() {
		return solar;
	}

	public int getLunar() {
		return lunar;
	}

	public int getMaxGold() {
		return maxGold;
	}

	public int getMaxSolar() {
		return maxSolar;
	}

	public int getMaxLunar() {
		return maxLunar;
	}

	public int getMax(Resource res) {
		if (res == Resource.GOLD)
			return maxGold;
		if (res == Resource.LUNAR)
			return maxLunar;
		if (res == Resource.SOLAR)
			return maxSolar;
		return 0;
	}

	public void modifyGold(int amount) throws Exception {
		if (-amount > gold)
			throw new Exception("Not enough gold");
		gold = (gold + amount > maxGold) ? maxGold : gold + amount;
	}

	public void modifySolar(int amount) throws Exception {
		if (-amount > solar)
			throw new Exception("Not enough solar");
		solar = (solar + amount > maxSolar) ? maxSolar : solar + amount;
	}

	public void modifyLunar(int amount) throws Exception {
		if (-amount > lunar)
			throw new Exception("Not enough lunar");
		lunar = (lunar + amount > maxLunar) ? maxLunar : lunar + amount;
	}

	public void modifyGoldWithoutException(int amount) {
		if (-amount > gold)
			gold = 0;
		else 
			gold += amount;
	}

	public void modifySolarWithoutException(int amount) {
		if (-amount > solar)
			solar = 0;
		else 
			solar += amount;
	}

	public void modifyLunarWithoutException(int amount) {
		if (-amount > lunar)
			lunar = 0;
		else 
			lunar += amount;
	}
	
	public void increaseMax(Resource res, int amount) {
		if (res == Resource.GOLD)
			maxGold += amount;
		if (res == Resource.LUNAR)
			maxLunar += amount;
		if (res == Resource.SOLAR)
			maxSolar += amount;
	}

	public Chest clone(){
		return new Chest(gold, solar, lunar, maxGold, maxSolar, maxLunar);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chest) {
			Chest objc = (Chest) obj;
			if (this.gold == objc.gold && this.solar == objc.solar && this.lunar == objc.lunar &&
					this.maxGold == objc.maxGold && this.maxSolar == objc.maxSolar && this.maxLunar == objc.maxLunar){
				return true;
			}
		}
		return false;
	}

	public int getAmountLeftOf(Resource res) {
		if (res == Resource.GOLD)
			return maxGold - gold;
		if (res == Resource.LUNAR)
			return maxLunar - lunar;
		if (res == Resource.SOLAR)
			return maxSolar - solar;
		
		return -1;
	}
}
