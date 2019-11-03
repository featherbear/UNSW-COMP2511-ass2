package unsw.dungeon.ui;

/**
 * SAM Interface for level selection events
 *
 * @param <BaseType>
 * @param <DataType>
 */
@FunctionalInterface
public interface LevelSelectedSAM {
	public void execute(String level);
}
