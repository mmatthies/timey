package rmblworx.tools.timey.gui;

import java.awt.TrayIcon;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
import rmblworx.tools.timey.ITimey;

/**
 * Hilfsmethoden zum Umgang mit der GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class GuiHelper {

	/**
	 * Spielt Sounds ab.
	 */
	private AudioPlayer audioPlayer = new AudioPlayer();

	/**
	 * Fassade.
	 */
	private ITimey facade;

	/**
	 * Symbol im System-Tray.
	 */
	private TrayIcon trayIcon;

	public void setAudioPlayer(final AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public final void setFacade(final ITimey facade) {
		this.facade = facade;
	}

	public final ITimey getFacade() {
		return facade;
	}

	public final void setTrayIcon(final TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	/**
	 * @param locale Sprache
	 * @return ResourceBundle für die jeweilige Sprache
	 */
	public final ResourceBundle getResourceBundle(final Locale locale) {
		return ResourceBundle.getBundle(getClass().getPackage().getName() + ".Timey_i18n", locale);
	}

	/**
	 * Zeigt einen Hinweis im System-Tray oder, falls dieser nicht verfügbar ist, in einem modalen Dialog an.
	 * @param caption Titel
	 * @param text Text
	 * @param i18n ResourceBundle
	 */
	public final void showTrayMessageWithFallbackToDialog(final String caption, final String text, final ResourceBundle i18n) {
		if (trayIcon != null) {
			showTrayMessage(caption, text);
		} else {
			showDialogMessage(caption, text, i18n);
		}
	}

	/**
	 * Zeigt einen Hinweis in einem modalen Dialog an.
	 * @param caption Titel
	 * @param text Text
	 * @param i18n ResourceBundle
	 */
	public void showDialogMessage(final String caption, final String text, final ResourceBundle i18n) {
		showMessageDialog(caption, text, i18n);
	}

	/**
	 * Zeigt einen Hinweis im System-Tray an.
	 * @param caption Titel
	 * @param text Text
	 */
	public final void showTrayMessage(final String caption, final String text) {
		if (trayIcon == null) {
			throw new RuntimeException("There's no system tray icon.");
		}

		trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
	}

	/**
	 * Zeigt ein modales Hinweisfenster.
	 * @param title Fenstertitel
	 * @param text Text
	 * @param i18n ResourceBundle
	 */
	public final void showMessageDialog(final String title, final String text, final ResourceBundle i18n) {
		try {
			final Stage stage = new Stage(StageStyle.UTILITY);
			final Parent root = FXMLLoader.load(getClass().getResource("MessageDialog.fxml"), i18n);

			final Label message = (Label) root.lookup("#message");
			message.setText(text);

			final Button okButton = (Button) root.lookup("#okButton");
			okButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					stage.close();
				}
			});

			stage.setScene(new Scene(root));
			stage.setTitle(title);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Spielt den Sound in einem separaten Thread ab und zeigt einen evtl. auftretenden Fehler in einem Dialog an.
	 * Falls {@code path == null} oder leer, macht die Methode nichts.
	 * @param path Pfad zur Datei
	 * @param i18n ResourceBundle
	 */
	public final void playSoundInThread(final String path, final ResourceBundle i18n) {
		if (path == null || path.length() == 0) {
			return;
		}

		audioPlayer.playInThread(path, new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(final Thread thread, final Throwable exception) {
				Platform.runLater(new Runnable() {
					public void run() {
						showDialogMessage(i18n.getString("messageDialog.error.title"),
								String.format(i18n.getString("sound.play.error"), exception.getLocalizedMessage()), i18n);
					}
				});
			}
		});
	}

	/**
	 * Führt den Task in einem separaten Thread aus, um die Anwendung nicht zu blockieren.
	 * @param task Task
	 * @param i18n ResourceBundle
	 */
	public final void runInThread(final Task<Void> task, final ResourceBundle i18n) {
		final Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(final Thread thread, final Throwable exception) {
				Platform.runLater(new Runnable() {
					public void run() {
						showDialogMessage(i18n.getString("messageDialog.error.title"), exception.getLocalizedMessage(), i18n);
					}
				});
			}
		});
		thread.start();
	}

}
