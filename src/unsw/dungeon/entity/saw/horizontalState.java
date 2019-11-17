package unsw.dungeon.entity.saw;

public class horizontalState implements SawState{
	private Saw saw;
	private boolean directionSwitched = false;
	
	public horizontalState(Saw saw) {
		this.saw = saw;
	}
	
	@Override
	public void move() {
	}

}
