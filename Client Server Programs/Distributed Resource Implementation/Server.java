import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

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
	
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
    static int count = 1;
    int id;
    static Queue<Integer> qa = new LinkedList<>();
    static Queue<Integer> qb = new LinkedList<>();
    static Queue<Integer> qc = new LinkedList<>();
	static HashMap<String,Integer> res = new HashMap<String,Integer>();
        HashMap<String,Integer> alloc = new HashMap<String,Integer>();

	// Constructor
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
	{
		this.s = s;
		this.dis = dis;
		this.dos = dos;
                synchronized(this)
                {
                    this.id = count;
                    count++;
                }
                
                //q.add(this.id);
	}
        
        
    void initialiseRes()
    {
        res.put("a",10);
        res.put("b",10);
        res.put("c",10);
    }

	@Override
	public void run()
	{
		String received;
		String toreturn;
        int flag=0;
        synchronized(this)
        {
            if(count==2)
            {
                System.out.println("entered!!");
                initialiseRes();
            }
        }
		while (flag==0 && true)
		{
			try {               
				// Ask user what he wants
				dos.writeUTF("Client id : "+this.id+"\nEnter :\nAlloc\nDealloc\nExit\nAvailable : "+res.values()+"\nqueue a: "+qa+"\nqueue b: "+qb+"\nqueue c: "+qc);
                                
				
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
                else if(received.equals("Alloc"))
                {
                    dos.writeUTF("Which resource?");
                    String resname = dis.readUTF();
                    dos.writeUTF("Number of resources?");
                    String rescount = dis.readUTF();
                    int no = Integer.parseInt(rescount);  
                    if(res.get(resname)>=no)
                    {
                        dos.writeUTF("Resource Allocating\nEnter ok");
                        String opt = dis.readUTF();
                        synchronized(this)
                        {
                            if(alloc.containsKey(resname))
                            {
                                int old = alloc.get(resname);
                                alloc.put(resname,old+no);
                            }
                            else
                            {
                                alloc.put(resname,no);
                            }
                            res.put(resname, res.get(resname)-no);
                        }
                    }
                    else
                    {
                        dos.writeUTF("Not Enough Resources available : Enter :\nWait\nExit\nContinue");
                        String opt = dis.readUTF();
                        if(opt.equals("Wait"))
                        {
                            switch(resname)
                            {
                                case "a":{
                                    qa.add(id);
                                    synchronized(this)
                                    {
                                        while(res.get(resname)<no || qa.peek()!=id)
                                        {
                                            // System.out.println(id + " " + qa.peek()+" r "+res.get(resname));
                                        }
                                        System.out.println(id + " " + qa.peek()+" r "+res.get(resname));
                                    
                                    
                                        if(alloc.containsKey(resname))
                                        {
                                            int old = alloc.get(resname);
                                            alloc.put(resname,old+no);
                                        }
                                        else
                                        {
                                            alloc.put(resname,no);
                                        }
                                        res.put(resname, res.get(resname)-no);
                                    }
                                    qa.remove();
                                    
                                break;}
                                case "b":{
                                    qb.add(id);
                                    synchronized(this)
                                    {
                                        while(res.get(resname)<no || qb.peek()!=id)
                                        {

                                        }
                                        System.out.println(id + " " + qb.peek()+" r "+res.get(resname));
                                    
                                        if(alloc.containsKey(resname))
                                        {
                                            int old = alloc.get(resname);
                                            alloc.put(resname,old+no);
                                        }
                                        else
                                        {
                                            alloc.put(resname,no);
                                        }
                                        res.put(resname, res.get(resname)-no);
                                    }
                                    qb.remove();
                                    
                                break;}
                                case "c":{
                                    qc.add(id);
                                    synchronized(this)
                                    {
                                        while(res.get(resname)<no || qc.peek()!=id)
                                        {

                                        }
                                        System.out.println(id + " " + qc.peek()+" r "+res.get(resname));
                                    
                                        if(alloc.containsKey(resname))
                                        {
                                            int old = alloc.get(resname);
                                            alloc.put(resname,old+no);
                                        }
                                        else
                                        {
                                            alloc.put(resname,no);
                                        }
                                        res.put(resname, res.get(resname)-no);
                                    }
                                    qc.remove();
                                    
                                break;}
                                default : break;
                                
                            }
                            
                        }
                        else if(opt.equals("Exit"))
                        {
                                System.out.println("Client " + this.s + " sends exit...");
                                System.out.println("Closing this connection.");
                                this.s.close();
                                System.out.println("Connection closed");
                                flag=1;
                                break;
                        }
                        else{

                        }
                    }
                }
                else if(received.equals("Dealloc"))
                {
                    dos.writeUTF("Enter Resource Name : ");
                    String resname = dis.readUTF();
                    dos.writeUTF("Enter Number : ");
                    String rescount = dis.readUTF();
                    int no = Integer.parseInt(rescount); 
                    synchronized(this)
                    {
                        res.put(resname,res.get(resname)+no);
                    }
                    alloc.put(resname,alloc.get(resname)-no);
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
