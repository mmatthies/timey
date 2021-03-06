package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Erzeugt die konkreten Kommandoimplementierungen und setzt deren Empfängerimplementierungen.
 * 
 * @author mmatthies
 */
class CountdownClient {

	/** Speichert die Empfänger Instanz des Klient. */
	private final ICountdown fReceiver;

	/**
	 * Erzeugt eine Klient-Instanz und speichert die uebergebene Empfängerimplementierung.
	 *
	 * @param receiver
	 *            Empfänger-Referenz.
	 */
	public CountdownClient(final ICountdown receiver) {
		this.fReceiver = receiver;
	}

	/**
	 * @return Werteobjekt das die Countdownzeit kapselt.
	 */
	public TimeDescriptor initCountdownStartCommand() {
		final CountdownStartCommand cmd = new CountdownStartCommand(this.fReceiver);
		final Invoker<TimeDescriptor> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return true wenn erfolgreich sonst false.
	 */
	public Boolean initCountdownStopCommand() {
		final CountdownStopCommand cmd = new CountdownStopCommand(this.fReceiver);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @param descriptor
	 *            Werteobjekt das die Countdownzeit kapselt.
	 * @return true wenn erfolgreich sonst false.
	 */
	public Boolean initSetCountdownTimeCommand(final TimeDescriptor descriptor) {
		final CountdownSetTimeCommand cmd = new CountdownSetTimeCommand(this.fReceiver, descriptor);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

}
