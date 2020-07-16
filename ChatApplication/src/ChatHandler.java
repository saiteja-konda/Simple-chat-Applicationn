import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ChatHandler implements Runnable
{
	private DatagramSocket socket;
	private boolean isRunning;
	
	public void bind(int port) throws SocketException
	{
		socket = new DatagramSocket(port);
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop()
	{
		isRunning = false;
		socket.close();
	}

	@Override
	public void run()
	{
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		isRunning = true;
		while(isRunning)
		{
			try
			{
				socket.receive(packet);
				
				String message = new String(buffer, 0, packet.getLength());
				System.out.println(message);
			} 
			catch (IOException e)
			{
				break;
			}
		}
	}

	public void sendTo(InetSocketAddress address, String message) throws IOException
	{
		byte[] buffer = message.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		packet.setSocketAddress(address);
		
		socket.send(packet);
	}
}
