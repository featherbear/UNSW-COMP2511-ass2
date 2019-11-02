package unsw.dungeon.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LevelSelectController {

	private final static String levelSuffix = ".json";
	private String selectedLevel;

	@FXML
	private ListView<String> levels;

	private List<String> levelNames; // Does not contain the extension

	@FXML
	private Button btnPlay;

	public LevelSelectController() {
		this.selectedLevel = null;
	}

	public String getSelectedLevel() {
		// return levels.getSelectionModel().getSelectedItem();
		return this.selectedLevel;
	}

	private LevelSelectedSAM onLevelSelected;

	public void onSelected(LevelSelectedSAM onLevelSelected) {
		this.onLevelSelected = onLevelSelected;
	}

	@FXML
	public void initialize() {

		File f = new File("dungeons");
		File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith(levelSuffix));

		ArrayList<String> filenames = new ArrayList<String>();
		for (File file : matchingFiles) {
			filenames.add(file.getName());
		}

		this.levelNames = filenames.stream().map(s -> s.substring(0, s.length() - levelSuffix.length()))
				.collect(Collectors.toList());
		levels.getItems().setAll(levelNames);

		levels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			btnPlay.setDisable(false);
			this.selectedLevel = newValue;
		});
	}

	@FXML
	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (this.getSelectedLevel() != null) {
				submit();
			}
		}
	}

	@FXML
	private void random() {
		Random rand = new Random();
		this.selectedLevel = this.levelNames.get(rand.nextInt(this.levelNames.size()));
		this.submit();
	}

	@FXML
	private void submit() {
		if (this.selectedLevel == null) {
			return;
		}

		this.onLevelSelected.execute(this.selectedLevel + levelSuffix);
	}

}
