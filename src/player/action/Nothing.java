package player.action;

import player.Player;

public class Nothing extends Action{

	public Nothing(Player p) {
		super(p);
	}

	@Override
	public void execute(){
		System.out.println(player.getName() + " does nothing.");
	}

	@Override
	public String toString() {
		return "To do nothing";
	}

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub
		
	}

}
