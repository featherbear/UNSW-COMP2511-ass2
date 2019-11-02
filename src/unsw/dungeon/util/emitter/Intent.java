package unsw.dungeon.util.emitter;

@FunctionalInterface
public interface Intent<BaseType, DataType extends EmitterData> {
	public boolean execute(BaseType obj, DataType data);
}
