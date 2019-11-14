package unsw.dungeon.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DungeonApplication extends Application {

	private DungeonControllerLoader dungeonControllerLoader;
	private Parent gameRoot;

	@Override
	public void start(Stage primaryStage) {

		LevelSelectController levelSelectController = new LevelSelectController();
		FXMLLoader levelSelectLoader = new FXMLLoader(getClass().getResource("LevelSelect.fxml"));
		levelSelectLoader.setController(levelSelectController);

		double zoomAmount = 0.03;

		// When the level is selected, load and start that level
		levelSelectController.onSelected((level) -> {
			try {
				this.dungeonControllerLoader = new DungeonControllerLoader(level);
				setGame(primaryStage);

				FXMLLoader startScreenLoader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
				StartScreenController startScreenController = new StartScreenController();
				startScreenLoader.setController(startScreenController);

				Parent startScreen = forceLoad(startScreenLoader);
				StackPane gameContainer = new StackPane((StackPane) primaryStage.getScene().getRoot());

				startScreenController.onPlay(() -> {
					unblurScreen(zoomAmount);
					gameContainer.getChildren().remove(startScreen);
					gameRoot.requestFocus();
				});

				blurScreen(zoomAmount);
				gameContainer.getChildren().add(startScreen);

				primaryStage.setScene(new Scene(gameContainer));

				primaryStage.setTitle("Dungeon");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		Parent levelSelector = forceLoad(levelSelectLoader);
		Scene levelScene = new Scene(levelSelector);
		levelSelector.requestFocus();
		primaryStage.setScene(levelScene);

		primaryStage.setTitle("Level Select | Dungeon");
		primaryStage.show();

	}

	private void blurScreen(double zoomAmount) {
		BoxBlur blur = new BoxBlur();
		blur.setWidth(5);
		blur.setHeight(5);
		blur.setIterations(3);
		gameRoot.setEffect(blur);

		gameRoot.setScaleX(1 + zoomAmount);
		gameRoot.setScaleY(1 + zoomAmount);
	}

	private void unblurScreen(double zoomAmount) {
		ScaleTransition st = new ScaleTransition(Duration.millis(500), gameRoot);
		st.setInterpolator(Interpolator.EASE_OUT);

		st.setByX(-zoomAmount);
		st.setByY(-zoomAmount);

		gameRoot.setEffect(null);
		st.play();
	}

	static private Parent forceLoad(FXMLLoader loader) {
		try {
			return loader.load();
		} catch (IOException e) {
			System.out.println("Fatal error thrown " + e);
			System.out.println("Eh.");
			return null;
		}
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

		gameRoot = null;
		Parent HUDnode = null;

		gameRoot = forceLoad(loader);
		HUDnode = forceLoad(HUDloader);

		// Hook HUD onto the controller
		HUD.attach(controller);

		StackPane gameScreen = new StackPane(gameRoot);

		VBox box = new VBox(gameScreen, HUDnode);
		container.getChildren().addAll(box);

		Scene scene = new Scene(container);
		gameRoot.requestFocus();
		primaryStage.setScene(scene);

		// Register restart event
		controller.restartEvent.register(() -> setGame(primaryStage));

		// Register finish event
		controller.getDungeon().finishEvent.register(() -> {
			FXMLLoader winLoader = new FXMLLoader(getClass().getResource("WinScreen.fxml"));
			primaryStage.setScene(new Scene(forceLoad(winLoader)));
		});

		// Register lose event
		controller.getDungeon().playerDeadEvent.register(() -> {
			FXMLLoader loseLoader = new FXMLLoader(getClass().getResource("LoseScreen.fxml"));
			gameScreen.getChildren().add(forceLoad(loseLoader));
		});

		return controller;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
