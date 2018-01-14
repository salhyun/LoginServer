import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;

public class PoolDispatcher {
	
	private int numThreads=5;

	public PoolDispatcher()
	{		
	}
	
	public void startDispatching(final ServerSocket server)
	{
		for(int i=0; i<(numThreads-1); i++)
		{
			Thread thread = new Thread() {
				public void run() {
					dispatchLoop(server);					
				}
			};
			thread.start();			
		}
		dispatchLoop(server);
	}

	private void dispatchLoop(ServerSocket server)
	{
		LoginResponse loginResponse = new LoginResponse();

		while(true)
		{
			loginResponse.accept(server, "UTF-8");
		}

	}
}
