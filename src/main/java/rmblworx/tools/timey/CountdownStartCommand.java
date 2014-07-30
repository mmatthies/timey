package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Kommandoimplementierung ermoeglicht das Starten eines Countdowns.
 *
 * @author "mmatthies"
 */
class CountdownStartCommand implements ICommand {

	/**
	 * Referenz der Empfaengerimplementierung.
	 */
	private final ICountdown fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz der Empfaengerimplementierung
	 */
	public CountdownStartCommand(final ICountdown receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return Referenz auf das Zeitbeschreibungsobjekt welches die noch verbleibende Zeit uebermittelt.
	 */
	@Override
	public TimeDescriptor execute() {
		return this.fReceiver.startCountdown();
	}
}
