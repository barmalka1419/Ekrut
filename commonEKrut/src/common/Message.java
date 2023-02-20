package common;
import java.io.Serializable; 


/**
 * The Class Message.
 * class that stores the information of a message that is sent between the client and the server
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Object data;
	
	private String task;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public String getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 *
	 * @param task the new task
	 */
	public void setTask(String task) {
		this.task = task;
	}

}
