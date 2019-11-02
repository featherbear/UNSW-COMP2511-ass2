package unsw.dungeon.util.emitter;

/**
 * Boolean SAM Interface for event intents
 *
 * @param <BaseType>
 * @param <DataType>
 */
@FunctionalInterface
public interface IntentSAM<BaseType, DataType extends EmitterData> {
	public boolean execute(BaseType obj, DataType data);
}
