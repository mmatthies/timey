package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.gui.component.TimePicker;

/**
 * GUI-Tests für den Countdown.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@Category(TimeyGuiTest.class)
public class CountdownControllerTest extends FxmlGuiControllerTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	// GUI-Elemente
	private Label countdownTimeLabel;
	private Button countdownStartButton;
	private Button countdownStopButton;
	private TimePicker countdownTimePicker;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Countdown.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();

		countdownTimeLabel = (Label) scene.lookup("#countdownTimeLabel");
		countdownStartButton = (Button) scene.lookup("#countdownStartButton");
		countdownStopButton = (Button) scene.lookup("#countdownStopButton");
		countdownTimePicker = (TimePicker) scene.lookup("#countdownTimePicker");
	}

	/**
	 * Testet den Zustand der Schaltflächen je nach Zustand des Countdowns.
	 */
	@Test
	public final void testStartStopButtonStates() {
		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertTrue(countdownStartButton.isDisabled());

		assertFalse(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());

		// Zeit setzen
		countdownTimePicker.setTime(DateTimeUtil.getLocalTimeForString("00:00:10"));

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());

		// Countdown starten
		countdownStartButton.fire();
		FXTestUtils.awaitEvents();
		verify(getController().getGuiHelper().getFacade()).startCountdown();

		// Zustand der Schaltflächen testen
		assertFalse(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());

		assertTrue(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());
		assertTrue(countdownStopButton.isFocused());

		// Countdown stoppen
		countdownStopButton.fire();
		FXTestUtils.awaitEvents();
		verify(getController().getGuiHelper().getFacade()).stopCountdown();

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());
		assertTrue(countdownStartButton.isFocused());

		assertFalse(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());

		// Zeit wieder auf 0 setzen
		countdownTimePicker.setTime(DateTimeUtil.getLocalTimeForString("00:00:00"));

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertTrue(countdownStartButton.isDisabled());
	}

	/**
	 * Testet die Übertragung der Zeit zwischen TimePicker und Label.
	 */
	@Test
	public final void testTimeConversionBetweenPickerAndLabel() {
		// Zeit setzen
		countdownTimePicker.setTime(DateTimeUtil.getLocalTimeForString("00:00:10"));

		// Countdown starten
		countdownStartButton.fire();
		FXTestUtils.awaitEvents();

		// verbleibende Zeit muss angezeigt sein
		assertEquals("00:00:10", countdownTimeLabel.getText());

		assertFalse(countdownTimePicker.isVisible());
		assertTrue(countdownTimeLabel.isVisible());

		// Countdown stoppen
		countdownStopButton.fire();
		FXTestUtils.awaitEvents();

		// verbleibende Zeit muss stimmen
		assertEquals(10000, countdownTimePicker.getTime().getMillisOfDay());

		assertTrue(countdownTimePicker.isVisible());
		assertFalse(countdownTimeLabel.isVisible());
	}

	/**
	 * Testet Starten und Stoppen per Tastatur unter Berücksichtigung der korrekten Fokussierung.
	 */
	@Test
	public final void testStartStopPerKeyboard() {
		// Sekunden-Feld fokussieren
		click(scene.lookup("#secondsTextField"));

		// Zeit auf 0 setzen
		countdownTimePicker.setTime(DateTimeUtil.getLocalTimeForString("00:00:00"));
		FXTestUtils.awaitEvents();

		// bei Zeit = 0 darf sich Countdown nicht starten lassen
		type(KeyCode.ENTER);
		verify(getController().getGuiHelper().getFacade(), never()).startCountdown();

		// Zeit setzen
		countdownTimePicker.setTime(DateTimeUtil.getLocalTimeForString("00:00:10"));
		FXTestUtils.awaitEvents();

		// Countdown starten
		type(KeyCode.ENTER);
		verify(getController().getGuiHelper().getFacade()).startCountdown();

		// Countdown stoppen
		type(KeyCode.ENTER);
		verify(getController().getGuiHelper().getFacade()).stopCountdown();
	}

}
