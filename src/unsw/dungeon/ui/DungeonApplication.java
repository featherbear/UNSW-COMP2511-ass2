package unsw.dungeon.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {

		LevelSelectController levelSelectController = new LevelSelectController();

		FXMLLoader levelSelectLoader = new FXMLLoader(getClass().getResource("LevelSelect.fxml"));
		levelSelectLoader.setController(levelSelectController);

		levelSelectController.onSelected((level) -> {
			try {
				DungeonController dungeonController = new DungeonControllerLoader(level).loadController();

				FXMLLoader dungeonLoader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
				dungeonLoader.setController(dungeonController);

				Parent root = dungeonLoader.load();
				Scene scene = new Scene(root);

				root.requestFocus();
				primaryStage.setScene(scene);
				primaryStage.setTitle("Dungeon");

			} catch (IOException e) {
				// FileNotFoundExceptions _shouldn't_ happen, but other exceptions may happen
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

	public static void main(String[] args) {
		launch(args);
	}

}
