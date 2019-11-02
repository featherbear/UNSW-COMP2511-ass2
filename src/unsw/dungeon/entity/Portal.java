package unsw.dungeon.entity;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.events.LocationChanged;

public class Portal extends Entity implements Interactable {

	private BooleanProperty activated;
	private int id;

	public Portal(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
		this.activated = new SimpleBooleanProperty(true);
	}

	@Override
	public boolean interact(Entity entity) {
		if (!(entity instanceof Player)) {
			return false;
		}

		if (!this.getActivated()) {
			return false;
		}

		Player player = (Player) entity;

		ArrayList<Entity> portals = Entity.filter(this.getDungeon().getEntities(), Portal.class);

		Portal matchingPortal = null;

		for (Entity obj : portals) {
			Portal portal = (Portal) obj;
			if (portal.id == this.id && portal != this) {
				matchingPortal = portal;
				break;
			}
		}

		if (matchingPortal == null) {
			System.out.println(String.format("No other portals with ID %d exist!", this.id));
			return false;
		}

		// Teleport the player to the destination portal
		int newX = matchingPortal.getX();
		int newY = matchingPortal.getY();

		Entity obstruction = this.getDungeon().getEntityAt(EntityLevel.OBJECT, newX, newY);
		if (obstruction instanceof Enemy) {
			((Enemy) obstruction).kill();
		}

		player.setXY(newX, newY);

		return true;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public BooleanProperty activated() {
		return this.activated;
	}

	public boolean getActivated() {
		return this.activated.get();
	}

	public void activate() {
		this.activated.set(true);

	}

	public void deactivate() {
		this.activated.set(false);

	}

	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}

		return !player.interact(this);
	}

}
