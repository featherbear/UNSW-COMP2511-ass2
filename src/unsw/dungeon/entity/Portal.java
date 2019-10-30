package unsw.dungeon.entity;

import java.util.ArrayList;
import java.util.Random;

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
		ArrayList<Portal> matchingPortals = new ArrayList<Portal>();
		for (Entity obj : portals) {
			Portal portal = (Portal) obj;
			if (portal == this) {
				continue;
			}
			if (portal.id == this.id) {
				matchingPortals.add(portal);
			}
		}

		if (matchingPortals.size() == 0) {
			return false;
		}

		// Teleport the player to the destination portal
		Portal destination = matchingPortals.get(new Random().nextInt(matchingPortals.size()));

		// Do the teleport
		int newX = destination.getX();
		int newY = destination.getY();

//		ArrayList<Entity> objectEntitiesAtNewLocation = Entity.filter(Entity.filter(this.getDungeon().getEntities(), newX, newY), EntityLevel.OBJECT);
//		for (Entity objectEntity : objectEntitiesAtNewLocation) {
//			if (objectEntity instanceof Enemy) {
//				((Enemy) objectEntity).kill();
//			}
//		}

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

	public boolean portalEnterIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}

		return !player.interact(this);
	}

}