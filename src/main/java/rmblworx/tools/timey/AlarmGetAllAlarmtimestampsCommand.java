/**
 * 
 */
package rmblworx.tools.timey;

/**
 * PatternBox: "ConcreteCommand" implementation.
 * <ul>
 *   <li>defines a binding between a Receiver object and an action.</li>
 *   <li>implements Execute by invoking the corresponding operation(s) on Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author "mmatthies"
 */
public class AlarmGetAllAlarmtimestampsCommand implements ICommand {

	/** stores the Receiver instance of the ConcreteCommand. */
	private final IAlarm fReceiver;

	/**
	 * Erweiterter Konstruktor.
	 * @param receiver Empfaengerimplementierung
	 */
	public AlarmGetAllAlarmtimestampsCommand(final IAlarm receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method executes the command by invoking the corresponding
	 * method of the Receiver instance.
	 */
	@Override
	public <T> T execute() {
		return (T) this.fReceiver.getAllAlarmtimestamps();
	}

}