package unsw.dungeon.util.emitter;

/**
 * SAM Interface for data events
 *
 * @param <BaseType>
 * @param <DataType>
 */
@FunctionalInterface
public interface EventSAM<BaseType, DataType extends EmitterData> {
	public void execute(BaseType obj, DataType data);
}
