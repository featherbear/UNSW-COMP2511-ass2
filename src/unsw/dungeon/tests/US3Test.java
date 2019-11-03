package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.goals.GoalComposite;
import unsw.dungeon.goals.GoalStrategyAND;
import unsw.dungeon.goals.GoalStrategyBoulder;
import unsw.dungeon.goals.GoalStrategyEnemy;
import unsw.dungeon.goals.GoalStrategyOR;
import unsw.dungeon.goals.GoalStrategyTreasure;

public class US3Test {
	private Boulder boulder;
	private Player player;
	private Dungeon dungeon;
	private Switch sw;
	private TestUtils create;
	private Exit exit;
	private Treasure treasure;
	private Enemy enemy;
	
	
	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		create = new TestUtils(dungeon);
		dungeon.setPlayer(player);
	}
	
	@Test
	void simpleGoal() {
		Goal goal = new Goal(dungeon, new GoalStrategyBoulder());
		dungeon.setGoal(goal);
		sw = create.Switch(2, 2);
		sw.activate();
		assertEquals(true, goal.achieved());
	}
	
	@Test
	void simpleCompositeGoalOR() {
		GoalComposite goals = new GoalComposite(dungeon, new GoalStrategyOR());
		Goal goal1 = new Goal(dungeon, new GoalStrategyBoulder());
		goals.addSubGoal(goal1);
		Goal goal2 = new Goal(dungeon, new GoalStrategyTreasure());
		goals.addSubGoal(goal2);
		dungeon.setGoal(goals);
		sw = create.Switch(2, 2);
		sw.activate();
		assertEquals(true, goal1.achieved());
		treasure = create.Treasure(1, 2);
		player.moveDown();
		assertEquals(treasure, player.getInventory().get(0));
		assertEquals(true, goal2.achieved());
		assertEquals(2, goals.getSubGoals().size());
		assertEquals(true, ((Goal)goals).achieved());
	}
	
	@Test
	void simpleCompositeGoalAND() {
		GoalComposite goals = new GoalComposite(dungeon, new GoalStrategyAND());
		Goal goal1 = new Goal(dungeon, new GoalStrategyBoulder());
		goals.addSubGoal(goal1);
		Goal goal2 = new Goal(dungeon, new GoalStrategyEnemy());
		goals.addSubGoal(goal2);
		dungeon.setGoal(goals);
		sw = create.Switch(2, 2);
		sw.activate();
		assertEquals(true, goal1.achieved());
		enemy = create.Enemy(1, 5);
		enemy.kill();
		assertEquals(true, goal2.achieved());
		assertEquals(2, goals.getSubGoals().size());
		assertEquals(true, ((Goal)goals).achieved());
		
	}
}
