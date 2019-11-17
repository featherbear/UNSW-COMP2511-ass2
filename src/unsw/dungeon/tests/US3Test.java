package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.goals.GoalComposite;
import unsw.dungeon.goals.GoalStrategyAND;
import unsw.dungeon.goals.GoalStrategyBoulder;
import unsw.dungeon.goals.GoalStrategyEnemy;
import unsw.dungeon.goals.GoalStrategyExit;
import unsw.dungeon.goals.GoalStrategyOR;
import unsw.dungeon.goals.GoalStrategyTreasure;

public class US3Test {
	private Player player;
	private Dungeon dungeon;
	private TestUtils Create;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
		Create = new TestUtils(dungeon);
	}

	@Test
	void dungeonSetGoal() {
		Goal goal = new Goal(dungeon, null);
		dungeon.setGoal(goal);
		assertEquals(goal, dungeon.getGoal());
	}

	@Test
	void simpleGoalBoulder() {
		Switch sw = Create.Switch(2, 2);

		Goal goal = new Goal(dungeon, new GoalStrategyBoulder());
		dungeon.setGoal(goal);

		assertFalse(goal.achieved());
		sw.activate();
		assertTrue(goal.achieved());
	}

	@Test
	void simpleGoalEnemy() {
		Enemy enemy1 = Create.Enemy(2, 2);
		Enemy enemy2 = Create.Enemy(3, 3);

		Goal goal = new Goal(dungeon, new GoalStrategyEnemy());
		dungeon.setGoal(goal);

		assertFalse(goal.achieved());
		enemy1.kill();
		assertFalse(goal.achieved());
		enemy2.kill();
		assertTrue(goal.achieved());
	}

	@Test
	void simpleGoalExit() {
		Exit exit = Create.Exit(2, 2);

		Goal goal = new Goal(dungeon, new GoalStrategyExit());
		dungeon.setGoal(goal);

		assertFalse(goal.achieved());
		exit.activate();
		assertTrue(goal.achieved());
	}

	@Test
	void simpleGoalExit_playerActivated() {
		Exit exit = Create.Exit(2, 2);

		Goal goal = new Goal(dungeon, new GoalStrategyExit());
		dungeon.setGoal(goal);
		Create.PostLoad();

		assertFalse(goal.achieved());
		player.moveRight();
		assertFalse(goal.achieved());
		player.moveDown();
		assertTrue(goal.achieved());
	}

	@Test
	void simpleGoalTreasure() {
		Treasure treasure = Create.Treasure(2, 1);
		Treasure treasure2 = Create.Treasure(2, 2);

		Goal goal = new Goal(dungeon, new GoalStrategyTreasure());
		dungeon.setGoal(goal);

		assertFalse(goal.achieved());
		player.moveRight();
		assertFalse(goal.achieved());
		player.moveDown();
		assertTrue(goal.achieved());
	}

	@Test
	void compositeGoalSize() {
		GoalComposite goals = new GoalComposite(dungeon, new GoalStrategyAND());

		goals.addSubGoal(new Goal(dungeon, new GoalStrategyBoulder()));
		goals.addSubGoal(new Goal(dungeon, new GoalStrategyEnemy()));

		assertEquals(2, goals.getSubGoals().size());
	}

	@Test
	void compositeGoalOR() {
		Switch sw = Create.Switch(2, 2);
		Treasure treasure = Create.Treasure(1, 2);

		GoalComposite goals = new GoalComposite(dungeon, new GoalStrategyOR());

		Goal goal1 = new Goal(dungeon, new GoalStrategyBoulder());
		Goal goal2 = new Goal(dungeon, new GoalStrategyTreasure());
		goals.addSubGoal(goal1);
		goals.addSubGoal(goal2);
		dungeon.setGoal(goals);

		assertFalse(goals.achieved());

		sw.activate();
		assertTrue(goal1.achieved());
		assertTrue(goals.achieved());

		sw.deactivate();
		assertFalse(goals.achieved());

		assertFalse(goal2.achieved());
		player.moveDown();
		assertTrue(goal2.achieved());
		assertTrue(goals.achieved());

		sw.activate();
		assertTrue(goal1.achieved());
		assertTrue(goal2.achieved());
		assertTrue(goals.achieved());
	}

	@Test
	void simpleCompositeGoalAND() {
		GoalComposite goals = new GoalComposite(dungeon, new GoalStrategyAND());

		Goal goal1 = new Goal(dungeon, new GoalStrategyBoulder());
		Goal goal2 = new Goal(dungeon, new GoalStrategyEnemy());
		goals.addSubGoal(goal2);
		goals.addSubGoal(goal1);
		dungeon.setGoal(goals);

		Switch sw = Create.Switch(2, 2);
		Enemy enemy = Create.Enemy(1, 5);

		assertFalse(goals.achieved());

		assertFalse(goal1.achieved());
		sw.activate();
		assertTrue(goal1.achieved());

		assertFalse(goals.achieved());

		assertFalse(goal2.achieved());
		enemy.kill();
		assertTrue(goal2.achieved());

		assertTrue(goals.achieved());
	}
}
