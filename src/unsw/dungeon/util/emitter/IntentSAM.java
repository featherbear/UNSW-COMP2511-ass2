package unsw.dungeon.util.emitter;

@FunctionalInterface
public interface IntentSAM<BaseType, DataType extends EmitterData> {
	public boolean execute(BaseType obj, DataType data);
}
