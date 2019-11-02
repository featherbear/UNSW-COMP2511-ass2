package unsw.dungeon.util.emitter;

@FunctionalInterface
public interface EventSAM<BaseType, DataType extends EmitterData> {
	public void execute(BaseType obj, DataType data);
}
