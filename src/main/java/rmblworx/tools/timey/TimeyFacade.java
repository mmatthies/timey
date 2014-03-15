package rmblworx.tools.timey;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Fassade fuer das System timey.
 * 
 * @author mmatthies
 */
public final class TimeyFacade implements ITimey {
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TimeyFacade.class);
	/**
	 * Alarm-Klient.
	 */
	private final AlarmClient alarmClient;
	/**
	 * Countdown-Klient.
	 */
	private final CountdownClient countdownClient;
	/**
	 * Spring-Kontext.
	 */
	private final ApplicationContext springContext;
	/**
	 * Stoppuhr-Klient.
	 */
	private final StopwatchClient stopwatchClient;

	/**
	 * Standardkonstruktor.
	 */
	public TimeyFacade() {
		this.springContext = new ClassPathXmlApplicationContext("spring-timey-context.xml");

		this.alarmClient = (AlarmClient) this.springContext.getBean("alarmClient");
		this.stopwatchClient = (StopwatchClient) this.springContext.getBean("stopwatchClient");
		this.countdownClient = (CountdownClient) this.springContext.getBean("countdownClient");
	}

	@Override
	public List<TimeDescriptor> getAllAlarmtimestamps() {
		return this.alarmClient.initGetAllAlarmtimestamps();
	}

	@Override
	public String getVersion() {
		File file;
		JarFile jar;
		String versionNumber = "";

		try {
			file = TimeyUtils.getPathToJar("timey*.jar").get(0).toFile();
			jar = new java.util.jar.JarFile(file);
			final Manifest manifest = jar.getManifest();
			final Attributes attributes = manifest.getMainAttributes();
			if (attributes != null) {
				final Iterator<Object> it = attributes.keySet().iterator();
				while (it.hasNext()) {
					final Attributes.Name key = (Attributes.Name) it.next();
					final String keyword = key.toString();
					if ("Implementation-Version".equals(keyword) || "Bundle-Version".equals(keyword)) {
						versionNumber = (String) attributes.get(key);
						break;
					}
				}
			}
			jar.close();
		} catch (final IOException e) {
			LOG.error("Die timey-Jar Datei konnte nicht gefunden und somit die Version nicht ermittelt werden!");
		}

		LOG.debug("Version: " + versionNumber);

		return versionNumber;
	}

	@Override
	public Boolean isAlarmtimestampActivated(final TimeDescriptor descriptor) {
		return this.alarmClient.initAlarmGetStateOfAlarmCommand(descriptor);
	}

	@Override
	public Boolean removeAlarmtimestamp(final TimeDescriptor descriptor) {
		return this.alarmClient.initAlarmDeleteAlarm(descriptor);
	}

	@Override
	public Boolean resetStopwatch() {
		return this.stopwatchClient.initStopwatchResetCommand();
	}

	@Override
	public Boolean setAlarmtimestamp(final TimeDescriptor descriptor) {
		return this.alarmClient.initSetAlarmtimestampCommand(descriptor);
	}

	@Override
	public Boolean setCountdownTime(final TimeDescriptor descriptor) {
		return this.countdownClient.initSetCountdownTimeCommand(descriptor);
	}

	@Override
	public Boolean setStateOfAlarmtimestamp(final TimeDescriptor descriptor, final Boolean isActivated) {
		return this.alarmClient.initAlarmSetStateOfAlarmCommand(descriptor, isActivated);
	}

	@Override
	public  TimeDescriptor startCountdown() {
		return this.countdownClient.initCountdownStartCommand();
	}

	@Override
	public TimeDescriptor startStopwatch() {
		return this.stopwatchClient.initStopwatchStartCommand();
	}

	@Override
	public  Boolean stopCountdown() {
		return this.countdownClient.initCountdownStopCommand();
	}

	@Override
	public Boolean stopStopwatch() {
		return this.stopwatchClient.initStopwatchStopCommand();
	}
}
