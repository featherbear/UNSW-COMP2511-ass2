package unsw.dungeon.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.ItemPickedUp;
import unsw.dungeon.util.emitter.EventSAM;

public class HUDController {
	private DungeonController dungeonController;
	private Dungeon dungeon;
	private List<EntityImagePair> entityImagePairs;

	private EventSAM<Player, ItemPickedUp> itemPickedUpEvent;

	@FXML
	private GridPane items;

	private GridPaneHUD inventory;

	public HUDController() {

		// When an item is picked up, find the associated EntityImagePair
		this.itemPickedUpEvent = (player, event) -> {
			EntityImagePair pair = null;
			for (EntityImagePair p : this.entityImagePairs) {
				if (p.entity == event.item) {
					pair = p;
					break;
				}
			}

			if (pair == null) {
				return;
			}

			// Register the item
			this.inventory.add(pair);
		};
	}

	@FXML
	public void initialize() {
		this.inventory = new GridPaneHUD(this.items);
	}

	public void attach(DungeonController dungeonController) {
		this.dungeonController = dungeonController;
		this.dungeon = this.dungeonController.getDungeon();

		this.entityImagePairs = this.dungeonController.getEntityImagePairs();

		// Register item pickup events
		this.dungeon.getPlayer().itemPickedUpEvent.register(this.itemPickedUpEvent);
	}

}

class GridPaneHUD {
	public final GridPane gridPane;
	private ArrayList<EntryData> items;

	public GridPaneHUD(GridPane gridPane) {
		this.gridPane = gridPane;
		this.items = new ArrayList<EntryData>();
		for (int i = 0; i < gridPane.getColumnCount(); i++) {
			this.items.add(null);
		}
	}

	public boolean add(EntityImagePair pair) {
		if (pair == null) {
			return false;
		}

		ItemEntity entity = (ItemEntity) pair.entity;
		ImageView image = pair.imageView;

		int freeIndex = -1;

		// Look for a free item slot, starting from the end
		// Gets the slot closest to index 0.
		for (int i = this.items.size() - 1; i >= 0; i--) {
			EntryData data = this.items.get(i);

			// Free slot
			if (data == null) {
				freeIndex = i;
				continue;
			}

			// Don't add duplicates
			if (data.entity == entity) {
				return false;
			}

			// Check for multiple items of the same class
			if (data.entity.getClass() == entity.getClass()) {
				// Check if multiple items are allowed
				if (!data.entity.maxOne()) {

					// Set text
					if (data.text == null) {
						this.gridPane.add(data.registerCountText(), i, 0);
					}

					data.count.set(data.count.get() + 1);
					return true;
				} else {
					return false;
				}
			}

		}

		// If there are no free slots, add a new one
		// Yay space efficiency!
		// - Could also count the max possible number of slots
		if (freeIndex == -1) {
			this.gridPane.getColumnConstraints().add(new ColumnConstraints(1));
			freeIndex = this.items.size() - 1;
		}

		EntryData data = new EntryData(pair, null);
		this.items.set(freeIndex, data);

		// Add item to the HUD
		this.gridPane.add(data.imageView, freeIndex, 0);
		image.setVisible(true);

		// Check if the item has uses
		if (entity instanceof Usable) {
			registerUsableEntity(data);
			if (data.text != null) {
				this.gridPane.add(data.text, freeIndex, 0);
			}
		}

		return true;
	}

	/**
	 * Register events for Usable items: Item usage
	 * 
	 * @param data
	 */
	private void registerUsableEntity(EntryData data) {
		Usable usableEntity = (Usable) data.entity;
		int uses = usableEntity.getUses();

		// Show usage
		if (uses >= 2) {
			Text usageText = new Text(String.valueOf(uses));

			// Remove text when the durability is 1
			usableEntity.itemUsed().register((itemObj, evt) -> {
				if (evt.newValue == 1) {
					this.removeItem(usageText);
				}

				usageText.setText(String.valueOf(evt.newValue));
			});

			data.text = usageText;
		}

		// Register item removal when the item is used up
		usableEntity.itemUsed().register((itemObj, evt) -> {
			if (evt.newValue == 0) {
				this.removeItem(data);
			}
		});

	}

	/**
	 * Remove a Text nide
	 */
	private void removeItem(Text text) {
		this.gridPane.getChildren().remove(text);
	}

	/**
	 * Remove an ImageView node
	 */
	private void removeItem(ImageView node) {
		this.gridPane.getChildren().remove(node);
	}

	/**
	 * Remove an item from the HUD
	 * 
	 * @param data
	 */
	private boolean removeItem(EntryData data) {
		if (data == null) {
			return false;
		}

		if (this.items.contains(data)) {
			int i = this.items.indexOf(data);
			this.items.set(i, null);
			this.removeItem(data.text);
			this.removeItem(data.imageView);
			return true;
		}

		return false;

	}
}

/**
 * Structure to hold entity entry data
 */
class EntryData {
	public final ItemEntity entity;
	public final ImageView imageView;
	public Text text;
	public final IntegerProperty count;

	public EntryData(ItemEntity entity, ImageView imageView, Text text) {
		this.entity = entity;
		this.imageView = imageView;
		this.text = text;
		this.count = new SimpleIntegerProperty(1);
	}

	public EntryData(EntityImagePair pair, Text text) {
		this((ItemEntity) pair.entity, pair.imageView, text);
	}

	/**
	 * Create a Text control that is binded to the object count
	 */
	public Text registerCountText() {
		if (this.text != null) {
			return this.text;
		}

		Text countText = new Text();
		this.count.addListener((observer, oldValue, newValue) -> {
			countText.setText(String.valueOf(newValue));
		});
		this.text = countText;

		return this.text;

	}
}