package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Starten der Stoppuhr.
 * @author mmatthies
 */
class StopwatchStartCommand implements ICommand {

	/**
	 * Referenz auf die Empfängerinstanz die von diesem Kommando gesteuert wird.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz auf die Empfängerimplementierung die von diesem Kommando gesteuert wird.
	 */
	public StopwatchStartCommand(final IStopwatch receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return Werteobjekt das die gemessene Zeit kapselt.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TimeDescriptor execute() {
		return this.fReceiver.startStopwatch();
	}
}
