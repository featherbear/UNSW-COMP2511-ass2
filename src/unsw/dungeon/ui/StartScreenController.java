package unsw.dungeon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import unsw.dungeon.util.emitter.GenericSAM;

public class StartScreenController {

	private GenericSAM onPlayButton;

	public void onPlay(GenericSAM onPlayButton) {
		this.onPlayButton = onPlayButton;
	}

	@FXML
	private Button btnPlay;

	@FXML
	private void play() {
		this.onPlayButton.execute();
	}
}
