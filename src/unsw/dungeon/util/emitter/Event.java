package unsw.dungeon.util.emitter;

public interface Event<BaseType, DataType extends EmitterData> {
	public void execute(BaseType obj, DataType data);
}
