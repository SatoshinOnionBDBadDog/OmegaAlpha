package ch.supsi.omega.omero;

/**
 * Keeps the services alive.
 */
class KeepClientAlive implements Runnable {

	/** Reference to the gateway. */
	private final Gateway gateway;

	/**
	 * Creates a new instance.
	 * 
	 * @param gateway
	 *            Reference to the gateway. Mustn't be <code>null</code>.
	 */
	KeepClientAlive(final Gateway gateway) {
		if (gateway == null)
			throw new IllegalArgumentException("No gateway specified.");
		this.gateway = gateway;
	}

	/** Runs. */
	@Override
	public void run() {
		try {
			synchronized (this.gateway) {
				this.gateway.keepSessionAlive();
			}
		} catch (final Throwable t) {
			// Handle error here.
		}
	}

}
