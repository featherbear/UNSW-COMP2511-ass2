package unsw.dungeon.util;

public interface SAM<T extends Object> {
	public boolean check(T ref, int newX, int newY);
}
