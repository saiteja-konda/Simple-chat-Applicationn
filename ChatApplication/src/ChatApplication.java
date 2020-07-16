import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class ChatApplication
{

	public static void main(String[] args) throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Please your Username : ");
		String name = scanner.nextLine();
		
		System.out.print(" Enter your port number : ");
		int sourcePort = Integer.parseInt(scanner.nextLine());
		
		System.out.print("Enter IP address : ");
		String destinationIP = scanner.nextLine();
		
		System.out.print("Enter user port numder that you wish to connect with : ");
		int destinationPort = Integer.parseInt(scanner.nextLine());
		
		ChatHandler chatHandler = new ChatHandler();
		chatHandler.bind(sourcePort);
		chatHandler.start(); // Start Receive
		
		System.out.println("Connnection successfully.");
		System.out.println("Start Messaging");

		//passing Destination Ip and port values as parameters
		InetSocketAddress address = new InetSocketAddress(destinationIP, destinationPort);
		
		while(true)
		{
			String message = scanner.nextLine();
			
			if(message.equalsIgnoreCase("Logout"))
				break;
			
			message = name + " says " + message;

			//InetAddress
			chatHandler.sendTo(address, message);
			System.out.println(message);
		}
		
		scanner.close();
		chatHandler.stop();
		
		System.out.println("Closed.");
	}

}
