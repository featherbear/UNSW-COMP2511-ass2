package unsw.dungeon;

import javafx.beans.value.ObservableValue;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Wall;

public class GameHooks implements LoaderHook {

	@Override
	public void onLoad(Player player) {
		player.addMoveIntentCheck((Player p, int newX, int newY) -> {

			System.out.println("Move intent!");
			return true;

		});
	}

	@Override
	public void onLoad(Wall wall) {

	}

	@Override
	public void onLoad(Exit exit) {
		Dungeon dungeon = exit.getDungeon();
		Player player = dungeon.getPlayer();

		player.x().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			if (exit.getX() == newValue.intValue() && exit.getY() == player.getY()) {
				System.out.println("Step!");
			}
		});

		player.y().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			if (exit.getX() == player.getX() && exit.getY() == newValue.intValue()) {
				System.out.println("Step!");
			}
		});
	}

	@Override
	public void postLoad(Dungeon dungeon) {
		System.out.println("Dungeon load complete");
	}

}
