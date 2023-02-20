package EntityController;


import entities.ClientStatus;
import javafx.collections.ObservableList;
import ocsf.server.ConnectionToClient;
import server.EchoServer;

/**
 * The Class ClientStatusController.
 * class for updating list of clients that connect to the server
 */
public class ClientStatusController {
	
	/**
	 * Update clients list.
	 *
	 * @param client the client
	 * @param status the status
	 */
	public static void updateClientsList(ConnectionToClient client,String status ) {
		
		
		ObservableList<ClientStatus> tempClientList = EchoServer.getClientsList();
		ClientStatus tempClientStatus = new ClientStatus(client.getInetAddress().getHostAddress(),client.getInetAddress().getHostName(),status);
		
		for(ClientStatus c : tempClientList) {
			
			if(c.equals(tempClientStatus)) {
				tempClientList.remove(c);
				break;
			}		
		}

		tempClientList.add(tempClientStatus);
		EchoServer.setClientsList(tempClientList);
	
	}
	

}
