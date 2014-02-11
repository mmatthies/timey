package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GuiHelper {

	public void showMessageDialog(final String title, final String text, final ResourceBundle i18n) {
		try {
			final Stage stage = new Stage(StageStyle.UTILITY);
			final Parent root = FXMLLoader.load(getClass().getResource("MessageDialogGui.fxml"), i18n);

			final Label message = (Label) root.lookup("#message");
			message.setText(text);

			final Button okButton = (Button) root.lookup("#okButton");
			okButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					stage.close();
				}
			});

			final Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(title);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}