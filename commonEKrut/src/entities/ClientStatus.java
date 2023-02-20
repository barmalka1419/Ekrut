package entities;

import java.util.Objects;

/**
 * The Class ClientStatus.
 * class that stores the client's ip
 */
public class ClientStatus {
	
	
	private String ip;
	
	private String host;
	
	private String status;
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(host, ip);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientStatus other = (ClientStatus) obj;
		return Objects.equals(host, other.host) && Objects.equals(ip, other.ip);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "[ip=" + ip + ", host=" + host + ", status=" + status + "]";
	}
	

	/**
	 * Instantiates a new client status.
	 *
	 * @param ip the ip
	 * @param host the host
	 * @param status the status
	 */
	public ClientStatus(String ip, String host, String status) {

		this.ip = ip;
		this.host = host;
		this.status = status;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	

}
