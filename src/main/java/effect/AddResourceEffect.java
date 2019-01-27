package effect;

import player.Player;

import java.util.ArrayList;
import java.util.Arrays;

import game.Resource;

public class AddResourceEffect extends Effect{

	private final Resource res;
	private final Resource res2;
	private final Resource res3;
	private final boolean choice;
	private final int amount;
	
	public AddResourceEffect(Resource res, int amount, Effect nextEffect) {
		super(nextEffect);
		this.res = res;
		this.amount = amount;
		choice = false;
		res2 = res3 =null;
	}
	
	public AddResourceEffect(Resource res, int amount) {
		super(null);
		this.res = res;
		choice = false;
		res2 = res3 =null;
		this.amount = amount;
	}
	
	public AddResourceEffect(Resource res, Resource res2, Resource res3, int amount) {
		super(null);
		this.res = res;
		this.res2 = res2;
		this.res3 = res3;
		this.amount = amount;
		choice = true;
	}

	@Override
	public void doEffect(Player player) {
		if(!choice)
			player.modifyResource(res, amount);
		else {
			ArrayList<Resource> resT = new ArrayList<>(Arrays.asList(res, res2, res3));
			ArrayList<Integer> amountT = new ArrayList<>(Arrays.asList(amount, amount, amount));
			
			int whatRes = player.findBestResourceToAdd(resT, amountT);

			if(whatRes == 0)
				player.modifyResource(res, amount);
			else if(whatRes == 1)
				player.modifyResource(res2, amount);
			else
				player.modifyResource(res3, amount);
		}
	}
	
}
