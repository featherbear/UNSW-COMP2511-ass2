package unsw.dungeon.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

	private DungeonControllerLoader dungeonControllerLoader;

	@Override
	public void start(Stage primaryStage) throws IOException {

		LevelSelectController levelSelectController = new LevelSelectController();
		FXMLLoader levelSelectLoader = new FXMLLoader(getClass().getResource("LevelSelect.fxml"));
		levelSelectLoader.setController(levelSelectController);

		// When the level is selected, load and start that level
		levelSelectController.onSelected((level) -> {
			try {
				this.dungeonControllerLoader = new DungeonControllerLoader(level);
				setGame(primaryStage);
				primaryStage.setTitle("Dungeon");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		Parent root = levelSelectLoader.load();
		Scene levelScene = new Scene(root);
		root.requestFocus();
		primaryStage.setScene(levelScene);

		primaryStage.setTitle("Level Select | Dungeon");
		primaryStage.show();

	}

	/**
	 * Load the game
	 * 
	 * @param primaryStage
	 * @return DungeonController instance
	 */
	private DungeonController setGame(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));

		DungeonController controller = dungeonControllerLoader.loadController();
		loader.setController(controller);

		StackPane container = new StackPane();

		HUDController HUD = new HUDController();
		FXMLLoader HUDloader = new FXMLLoader(getClass().getResource("HUD.fxml"));
		HUDloader.setController(HUD);

		Parent root = null;
		Parent HUDnode = null;

		try {
			root = loader.load();
			HUDnode = HUDloader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Hook HUD onto the controller
		HUD.attach(controller);

		StackPane gameScreen = new StackPane(root);

		VBox box = new VBox(gameScreen, HUDnode);
		box.setAlignment(Pos.CENTER);
		box.setBackground(new Background(new BackgroundImage(new Image("backgroundTile.png"), BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

		container.getChildren().addAll(box);

		Scene scene = new Scene(container);
		root.requestFocus();
		primaryStage.setScene(scene);

		// Register restart event
		controller.restartEvent.register(() -> setGame(primaryStage));

		// Register finish event
		controller.getDungeon().finishEvent.register(() -> {
			try {
				// Show the Win Screen
				FXMLLoader winLoader = new FXMLLoader(getClass().getResource("WinScreen.fxml"));
				primaryStage.setScene(new Scene(winLoader.load()));
			} catch (IOException e) {
			}
		});

		controller.getDungeon().playerDeadEvent.register(() -> {
			try {
				// Show the Lose Screen
				FXMLLoader loseLoader = new FXMLLoader(getClass().getResource("LoseScreen.fxml"));
				gameScreen.getChildren().add(loseLoader.load());
			} catch (IOException e) {
			}
		});

		return controller;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
