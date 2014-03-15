package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rmblworx.tools.timey.gui.config.ConfigManager;
import rmblworx.tools.timey.gui.config.FileConfigStorage;

/**
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class Main extends Application {

	/**
	 * Dateiname der Konfigurationsdatei (kann auch Pfad enthalten).
	 */
	private static final String CONFIG_FILENAME = "Timey.config.xml";

	/**
	 * Startet die Anwendung.
	 * @param stage Fenster der Anwendung
	 */
	public final void start(final Stage stage) {
		try {
			ConfigManager.setCurrentConfig(new FileConfigStorage().loadFromFile(CONFIG_FILENAME));

			final ResourceBundle resources = new GuiHelper().getResourceBundle(ConfigManager.getCurrentConfig().getLocale());
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeyGui.fxml"), resources);
			final Parent root = (Parent) loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle(resources.getString("application.title"));
			stage.setResizable(false);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("img/clock.png")));
			stage.show();

			final TimeyController timeyController = loader.getController();
			timeyController.setStage(stage);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Beendet die Anwendung.
	 */
	public final void stop() {
		new FileConfigStorage().saveToFile(ConfigManager.getCurrentConfig(), CONFIG_FILENAME);
		System.exit(0);
	}

	/**
	 * Einstiegspunkt der Anwendung.
	 * @param args Parameter
	 */
	public static void main(final String[] args) {
		launch(args);
	}

}
