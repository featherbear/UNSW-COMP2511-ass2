package unsw.dungeon.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

	private DungeonControllerLoader dungeonControllerLoader;

	@Override
	public void start(Stage primaryStage) throws IOException {

		LevelSelectController levelSelectController = new LevelSelectController();

		FXMLLoader levelSelectLoader = new FXMLLoader(getClass().getResource("LevelSelect.fxml"));
		levelSelectLoader.setController(levelSelectController);

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

	private DungeonController setGame(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));

		DungeonController controller = dungeonControllerLoader.loadController();
		loader.setController(controller);

		Parent root = null;

		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		root.requestFocus();
		primaryStage.setScene(scene);

		controller.onRestart(() -> setGame(primaryStage));
		controller.getDungeon().finishEvent.register(() -> {
			FXMLLoader winLoader = new FXMLLoader(getClass().getResource("WinScreen.fxml"));

			WinScreenController winController = new WinScreenController();
			winLoader.setController(winController);
			try {
				primaryStage.setScene(new Scene(winLoader.load()));
			} catch (IOException e) {

			}
		});

		return controller;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
