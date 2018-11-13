package ch.supsi.omega.omero;

/**
 * @author Vanni Galli
 */
public class LoginCredentials {

	/** The default port value. */
	private static final int DEFAULT_PORT = 4064;

	/** The name of the user. */
	private final String userName;

	/** The password of the user. */
	private final String password;

	/** The address of the server. */
	private final String hostName;

	/** The port. */
	private int port;

	/**
	 * Creates a new instance.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The password.
	 * @param hostname
	 *            The name of the server.
	 */
	public LoginCredentials(final String userName, final String password,
	        final String hostname) {
		this(userName, password, hostname, LoginCredentials.DEFAULT_PORT);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The password.
	 * @param hostName
	 *            The name of the server.
	 * @param port
	 *            The port to use.
	 */
	public LoginCredentials(final String userName, final String password,
	        final String hostName, final int port) {
		this.userName = userName;
		this.password = password;
		this.hostName = hostName;
		this.port = port;
	}

	/**
	 * Sets the port.
	 * 
	 * @param port
	 *            The value to set.
	 */
	public void setPort(final int port) {
		this.port = port;
	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return See above.
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Returns the address of the server
	 * 
	 * @return See above.
	 */
	public String getHostName() {
		return this.hostName;
	}

	/**
	 * Returns the address of the server
	 * 
	 * @return See above.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Returns the port.
	 * 
	 * @return See above.
	 */
	public int getPort() {
		return this.port;
	}

}
