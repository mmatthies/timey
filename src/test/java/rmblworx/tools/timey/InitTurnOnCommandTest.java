/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.Alarm;
import rmblworx.tools.timey.AlarmClient;

/**
 * @author mmatthies
 * 
 */
public class InitTurnOnCommandTest {
	private AlarmClient client;
	private Alarm alarm;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.alarm = new Alarm();
		this.client = new AlarmClient(alarm);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.alarm = null;
		this.client = null;

	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmClient#initTurnOnCommand()}.
	 */
	@Test
	public final void testInitTurnOnCommand() {
		Boolean expectedValue = Boolean.TRUE;
		
		Boolean actual = this.client.initTurnOnCommand();
		assertEquals(expectedValue, actual);

	}
}
