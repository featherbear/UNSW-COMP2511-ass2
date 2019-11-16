package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;

public class Goal {

	protected GoalStrategy strategy;
	protected boolean achieved;
	protected Dungeon dungeon;

	public Goal(Dungeon dungeon, GoalStrategy strategy) {
		this.dungeon = dungeon;
		this.strategy = strategy;
	}

	/**
	 * @return Goal achieved status
	 */
	public boolean achieved() {
		return this.achieved;
	}

	/**
	 * Check and update the goal achieved status
	 * 
	 * @return Goal achieved status
	 */
	public boolean check() {
		return (this.achieved = this.strategy.achieved(this));
	}

	/**
	 * @return Dungeon
	 */
	public Dungeon getDungeon() {
		return this.dungeon;
	}

	@Override
	public String toString() {
		return this.strategy.getInfoText();
	}

}
