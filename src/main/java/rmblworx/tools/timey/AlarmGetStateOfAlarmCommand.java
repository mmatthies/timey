/**
 * 
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "ConcreteCommand" implementation.
 * <ul>
 * <li>defines a binding between a Receiver object and an action.</li>
 * <li>implements Execute by invoking the corresponding operation(s) on Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author "mmatthies"
 */
public class AlarmGetStateOfAlarmCommand implements ICommand {

	/** stores the Receiver instance of the ConcreteCommand. */
	private final IAlarm fReceiver;
	/**
	 * Beschreibung des Alarmzeitpunktes.
	 */
	private final TimeDescriptor timeDescriptor;

	/**
	 * Erweiterter Konstruktor.
	 * @param receiver Empfaengerimplementierung
	 * @param descriptor Beschreibung des Alarmzeitpunktes
	 */
	public AlarmGetStateOfAlarmCommand(final IAlarm receiver, final TimeDescriptor descriptor) {
		super();
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * This method executes the command by invoking the corresponding
	 * method of the Receiver instance.
	 */
	@Override
	public <T> T execute() {
		return (T) this.fReceiver.isAlarmtimestampActivated(this.timeDescriptor);
	}

}