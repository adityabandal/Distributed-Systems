import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

// Server class
public class Server
{
	public static void main(String[] args) throws IOException
	{
		// server is listening on port 5056
		ServerSocket ss = new ServerSocket(5056);
		
		// running infinite loop for getting
		// client request
		while (true)
		{
			Socket s = null;
			
			try
			{
				// socket object to receive incoming client requests
				s = ss.accept();
				
				System.out.println("A new client is connected : " + s);
				
				// obtaining input and out streams
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				System.out.println("Assigning new thread for this client");

				// create a new thread object
				Thread t = new ClientHandler(s, dis, dos);

				// Invoking the start() method
				t.start();
				
			}
			catch (Exception e){
				s.close();
				e.printStackTrace();
			}
		}
	}
}

// ClientHandler class
class ClientHandler extends Thread
{
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
	DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	

	// Constructor
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
	{
		this.s = s;
		this.dis = dis;
		this.dos = dos;
	}

	@Override
	public void run()
	{
		String received,rw;
		String toreturn;
		while (true)
		{
			try {

				// Ask user what he wants
				dos.writeUTF("Enter the file name to access: ");
				
				// receive the answer from client
				received = dis.readUTF();
                                
				if(received.equals("Exit"))
				{
					System.out.println("Client " + this.s + " sends exit...");
					System.out.println("Closing this connection.");
					this.s.close();
					System.out.println("Connection closed");
					break;
				}
                                
				dos.writeUTF("read | write : ");
				
				// receive the answer from client
				rw = dis.readUTF();
				if(rw.equals("read"))
				{
					Path path = Paths.get(received);
					if(Files.isReadable(path))
					{
						try {
						File myObj = new File(received);
						myObj.setWritable(false);
						Scanner myReader = new Scanner(myObj);
						String data = "";
						while (myReader.hasNextLine()) {
							data += myReader.nextLine()+"\n";
							System.out.println(data);
							dos.writeUTF(data);
							
							//System.out.println("hello");
						}
						
						Thread.sleep(10000);
						myObj.setWritable(true);
						myReader.close();
						} catch (FileNotFoundException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
						} catch (InterruptedException ex) {
							Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
					else
					{
						dos.writeUTF("Someone else is writing the file, try later!!");
					}
				}
				else if(rw.equals("write"))
				{
					Path path = Paths.get(received);
					if(Files.isWritable(path))
					{
						try {
						File myObj = new File(received);
						myObj.setReadable(false);
						FileWriter myWriter = new FileWriter(received);
						myWriter.write("New Data written in the file");
						myObj.setWritable(false);
						Thread.sleep(10000);
						myObj.setWritable(true);
						myObj.setReadable(true);
						myWriter.close();
						System.out.println("Successfully wrote to the file.");
						dos.writeUTF("Successfully wrote to the file.");
						} catch (IOException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
						}catch(InterruptedException ex){
							Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
					else
					{
						dos.writeUTF("Someone else is sccessing the file, try later!!");
					}
				}
                                
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try
		{
			// closing resources
			this.dis.close();
			this.dos.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

